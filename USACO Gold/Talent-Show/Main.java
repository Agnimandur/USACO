/*
ID: shivara2
LANG: JAVA
TASK: talent
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //talent.in
    BufferedReader br = new BufferedReader(new FileReader("talent.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int W = Integer.parseInt(st.nextToken());
    int[][] cows = new int[N][2];
    int talent = 0;
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      cows[i][0] = Integer.parseInt(st.nextToken());
      cows[i][1] = Integer.parseInt(st.nextToken());
      talent += cows[i][1];
    }
    br.close();

    int[][] dp = new int[N+1][talent+1];
    for (int i = 0; i < N+1; i++) {
      for (int j = 1; j < talent+1; j++) {
        dp[i][j] = Integer.MAX_VALUE;
      }
    }

    //Knapsack DP
    //Every entry records the minimum weight necessary to achieve the required talent
    for (int maxCow = 1; maxCow < N+1; maxCow++) {
      for (int minTalent = 1; minTalent < talent+1;minTalent++) {
        int weightWithoutLastCow = dp[maxCow-1][minTalent];
        int weightWithLastCow = Integer.MAX_VALUE;
        if (cows[maxCow-1][1] == minTalent) {
          weightWithLastCow = cows[maxCow-1][0];
        } else if (cows[maxCow-1][1] > minTalent) {
          weightWithLastCow = Integer.MAX_VALUE;
        } else if (dp[maxCow-1][minTalent - cows[maxCow-1][1]] != Integer.MAX_VALUE) {
          weightWithLastCow = cows[maxCow-1][0] + dp[maxCow-1][minTalent - cows[maxCow-1][1]];
        } else {
          weightWithLastCow = Integer.MAX_VALUE;
        }
        dp[maxCow][minTalent] = Math.min(weightWithoutLastCow,weightWithLastCow);
      }
    }


    double ratio = 0.0;
    for (int i = 1; i <= talent; i++) {
      if (dp[N][i] >= W) {
        ratio = Math.max(ratio,(double)i/dp[N][i]);
      }
    }

    //talent.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
    pw.println((int)Math.floor(1000*ratio));
    pw.close();
  }
}