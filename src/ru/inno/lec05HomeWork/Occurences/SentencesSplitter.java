package ru.inno.lec05HomeWork.Occurences;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс разделяющий текст на предложения
 *
 */
public class SentencesSplitter {

    /**
     * Маска для нахождения предложения: Заглавная буква, символ конца предложения
     * и между ними сколь угодно любых символов не равных символу конца предложения
     */
    private final static String mask = "[A-ZА-ЯЁ][^.?!…]*?[.?!…]";
    /**
     * объект для работы с регулярными выражениями
     */
    private final static Pattern pattern = Pattern.compile(mask);

    /**
     * Разделяет текст на предложения
     *
     * @param text исходный текст
     * @return список предложений
     */
    public static List<String> getSplittedText(String text) {
        Matcher matcher = pattern.matcher(text);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(text.substring(matcher.start(), matcher.end()));
        }

        return result;
    }
}
