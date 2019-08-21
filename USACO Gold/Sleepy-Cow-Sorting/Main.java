import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("sleepy.in"));
    int N = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());
    int[] cows = new int[N];
    for (int i = 0; i < N; i++) {
      cows[i] = Integer.parseInt(st.nextToken());
    }
    br.close();
    int[] minFromI = new int[N];
    minFromI[N-1] = cows[N-1];
    for (int i = N-2; i >= 0; i--)
      minFromI[i] = Math.min(minFromI[i+1],cows[i]);
    //keep track of the sorted cows, to the right of the red line.
    BinaryIndexedTree bit = new BinaryIndexedTree(N);
    //Find index of "red line" (aka K).
    int redLine = 0;
    for (int i = N-1; i >= 0; i--) {
      if (cows[i] > minFromI[i]) {
        redLine = i+1;
        break;
      } else {
        bit.add(1,cows[i]-1);
      }
    }

    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
    pw.println(redLine);

    //Move the unsorted cows to the left of the red line over to the right.
    for (int i = 0; i < redLine; i++) {
      int leftDistance = (redLine - i) - 1;
      int rightDistance = bit.sum(0,cows[i]);
      pw.print(leftDistance+rightDistance);
	  if (i < redLine-1) {
        pw.print(" ");
      }
      bit.add(1,cows[i]-1);
    }
	pw.println();
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