package com.linka39.testUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class toMap {
    static class line{
        int x,y;
        public line(int x,int y){
            this.x=x;
            this.y=y;
        }
        public String toString(){
            return this.x+"_"+this.y;
        }
    }
    static class getline{
        List<line> lineArr = new ArrayList<>();
        int value;
        public getline(int v){
            value=v;
        }
        public getline(){
            value=0;
        }
        public String toString(){
            return this.value+" ";
        }
    }

    static public getline[][] getMin(int map[][],int m,int n){
        getline dpmap[][]= new getline[m][n];
        dpmap[0][0] = new getline(map[0][0]);
        dpmap[0][0].lineArr.add(new line(0,0));
        for(int i=1;i<n;++i){
            dpmap[0][i]=new getline();

            for(int j=0;j<=i;++j){
                dpmap[0][i].value += map[0][j];
            }
            dpmap[0][i].lineArr.addAll(dpmap[0][i-1].lineArr);
            dpmap[0][i].lineArr.add(new line(0,i));
        }
        for(int i=1;i<m;++i){
            dpmap[i][0]=new getline();
            for(int j=0;j<=i;++j){
                dpmap[i][0].value += map[j][0];
            }
            dpmap[i][0].lineArr.addAll(dpmap[i-1][0].lineArr);
            dpmap[i][0].lineArr.add(new line(i,0));
        }
        for(int i=1;i<m;++i){
            for(int j=1;j<n;++j) {
                dpmap[i][j] = new getline();
                if(dpmap[i][j-1].value < dpmap[i-1][j].value){
                    dpmap[i][j].value=dpmap[i][j-1].value+map[i][j];
                    dpmap[i][j].lineArr.addAll(dpmap[i][j-1].lineArr);
                    dpmap[i][j].lineArr.add(new line(i,j));
                }else{
                    dpmap[i][j].value=dpmap[i-1][j].value+map[i][j];
                    dpmap[i][j].lineArr.addAll(dpmap[i-1][j].lineArr);
                    dpmap[i][j].lineArr.add(new line(i,j));
                }
            }
        }
        return dpmap;
    }

    /**
     * map从左上角开始移动时的最短路径(只能向右或向下移动)
     * @param args
     */
    public static void main(String[] args) {
        int map[][]={
                {1,2,3,1},
                {3,2,1,1}
        };
        int m = map.length;
        int n = map[0].length;
//        System.out.println(Arrays.toString(map[0]));
        getline dpmap[][]=getMin(map,m,n);
        for(int i=0;i<m;++i){
            for(int j=0;j<n;++j)
                System.out.print(dpmap[i][j]);
            System.out.println();
        }
        for(int i=0;i<m;++i){
            for(int j=0;j<n;++j)
                System.out.print(dpmap[i][j].lineArr+"  ");
            System.out.println();
        }
    }
}
