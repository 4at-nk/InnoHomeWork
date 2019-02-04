package ru.inno.lec06HomeWork.JSSaver;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для парсинга JSON
 *
 * @author FOAT
 * @version 1.0  23.01.2019
 */
class JSonParser {

    /**
     * Маска для поиска массивов в JSON-файле
     */
    private final static Pattern arrPattern = Pattern.compile("\".*?\".*?\\[(.|\r|\n)*?]");

    /**
     * Маска для поиска объектов в JSON-файле
     */
    private final static Pattern objectPattern = Pattern.compile("\".*?\" : \r\n\\{(.|\r|\n)*?}");

    /**
     * Маска для поиска примитивов, String и enum-полей
     */
    private final static Pattern primitiveAndEnumPattern = Pattern.compile("\".*?\".*?\".*?\"");

    /**
     * Парсит JSON-текст и формирует наборы данных по полям-массивам
     *
     * @param text JSON-текст
     * @return набор данных массивов (имя поля, список значений)
     */
    static Map<String, List<String>> arrayDataParse(String text) {
        Map<String, List<String>> arrayData = new TreeMap<>();

        Matcher matcher = arrPattern.matcher(text);
        while (matcher.find()) {
            String arrayString = text.substring(matcher.start(), matcher.end());
            int firstQuote = arrayString.indexOf('\"') + 1;
            int secondQuote = arrayString.indexOf('\"', firstQuote + 1);
            String arrayName = arrayString.substring(firstQuote, secondQuote);

            secondQuote++;
            List<String> list = new ArrayList<>();
            while ((firstQuote = arrayString.indexOf('\"', secondQuote)) != -1) {
                secondQuote = arrayString.indexOf('\"', firstQuote + 1);

                String element = arrayString.substring(firstQuote + 1, secondQuote);
                list.add(element);
                secondQuote++;
            }

            arrayData.put(arrayName, list);
        }

        return arrayData;
    }

    /**
     * Парсит JSON-текст и формирует наборы данных по объектам
     *
     * @param text JSON-текст
     * @return набор данных объектов (имя поля, текст в формате JSON)
     */
    static Map<String, String> objectDataParse(String text) {
        Map<String, String> objectData = new TreeMap<>();

        Matcher matcher = objectPattern.matcher(text);
        while (matcher.find()) {
            String objectString = text.substring(matcher.start(), matcher.end());
            int firstQuote = objectString.indexOf('\"') + 1;
            int secondQuote = objectString.indexOf('\"', firstQuote + 1);
            String objectName = objectString.substring(firstQuote, secondQuote);
            int firstBrace = objectString.indexOf("{");
            String obgectDataString = objectString.substring(firstBrace);

            objectData.put(objectName, obgectDataString);
        }

        return objectData;
    }

    /**
     * Парсит JSON-текст и формирует наборы данных примитивных полей и String
     * и enum-полей
     *
     * @param text     JSON-текст
     * @param enumData набор enum-данных (имя поля, (имя класса enum, значение)), который будет заполнен
     * @return набор примитивных данных и String (имя поля, значение)
     */
    static Map<String, String> primitiveAndEnumParse(String text, Map<String, Pair<String, String>> enumData) {
        Map<String, String> simpleData = new TreeMap<>();
        enumData.clear();

        Matcher matcher = primitiveAndEnumPattern.matcher(text);
        while (matcher.find()) {
            String dataLine = text.substring(matcher.start(), matcher.end());
            int firstQuote = dataLine.indexOf('\"') + 1;
            int secondQuote = dataLine.indexOf('\"', firstQuote + 1);
            int thirdQuote = dataLine.indexOf('\"', secondQuote + 1) + 1;
            int fourthQuote = dataLine.indexOf('\"', thirdQuote + 1);

            String fieldName = dataLine.substring(firstQuote, secondQuote);
            String fieldValue = dataLine.substring(thirdQuote, fourthQuote);

            int enumDelimeterIndex;
            if ((enumDelimeterIndex = fieldName.indexOf('%')) != -1) {      //если enum
                String enumFieldName = fieldName.substring(enumDelimeterIndex + 1);
                String enumClassName = fieldName.substring(0, enumDelimeterIndex);

                enumData.put(enumFieldName, new Pair<>(
                        enumClassName, fieldValue));
            } else {
                //чтобы не перезаписались данные полей класса верхнего уровня при совпадении имён полей
                simpleData.putIfAbsent(fieldName, fieldValue);
            }
        }

        return simpleData;
    }
}
