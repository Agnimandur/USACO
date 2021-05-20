/*
ID: shivara2
LANG: JAVA
TASK: maxcross
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //maxcross.in
    BufferedReader br = new BufferedReader(new FileReader("maxcross.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int B = Integer.parseInt(st.nextToken());
    int[] brokenSignals = new int[B];
    for (int i = 0; i < B; i++) {
      brokenSignals[i] = Integer.parseInt(br.readLine());
    }
    Arrays.sort(brokenSignals);
    int[] brokenCount = new int[N+1];
    brokenCount[0] = 0;
    br.close();

    int index = 0;
    for (int i = 1; i < N+1; i++) {
      if (index < brokenSignals.length && brokenSignals[index] == i) {
        brokenCount[i] = brokenCount[i-1] + 1;
        index++;
      } else {
        brokenCount[i] = brokenCount[i-1];
      }
    }

    int minSignal = 1000000;
    for (int i = K; i <= N; i++) {
      minSignal = Math.min(minSignal,brokenCount[i] - brokenCount[i-K]);
    }

    //maxcross.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("maxcross.out")));
    pw.println(minSignal);
    pw.close();

  }
}