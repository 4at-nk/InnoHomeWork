package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordFindThreadTest {

    private SentencesWriter SENTENCES_WRITER;

    @BeforeEach
    void setUp() {
        SENTENCES_WRITER = Mockito.mock(SentencesWriter.class);
    }

    @Test
    void run() throws InterruptedException, IOException {
        WordFindThread wordFindThread =
                new WordFindThread(TestExample.getSentencesList(),
                        TestExample.getWordToFind(), SENTENCES_WRITER);

        wordFindThread.start();
        wordFindThread.join();

        Mockito.verify(SENTENCES_WRITER, Mockito.times(TestExample.getGoodSentencesList().size()))
                .write(Mockito.anyString());

        Mockito.doThrow(IOException.class).when(SENTENCES_WRITER).write(Mockito.anyString());
        assertDoesNotThrow(wordFindThread::run);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (SENTENCES_WRITER != null) {
            SENTENCES_WRITER.close();
        }
    }
}