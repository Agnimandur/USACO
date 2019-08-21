/*
ID: shivara2
LANG: JAVA
TASK: mountains
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //mountains.in
    BufferedReader br = new BufferedReader(new FileReader("mountains.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] peaks = new int[N][2];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      peaks[i][0] = a;
      peaks[i][1] = b;
    }
    br.close();

    Arrays.sort(peaks, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[1]-arr2[1];
      }
    });

    int visiblePeaks = N;
    for (int i = 0; i < N-1; i++) {
      for (int j = N-1; j > i; j--) {
        if (Math.abs(peaks[i][0] - peaks[j][0]) <= Math.abs(peaks[i][1] - peaks[j][1])) {
          visiblePeaks--;
          break;
        }
      }
    }

    //mountains.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mountains.out")));
    pw.println(visiblePeaks);
    pw.close();
  }
}