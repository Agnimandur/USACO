/*
ID: shivara2
LANG: JAVA
TASK: perimeter
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //perimeter.in
    BufferedReader br = new BufferedReader(new FileReader("perimeter.in"));
    int N = Integer.parseInt(br.readLine());
    String[][] blobs = new String[N][N];
    int[][] perimeters = new int[N][N];
    for (int i = 0; i < N; i++) {
      String stRow = br.readLine();
      for (int j = 0; j < N; j++) {
        blobs[i][j] = stRow.substring(j,j+1);
      }
    }

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (blobs[i][j].equals("#")) {
          int counter = 0;
          //Check Left
          if (j == 0 || blobs[i][j-1].equals(".")) {
            counter++;
          }
          //Check Right
          if (j == N-1 || blobs[i][j+1].equals(".")) {
            counter++;
          }
          //Check Up
          if (i == 0 || blobs[i-1][j].equals(".")) {
            counter++;
          }
          //Check Down
          if (i == N-1 || blobs[i+1][j].equals(".")) {
            counter++;
          }

          perimeters[i][j] = counter;
        }
      }
    }
    br.close();
    int[] answer = largestRegion(blobs,perimeters,N);
    //perimeter.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("perimeter.out")));
    pw.println(answer[0] + " " + answer[1] + "");
    pw.close();
  }

  public static int[] largestRegion(String[][] blobs, int[][] perimeters, int N) {
    boolean[][] visited = new boolean[N][N];
    ArrayList<Integer> regionAreas = new ArrayList<Integer>();
    ArrayList<Integer> regionPerimeters = new ArrayList<Integer>();
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        if (!visited[row][col] && blobs[row][col].equals("#")) {
          int[] results = findRegion(blobs,perimeters,visited,row,col,N);
          regionAreas.add(results[0]);
          regionPerimeters.add(results[1]);
        }
      }
    }
    
    int maxArea = 0;
    int bestPerimeter = 0;
    for (int i = 0; i < regionAreas.size(); i++) {
      if (regionAreas.get(i) > maxArea) {
        maxArea = regionAreas.get(i);
        bestPerimeter = regionPerimeters.get(i);
      }

      if (regionAreas.get(i) == maxArea) {
        bestPerimeter = Math.min(bestPerimeter,regionPerimeters.get(i));
      }
    }

    int[] answer = {maxArea,bestPerimeter};
    return answer;
  }

  public static int[] findRegion(String[][] blobs, int[][] perimeters, boolean[][] visited, int rootRow, int rootCol,int N) {
    int area = 0;
    int perimeter = 0;
    //Initialize Stack
    Stack<int[]> s = new Stack<int[]>();
    int[] root = {rootRow,rootCol};
    visited[rootRow][rootCol] = true;
    s.push(root);

    while (s.isEmpty() == false) {
      root = s.pop();
      area++;
      perimeter += perimeters[root[0]][root[1]];
      int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
      for (int[] item: dirs) {
        int newRow = root[0] + item[0];
        int newCol = root[1] + item[1];
        if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N && !visited[newRow][newCol] && blobs[newRow][newCol].equals("#")) {
          visited[newRow][newCol] = true;
          int[] pushRoot = {newRow,newCol};
          s.push(pushRoot);
        }
      }
    }
    int[] results = {area,perimeter};
    return results;
  }
}