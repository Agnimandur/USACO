/*
ID: shivara2
LANG: JAVA
TASK: hps
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //hps.in
    BufferedReader br = new BufferedReader(new FileReader("hps.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int[] moves = new int[N];
    for (int i = 0; i < N; i++) {
      String s = br.readLine();
      if (s.equals("H")) {
        moves[i] = 0;
      } else if (s.equals("P")) {
        moves[i] = 1;
      } else {
        moves[i] = 2;
      }
    }
    br.close();

    int[][][] dp = new int[N+1][K+1][3];
    dp[0][0][0] = 0;
    dp[0][0][1] = 0;
    dp[0][0][2] = 0;
    for (int n = 1; n < N+1; n++) {
      int[] temp = {0,0,0};
      temp[moves[n-1]] = 1;
      dp[n][0][0] = dp[n-1][0][0] + temp[0];
      dp[n][0][1] = dp[n-1][0][1] + temp[1];
      dp[n][0][2] = dp[n-1][0][2] + temp[2];
    }

    int[][] dp1 = new int[N+1][3];
    for (int k = 1; k < K+1; k++) {
      dp[0][k][0] = 0;
      dp[0][k][1] = 0;
      dp[0][k][2] = 0;
      for (int n = 1; n < N+1; n++) {
        int[] temp = {0,0,0};
        temp[moves[n-1]] = 1;
        dp[n][k][0] = Math.max(dp[n-1][k][0],Math.max(dp[n-1][k-1][1],dp[n-1][k-1][2])) + temp[0];
        dp[n][k][1] = Math.max(dp[n-1][k][1],Math.max(dp[n-1][k-1][0],dp[n-1][k-1][2])) + temp[1];
        dp[n][k][2] = Math.max(dp[n-1][k][2],Math.max(dp[n-1][k-1][0],dp[n-1][k-1][1])) + temp[2];
      }
    }

    //hps.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
    pw.println(Math.max(Math.max(dp[N][K][0],dp[N][K][1]),dp[N][K][2]) + "");
    pw.close();
  }
}