package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Set {
    final private int[] set;
    private final int[] block;
    private final int[] next;
    private final int[] previous;
    private final boolean[] toward;// true якщо елемент рухається вперед

    Set(int n) {
        this.set = new int[n + 1];
        this.block = new int[n + 1];
        this.next = new int[n + 1];
        this.previous = new int[n + 1];
        this.toward = new boolean[n + 1];
    }

    public void generateSubsets(int n) {
        for (int i = 1; i <= n; i++) {
            set[i] = i;
            block[i] = 1;
            toward[i] = true;
        }

        next[1] = 0;
        printBreakingSet(set, block, n);// вивід першого розбиття {{1,...,n}}

        int j = n;// j то активний елемент який рухається в блоках
//        int amount = 1;
        while (j > 1) {
            int k = block[j];//це наймеший елемент блоку і по суті є його номером, в якому перебуває активний елемент j

            if (toward[j]) {// блок рухається вперед
                if (next[k] == 0) {// k є номером останнього блоку розбиття
                    next[k] = j;
                    previous[j] = k;
                    next[j] = 0;
                }
                if (next[k] > j) { // всі блоки справа від блоку k містять елементи більші за j
                    previous[j] = k;
                    next[j] = next[k];// в цьому випадку створений блок не є останнім блоком розбиття
                    previous[next[j]] = j;// створив одноелементний блок
                    next[k] = j;
                }

                block[j] = next[k];// якщо він рухається вправо то просто переносим його в блок next

            } else {// блок рухається назад
                block[j] = previous[k];// помістив в попередній блок

                if (k == j) {// j утворює одноелементний блок
                    if (next[k] == 0) {// останній блок розбиття зліва
                        next[previous[k]] = 0;
                    } else {
                        next[previous[k]] = next[k];
                        previous[next[k]] = previous[k];
                    }
                }
            }

            printBreakingSet(set, block, n);
//            amount++;

            j = n;
            while ((j > 1) && ((toward[j] && (block[j] == j)) || (!toward[j] && (block[j] == 1)))) {// &&-and
                toward[j] = !toward[j];
                j--;
            }
        }
//        System.out.println(amount);
    }

    private void printBreakingSet(int[] set, int[] block, int n) {
        ArrayList<Integer> list1 = (ArrayList<Integer>) Arrays.stream(set).boxed().collect(Collectors.toList());
        ArrayList<Integer> list2 = (ArrayList<Integer>) Arrays.stream(block).boxed().collect(Collectors.toList());

        int p;
        for (int k = 1; k <= n - 1; k++) {
            for (int i = 1; i <= n - k; i++) {
                if (list2.get(i) > list2.get(i + 1)) {
                    p = list2.get(i);
                    list2.set(i, list2.get(i + 1));
                    list2.set(i + 1, p);
                    p = list1.get(i);
                    list1.set(i, list1.get(i + 1));
                    list1.set(i + 1, p);
                }
            }
        }

        System.out.print("(" + list1.get(1));
        for (int i = 2; i <= n; i++) {
            if (!Objects.equals(list2.get(i), list2.get(i - 1))) {
                System.out.print(") (" + list1.get(i));
            } else {
                System.out.print(" " + list1.get(i));
            }
        }
        System.out.println(")");
    }
}
