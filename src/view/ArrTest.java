package view;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.io.Reader;

import java.util.Scanner;

public class ArrTest {

    public int[][] chessmatrixArrTest = Chessboard.chessmatrix;


    public static void toXs(int[][] chessmatrixArrTest) throws IOException {
//转换稀疏数组
        /*
         * row col val
         * 0 11 11 2
         */
//统计二维数组中有效数据的个数，遍历二维数组
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessmatrixArrTest[i][j] != 0) {
                    sum++;
                }
            }
        }
        System.out.println("有效数据的个数是====>>>>" + sum);
//根据二维数组创建二维稀疏数组
        int[][] chessmatrixArrTestXs = new int[sum + 1][3];
//给稀疏数组赋值，初始化第一行数据
        chessmatrixArrTestXs[0][0] = 8;
        chessmatrixArrTestXs[0][1] = 8;
        chessmatrixArrTestXs[0][2] = sum;
//给余下的行赋值
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessmatrixArrTest[i][j] != 0) {
                    count++;
                    chessmatrixArrTestXs[count][0] = i;
                    chessmatrixArrTestXs[count][1] = j;
                    chessmatrixArrTestXs[count][2] = chessmatrixArrTest[i][j];
                }
            }
        }
//遍历稀疏数组
        System.out.println("稀疏数组是~~~~~");
        for (int i = 0; i < sum; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d\t", chessmatrixArrTestXs[i][j]);
            }
        }
        System.out.println();
    }
}












