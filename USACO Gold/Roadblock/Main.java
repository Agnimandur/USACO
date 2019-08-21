/*
ID: shivara2
LANG: JAVA
TASK: rblock
*/

import java.util.*;
import java.io.*;

class Main {
  static int N;
  static int M;
  static int[][] adjacencyMatrix;
  static int[] previousNode;

  public static void main(String[] args) throws IOException{
    //rblock.in
    BufferedReader br = new BufferedReader(new FileReader("rblock.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    adjacencyMatrix = new int[N][N];

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        adjacencyMatrix[i][j] = -1;
      }
    }
    
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int p = Integer.parseInt(st.nextToken()) - 1;
      int q = Integer.parseInt(st.nextToken()) - 1;
      int w = Integer.parseInt(st.nextToken());
      adjacencyMatrix[p][q] = w;
      adjacencyMatrix[q][p] = w;
    }
    br.close();

    int baseDistance = dijkstra();
    ArrayList<Integer> path = new ArrayList<Integer>();
    for (int i = N-1; i >= 0; i = previousNode[i])
      path.add(i);
    
    int cowDistance = 0;
    for (int i = 0; i < path.size() - 1; i++) {
      adjacencyMatrix[path.get(i)][path.get(i+1)] *= 2;
      adjacencyMatrix[path.get(i+1)][path.get(i)] *= 2;
      cowDistance = Math.max(cowDistance,dijkstra());
      adjacencyMatrix[path.get(i)][path.get(i+1)] /= 2;
      adjacencyMatrix[path.get(i+1)][path.get(i)] /= 2;
    }

    //rblock.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rblock.out")));
    int increase = cowDistance - baseDistance;
    pw.println(increase + "");
    pw.close();
  }

  public static int dijkstra() {
    previousNode = new int[N];
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[N];
    previousNode[0] = -1;
    for (int i = 1; i < N; i++) {
      distances[i] = Integer.MAX_VALUE;
      previousNode[i] = -1;
    }
    //Dijkstra's Algorithm
    int[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      for (int child = 0; child < N; child++) {
        if (adjacencyMatrix[parent][child] >= 0 && distances[child] > distances[parent] + adjacencyMatrix[parent][child]) {
          previousNode[child] = parent;
          distances[child] = distances[parent] + adjacencyMatrix[parent][child];
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }

    return distances[N-1];
  }
}