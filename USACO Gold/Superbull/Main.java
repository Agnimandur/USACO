/*
ID: shivara2
LANG: JAVA
TASK: superbull
*/

import java.util.*;
import java.io.*;

class Main {
  static int N;
  static int[] IDs;

  public static void main(String[] args) throws IOException {
    //superbull.in
    BufferedReader br = new BufferedReader(new FileReader("superbull.in"));
    N = Integer.parseInt(br.readLine());
    IDs = new int[N];
    for (int i = 0; i < N; i++)
      IDs[i] = Integer.parseInt(br.readLine());
    br.close();
    
    long totalDistance = prim();
    //superbull.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("superbull.out")));
    pw.println(totalDistance + "");
    pw.close();
  }

  public static long prim() {
    int[] distances = new int[N];
    boolean[] visited = new boolean[N];
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return b[1] - a[1];
      }
    });

    int[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      visited[temp[0]] = true;
      //parent and child are indices in IDs.
      int parent = temp[0];
      for (int child = 0; child < N; child++) {
        if (!visited[child] && distances[child] < (IDs[parent]^IDs[child])) {
          distances[child] = IDs[parent]^IDs[child];
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    long totalDistance = 0;
    for (int i = 1; i < N; i++)
      totalDistance += distances[i];
    return totalDistance;
  }
}