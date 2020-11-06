package com.linka39.testUtil;

import java.util.Arrays;
//n后问题，回溯算法，求解空间树
public class nQueen {
    static int resultCount = 0;

    private static boolean place(int[] arr, int s) {
        for(int i = 0; i < s; i++) {
            if((arr[i] == arr[s]) || (Math.abs(i-s) == Math.abs(arr[i]-arr[s]))) {
                return false;
            }
        }
        return true;
    }

    public static void tria(int[] arr, int i, int n) {
        if(i >= n) {
            ++resultCount;
            System.out.println(Arrays.toString(arr));
        } else {
            for(int j = 0; j < n; j++) {
                arr[i] = j;
                if(place(arr, i)) {
                    tria(arr, i+1, n);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] queen = new int[3];
        tria(queen, 0, 3);

        System.out.println(resultCount);
    }

}
