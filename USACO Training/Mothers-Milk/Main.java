/*
ID: shivara2
LANG: JAVA
TASK: milk3
*/

import java.util.*;
import java.io.*;

class milk3 {
  public static void main(String[] args) throws IOException {
    // milk3.in
    BufferedReader br = new BufferedReader(new FileReader("milk3.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int[] inputArray = new int[3];
    for (int i = 0; i < 3; i++) {
      inputArray[i] = Integer.parseInt(st.nextToken());
    }
    br.close();

    //Capacities of buckets A,B,C
    int A = inputArray[0];
    int B = inputArray[1];
    int C = inputArray[2];

    //Initial State of the Buckets
    int[] rootValue = {0,0,C};
    Node Root = new Node(rootValue);
    boolean[][][] statesVisited = new boolean[C+1][C+1][C+1]; 

    // milk3.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
    ArrayList<Integer> answerAL = DFS(A,B,C,Root,statesVisited);
    for (int i = 0; i < answerAL.size(); i++) {
      pw.print(answerAL.get(i));
      if (i < answerAL.size() - 1) {
        pw.print(" ");
      }
    }
    pw.print("\n");
    pw.close();
  }

  public static ArrayList DFS(int A, int B, int C, Node root, boolean[][][] statesVisited) {
    //Initialize Stack
    ArrayList<Integer> answerAL = new ArrayList<Integer>();
    Stack<Node> s = new Stack<Node>();
    Node n = root;
    statesVisited[n.value[0]][n.value[1]][n.value[2]] = true;
    s.push(n);
    
    //Loop through DFS Algorithm
    while (s.isEmpty() == false) {
      n = s.pop();
      if (n.value[0] == 0) {
        answerAL.add(n.value[2]);
      }
      //Generate n's children
      ArrayList<Node> nChildren = new ArrayList<Node>();
      nChildren = generateChildren(A,B,C,n);
      for (Node item: nChildren) {
        if (!statesVisited[item.value[0]][item.value[1]][item.value[2]]) {
          statesVisited[item.value[0]][item.value[1]][item.value[2]] = true;
          s.push(item);
        }
      }
    }

    //Return Answer
    Collections.sort(answerAL);
    return answerAL;
  }

  public static ArrayList<Node> generateChildren(int A, int B, int C, Node n) {
    ArrayList<Node> childrenAL = new ArrayList<Node>();
    //A -> B
    if (n.value[0] <= B - n.value[1]) {
      int[] ABarray = {0,n.value[1] + n.value[0],n.value[2]};
      Node AB = new Node(ABarray);
      childrenAL.add(AB);
    } else if (B - n.value[1] > 0) {
      int[] ABarray = {n.value[0] - (B - n.value[1]),B,n.value[2]};
      Node AB = new Node(ABarray);
      childrenAL.add(AB);
    }

    //A -> C
    if (n.value[0] <= C - n.value[2]) {
      int[] ACarray = {0,n.value[1],n.value[2] + n.value[0]};
      Node AC = new Node(ACarray);
      childrenAL.add(AC);
    } else if (C - n.value[2] > 0) {
      int[] ACarray = {n.value[0] - (C - n.value[2]),n.value[1],C};
      Node AC = new Node(ACarray);
      childrenAL.add(AC);
    }

    //B -> A
    if (n.value[1] <= A - n.value[0]) {
      int[] BAarray = {n.value[0] + n.value[1],0,n.value[2]};
      Node BA = new Node(BAarray);
      childrenAL.add(BA);
    } else if (A - n.value[0] > 0) {
      int[] BAarray = {n.value[0] + n.value[1],0,n.value[2]};
      Node BA = new Node(BAarray);
      childrenAL.add(BA);
    }

    //B -> C
    if (n.value[1] <= C - n.value[2]) {
      int[] BCarray = {n.value[0],0,n.value[2] + n.value[1]};
      Node BC = new Node(BCarray);
      childrenAL.add(BC);
    } else if (C - n.value[2] > 0) {
      int[] BCarray = {n.value[0],n.value[1] - (C - n.value[2]),C};
      Node BC = new Node(BCarray);
      childrenAL.add(BC);
    }

    //C -> A
    if (n.value[2] <= A - n.value[0]) {
      int[] CAarray = {n.value[0] + n.value[2],n.value[1],0};
      Node CA = new Node(CAarray);
      childrenAL.add(CA);
    } else if (A - n.value[0] > 0) {
      int[] CAarray = {A,n.value[1],n.value[2] - (A - n.value[0])};
      Node CA = new Node(CAarray);
      childrenAL.add(CA);
    }

    //C -> B
    if (n.value[2] <= B - n.value[1]) {
      int[] CBarray = {n.value[0],n.value[1] + n.value[2],0};
      Node CB = new Node(CBarray);
      childrenAL.add(CB);
    } else if (B - n.value[1] > 0) {
      int[] CBarray = {n.value[0],B,n.value[2] - (B - n.value[1])};
      Node CB = new Node(CBarray);
      childrenAL.add(CB);
    }

    return childrenAL;
  }
}

class Node {
  //Instance Variables
  public int[] value;

  //Constructor Method
  public Node(int[] valueInput) {
    value = valueInput;
  }

  public String toString() {
    return "[" + value[0] + ", " + value[1] + ", " +value[2] + "]";
  }

}