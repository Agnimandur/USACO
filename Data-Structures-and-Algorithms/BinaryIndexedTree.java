//0 indexed
public class BinaryIndexedTree {
  public int[] arr;

  public BinaryIndexedTree (int N) {
    arr = new int[N+1];
  }

  //add k to the i-th element.
  public void add(int k, int i) {
    int node = i+1;
    while (node < arr.length) {
      arr[node] += k;
      node += node & (-node);
    }
  }

  //sum up the elements from input[s_i] to input[e_i], from [s_i,e_i].
  public int sum(int s_i, int e_i) {
    return sum(e_i+1) - sum(s_i);
  }

  private int sum(int i) {
    int total = 0;
    int node = i;
    while (node > 0) {
      total += arr[node];
      node -= node & (-node);
    }
    return total;
  }
}

/*
//add and sum queries are one-indexed
public class BinaryIndexedTree2D {
  public int[][] arr;

  public BinaryIndexedTree2D (int N, int M) {
    arr = new int[N+1][M+1];
  }

  //add k to index [t,x]
  public void add(int t, int x, int k) {
    for (int i = t; i < arr.length; i += i & -i) {
      for (int j = x; j < arr[0].length; j += j & -j) {
        arr[i][j] += k;
      }
    }
  }

  //find the prefix sum up to index[t,x]
  public int sum(int t, int x) {
    int total = 0;
    for (int i = t; i > 0; i -= i & -i) {
      for (int j = x; j > 0; j -= j & -j) {
        total += arr[i][j];
      }
    }
    return total;
  }
}
*/