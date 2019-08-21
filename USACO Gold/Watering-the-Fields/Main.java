/*
ID: shivara2
LANG: JAVA
TASK: irrigation
*/

import java.util.*;
import java.io.*;
import java.awt.Point;

class Main {
  static int N;
  static int C;
  static Point[] fields;
  public static void main(String[] args) throws IOException {
    //irrigation.in
    BufferedReader br = new BufferedReader(new FileReader("irrigation.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    fields = new Point[N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken());
      int y = Integer.parseInt(st.nextToken());
      fields[i] = new Point(x,y);
    }
    br.close();

    long totalDistance = prim();
    //irrigation.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("irrigation.out")));
    pw.println(totalDistance + "");
    pw.close();
  }

  public static long prim() {
    int[] distances = new int[N];
    for (int i = 1; i < N; i++)
      distances[i] = Integer.MAX_VALUE;
    boolean[] visited = new boolean[N];
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });


    int[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      visited[temp[0]] = true;
      //parent and child are indices in fields.
      int parent = temp[0];
      for (int child = 0; child < N; child++) {
        int pcDistance = (int) (Math.pow((fields[parent].y-fields[child].y),2) + Math.pow((fields[parent].x-fields[child].x),2));
        if (!visited[child] && pcDistance >= C && distances[child] > pcDistance) {
          distances[child] = pcDistance;
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    long totalDistance = 0;
    for (int i = 1; i < N; i++) {
      if (distances[i] == Integer.MAX_VALUE)
        return -1;
      else
        totalDistance += distances[i];
    }
    return totalDistance;
  }
}