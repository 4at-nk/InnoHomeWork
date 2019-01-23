package ru.inno.lec06HomeWork.JSSaver;

import javafx.util.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Класс для сериализации объектов в формате JSON
 *
 * @author FOAT
 * @version 1.0  23.01.2019
 */
public class JSonSerializer implements Serializer {

    /**
     * Множество хранящее все примитивные типы и String
     */
    private static final Set<String> primitives = new TreeSet<>(
            Arrays.asList("java.lang.Integer", "java.lang.Long",
                    "java.lang.Short", "java.lang.Character", "java.lang.Byte",
                    "java.lang.Double", "java.lang.Float", "java.lang.Boolean", "java.lang.String"));

    /**
     * Сериализует объект в файл в формате JSON
     *
     * @param object объект для сериализации
     * @param file   имя файла, в котором сохранится сериализация
     * @throws IOException при проблемах при открытии файла на запись
     */
    @Override
    public void serialize(Object object, String file) throws IOException {
        String classString = objectToString(object);

        try (Writer writer = new FileWriter(file)) {
            writer.write(classString);
        } catch (IOException e) {
            throw new IOException("Ошибка при записи в файл " + file + "!");
        }
    }

    /**
     * Десериализирует объект из файла в формате JSON
     *
     * @param file имя файла, в котором хранится сериальзация
     * @return десериализованный объект
     * @throws Exception при проблемах при открытии файла на чтение и несоответствии типов
     */
    @Override
    public Object deSerialize(String file) throws Exception {
        String text;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            //считываем весь текст из файла
            StringBuilder sb = new StringBuilder();
            while ((text = reader.readLine()) != null) {
                sb.append(text).append("\r\n");
            }
            text = sb.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл " + file + " не найден!");
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении из файла " + file + "!");
        }

        return objectFromString(text);
    }

    /**
     * Формирует объект из текста в формате JSON
     *
     * @param text текст в формате JSON
     * @return сформированный объект
     */
    private static Object objectFromString(String text) throws Exception {
        Map<String, Pair<String, String>> enumData = new TreeMap<>();
        Map<String, String> simpleData = JSonParser.primitiveAndEnumParse(text, enumData);
        Map<String, List<String>> arrayData = JSonParser.arrayDataParse(text);
        Map<String, String> objectData = JSonParser.objectDataParse(text);

        return initObject(simpleData, enumData, arrayData, objectData);
    }

    /**
     * Формирует объект из набора данных
     *
     * @param simpleData набор примитивных данных и String (имя поля, значение)
     * @param enumData   набор enum-данных (имя поля, (имя класса enum, значение))
     * @param arrayData  набор данных массивов (имя поля, список значений)
     * @param objectData набор данных объектов (имя поля, текст в формате JSON)
     * @return сформированный объект
     */
    private static Object initObject(Map<String, String> simpleData,
                                     Map<String, Pair<String, String>> enumData,
                                     Map<String, List<String>> arrayData,
                                     Map<String, String> objectData) throws Exception {
        //считываем имя класса
        String className = simpleData.get("class");
        Class clazz;
        Object object;
        try {
            clazz = Class.forName(className);
            object = clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new Exception("Не удалось создать объект класса " + className);
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            //инициализируем
            if (field.getType().isArray()) {                            //массивы
                initArray(object, field, arrayData);
            } else if (field.getType().isEnum()) {                      //enum
                initEnum(object, field, enumData);
            } else {
                String fieldValue = simpleData.get(field.getName());
                if (!setPrimitive(object, field, fieldValue)) {         //примитивы и String
                    String text = objectData.get(field.getName());      //если не оказался примитивом, то объект
                    field.set(object, objectFromString(text));          //почти рекурсия
                }
            }
        }

        return object;
    }

    /**
     * Инициализирует поля-массивы объекта
     *
     * @param object    объект десириализации
     * @param field     поле-массив объекта
     * @param arrayData набор данных по массивам (имя поля, список значений)
     */
    private static void initArray(Object object, Field field, Map<String,
            List<String>> arrayData) throws IllegalAccessException {
        //список элементов нужного поля-массива
        List<String> list = arrayData.get(field.getName());

        //тип массива
        Class arrayClass = field.getType();
        //тип элементов массива
        Class componentType = arrayClass.getComponentType();
        //выделяем память под элементы
        Object[] array = (Object[]) Array.newInstance(componentType, list.size());
        //копируем
        System.arraycopy(list.toArray(), 0, array, 0, list.size());

        field.set(object, array);
    }

    /**
     * Инициализирует enum-поля объекта
     *
     * @param object   объект десириализации
     * @param field    enum-поле объекта
     * @param enumData набор enum-данных (имя поля, (имя класса enum, значение))
     */
    private static void initEnum(Object object, Field field,
                                 Map<String, Pair<String, String>> enumData) throws ClassNotFoundException, IllegalAccessException {
        //выбираем по имени enum-поля
        Pair pair = enumData.get(field.getName());

        //класс enum-поля
        Class enumClass = Class.forName(pair.getKey().toString());
        //устанавливаем значения
        Enum anEnum = Enum.valueOf(enumClass, pair.getValue().toString());

        field.set(object, anEnum);
    }

    /**
     * Формирует текст в формате JSON из объекта
     *
     * @param object объект для сериализации
     * @return текст в формате JSON
     */
    private static String objectToString(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder toWrite = new StringBuilder();
        //сохраняем поле class, не являющимся полем объекта, а являющимся его именем
        toWrite.append("{\r\n").append(quoteWrap("class")).append(" : ").append(
                quoteWrap(clazz.getName())).append(",\r\n");

        //идём по всем полям объекта
        for (int i = 0; i < fields.length; ++i) {
            fields[i].setAccessible(true);

            String fieldName = fields[i].getName();
            String fieldValue = "";
            if (fields[i].getType().isArray()) {                    //поля-массивы
                fieldValue = getArrayValue(object, fields[i]);
            } else if (fields[i].getType().isEnum()) {              //enum-поля

                try {
                    //нужно сохранить класс enum'а для его десериализации
                    fieldName = fields[i].get(object).getClass().getName() + "%" + fieldName;
                    fieldValue = quoteWrap(fields[i].get(object).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    if (!isPrimitive(fields[i].get(object).getClass().getName())) {
                        //если не примитивы и String, то объект (рекурсия)
                        fieldValue = "\r\n" + objectToString(fields[i].get(object));
                    } else {
                        fieldValue = quoteWrap(fields[i].get(object).toString());       //примитивы и String
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            toWrite.append(quoteWrap(fieldName)).append(" : ").append(fieldValue);

            if (i != fields.length - 1) {
                toWrite.append(",\r\n");
            }
        }
        toWrite.append("\r\n}");

        return toWrite.toString();
    }

    /**
     * По имени типа определяет примитив это (или String) или нет
     *
     * @param type имя типа
     * @return true - примитив или String, false - не приметив и не String
     */
    private static boolean isPrimitive(String type) {
        return primitives.contains(type);
    }

    /**
     * Обрамляет текст ковычками
     *
     * @param s текст
     * @return текст с ковычками
     */
    private static String quoteWrap(String s) {
        return "\"" + s + "\"";
    }

    /**
     * Формирует текст в формате JSON из массивов
     *
     * @param object объект, чьим полем является массив
     * @param field  поле-массив
     * @return текст в формате JSON
     */
    private static String getArrayValue(Object object, Field field) {
        Object[] array = new Object[0];
        try {
            array = (Object[]) field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        StringBuilder arrayValuesString = new StringBuilder();
        arrayValuesString.append("[\r\n");
        for (int j = 0; j < array.length; ++j) {
            arrayValuesString.append("\t").append(quoteWrap(array[j].toString()));

            if (j != array.length - 1) {
                arrayValuesString.append(",\r\n");
            }
        }
        arrayValuesString.append("\r\n]");

        return arrayValuesString.toString();
    }

    /**
     * Устанавливает значение в примитивном или String поле
     *
     * @param object объект, в чье поле устанавливаем примитив
     * @param field  поле-примитив или String
     * @param value  значение, которое нужно установить
     * @return true - значение установлено, false - поле не является примитивом или String
     */
    private static boolean setPrimitive(Object object, Field field, String value) throws Exception {
        String fieldType = field.getType().getTypeName();
        switch (fieldType) {
            case "int":
                field.set(object, Integer.valueOf(value));
                break;
            case "long":
                field.set(object, Long.valueOf(value));
                break;
            case "short":
                field.set(object, Short.valueOf(value));
                break;
            case "char":
                field.set(object, value.charAt(0));
                break;
            case "byte":
                field.set(object, Byte.valueOf(value));
                break;
            case "double":
                field.set(object, Double.valueOf(value));
                break;
            case "float":
                field.set(object, Float.valueOf(value));
                break;
            case "boolean":
                field.set(object, Boolean.valueOf(value));
                break;
            case "java.lang.String":
                field.set(object, value);
                break;
            default:
                return false;
        }

        return true;
    }
}
