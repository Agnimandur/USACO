/*
ID: shivara2
LANG: JAVA
TASK: 248
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    //248.in
    BufferedReader br = new BufferedReader(new FileReader("248.in"));
    int N = Integer.parseInt(br.readLine());
    int max = 0;
    int[] nums = new int[N];
    for (int i = 0; i < N; i++)
      nums[i] = Integer.parseInt(br.readLine());
    br.close();

    int[][] dp = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N-i; j++) {
        dp[i][j] = calculate(dp,nums,i,j);
        max = Math.max(max,dp[i][j]);
      }
    }

    /*for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        System.out.print(dp[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println(max);*/

    //taskName.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("248.out")));
    pw.println(max);
    pw.close();
  }

  public static int calculate(int[][] dp, int[] nums, int i, int j) {
    if (i > 0) {
      //k is the split index
      int kMax = 0;
      for (int k = 0; k < i; k++) {
        if (dp[k][j] == dp[(i-1)-k][j+k+1])
          kMax = Math.max(kMax,dp[k][j] + 1);
      }
      return kMax;
    } else {
      //The i = 0 row is the same as nums.
      return nums[j];
    }
  }
}