/*
ID: shivara2
LANG: JAVA
TASK: pairup
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //pairup.in
    BufferedReader br = new BufferedReader(new FileReader("pairup.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] cows = new int[N][2];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      cows[i][0] = Integer.parseInt(st.nextToken());
      cows[i][1] = Integer.parseInt(st.nextToken());
    }
    br.close();
    Arrays.sort(cows, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[1]-arr2[1];
      }
    });

    int i = 0;
    int j = N-1;
    int maxTime = 0;
    while (i <= j) {
      int numPairs = Math.min(cows[i][0],cows[j][0]);
      maxTime = Math.max(maxTime,cows[i][1] + cows[j][1]);
      cows[i][0] -= numPairs;
      cows[j][0] -= numPairs;
      if (cows[i][0] <= 0) {
        i += 1;
      }
      if (cows[j][0] <= 0) {
        j -= 1;
      }
    }
  
    //pairup.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pairup.out")));
    pw.println(maxTime);
    pw.close();
  }
}