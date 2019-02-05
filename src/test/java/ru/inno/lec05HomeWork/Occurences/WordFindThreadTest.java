package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.FileSentencesWriter;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.SentencesWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordFindThreadTest {

    private SentencesWriter SENTENCES_WRITER =
            Mockito.mock(FileSentencesWriter.class);

    @Test
    void run() throws InterruptedException, IOException {
        WordFindThread wordFindThread =
                new WordFindThread(TestExample.getSentencesList(),
                        TestExample.getWordToFind(), SENTENCES_WRITER);

        wordFindThread.start();
        wordFindThread.join();

        // проверяем что записалось правильное количество результатов
        Mockito.verify(SENTENCES_WRITER, Mockito.times(TestExample.getGoodSentencesList().size()))
                .write(Mockito.anyString());

        // проверяем, что метод не пробрасывает исключения IOException
        Mockito.doThrow(IOException.class).when(SENTENCES_WRITER).write(Mockito.anyString());
        assertDoesNotThrow(wordFindThread::run);
    }
}