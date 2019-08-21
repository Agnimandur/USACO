import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int B = Integer.parseInt(st.nextToken());

    int[][] sortedTiles = new int[N][2];
    Tile[] tiles = new Tile[N];
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++) {
      sortedTiles[i][0] = Integer.parseInt(st.nextToken());
      sortedTiles[i][1] = i;
      Tile t = new Tile(sortedTiles[i][0]);
      tiles[i] = t;
    }
    Arrays.sort(sortedTiles, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr2[0]-arr1[0];
      }
    });
    //Set Previous
    for (int i = 1; i < N; i++) {
      tiles[i].setPrevious(tiles[i-1]);
    }
    //Set Next
    for (int i = N-2; i >= 0; i--) {
      tiles[i].setNext(tiles[i+1]);
    }

    int[][] boots = new int[B][3];
    for (int i = 0; i < B; i++) {
      st = new StringTokenizer(br.readLine());
      boots[i][0] = Integer.parseInt(st.nextToken());
      boots[i][1] = Integer.parseInt(st.nextToken());
      boots[i][2] = i;
    }
    br.close();
    Arrays.sort(boots, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr2[0]-arr1[0];
      }
    });
    //System.out.println("Initial Tiles: ");
    //print(tiles);
    int maxStepSize = 1;
    int pos = 0;
    int[] ans = new int[B];
    for (int[] boot: boots) {
      int s = boot[0];
      int d = boot[1];
      while (sortedTiles[pos][0] > s) {
        int i = sortedTiles[pos][1];
        Tile pTile = tiles[i].previous;
        Tile nTile = tiles[i].next;
        pTile.combineStepSize(tiles[i]);
        if (pTile.stepSize > maxStepSize)
          maxStepSize = pTile.stepSize;
        pTile.setNext(nTile);
        nTile.setPrevious(pTile);
        pos++;
      }
      if (maxStepSize <= d)
        ans[boot[2]] = 1;
      else
        ans[boot[2]] = 0;
      //System.out.println("Boot: " + s + " " + d);
      //System.out.println(maxStepSize);
      //print(tiles);
    }
    //print(ans);

    //snowboots.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
    for (int a: ans)
      pw.println(a);
    pw.close();
  }
  
  public static void print(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }

  public static void print(Tile[] arr) {
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
}

class Tile {
  public Tile previous;
  public int tile;
  public Tile next;
  public int stepSize;

  public Tile(int t) {
    previous = null;
    tile = t;
    next = null;
    stepSize = 1;
  }

  public void setPrevious(Tile p) {
    previous = p;
  }

  public void setNext(Tile n) {
    next = n;
  }

  public void combineStepSize(Tile t) {
    stepSize += t.stepSize;
  }

  public String toString() {
    /*if (previous == null)
      return ("[-1 " + tile + " " + next.tile + "]");
    else if (next == null)
      return ("[" + previous.tile + " " + tile + " -1]");
    else
      return ("[" + previous.tile + " " + tile + " " + next.tile + "]");*/
    return (stepSize+"");
  }
}