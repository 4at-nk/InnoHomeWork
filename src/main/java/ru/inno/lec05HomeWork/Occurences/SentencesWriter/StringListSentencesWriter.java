package ru.inno.lec05HomeWork.Occurences.SentencesWriter;

import java.util.ArrayList;
import java.util.List;

public class StringListSentencesWriter implements SentencesWriter {

    private List<String> stringList = new ArrayList<>();

    @Override
    public void init(String destination) {
        close();
    }

    @Override
    public void write(String sentence) {
        if (sentence == null) {
            throw new NullPointerException("На запись в файл подана null-строка");
        }

        stringList.add(sentence);
    }

    @Override
    public void close() {
        stringList.clear();
    }

    public List<String> getStringList() {
        return stringList;
    }
}
