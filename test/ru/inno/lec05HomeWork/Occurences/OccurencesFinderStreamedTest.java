package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OccurencesFinderStreamedTest {

    private File RES_FILE;
    private String[] WORDS = {TestExample.getWordToFind()};
    private String[] SOURCES = new String[1];

    @BeforeEach
    void setUp() throws IOException {
        RES_FILE = File.createTempFile("temp", ".txt");
        RES_FILE.deleteOnExit();

        File sourceFile = File.createTempFile("temp", ".txt");
        sourceFile.deleteOnExit();
        SOURCES[0] = sourceFile.getAbsolutePath();
        try (Writer writer = new FileWriter(sourceFile.getAbsolutePath(), false)) {
            List<String> sentencesList = TestExample.getSentencesList();
            for (String s : sentencesList) {
                writer.write(s);
            }
        }
    }

    @Test
    void getOccurences() throws IOException {
        OccurencesFinderStreamed
                .getOccurences(SOURCES, WORDS, RES_FILE.getAbsolutePath());

        List<String> resList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(RES_FILE.getAbsolutePath()))) {
            String s;
            while ((s = reader.readLine()) != null) {
                resList.add(s);
            }
        }

        assertEquals(TestExample.getGoodSentencesList().size(),
                resList.size(),
                "Количество корректных предложений != количество найденных");

        for (int i = 0; i < TestExample.getGoodSentencesList().size(); ++i) {
            assertEquals(TestExample.getGoodSentencesList().get(i),
                    resList.get(i));
        }
    }
}