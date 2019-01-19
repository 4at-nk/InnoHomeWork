package ru.inno.lec04HomeWork.Builders;

import ru.inno.RangedRandom;

/**
 * Класс для генерации случайного слова по пункту 2 домашнего задания
 *
 * @author FOAT
 * @version 1.0  18.01.2019
 */
public class WordBuilder {

    private static final int minSymbol = 'a';
    private static final int maxSymbol = 'z';
    private static final int minSymbolCount = 1;
    private static final int maxSymbolCount = 15;

    /**
     * Генерирует случайное слово
     *
     * @return случайное слово
     */
    public static String build() {
        //определяем количество букв
        int symbolCount = 0;
        try {
            symbolCount = RangedRandom.getRandInt(minSymbolCount,
                    maxSymbolCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //заполняем случайными буквами
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < symbolCount; ++i) {
            try {
                word.append((char) RangedRandom.getRandInt(minSymbol, maxSymbol));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return word.toString();
    }
}
