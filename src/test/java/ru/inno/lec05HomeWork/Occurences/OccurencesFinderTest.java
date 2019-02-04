package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

class OccurencesFinderTest {

    private static String[] FILES = new String[100];
    private static final String[] WORDS = {};
    private static File resFile;

    @BeforeAll
    static void setUp() throws IOException {
        for (int i = 0; i < FILES.length; ++i) {
            File f = File.createTempFile("temp", ".txt");
            f.deleteOnExit();
            FILES[i] = f.getAbsolutePath();
        }

        resFile = File.createTempFile("resTemp", ".txt");
        resFile.deleteOnExit();
    }

    @Test
    void getOccurences100FilesTest() throws IOException, InterruptedException {
        ThreadLauncher threadLauncher = Mockito.mock(ThreadLauncher.class);
        OccurencesFinder.setThreadLauncher(threadLauncher);
        OccurencesFinder.getOccurences(FILES, WORDS, resFile.getAbsolutePath());

        Mockito.verify(threadLauncher, Mockito.times(FILES.length))
                .launch(Mockito.any(FileReadThread.class));
    }

    @Test
    void getOccurences0FilesTest() throws IOException, InterruptedException {
        ThreadLauncher threadLauncher = Mockito.mock(ThreadLauncher.class);
        OccurencesFinder.setThreadLauncher(threadLauncher);
        OccurencesFinder.getOccurences(new String[0], WORDS, resFile.getAbsolutePath());

        Mockito.verify(threadLauncher, Mockito.times(0))
                .launch(Mockito.any(FileReadThread.class));
    }
}