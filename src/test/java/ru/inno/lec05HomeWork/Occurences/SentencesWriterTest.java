package ru.inno.lec05HomeWork.Occurences;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.inno.lec05HomeWork.Occurences.SentencesWriter.FileSentencesWriter;

import java.io.*;

class SentencesWriterTest {

    private static FileWriter writer;

    @BeforeAll
    static void setUp() {
        writer = Mockito.mock(FileWriter.class);
    }

    @Test
    void bufferedWriterConstructorTest() throws IOException {
        try (FileSentencesWriter sentencesWriter = new FileSentencesWriter(writer)) {
            Assertions.assertThrows(NullPointerException.class, () -> sentencesWriter.write(null));
            Assertions.assertDoesNotThrow(() -> sentencesWriter.write("test"));
        }
        Assertions.assertThrows(NullPointerException.class, () -> new FileSentencesWriter(null));
    }

    @Test
    void filenameConstructorTest() throws IOException {
        File file = File.createTempFile("temp", ".txt");
        file.deleteOnExit();
        try (FileSentencesWriter sentencesWriter = new FileSentencesWriter()) {
            sentencesWriter.init(file.getAbsolutePath());
        }
    }

    @AfterAll
    static void tearDown() throws IOException {
        // проверяем что метод close() вызвался ровно 1 раз
        Mockito.verify(writer, Mockito.times(1)).close();
    }
}