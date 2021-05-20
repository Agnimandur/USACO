import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("closing.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    Node[] barns = new Node[N];
    for (int i = 0; i < N; i++) {
      barns[i] = new Node(i);
    }
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken())-1;
      int y = Integer.parseInt(st.nextToken())-1;
      barns[x].addChild(barns[y]);
      barns[y].addChild(barns[x]);
    }

    int[] closingOrder = new int[N];
    for (int i = 0; i < N; i++)
      closingOrder[i] = Integer.parseInt(br.readLine())-1;
    br.close();

    //closing.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("closing.out")));
    int barnsLeft = N;
    for (int i = 0; i < N; i++) {
      pw.println(DFSconnected(barns[closingOrder[i]],N,barnsLeft));
      barns[closingOrder[i]].close();
      barnsLeft--;
    }
    pw.close();
  }

  public static String DFSconnected(Node root, int N, int barnsLeft) {
    boolean[] visited = new boolean[N];
    int barnsVisited = 0;

    //Initialize Stack
    Stack<Node> s = new Stack<Node>();
    Node n = root;
    visited[n.num] = true;
    s.push(n);
    
    //Loop through DFS Algorithm
    while (s.isEmpty() == false) {
      n = s.pop();
      barnsVisited++;
      for (Node item: n.children) {
        if (item.exists && !visited[item.num]) {
          visited[item.num] = true;
          s.push(item);
        }
      }
    }
    if (barnsVisited == barnsLeft)
      return "YES";
    else
      return "NO";
  }
}

class Node {
  public int num;
  public boolean exists;
  public ArrayList<Node> children;

  public Node(int n) {
    num = n;
    exists = true;
    children = new ArrayList<Node>();
  }

  public void addChild(Node n) {
    children.add(n);
  }

  public void close() {
    exists = false;
  }
}