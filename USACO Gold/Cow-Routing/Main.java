/*
ID: shivara2
LANG: JAVA
TASK: cowroute
*/

import java.util.*;
import java.io.*;
import java.awt.Point;

class Main {
  static int A;
  static int B;
  static int N;
  //C == numCities
  static int C;
  //Point: {Cost,minFlights}
  static Point[][] adj;
  static long[][] minCFs;

  public static void main(String[] args) throws IOException {
    //cowroute.in
    BufferedReader br = new BufferedReader(new FileReader("cowroute.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    A = Integer.parseInt(st.nextToken());
    B = Integer.parseInt(st.nextToken());
    N = Integer.parseInt(st.nextToken());
    C = 0;
    Point[][] adjTemp = new Point[1000][1000];
    for (int a = 0; a < 2 * N; a += 2) {
      st = new StringTokenizer(br.readLine());
      int cost = Integer.parseInt(st.nextToken());
      int routeLength = Integer.parseInt(st.nextToken());
      st = new StringTokenizer(br.readLine());
      int[] route = new int[routeLength];
      for (int b = 0; b < routeLength; b++) {
        route[b] = Integer.parseInt(st.nextToken());
        C = Math.max(C,route[b]);
      }
      for (int i = 0; i < routeLength - 1; i++) {
        for (int j = i + 1; j < routeLength; j++) {
          Point p = adjTemp[route[i]-1][route[j]-1];
          Point newP = new Point(cost,j-i);
          if (p == null || p.x > cost || (p.x == cost && p.y > j-i))
            adjTemp[route[i]-1][route[j]-1] = newP;
        }
      }
    }
    adj = new Point[C][C];
    for (int i = 0; i < C; i++) {
      for (int j = 0; j < C; j++)
        adj[i][j] = adjTemp[i][j];
    }
    br.close();

    long[] ans = dijkstra();

    //cowroute.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowroute.out")));
    pw.println(ans[0] + " " + ans[1]);
    pw.close();
  }

  public static long[] dijkstra() {
    PriorityQueue<long[]> pq = new PriorityQueue<long[]>(new Comparator<long[]>(){
      public int compare(long[] a, long[] b) {
        return a[1] == b[1] ? (int) (a[2] - b[2]) : (int) (a[1] - b[1]);
      }
    });
    minCFs = new long[C][2];
    for (int i = 0; i < C; i++) {
      minCFs[i][0] = Long.MAX_VALUE;
      minCFs[i][1] = Long.MAX_VALUE;
    }
    minCFs[A-1][0] = 0;
    minCFs[A-1][1] = 0;

    //dijkstra
    long[] temp = {A-1,0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int city = (int) temp[0];
      for (int child = 0; child < C; child++) {
        Point p = adj[city][child];
        if (p != null && minCFs[child][0] > minCFs[city][0] + p.x) {
          minCFs[child][0] = minCFs[city][0] + p.x;
          minCFs[child][1] = minCFs[city][1] + p.y;
          long[] temp2 = {child,minCFs[child][0],minCFs[child][1]};
          pq.add(temp2);
        } else if (p != null && minCFs[child][0] == minCFs[city][0] + p.x && minCFs[child][1] > minCFs[city][1] + p.y) {
          minCFs[child][1] = minCFs[city][1] + p.y;
          long[] temp2 = {child,minCFs[child][0],minCFs[child][1]};
          pq.add(temp2);
        }
      }
    }

    long[] noSolution = {-1,-1};
    return minCFs[B-1][0] == Long.MAX_VALUE ? noSolution : minCFs[B-1];
  }
}