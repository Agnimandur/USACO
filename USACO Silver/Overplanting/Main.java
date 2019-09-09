/*
ID: shivara2
LANG: JAVA
TASK: taskName
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner();
    int N = sc.nextInt();
    long area = 0;
    int[][] rectangles = new int[N][4];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < 4; j++)
        rectangles[i][j] = sc.nextInt();
      area += ((long)(rectangles[i][2] - rectangles[i][0]))*(rectangles[i][1] - rectangles[i][3]);
    }
    for (int i = 0; i < N-1; i++) {
      for (int j = i+1; j < N; j++) {
        int[] rec1 = rectangles[i];
        int[] rec2 = rectangles[j];
        int xOverlap = 0;
        if (rec1[0] <= rec2[0] && rec2[0] <= rec1[2]) {
          xOverlap = Math.min(rec1[2],rec2[2]) - rec2[0];
        } else if (rec1[0] >= rec2[0] && rec2[0] >= rec1[2]) {
          xOverlap = Math.min(rec1[2],rec2[2]) - rec1[0];
        }
        int yOverlap = 0;
        if (rec1[3] <= rec2[3] && rec2[3] <= rec1[1]) {
          yOverlap = Math.min(rec1[1],rec2[1]) - rec2[3];
        } else if (rec1[3] >= rec2[3] && rec2[3] >= rec1[1]) {
          yOverlap = Math.min(rec1[1],rec2[1]) - rec1[3];
        }
        area -= (xOverlap * yOverlap);
      }
    }

    //planting.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("planting.out")));
    pw.println(area);
    pw.close();
  }
  
  public static void print(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }
  
  public static void print(int[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++)
        System.out.print(arr[i][j] + " ");
      System.out.println();
    }
  }

  static class FastScanner { 
    BufferedReader br; 
    StringTokenizer st; 
  
    public FastScanner() throws IOException { 
      br = new BufferedReader(new FileReader("planting.in"));
    }
  
    String next() { 
      while (st == null || !st.hasMoreElements()) { 
        try
        { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) 
        { 
          e.printStackTrace(); 
        } 
      } 
      return st.nextToken(); 
    } 
  
    int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    double nextDouble() { 
      return Double.parseDouble(next()); 
    } 
  
    String nextLine() { 
      String str = ""; 
      try
      { 
        str = br.readLine(); 
      } 
      catch (IOException e) 
      { 
        e.printStackTrace(); 
      } 
      return str; 
    }
  }
}