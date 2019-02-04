package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SentencesWriterTest {

    private static BufferedWriter bufferedWriter;

    @BeforeAll
    static void setUp() {
        bufferedWriter = Mockito.mock(BufferedWriter.class);
    }

    @Test
    void bufferedWriterConstructorTest() throws IOException {
        try (SentencesWriter sentencesWriter = new SentencesWriter(bufferedWriter)) {
            assertThrows(NullPointerException.class, () -> sentencesWriter.write(null));
            assertDoesNotThrow(() -> sentencesWriter.write("test"));
        }
        assertThrows(NullPointerException.class, () -> new SentencesWriter((BufferedWriter) null));
    }

    @Test
    void filenameConstructorTest() throws IOException {
        File file = File.createTempFile("temp", ".txt");
        file.deleteOnExit();
        try (SentencesWriter sentencesWriter = new SentencesWriter(file.getAbsolutePath())) {

        }
    }

    @AfterAll
    static void tearDown() throws IOException {
        Mockito.verify(bufferedWriter, Mockito.times(1)).close();
    }
}