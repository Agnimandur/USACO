/*
ID: shivara2
LANG: JAVA
TASK: numtri
*/

import java.util.*;
import java.io.*;

class numtri {
  public static void main(String[] args) throws IOException{
    //numtri.in
    BufferedReader br = new BufferedReader(new FileReader("numtri.in"));
    int R = Integer.parseInt(br.readLine());

    //initialize tree
    int[][] tree = new int[R][R];
    for (int row = 0; row < R; row++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int col = 0; col <= row; col++) {
        tree[row][col] = Integer.parseInt(st.nextToken());
      }
    }

    //modify tree
    for (int i = 0; i < R-1;i++) {
      tree = changeRow(tree,R-i);
    }
    int maxSum = tree[0][0];

    //numtri.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
    pw.print(maxSum);
    pw.print("\n");
    pw.close();

  }

  public static int[][] changeRow(int[][] tree, int R) {
    for (int col = 0; col < R-1;col++) {
      tree[R-2][col] += Math.max(tree[R-1][col],tree[R-1][col+1]);
    }
    return tree;
  }
}