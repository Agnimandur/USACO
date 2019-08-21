/*
ID: shivara2
LANG: JAVA
TASK: walk
*/

import java.util.*;
import java.io.*;

class Main {
  static int N;
  static int K;

  public static void main(String[] args) throws IOException {
    //walk.in
    BufferedReader br = new BufferedReader(new FileReader("walk.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    br.close();

    int M = prim();

    //walk.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("walk.out")));
    pw.println(M + "");
    pw.close();
  }

  public static int prim() {
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
      //parent and child are indices in cows.
      int parent = temp[0];
      for (int child = 0; child < N; child++) {
        if (! visited[child]) {
          long x = Math.min(parent,child)+1;
          long y = Math.max(parent,child)+1;
          int maxWalk = (int)((2019201913 * x + 2019201949 * y) % 2019201997);
          if (distances[child] > maxWalk) {
            distances[child] = maxWalk;
            int[] temp2 = {child,distances[child]};
            pq.add(temp2);
          }
        }
      }
    }
    Arrays.sort(distances);
    return distances[distances.length-K+1];
  }
}