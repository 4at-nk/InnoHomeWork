package ru.inno.lec05HomeWork.Occurences.SentencesWriter;

import java.io.IOException;

public interface SentencesWriter {
    void init(String destination) throws IOException;
    void write(String sentence) throws IOException;
    void close() throws IOException;
}
