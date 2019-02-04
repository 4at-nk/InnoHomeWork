package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

class FileReadThreadTest {

    private static SentencesWriter SENTENCES_WRITER;
    private static Random RANDOM = new Random();
    private static File FILE;
    private static String[] WORDS = new String[100];

    @BeforeAll
    static void setUp() throws IOException {
        SENTENCES_WRITER = Mockito.mock(SentencesWriter.class);
        Mockito.doNothing().when(SENTENCES_WRITER).write(Mockito.anyString());

        FILE = File.createTempFile("temp", ".txt");
        FILE.deleteOnExit();
        try (Writer writer = new FileWriter(FILE.getAbsolutePath())) {
            writer.write("text");
        }

        for (int i = 0; i < WORDS.length; ++i) {
            WORDS[i] = "" + RANDOM.nextInt();
        }
    }

    @Test
    void run100WordsTest() throws InterruptedException {
        ThreadLauncher threadLauncher = Mockito.mock(ThreadLauncher.class);
        FileReadThread fileReadThread = new FileReadThread(FILE.getAbsolutePath(), WORDS, SENTENCES_WRITER);
        fileReadThread.setThreadLauncher(threadLauncher);

        fileReadThread.start();
        fileReadThread.join();

        Mockito.verify(threadLauncher, Mockito.times(WORDS.length))
                .launch(Mockito.any(WordFindThread.class));
    }

    @Test
    void run0WordsTest() throws InterruptedException {
        //String[] nullCountArr = new String[0];
        ThreadLauncher threadLauncher = Mockito.mock(ThreadLauncher.class);
        FileReadThread fileReadThread = new FileReadThread(FILE.getAbsolutePath(),
                new String[0], SENTENCES_WRITER);
        fileReadThread.setThreadLauncher(threadLauncher);

        fileReadThread.start();
        fileReadThread.join();

        Mockito.verify(threadLauncher, Mockito.times(0))
                .launch(Mockito.any(WordFindThread.class));
    }
}