/*
ID: shivara2
LANG: JAVA
TASK: revegetate
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //revegetate.in
    BufferedReader br = new BufferedReader(new FileReader("revegetate.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    Node[] pastures = new Node[N];
    for (int i = 0; i < N; i++) {
      Node n = new Node(i+1);
      pastures[i] = n;
    }

    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      String type = st.nextToken();
      int p1 = Integer.parseInt(st.nextToken());
      int p2 = Integer.parseInt(st.nextToken());
      pastures[p1-1].add(p2);
      pastures[p2-1].add(p1);
    }
    br.close();

    ArrayList<Integer> connections = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      if (!pastures[i].getVisited()) {
        connections.add(DFS(pastures,pastures[i]));
      }
    }

    //System.out.println(connections);
    //The size of connections is how many discreet chunks the graph can be simplified into. Every chunk as two possibilities (solely based on the type of the root, since the types of all others must follow from there.) Therefore, the answer is 2^connnections.size() assuming there are no contradictions in the graph, in which case the answer is 0.

    //taskName.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("revegetate.out")));
    //Express answer in binary
    String answer = "1";
    for (int i = 0; i < connections.size(); i++) {
      answer += "0";
    }
    pw.println(answer);
    pw.close();
  }

  public static int DFS(Node[] pastures, Node root) {
    //Initialize Stack
    Stack<Node> s = new Stack<Node>();
    root.setVisited(true);
    int connections = 1;
    s.push(root);
    Node n;
    
    //Loop through DFS Algorithm
    while (! s.isEmpty()) {
      n = s.pop();

      for (Node item: n.getChildren()) {
        if (! pastures[item.getRoot()-1].getVisited()) {
          pastures[item.getRoot()-1].setVisited(true);
          s.push(pastures[item.getRoot()-1]);
          connections++;
        }
      }
    }

    return connections;
  }
}

class Node {
  private int root;
  private ArrayList<Node> children;
  private boolean visited;

  public Node(int rootInput) {
    root = rootInput;
    children = new ArrayList<Node>();
    visited = false;
  }

  public int getRoot() {return root;}
  public ArrayList<Node> getChildren() {return children;}
  public boolean getVisited() {return visited;}
  public void setVisited(boolean bool) {visited = bool;}

  public void add(int n) {
    Node node = new Node(n);
    children.add(node);
  }

  public String toString() {
    return root + "";
  }
}