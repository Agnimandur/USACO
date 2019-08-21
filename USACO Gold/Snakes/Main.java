/*
ID: shivara2
LANG: JAVA
TASK: snakes
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //snakes.in
    BufferedReader br = new BufferedReader(new FileReader("snakes.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int[] groups = new int[N];
    int[] cumGroup = new int[N+1];
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++) {
      groups[i] = Integer.parseInt(st.nextToken());
      cumGroup[i+1] = groups[i] + cumGroup[i];
    }
    br.close();

    //The maximum group size from index i to j (inclusive).
    int[][] maxSE = new int[N][N];
    for (int i = 0; i < N; i++)
      maxSE[i][i] = groups[i];
    for (int diff = 1; diff < N; diff++) {
      for (int s = 0; s < N-diff; s++) {
        maxSE[s][s+diff] = Math.max(maxSE[s][s+diff-1],maxSE[s+1][s+diff]);
      }
    }

    //Find the wasted space from index i to j, assuming no splits.
    int[][] noSplits = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = i; j < N; j++) {
        int size = (j - i) + 1;
        noSplits[i][j] = (size * maxSE[i][j]) - (cumGroup[j+1] - cumGroup[i]);
      }
    }

    //DP. Rows are # of splits from 0 to K. Cols are index of last element in the group. The inner loop is the index of the "last split". Everything before the last split is just a smaller DP problem. Everything after has been pre-computed above.
    int[][] dp = new int[K+1][N];
    for (int col = 0; col < N; col++)
      dp[0][col] = noSplits[0][col];
    for (int row = 1; row <= K; row++) {
      for (int col = 0; col < N; col++) {
        int minWaste = Integer.MAX_VALUE;
        for (int last = 1; last <= col; last++)
          minWaste = Math.min(minWaste,dp[row-1][last-1] + noSplits[last][col]);
        if (minWaste == Integer.MAX_VALUE)
          minWaste = 0;
        dp[row][col] = minWaste;
      }
    }

    //snakes.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
    pw.println(dp[K][N-1] + "");
    pw.close();
  }
}