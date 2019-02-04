package ru.inno.lec04HomeWork.Builders;

import java.io.FileWriter;
import java.io.Writer;

/**
 * Класс для генерации файлов и их записи
 *
 * @author FOAT
 * @version 1.0  18.01.2019
 */
public class FileBuilder {

    /**
     * Формирует весь текст и записывает его в файл
     *
     * @param name полное имя файла
     * @param size количество параграфов
     * @param words массив слов из п. 7
     * @param probability вероятность из п. 7
     * @throws Exception {@link ru.inno.lec04HomeWork.Builders.ParagraphBuilder#build(String[], int)}
     * @throws java.io.IOException в случае проблем с созданием файла или записью в него
     */
    public static void buildFile(String name, int size, String[] words, int probability) throws Exception {
        //формируем параграф
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            res.append(ParagraphBuilder.build(words, probability));
        }

        //запись в файл
        try (Writer writer = new FileWriter(name)) {
            writer.write(res.toString());
        }
    }

    /**
     * Создаёт файлы в указанной директории
     *
     * @param path директория, в которой нужно создать файлы
     * @param n количество файлов
     * @param size количество абзацев в каждом файле
     * @param words массив слов из п. 7
     * @param probability вероятность из п. 7
     * @throws Exception {@link ru.inno.lec04HomeWork.Builders.FileBuilder#buildFile(String, int, String[], int)}
     */
    public static void getFiles(String path, int n, int size, String[] words, int probability) throws Exception {
        for (int i = 0; i < n; ++i) {
            buildFile(path + "\\" + i + ".txt", size, words, probability);
        }
    }
}
