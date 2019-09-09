/*
ID: shivara2
LANG: JAVA
TASK: paintbarn
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //paintbarn.in
    BufferedReader br = new BufferedReader(new FileReader("paintbarn.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int[][] canvas = new int[1001][1001];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      int x1 = Integer.parseInt(st.nextToken());
      int y1 = Integer.parseInt(st.nextToken());
      int x2 = Integer.parseInt(st.nextToken());
      int y2 = Integer.parseInt(st.nextToken());
      for (int j = y1; j < y2; j++) {
        canvas[j][x1]++;
        canvas[j][x2]--;
      }
    }

    int matches = 0;
    for (int i = 0; i < 1001; i++) {
      int paintDepth = 0;
      for (int j = 0; j < 1001; j++) {
        paintDepth += canvas[i][j];
        if (paintDepth == K) {
          matches++;
        }
      }
    }

    //paintbarn.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("paintbarn.out")));
    pw.println(matches);
    pw.close();
  }
}