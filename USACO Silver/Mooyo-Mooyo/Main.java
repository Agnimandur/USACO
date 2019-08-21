import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("mooyomooyo.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int[][] board = new int[N][10];
    for (int i = 0; i < N; i++) {
      String row = br.readLine();
      for (int j = 0; j < 10; j++) {
        int cell = row.charAt(j);
        board[i][j] = cell-48; 
      }
    }
    br.close();

    while (true) {
      int[][] oldBoard = new int[N][10];
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < 10; j++) {
          oldBoard[i][j] = board[i][j];
        }
      }
      board = floodfill(board,N,K);
      board = gravity(board,N);
      boolean equal = true;
      for (int i = N-1; i >= 0; i--) {
        for (int j = 0; j < 10; j++) {
          if (board[i][j] != oldBoard[i][j]) {
            equal = false;
            break;
          }
        }
        if (! equal)
          break;
      }

      if (equal)
        break;
    }

    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mooyomooyo.out")));
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < 10; j++) {
        pw.print(board[i][j]);
      }
      pw.println();
    }
    pw.close();
  }

  public static int[][] floodfill(int[][] board, int N, int K) {
    int[][] newBoard = new int[N][10];
    boolean[][] visited = new boolean[N][10];
    int cellsVisited = 0;
    while (cellsVisited < 10 * N) {
      int[] root = new int[2];
      boolean complete = true;
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < 10; j++) {
          if (! visited[i][j]) {
            root[0] = i;
            root[1] = j;
            break;
          }
        }
        if (! visited[root[0]][root[1]]) {
          complete = false;
          break;
        }
      }
      if (complete)
        break;
      int color = board[root[0]][root[1]];
      int regionSize = 0;
      ArrayList<int[]> regionCells = new ArrayList<int[]>();

      //Initialize Floodfill Stack
      Stack<int[]> s = new Stack<int[]>();
      s.push(root);
      visited[root[0]][root[1]] = true;
      while (s.isEmpty() == false) {
        int[] n = s.pop();
        regionCells.add(n);
        regionSize++;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] item: dirs) {
          int newR = n[0] + item[0];
          int newC = n[1] + item[1];
          if (newR >= 0 && newR < N && newC >= 0 && newC < 10 && !visited[newR][newC] && board[newR][newC] == color) {
            visited[newR][newC] = true;
            int[] pushRoot = {newR,newC};
            s.push(pushRoot);
          }
        }
      }

      if (regionSize < K) {
        for (int[] cell: regionCells) {
          newBoard[cell[0]][cell[1]] = color;
        }
      }
      cellsVisited += regionSize;
    }

    return newBoard;
  }

  public static int[][] gravity(int[][] board, int N) {
    for (int j = 0; j < 10; j++) {
      int[] column = new int[N];
      int index = N-1;
      for (int i = N-1; i >= 0; i--) {
        if (board[i][j] > 0) {
          column[index] = board[i][j];
          index--;
        }
      }
      for (int i = 0; i < N; i++) {
        board[i][j] = column[i];
      }
    }
    return board;
  }
}