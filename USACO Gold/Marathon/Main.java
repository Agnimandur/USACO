/*
ID: shivara2
LANG: JAVA
TASK: marathon
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //marathon.in
    BufferedReader br = new BufferedReader(new FileReader("marathon.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());

    int[][] checkpoints = new int[N][2];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      checkpoints[i][0] = Integer.parseInt(st.nextToken());
      checkpoints[i][1] = Integer.parseInt(st.nextToken());
    }
    br.close();

    int[][] dp = new int[N][K+1];

    for (int i = 1; i < N; i++) {
      for (int j = 0; j <= Math.min(i-1,K); j++) {
        dp[i][j] = calculate(dp,checkpoints,i,j);
      }
      for (int j = i; j <= K; j++) {
        dp[i][j] = Integer.MAX_VALUE;
      }
    }

    //marathon.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));
    pw.println(dp[N-1][K]);
    pw.close();
  }

  public static int calculate(int[][] dp, int[][] checkpoints, int i, int j) {
    int minDistance = Integer.MAX_VALUE;
    for (int n = 0; n <= j; n++) {
      if (dp[i-n-1][j-n] != Integer.MAX_VALUE) {
        minDistance = Math.min(minDistance,dp[i-n-1][j-n] + Math.abs(checkpoints[i-n-1][0] - checkpoints[i][0]) + Math.abs(checkpoints[i-n-1][1] - checkpoints[i][1]));
      }
    }
    return minDistance;
  }
}