package ru.inno.lec02HomeWork;

import java.util.Arrays;

/**
 * Класс реализующий сортировку массива Integer методом BogoSort.
 * Принцип метода BogoSort(stupid sort): тасуем массив наугад до тех пор, пока он не
 * окажется отсортированным
 *
 * @author FOAT
 * @version 1.0  16.01.2019
 */
public class BogoSort {

    /**
     * Метод тасующий элементы массива в случайном порядке
     *
     * @param arr ссылка на массив, который нужно перетасовать
     */
    private static void shuffle(Integer[] arr) {
        Integer[] tempArr = Arrays.copyOf(arr, arr.length);

        //заполняем с первого элемента конечный массив
        int curPos = 0;
        while (tempArr.length > 1) {
            int pos = 0;
            try {
                //выбираем наугад любой элемент, точнее номер элемента
                pos = Helper.getRandInt(0, tempArr.length - 1);
            } catch (Exception e) { //если метод гарантированно не сгенерирует Exception, можно ли делать так?
            }
            //кладём его в конечный массив
            arr[curPos++] = tempArr[pos];

            //формируем новый массив без выбранного элемента
            Integer[] tempArr2 = new Integer[tempArr.length - 1];
            for (int i = 0, j = 0; i < tempArr2.length; ) {
                if (i == pos) {
                    ++j;
                }

                tempArr2[i++] = tempArr[j++];
            }
            tempArr = tempArr2;
        }

        //последний оставшийся элемент просто кладём в конец
        arr[arr.length - 1] = tempArr[0];
    }

    /**
     * Метод выполняет сортировку
     *
     * @param arr ссылка на массив, который нужно отсортировать
     */
    public static void sort(Integer[] arr) {
        if (arr == null) {
            return;
        }

        //тасуем, пока не отсортируется
        while (!Helper.isSorted(arr)) {
            shuffle(arr);
        }
    }
}
