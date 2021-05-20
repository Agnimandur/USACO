import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    //fenceplan.in
    BufferedReader br = new BufferedReader(new FileReader("fenceplan.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());

    Node[] cows = new Node[N+1];
    int[][] locations = new int[N+1][2];
    boolean[] visited = new boolean[N+1];

    for (int i = 1; i <= N; i++) {
      st = new StringTokenizer(br.readLine());
      cows[i] = new Node(i);
      locations[i][0] = Integer.parseInt(st.nextToken());
      locations[i][1] = Integer.parseInt(st.nextToken());
    }
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int cow1 = Integer.parseInt(st.nextToken());
      int cow2 = Integer.parseInt(st.nextToken());
      cows[cow1].addChild(cows[cow2]);
      cows[cow2].addChild(cows[cow1]);
    }
    br.close();
    int minPerimeter = Integer.MAX_VALUE;
    int numVisited = 0;
    int rVal = 1;

    while (numVisited < N) {
      for (int i = rVal; i <= N; i++) {
        if (visited[i] == false) {
          rVal = i;
          break;
        }
      }
      Node root = cows[rVal];
      visited[rVal] = true;
      Stack<Node> s = new Stack<Node>();
      s.push(root);
      int minX = locations[root.id][0];
      int maxX = locations[root.id][0];
      int minY = locations[root.id][1];
      int maxY = locations[root.id][1];

      //Loop through DFS Algorithm
      while (! s.isEmpty()) {
        Node n = s.pop();
        numVisited++;
        int nx = locations[n.id][0];
        int ny = locations[n.id][1];
        maxX = Math.max(maxX,nx);
        minX = Math.min(minX,nx);
        maxY = Math.max(maxY,ny);
        minY = Math.min(minY,ny);
        for (Node item: n.children) {
          if (visited[item.id] == false) {
            visited[item.id] = true;
            s.push(item);
          }
        }
      }
      int perimeter = 2*((maxX - minX) + (maxY - minY));
      minPerimeter = Math.min(perimeter,minPerimeter);
    }

    //enceplan.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fenceplan.out")));
    pw.println(minPerimeter);
    pw.close();
  }
}

class Node {
  public int id;
  public ArrayList<Node> children;

  public Node(int num) {
    id = num;
    children = new ArrayList<Node>();
  }

  public void addChild(Node n) {
    children.add(n);
  }
}