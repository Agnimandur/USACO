/*
ID: shivara2
LANG: JAVA
TASK: shortcut
*/

import java.util.*;
import java.awt.Point;
import java.io.*;

class Main {
  static int N;
  static int M;
  static int T;
  static ArrayList<Point>[] adjacencyMatrix;
  static int[] previousNode;
  static int[] distances;

  public static void main(String[] args) throws IOException{
    //shortcut.in
    BufferedReader br = new BufferedReader(new FileReader("shortcut.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    T = Integer.parseInt(st.nextToken());
    st = new StringTokenizer(br.readLine());
    int[] cows = new int[N];
    for (int i = 0; i < N; i++)
      cows[i] = Integer.parseInt(st.nextToken());

    adjacencyMatrix = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      ArrayList<Point> addAL = new ArrayList<Point>();
      adjacencyMatrix[i] = addAL;
    }
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      int t = Integer.parseInt(st.nextToken());
      adjacencyMatrix[a-1].add(new Point(b-1,t));
      adjacencyMatrix[b-1].add(new Point(a-1,t));
    }
    br.close();

    dijkstra();
    long[] numCowsThroughField = new long[N];
    for (int i = 1; i < N; i++) {
      for (int j = i; j != -1; j = previousNode[j])
        numCowsThroughField[j] += cows[i];
    }
    
    long timeSaved = 0;
    int shortcut = 0;
    for (int i = 1; i < N; i++) {
      shortcut = Math.max(0,distances[i] - T);
      timeSaved = Math.max(timeSaved,(long)(numCowsThroughField[i]*shortcut));
    }

    //shortcut.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
    pw.println(timeSaved + "");
    pw.close();
  }

  public static void dijkstra() {
    previousNode = new int[N];
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] == b[1] ? a[0] - b[0] : a[1] - b[1];
      }
    });
    distances = new int[N];
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
      for (Point p: adjacencyMatrix[parent]) {
        int newDistance = distances[parent] + p.y;
        if (distances[p.x] > newDistance) {
          previousNode[p.x] = parent;
          distances[p.x] = newDistance;
          int[] temp2 = {p.x,newDistance};
          pq.add(temp2);
        }
        if (distances[p.x] == newDistance && previousNode[p.x] > parent) {
          previousNode[p.x] = parent;
          int[] temp2 = {p.x,newDistance};
          pq.add(temp2);
        }
      }
    }
  }
}