package ru.inno.lec05HomeWork.Occurences;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для поиска предложений, в которых встречается искомое слово
 *
 * @author FOAT
 * @version 1.0  22.01.2019
 */
class WordFinder {

    /**
     * Находит предложения, в которых встречается искомое слово
     *
     * @param sentences предложения, которые нужно проверить
     * @param word      слово, которое нужно найти
     * @return список предложений, в которых встречается искомое слово
     */
    static List<String> find(List<String> sentences, String word) {
        String w = word.substring(0, 1);
        String ord = word.substring(1);
        //маска для нахождения искомого слова
        String mask =
                //если слово не вначале предложения, то оно начинается с маленькой буквы и
                //перед ним должен быть пробел или иной возможный символ
                "(([ ('\"\\[{/]" + w.toLowerCase() + ")"
                        //или, если слово идёт первым, то оно начинается с заглавной буквы
                        //и перед ним ничего нет
                        + "|(" + w.toUpperCase() + "))"
                        //оставшаяся часть слова и после неё должна быть не буква
                        + ord.toLowerCase() + "[^a-zа-яё]";
        Pattern pattern = Pattern.compile(mask);
        Matcher matcher;

        List<String> result = new ArrayList<>();
        for (String sentence : sentences) {
            matcher = pattern.matcher(sentence);
            if (matcher.find()) {
                result.add(sentence);
            }
        }

        return result;
    }
}
