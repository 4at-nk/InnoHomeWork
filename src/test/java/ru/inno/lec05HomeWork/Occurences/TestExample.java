package ru.inno.lec05HomeWork.Occurences;

import java.util.ArrayList;
import java.util.List;

/**
 * класс содержащий тестовый набор предложений
 * и правильный результат
 */
class TestExample {
    private static final String WORD_TO_FIND = "конь";
    private static final List<String> SENTENCES_LIST =
            new ArrayList<String>() {{
                add("Конь скачет по полю.");
                add("Олень скачет по полю.");
                add("Коня не видать.");
                add("Оленя не видать.");
                add("Нужно раздобыть коня.");
                add("Нужно раздобыть оленя.");
                add("Вдали виднелся конь.");
                add("Вдали виднелся олень.");
                add("Конь.");
                add("Олень.");
                add("Кконь.");
                add("Коньь.");
                add("Бежит конь по полю.");
                add("Бежит олень по полю.");
                add("Олень, конь, олень.");
            }};
    private static final List<String> GOOD_SENTENCES_LIST =
            new ArrayList<String>() {{
                add("Конь скачет по полю.");
                add("Вдали виднелся конь.");
                add("Конь.");
                add("Бежит конь по полю.");
                add("Олень, конь, олень.");
            }};

    static String getWordToFind() {
        return WORD_TO_FIND;
    }

    static List<String> getSentencesList() {
        return SENTENCES_LIST;
    }

    static List<String> getGoodSentencesList() {
        return GOOD_SENTENCES_LIST;
    }
}
