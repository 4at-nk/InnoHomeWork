package ru.inno.lec04HomeWork.Builders;

import ru.inno.RangedRandom;

/**
 * Класс для генерации предложения
 *
 * @author FOAT
 * @version 1.0  18.01.2019
 */
public class SentenceBuilder {

    private static final int minWordCount = 1;
    private static final int maxWordCount = 15;

    /**
     * вероятность появления запятой после слова в процентах
     */
    private static double commaProbability = 10.0;
    /**
     * набор слов из пункта 7
     */
    private String[] wordSet;
    /**
     * вероятность из пункта 7 в процентах
     */
    private double probabilityPercent;

    /**
     * Конструктор класса
     *
     * @param wordSet     набор слов из п. 7
     * @param probability вероятность из п.7
     * @throws Exception генерируется при некорректной вероятности
     */
    public SentenceBuilder(String[] wordSet, int probability) throws Exception {
        if (wordSet == null) {
            throw new NullPointerException("Неинициализированный массив слов!");
        }

        if (probability < 1) {
            throw new Exception("Некорректная вероятность");
        }

        this.wordSet = wordSet;
        //переводим вероятность в проценты
        this.probabilityPercent = 1.0 / probability * 100.0;
    }

    /**
     * Генерирует строку
     *
     * @return сгенерированная строка
     */
    public String build() {
        //количество слов в строке
        int wordCount = 0;
        try {
            wordCount = RangedRandom.getRandInt(minWordCount,
                    maxWordCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //генерируем слова
        String[] sArr = new String[wordCount];
        for (int i = 0; i < wordCount; ++i) {
            sArr[i] = WordBuilder.build();
        }

        //проверяем сработала ли вероятность из п. 7
        try {
            if (RangedRandom.isItHappen(probabilityPercent)) {
                //случайное слово из массива из п. 7
                String word = wordSet[RangedRandom.getRandInt(0,
                        wordSet.length - 1)];
                //заменяем случайное сгенерированное слово на случайное слово из массива из п. 7
                sArr[RangedRandom.getRandInt(0, sArr.length - 1)] =
                        word;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //делаем заглавной первую букву первого слова
        sArr[0] = sArr[0].substring(0, 1).toUpperCase() + sArr[0].substring(1);

        //формируем результирующую строку
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < wordCount; ++i) {
            res.append(sArr[i]);

            //если слово последнее в предложении, то запятая и пробел не нужны
            if (i == wordCount - 1)
                break;

            //добавляем пробел и, если сработала вероятность, запятую
            res.append(getComma()).append(" ");
        }

        //завершаем строку
        return res + getEndOfSentence() + " ";
    }

    /**
     * Возвращает рандомно один из символов завершения строки
     *
     * @return рандомный символ завершения строки
     */
    private String getEndOfSentence() {
        String[] ends = {".", "!", "?"};

        String res = ends[0];
        try {
            res = ends[RangedRandom.getRandInt(0, ends.length - 1)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Возвращает запятую при срабатывании её вероятности
     *
     * @return пустую строку или с запятой, в зависимости от результата
     */
    private String getComma() {
        String comma = "";

        try {
            if (RangedRandom.isItHappen(commaProbability)) {
                comma = ",";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comma;
    }

    /**
     * Устанавливает новое значение вероятности появления запятой
     *
     * @param commaProbability вероятность в процентах
     * @throws Exception генерируется при некорректной вероятности
     */
    public static void setCommaProbability(double commaProbability) throws Exception {
        if (commaProbability < 0.0 || commaProbability > 100.0) {
            throw new Exception("Некорректная вероятность запятой");
        }

        SentenceBuilder.commaProbability = commaProbability;
    }
}
