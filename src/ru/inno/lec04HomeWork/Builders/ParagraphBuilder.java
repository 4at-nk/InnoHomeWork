package ru.inno.lec04HomeWork.Builders;

import ru.inno.RangedRandom;

/**
 * Класс для генерации параграфа
 *
 * @author FOAT
 * @version 1.0  18.01.2019
 */
public class ParagraphBuilder {

    private final static int minSentencesCount = 1;
    private final static int maxSentencesCount = 20;

    /**
     * Генерирует параграф
     *
     * @param words набор слов из п. 7
     * @param probability вероятность из п.7
     * @return сгенерированный параграф
     * @throws Exception {@link ru.inno.lec04HomeWork.Builders.SentenceBuilder#SentenceBuilder(String[], int)}
     */
    public static String build(String[] words, int probability) throws Exception {
        //количество предложений в параграфе
        final int sentencesCount = RangedRandom.getRandInt(minSentencesCount,
                maxSentencesCount);

        //генерируем предложения
        SentenceBuilder sentenceBuilder = new SentenceBuilder(words, probability);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < sentencesCount; ++i) {
            res.append(sentenceBuilder.build());
        }

        //заканчиваем параграф
        return res + "\r\n";
    }
}
