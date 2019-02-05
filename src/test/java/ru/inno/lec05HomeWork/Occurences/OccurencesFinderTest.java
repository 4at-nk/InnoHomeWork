package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.FileSentencesWriter;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.SentencesWriter;

import java.io.File;
import java.io.IOException;

class OccurencesFinderTest {

    private static String[] FILES = new String[100];
    private static final String[] WORDS = {};
    private static SentencesWriter SENTENCES_WRITER = Mockito.mock(FileSentencesWriter.class);

    @BeforeAll
    static void setUp() throws IOException {
        for (int i = 0; i < FILES.length; ++i) {
            File f = File.createTempFile("temp", ".txt");
            f.deleteOnExit();
            FILES[i] = f.getAbsolutePath();
        }

        Mockito.doNothing().when(SENTENCES_WRITER).init(Mockito.anyString());
        Mockito.doNothing().when(SENTENCES_WRITER).write(Mockito.anyString());
    }

    @Test
    void getOccurences100FilesTest() throws Exception {
        ThreadLauncher threadLauncher = Mockito.mock(ThreadLauncher.class);

        try (OccurencesFinder finder = new OccurencesFinder(SENTENCES_WRITER)) {
            finder.setThreadLauncher(threadLauncher);
            finder.getOccurences(FILES, WORDS, null);
        }

        // проверяем, что запутилось столько потоков, сколько и файлов
        Mockito.verify(threadLauncher, Mockito.times(FILES.length))
                .launch(Mockito.any(FileReadThread.class));
    }

    @Test
    void getOccurences0FilesTest() throws Exception {
        ThreadLauncher threadLauncher = Mockito.mock(ThreadLauncher.class);

        try (OccurencesFinder finder = new OccurencesFinder(SENTENCES_WRITER)) {
            finder.setThreadLauncher(threadLauncher);
            finder.getOccurences(new String[0], WORDS, null);
        }

        // проверяем, что запутилось столько потоков, сколько и файлов, то есть 0
        Mockito.verify(threadLauncher, Mockito.times(0))
                .launch(Mockito.any(FileReadThread.class));
    }
}