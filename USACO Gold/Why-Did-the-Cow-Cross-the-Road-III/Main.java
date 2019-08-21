import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    //taskName.in
    BufferedReader br = new BufferedReader(new FileReader("circlecross.in"));
    int N = Integer.parseInt(br.readLine());
    int[] firstSeen = new int[N+1];
    for (int cow = 1; cow <= N; cow++)
      firstSeen[cow] = -1;
    BinaryIndexedTree bit = new BinaryIndexedTree(2*N);
    int numCrosses = 0;
    for (int i = 0; i < 2*N; i++) {
      int cross = Integer.parseInt(br.readLine());
      if (firstSeen[cross] >= 0) {
        //cow is exiting. remove the cow from the field by removing it from the bit.
        bit.add(-1,firstSeen[cross]);
        numCrosses += bit.sum(firstSeen[cross],i);
      } else {
        //cow is entering. add it to the bit/
        firstSeen[cross] = i;
        bit.add(1,i);
      }
    }

    br.close();

    //circlecross.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));
    pw.println(numCrosses);
    pw.close();
  }
}

class BinaryIndexedTree {
  private int[] arr;

  public BinaryIndexedTree (int len) {
    arr = new int[len+1];
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