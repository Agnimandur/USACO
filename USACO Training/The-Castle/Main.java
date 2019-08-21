/*
ID: shivara2
LANG: JAVA
TASK: castle
*/

import java.util.*;
import java.io.*;

class castle {
  public static void main(String[] args) throws IOException{
    //castle.in
    BufferedReader br = new BufferedReader(new FileReader("castle.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int M = Integer.parseInt(st.nextToken());
    int N = Integer.parseInt(st.nextToken());
    Room[][] roomArray = new Room[N][M];
    for (int row = 0; row < N; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < M; col++) {
        roomArray[row][col] = new Room(Integer.parseInt(st.nextToken()));
      }
    }
    br.close();

    //Find largestRoom
    ArrayList<Integer> roomDimensionAL = largestRoom(roomArray);

    //Find room sizes with a wall removed
    ArrayList<String> answerAL = removeWalls(M,N,roomArray);

    //castle.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
    pw.println(roomDimensionAL.get(0));
    pw.println(roomDimensionAL.get(1));
    pw.println(answerAL.get(0));
    pw.println(answerAL.get(1));
    pw.close();
  }

  public static ArrayList<Integer> largestRoom(Room[][] roomArray) {
    boolean[][] visited = new boolean[roomArray.length][roomArray[0].length];
    ArrayList<Integer> roomDimensionAL = new ArrayList<Integer>();
    int maxRegionSize = 0;
    int numberOfRooms = 0;
    for (int row = 0; row < roomArray.length; row++) {
      for (int col = 0; col < roomArray[0].length; col++) {
        if (!visited[row][col]) {
          maxRegionSize = Math.max(maxRegionSize,findRegionSize(roomArray,visited,row,col));
          numberOfRooms++;
        }
      }
    }

    //return roomDimensionAL
    roomDimensionAL.add(numberOfRooms);
    roomDimensionAL.add(maxRegionSize);
    return roomDimensionAL;
  }

  public static int findRegionSize(Room[][] roomArray, boolean[][] visited, int rootX, int rootY) {
    int counter = 0;
    //Initialize Stack
    Stack<int[]> s = new Stack<int[]>();
    int[] root = {rootX,rootY};
    visited[rootX][rootY] = true;
    s.push(root);

    while (s.isEmpty() == false) {
      root = s.pop();
      counter++;
      //[S,E,N,W]
      int[][] dirs = {{1,0},{0,1},{-1,0},{0,-1}};
      for (int i = 0; i < 4; i++) {
        int newX = root[0] + dirs[i][0];
        int newY = root[1] + dirs[i][1];
        if (newX >= 0 && newX < roomArray.length && newY >= 0 && newY < roomArray[0].length && !visited[newX][newY]) {
          if (!roomArray[root[0]][root[1]].walls[i]) {
            visited[newX][newY] = true;
            int[] pushRoot = {newX,newY};
            s.push(pushRoot);
          }
        }
      }
    }

    return counter;
  }

  public static ArrayList<String> removeWalls(int M,int N,Room[][] roomArray) {
    int maxRemoveWall = 0;
    int optimalRow = -1;
    int optimalCol = -1;
    String direction = "";
    ArrayList<String> answerAL = new ArrayList<String>();
    for (int col = 0;col < M; col++) {
      for (int row = N-1; row >= 0; row--) {
        //Check North Wall
        if (roomArray[row][col].walls[2] && row > 0) {
          roomArray[row][col].walls[2] = false;
          roomArray[row-1][col].walls[0] = false;
          boolean[][] visited = new boolean[N][M];
          int n = findRegionSize(roomArray,visited,row,col);
          if (n > maxRemoveWall) {
            maxRemoveWall = n;
            optimalRow = row;
            optimalCol = col;
            direction = "N";

          }
          roomArray[row][col].walls[2] = true;
          roomArray[row-1][col].walls[0] = true;
        }

        //Check East Wall
        if (roomArray[row][col].walls[1] && col < M-1) {
          roomArray[row][col].walls[1] = false;
          roomArray[row][col+1].walls[3] = false;
          boolean[][] visited = new boolean[N][M];
          int n = findRegionSize(roomArray,visited,row,col);
          if (n > maxRemoveWall) {
            maxRemoveWall = n;
            optimalRow = row;
            optimalCol = col;
            direction = "E";
          }
          roomArray[row][col].walls[1] = true;
          roomArray[row][col+1].walls[3] = true;
        }
      }
    }

    //return answerAL
    answerAL.add(maxRemoveWall + "");
    optimalRow++;
    optimalCol++;
    answerAL.add(optimalRow + " " + optimalCol + " " + direction);
    return answerAL;
  }

  public static boolean inBounds(int M,int N,int row,int col,int wallDir) {
    return !((row == M-1 && wallDir == 0) || (col == N-1 && wallDir == 1) || (row == 0 && wallDir == 2) || (col == 0 && wallDir == 3));
  }
}

class Room {
  //Instance Variables
  public boolean[] walls;

  //Constructor Method
  //[S,E,N,W]
  public Room(int roomInput) {
    walls = new boolean[4];
    if (roomInput >= 8) {
      walls[0] = true;
      roomInput -= 8;
    }
    if (roomInput >= 4) {
      walls[1] = true;
      roomInput -= 4;
    }
    if (roomInput >= 2) {
      walls[2] = true;
      roomInput -= 2;
    }
    if (roomInput >= 1) {
      walls[3] = true;
    }
  }
}