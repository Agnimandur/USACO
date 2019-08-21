import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("bphoto.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] cows = new int[N][2];
    int[] ones = new int[N];
    for (int i = 0; i < N; i++) {
      int height = Integer.parseInt(br.readLine());
      cows[i][0] = height;
      cows[i][1] = i;
      ones[i] = 1;
    }
    br.close();
    
    BinaryIndexedTree bit = new BinaryIndexedTree(ones);
    Arrays.sort(cows, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
      }
    });

    int unbalanced = 0;
    for (int[] cow: cows) {
      int index = cow[1];
      bit.add(-1,index);
      int L_i = bit.sum(0,index);
      int R_i = bit.sum(index+1,cows.length);
      if (Math.max(L_i,R_i) > 2 * Math.min(L_i,R_i))
        unbalanced++;
    }

    //bphoto.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
    pw.println(unbalanced);
    pw.close();
  }
}

class BinaryIndexedTree {
  private int[] arr;

  public BinaryIndexedTree (int[] input) {
    arr = new int[input.length+1];
    arr[0] = 0;
    for (int i = 0; i < input.length; i++)
      add(input[i],i);
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
}