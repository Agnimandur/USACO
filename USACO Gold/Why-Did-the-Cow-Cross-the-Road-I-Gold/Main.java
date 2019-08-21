/*
ID: shivara2
LANG: JAVA
TASK: visitfj
*/

import java.util.*;
import java.io.*;

class Main {
  static int N;
  static int T;
  static int[][] times;
  static int[][] timeToGet;


  public static void main(String[] args) throws IOException {
    //visitfj.in
    BufferedReader br = new BufferedReader(new FileReader("visitfj.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    T = Integer.parseInt(st.nextToken());
    times = new int[N][N];
    timeToGet = new int[N][N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        times[i][j] = Integer.parseInt(st.nextToken());
        timeToGet[i][j] = Integer.MAX_VALUE;
      }
    }
    timeToGet[0][0] = 0;
    br.close();
    int minTime = dijkstra();

    //visitfj.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));
    pw.println(minTime + "");
    pw.close();
  }

  public static int dijkstra() {
    //int[] = {row,col,timeToGet[row][col]}
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[2] - b[2];
      }
    });
    int[] temp = {0,0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int[] parent = {temp[0],temp[1]};
      for (int rowMod = -3; rowMod <= 3; rowMod++) {
        for (int colMod = -3; colMod <= 3; colMod++) {
          int childRow = parent[0] + rowMod;
          int childCol = parent[1] + colMod;
          if ((Math.abs(colMod) + Math.abs(rowMod) == 1 || Math.abs(colMod) + Math.abs(rowMod) == 3) && childRow >= 0 && childCol >= 0 && childRow < times.length && childCol < times[0].length) {
            int newTime = timeToGet[parent[0]][parent[1]] + times[childRow][childCol] + 3 * T;
            if (timeToGet[childRow][childCol] > newTime) {
              timeToGet[childRow][childCol] = newTime;
              int[] temp2 = {childRow,childCol,timeToGet[childRow][childCol]};
              pq.add(temp2);
            }
          }
        }
      }
    }

    int minTime = timeToGet[times.length - 1][times[0].length - 1];
    for (int row = times.length - 3; row < times.length; row++) {
      for (int col = times[0].length - 3; col < times[0].length; col++) {
        if (row + col > (times.length + times[0].length - 5))
          minTime = Math.min(minTime,timeToGet[row][col] + T * ((times.length - row - 1) + (times[0].length - col - 1)));
      }
    }
    return minTime;
  }
}