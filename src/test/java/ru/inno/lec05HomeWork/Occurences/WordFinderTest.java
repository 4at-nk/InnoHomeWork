package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordFinderTest {

    @Test
    void find() {
        List<String> res = WordFinder.find(TestExample.getSentencesList(),
                TestExample.getWordToFind().toUpperCase());

        assertEquals(TestExample.getGoodSentencesList().size(), res.size(),
                "Количество корректных предложений != количество найденных");

        for (int i = 0; i < TestExample.getGoodSentencesList().size(); ++i) {
            assertEquals(TestExample.getGoodSentencesList().get(i),
                    res.get(i));
        }
    }

    @Test
    void nullSentencesTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> WordFinder.find(null, TestExample.getWordToFind()));
    }

    @Test
    void nullWordTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> WordFinder.find(TestExample.getSentencesList(), null));
    }
}