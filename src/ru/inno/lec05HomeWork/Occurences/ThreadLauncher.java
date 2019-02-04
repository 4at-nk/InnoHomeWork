package ru.inno.lec05HomeWork.Occurences;

import java.util.ArrayList;
import java.util.List;

/**
 * класс для запуска набора потоков
 */
class ThreadLauncher {

    /**
     * список потоков
     */
    private List<Thread> threads = new ArrayList<>();

    /**
     * добавление потока и его запуск
     *
     * @param thread экземпляр потока
     */
    void launch(Thread thread) {
        threads.add(thread);
        threads.get(threads.size() - 1).start();
    }

    /**
     * метод для ожидания окончания всех потоков из списка
     */
    void waitAllLaunched() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    /**
     * очистка списка потоков
     */
    void clear() {
        threads.clear();
    }
}
