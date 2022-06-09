package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println("Результат:\n");
        int n = 4;

        Set set = new Set(n);
        set.generateSubsets(n);
    }
}
