package com.linka39.sortUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class MySortUtil {
    private static int[] arr = new int[]{11,5,4,8,7,10,9,17,15,14,16,19};
    //冒泡
    public static void bubbleSort(int[] arr) {
        if(arr == null || arr.length == 0)
            return ;
        for(int i=0; i<arr.length-1; i++) {
            int tag = 0;
            for(int j=i; j<arr.length-1; j++) {
                if(arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                    tag = 1;
                }
            }
            if(tag == 0)
                break;
        }
    }

    //直接选择
    public static void selectSort(int[] arr) {
        if(arr == null || arr.length == 0)
            return ;
        int minIndex = 0;
        for(int i=0; i<arr.length-1; i++) { //只需要比较n-1次
            minIndex = i;
            for(int j=i+1; j<arr.length; j++) { //从i+1开始比较，因为minIndex默认为i了，i就没必要比了。
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if(minIndex != i) { //如果minIndex不为i，说明找到了更小的值，交换之。
                swap(arr, i, minIndex);
            }
        }
    }

    // 插入排序
    public static void insertSort(int[] arr) {
        if(arr == null || arr.length == 0)
            return;
        for(int i=1; i<arr.length; i++) {//假设第一个数位置时正确的；要往后移，必须要假设第一个。
            int j = i;
            int target = arr[i]; //待插入的
            //后移
            while(j > 0 && target < arr[j-1]) {
                arr[j] = arr[j-1];
                j --;
            }
            //插入
            arr[j] = target;
        }
    }

    //  快速排序算法
    //  一次划分
    public static int partition(int[] arr, int left, int right) {
        int pivotKey = arr[left];

        while(left < right) {
            while(left < right && arr[right] >= pivotKey)
                right --;
            arr[left] = arr[right]; //把小的移动到左边
            while(left < right && arr[left] <= pivotKey)
                left ++;
            arr[right] = arr[left]; //把大的移动到右边
            // swap(arr, left, right); //把大的交换到右边，把小的交换到左边。
        }
        arr[left] = pivotKey; //最后把pivot赋值到中间
        //swap(arr, pivotPointer, left); //最后把pivot交换到中间
        return left;
    }

    public static void quickSort_(int[] arr, int left, int right) {
        if(left >= right)
            return ;
        int pivotPos = partition(arr, left, right);
        quickSort_(arr, left, pivotPos-1);
        quickSort_(arr, pivotPos+1, right);
    }

    public static void quickSort(int[] arr) {
        if(arr == null || arr.length == 0)
            return ;
        quickSort_(arr, 0, arr.length-1);
    }

    /**
     * 堆筛选，除了start之外，start~end均满足大顶堆的定义。
     * 调整之后start~end称为一个大顶堆。
     * @param arr 待调整数组
     * @param start 起始指针
     * @param end 结束指针
     */
    public static void heapAdjust(int[] arr, int start, int end){
        int temp = arr[start];
        for(int i=2*start+1; i<=end; i*=2) {
            // 左右孩子的节点分别为2*i+1,2*i+2
            // 选择出左右孩子较小的下标
            if(i < end && arr[i] < arr[i+1])
                i ++;
            if(temp >= arr[i])
                break; //已经为大顶堆，=保持稳定性。
            arr[start] = arr[i]; //将子节点上移
            start = i; //下一轮筛选
        }
        arr[start] = temp; //插入正确的位置
    }

    public static void heapSort(int[] arr) {
        if(arr == null || arr.length == 0)
            return ;
        //建立大顶堆
        for(int i=arr.length/2; i>=0; i--){
            heapAdjust(arr, i, arr.length-1);
        }
        for(int i=arr.length-1; i>=0; i--) {
            swap(arr, 0, i);
            heapAdjust(arr, 0, i-1);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int a[] = Arrays.copyOfRange(arr,0,arr.length);
        System.out.println("原数组为："+ Arrays.toString(a));
        // bubbleSort(a);
        // selectSort(a);
         insertSort(a);
        quickSort(a);

        System.out.println("排序后为："+ Arrays.toString(a));
    }


}
