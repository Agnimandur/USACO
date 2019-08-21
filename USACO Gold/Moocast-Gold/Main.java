/*
ID: shivara2
LANG: JAVA
TASK: moocast
*/

import java.util.*;
import java.io.*;
import java.awt.Point;

class Main {
  static int N;
  static Point[] cows;
  public static void main(String[] args) throws IOException {
    //moocast.in
    BufferedReader br = new BufferedReader(new FileReader("moocast.in"));
    N = Integer.parseInt(br.readLine());
    cows = new Point[N];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken());
      int y = Integer.parseInt(st.nextToken());
      cows[i] = new Point(x,y);
    }
    br.close();

    int X = prim();
    //moocast.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
    pw.println(X + "");
    pw.close();
  }

  public static int prim() {
    double[] distances = new double[N];
    for (int i = 1; i < N; i++)
      distances[i] = Double.MAX_VALUE;
    boolean[] visited = new boolean[N];
    PriorityQueue<double[]> pq = new PriorityQueue<double[]>(new Comparator<double[]>(){
      public int compare(double[] a, double[] b) {
        if (a[1] > b[1])
          return 1;
        else if (a[1] < b[1])
          return -1;
        else
          return 0;
      }
    });

    double[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      visited[(int)temp[0]] = true;
      //parent and child are indices in cows.
      int parent = (int)temp[0];
      for (int child = 0; child < N; child++) {
        double pcDistance = cows[parent].distance(cows[child]);
        if (!visited[child] && distances[child] > pcDistance) {
          distances[child] = pcDistance;
          double[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    int X = 0;
    for (int i = 1; i < N; i++) {
      int distSquared = (int) Math.rint(Math.pow(distances[i],2));
      X = Math.max(X,distSquared);
    }
    return X;
  }
}