/*
ID: shivara2
LANG: JAVA
TASK: countcross
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //countcross.in
    BufferedReader br = new BufferedReader(new FileReader("countcross.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int R = Integer.parseInt(st.nextToken());
    Field[][] farm = new Field[N][N];
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < N; c++) {
        Field ground = new Field();
        //Edge of the Field
        if (r == 0) {
          ground.addN();
        }
        if (r == N-1) {
          ground.addS();
        }
        if (c == 0) {
          ground.addW();
        }
        if (c == N-1) {
          ground.addE();
        }

        farm[r][c] = ground;
      }
    }

    //Create all the roads
    for (int i = 0; i < R; i++) {
      st = new StringTokenizer(br.readLine());
      int row1 = Integer.parseInt(st.nextToken());
      int col1 = Integer.parseInt(st.nextToken());
      int row2 = Integer.parseInt(st.nextToken());
      int col2 = Integer.parseInt(st.nextToken());
      Field f1 = farm[row1 - 1][col1 - 1];
      Field f2 = farm[row2 - 1][col2 - 1];

      if (row1 == row2 && col1 < col2) {
        f1.addE();
        f2.addW();
      } else if (row1 == row2 && col1 > col2) {
        f1.addW();
        f2.addE();
      } else if (row1 < row2 && col1 == col2) {
        f1.addS();
        f2.addN();
      } else {
        f1.addN();
        f2.addS();
      }

      farm[row1 - 1][col1 - 1] = f1;
      farm[row2 - 1][col2 - 1] = f2;
    }

    //Initialize Regions and Floodfill
    int[][] region = new int[N][N];
    int regionNum = 0;
    for (int rRow = 0; rRow < N; rRow++) {
      for (int rCol = 0; rCol < N; rCol++) {
        if (region[rRow][rCol] == 0) {
          regionNum++;
          floodFillStack(N,farm,rRow + 1,rCol + 1,regionNum,region);
        }
      }
    }

    //Determine the positions of the Cows
    int[] numCowsInRegion = new int[regionNum];
    for (int i = 0; i < K; i++) {
      st = new StringTokenizer(br.readLine());
      int row = Integer.parseInt(st.nextToken());
      int col = Integer.parseInt(st.nextToken());
      int regionIn = region[row - 1][col - 1];
      numCowsInRegion[regionIn - 1]++;
    }
    br.close();

    //There are n(n-1)/2 distant pairs if all the cows are in different regions. From this initial number, subtract off all the pairs that are in the same region.
    int distantPairs = (K * (K - 1))/2;
    for (int item: numCowsInRegion) {
      distantPairs -= (item * (item - 1))/2;
    }

    //countcross.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("countcross.out")));
    pw.println(distantPairs);
    pw.close();
  }

  public static void floodFillStack(int N, Field[][] farm, int rootRow, int rootCol, int regionNum, int[][] region) {
    //Initialize Stack
    Stack<int[]> s = new Stack<int[]>();
    boolean[][] visited = new boolean[N][N];
    int[] root = {rootRow,rootCol};
    visited[rootRow - 1][rootCol - 1] = true;
    s.push(root);

    while (!s.isEmpty()) {
      root = s.pop();
      region[root[0] - 1][root[1] - 1] = regionNum;
      //S,E,N,W
      int[][] dirs = {{1,0,0},{0,1,1},{-1,0,2},{0,-1,3}};
      for (int[] item: dirs) {
        int newRow = root[0] + item[0];
        int newCol = root[1] + item[1];
        if (farm[root[0] - 1][root[1] - 1].roads[item[2]] == false && visited[newRow - 1][newCol - 1] == false) {
          visited[newRow - 1][newCol - 1] = true;
          int[] pushRoot = {newRow,newCol};
          s.push(pushRoot);
        }
      }
    }
  }
}
class Field {
  //Instance Variables
  public boolean[] roads;

  //Constructor Method
  //[S,E,N,W]
  public Field() {
    roads = new boolean[4];
  }

  public void addS() {
    roads[0] = true;
  }

  public void addE() {
    roads[1] = true;
  }

  public void addN() {
    roads[2] = true;
  }

  public void addW() {
    roads[3] = true;
  }
}