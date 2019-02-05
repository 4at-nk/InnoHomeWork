package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.StringListSentencesWriter;

import java.io.*;
import java.util.List;

class OccurencesFinderStreamedTest {

    private StringListSentencesWriter stringListSentencesWriter =
            new StringListSentencesWriter();
    private String[] WORDS = {TestExample.getWordToFind()};
    private String[] SOURCES = new String[1];

    @BeforeEach
    void setUp() throws IOException {
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
    void getOccurences() throws Exception {
        try (OccurencesFinderStreamed occurencesFinderStreamed =
                     new OccurencesFinderStreamed(stringListSentencesWriter)) {
            occurencesFinderStreamed.getOccurences(SOURCES, WORDS, null);

            List<String> resList = stringListSentencesWriter.getStringList();

            Assertions.assertEquals(TestExample.getGoodSentencesList().size(),
                    resList.size(),
                    "Количество корректных предложений != количество найденных");

            for (int i = 0; i < TestExample.getGoodSentencesList().size(); ++i) {
                Assertions.assertEquals(TestExample.getGoodSentencesList().get(i),
                        resList.get(i));
            }
        }
    }
}