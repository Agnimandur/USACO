/*
ID: shivara2
LANG: JAVA
TASK: ariprog
*/

import java.util.*;
import java.io.*;

class ariprog {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("ariprog.in"));
    int N = Integer.parseInt(br.readLine());
    int M = Integer.parseInt(br.readLine());
    br.close();
    boolean[] biSquares = generateBiSquares(M);
    ArrayList<int[]> answerAL = new ArrayList<int[]>();
    answerAL = findSequences(N,M,biSquares);
    
    //ariprog.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
    if (answerAL.size() > 0) {
      for (int i = 0; i < answerAL.size(); i++) {
        pw.print(answerAL.get(i)[0]);
        pw.print(" ");
        pw.print(answerAL.get(i)[1]);
        pw.print("\n");
      }
    } else {
      pw.print("NONE\n");
    }
    pw.close();
  }

  public static boolean[] generateBiSquares(int M) {
    boolean[] biSquares = new boolean[2*M*M + 1];
    for (int p = 0; p < M + 1; p++) {
      for (int q = 0; q < M + 1; q++) {
        biSquares[p*p + q*q] = true;
      }
    }

    return biSquares;
  }

  public static ArrayList<int[]> findSequences(int N, int M, boolean[] biSquares) {
    //z1 and z2 are placeholder integers.
    int z1 = (int) ((2 * Math.pow(M,2))/(N-1));
    ArrayList<int[]> answerAL = new ArrayList<int[]>();

    for (int b = 1;b <= z1;b++) {
      int z2 = (int) ((2 * Math.pow(M,2)) - ((N-1) * b));
      for (int a = 0;a <= z2;a++) {
        if (sequenceInBiSquares(a,b,N,biSquares)) {
          int[] placeholderArray = {a,b};
          answerAL.add(placeholderArray);
        }
      }
    }

    return answerAL;
  }

  public static boolean sequenceInBiSquares(int a, int b, int N, boolean[] biSquares) {
    for (int i = 0; i < N; i++) {
      int z3 = a + (b*i);
      if (! biSquares[z3]) {
        return false;
      }
    }

    return true;
  }
}