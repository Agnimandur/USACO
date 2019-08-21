import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("sort.in"));
    int N = Integer.parseInt(br.readLine());
    int[] A = new int[N];
    int[][] sorted = new int[N][2];
    for (int i = 0; i < N; i++) {
      A[i] = Integer.parseInt(br.readLine());
      sorted[i][0] = A[i];
      sorted[i][1] = i;
    }
    Arrays.sort(sorted, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
      }
    });

    int moo = 0;
    BinaryIndexedTree bit = new BinaryIndexedTree(N);
    for (int line = 0; line < N-1; line++) {
      int num = sorted[line][0];
      int index = sorted[line][1];
      bit.add(1,index);
      moo = Math.max(moo,bit.sum(line+1,N));
    }

    //sort.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
    pw.println(Math.max(moo,1));
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
}

class BinaryIndexedTree {
  private int[] arr;

  public BinaryIndexedTree (int N) {
    arr = new int[N+1];
    arr[0] = 0;
  }

  //add k to the i-th element.
  public void add(int k, int i) {
    int node = i+1;
    while (node < arr.length) {
      arr[node] += k;
      node += node & (-node);
    }
  }

  //sum up the elements from input[s_i] to input[e_i], from [s_i,e_i).
  public int sum(int s_i, int e_i) {
    return sum(e_i) - sum(s_i);
  }

  public int sum(int i) {
    int total = 0;
    int node = i;
    while (node > 0) {
      total += arr[node];
      node -= node & (-node);
    }
    return total;
  }

  public int[] getArr() {
    return arr;
  }
}