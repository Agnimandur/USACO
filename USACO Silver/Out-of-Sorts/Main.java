import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("sort.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] arr = new int[N][2];
    for (int i = 0; i < N; i++) {
      int num = Integer.parseInt(br.readLine());
      arr[i][0] = num;
      arr[i][1] = i;
    }
    Arrays.sort(arr, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
      }
    });

    int moo = 0;
    for (int i = 0; i < arr.length; i++) {
      moo = Math.max(moo,arr[i][1] - i);
    }
    moo++;

    //sort.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
    pw.println(moo);
    pw.close();
  }
}