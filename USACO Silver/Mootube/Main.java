/*
ID: shivara2
LANG: JAVA
TASK: mootube
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    //mootube.in
    BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int Q = Integer.parseInt(st.nextToken());

    //Create Array of Nodes
    Node[] nodeArray = new Node[N];
    for (int i = 0; i < N; i++) {
      nodeArray[i] = new Node(i+1);
    }

    //Create edge

    //Add all the edge children to all Nodes
    for (int i = 0; i < N - 1; i++) {
      st = new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken());
      int n2 = Integer.parseInt(st.nextToken());
      int relevance = Integer.parseInt(st.nextToken());
      Edge edge = new Edge(relevance,nodeArray[n2 - 1]);
      nodeArray[n1 - 1].children.add(edge);
      edge = new Edge(relevance,nodeArray[n1 - 1]);
      nodeArray[n2 - 1].children.add(edge);
    }

    //Solve each Question
    int[] answerArray = new int[Q];

    for (int i = 0; i < Q; i++) {
      st = new StringTokenizer(br.readLine());
      int minRelevance = Integer.parseInt(st.nextToken());
      int videoIndex = Integer.parseInt(st.nextToken()) - 1;
      answerArray[i] = BFS(minRelevance,nodeArray[videoIndex]);
      
      //Reset Visiting States
      for (int j = 0; j < N; j++) {
        nodeArray[j].visited = false;
      }
    }

    //mootube.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));
    for (int i = 0; i < Q; i++) {
      pw.print(answerArray[i]);
      if (i < Q - 1) {
        pw.print("\n");
      }
    }
    pw.close();
  }

  public static int BFS(int K, Node root) {
    int relevantVideos = 0;
    //Initialize Queue
    Queue<Node> q = new LinkedList<Node>();
    q.add(root);
    root.visited = true;

    //BFS Algorithm
    while (! q.isEmpty()) {
      Node n = q.remove();
      for (Edge item: n.children) {
        if (! (item.cNode.visited || K > item.relevance)) {
          q.add(item.cNode);
          relevantVideos++;
          item.cNode.visited = true;
        }
      }
    }

    return relevantVideos;
  }
}

class Edge {
  //Instance Variables
  public int relevance;
  public Node cNode;

  //Constructor Method
  public Edge(int relevanceInput, Node cNodeInput) {
    relevance = relevanceInput;
    cNode = cNodeInput;
  }

}

class Node {
  //Instance Variables
  public int value;
  public ArrayList<Edge> children;
  public boolean visited;

  //Constructor Method
  public Node(int valueInput) {
    value = valueInput;
    children = new ArrayList<Edge>();
    visited = false;
  }

  public String toString() {
    return "Node" + value;
  }

}