package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SentencesSplitterTest {

    private static final String[] SENTENCES = {
            "Предложение! ",
            " некорректное. ",
            "   Предложение побольше? ",
            "    Sadadadasdads sdasdad.     ",
            "Еще большее предложение.",
            "1111111.",
            " Предложение с многоточием…",
            "",
            "Некорректное предложение"
    };

    private static final int[] CORRECT_SENTENCES_INDEXES = {0, 2, 3, 4, 6};

    @Test
    void getSplittedText() {
        StringBuilder text = new StringBuilder();
        for (String s : SENTENCES) {
            text.append(s);
        }

        List<String> sentencesList = SentencesSplitter.getSplittedText(text.toString());
        Assertions.assertEquals(CORRECT_SENTENCES_INDEXES.length, sentencesList.size(),
                "Количество корректных предложений != количество найденных");

        int j = 0;
        for (int i : CORRECT_SENTENCES_INDEXES) {
            Assertions.assertEquals(SENTENCES[i].trim(), sentencesList.get(j++));
        }
    }

    @Test
    void nullStringTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> SentencesSplitter.getSplittedText(null));

        Assertions.assertDoesNotThrow(() -> SentencesSplitter.getSplittedText(""));
    }
}