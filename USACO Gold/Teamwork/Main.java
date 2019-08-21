/*
ID: shivara2
LANG: JAVA
TASK: teamwork
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    //teamwork.in
    BufferedReader br = new BufferedReader(new FileReader("teamwork.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    if (K > N) {
      K = N;
    }
    int[] skills = new int[N];
    for (int i = 0; i < N; i++) {
      skills[i] = Integer.parseInt(br.readLine());
    }
    br.close();

    int[] dp = new int[N+1];
    int maxToN = 0;
    for (int n = 1; n < K; n++) {
      maxToN = Math.max(maxToN, skills[n-1]);
      dp[n] = maxToN * n;
    }

    for (int n = K; n < N+1; n++) {
      int max = 0;
      for (int i = 1; i <= K; i++) {
        int tMax = 0;
        for (int j = 0; j < i; j++) {
          tMax = Math.max(tMax,skills[n-j-1]);
        }
        max = Math.max(max,dp[n-i] + tMax * i);
      }
      dp[n] = max;
    }

    //teamwork.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
    pw.println(dp[N]);
    pw.close();
  }
}