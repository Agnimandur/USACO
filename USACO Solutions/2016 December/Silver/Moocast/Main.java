/*
ID: shivara2
LANG: JAVA
TASK: moocast*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //moocast.in
    BufferedReader br = new BufferedReader(new FileReader("moocast.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] cows = new int[N][3];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      cows[i][0] = Integer.parseInt(st.nextToken());
      cows[i][1] = Integer.parseInt(st.nextToken());
      cows[i][2] = Integer.parseInt(st.nextToken());
    }
    HashMap<Integer,ArrayList> cowDistance = new HashMap<Integer,ArrayList>();
    for (int i = 0; i < N; i++) {
      ArrayList<Integer> temp = new ArrayList<Integer>();
      for (int j = 0; j < N; j++) {
        if (i != j && distance(cows[i],cows[j])) {
          temp.add(j);
        }
      }
      cowDistance.put(i,temp);
    }
    
    //moocast.out
    int max = 1;
    for (int i = 0; i < N; i++) {
      max = Math.max(max,BFS(cowDistance,i));
    }
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
    pw.println(max);
    pw.close();
  }

  public static boolean distance(int[] cow1, int[] cow2) {
    //cow1 is attempting to broadcast to cow2
    double power = cow1[2];
    int xDist = cow2[0] - cow1[0];
    int yDist = cow2[1] - cow1[1];
    double distance = Math.sqrt(Math.pow(xDist,2) + Math.pow(yDist,2));
    return power >= distance;
  }

  public static int BFS(HashMap<Integer,ArrayList> cowDistance, int root) {
    //Initialize Queue
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Queue<Integer> q = new LinkedList<Integer>();
    q.add(root);
    visited.add(root);
    //BFS Algorithm
    while (! q.isEmpty()) {
      int n = q.remove();
      ArrayList<Integer> al = cowDistance.get(n);
      for (int item: al) {
        if (! visited.contains(item)) {
          q.add(item);
          visited.add(item);
        }
      }
    }

    return visited.size();
  }
}