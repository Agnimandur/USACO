import java.util.*;
import java.io.*;

class Main {

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cereal.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cereal.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int[][] cereals = new int[N+1][2];
    for (int i = 1; i <= N; i++) {
      cereals[i][0] = sc.ni()-1; //first choice
      cereals[i][1] = sc.ni()-1; //second choice
    }
    ArrayDeque<Integer>[] firstI = new ArrayDeque[M];
    ArrayDeque<Integer>[] secondI = new ArrayDeque[M];
    for (int i = 0; i < M; i++) {
      firstI[i] = new ArrayDeque<Integer>();
      firstI[i].add(0);
      secondI[i] = new ArrayDeque<Integer>();
    }
    firstI[cereals[1][0]].add(1);
    int[][] ans = new int[N+1][3];
    ans[0] = new int[]{-1,-1,-1};
    ans[1] = new int[]{0,-1,0}; //the first column is the minimum value such that person i will get his top choice. The second column is the minimum value such that person i will get his second choice. The third column is the first index that cow i can take something.

    for (int i = 2; i <= N; i++) {
      //Calculate up to what point the head of the list must be removed so that cow i gets his top choice.
      //The only two cows that matter is the previous cow to have flavor cereals[i][0] as his first choice, and the previous cow to have flavor cereals[i][0] has his second choice.
      int t1 = firstI[cereals[i][0]].peekLast();
      int t2 = 0;
      if (!secondI[cereals[i][0]].isEmpty()) {
        t2 = ans[secondI[cereals[i][0]].peekLast()][0];
      }
      int first = Math.max(t1,t2);

      //Do a similar thing for cow i's second choice.
      int t3 = firstI[cereals[i][1]].peekLast();
      int t4 = 0;
      if (!secondI[cereals[i][1]].isEmpty()) {
        t4 = ans[secondI[cereals[i][1]].peekLast()][0];
      }
      int second = Math.max(t3,t4);

      if (first > second) {
        ans[i] = new int[]{first,second,second};
        firstI[cereals[i][0]].add(i);
        secondI[cereals[i][1]].add(i);
      } else {
        ans[i] = new int[]{first,-1,first};
        firstI[cereals[i][0]].add(i);
      }
    }

    BinaryIndexedTree bit = new BinaryIndexedTree(N+1);
    for (int i = 1; i <= N; i++) {
      bit.add(1,ans[i][2]);
      bit.add(-1,i);
    }

    for (int i = 0; i < N; i++) {
      pw.println(bit.sum(i+1));
    }

    pw.close();
  }

  static class BinaryIndexedTree {
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

  static class FastScanner { 
    public BufferedReader br; 
    public StringTokenizer st; 
  
    public FastScanner(InputStream is) throws IOException { 
      br = new BufferedReader(new InputStreamReader(is),32768);
      st = null;
    }
  
    public String next() { 
      while (st == null || !st.hasMoreTokens()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          throw new RuntimeException(e);
        }
      } 
      return st.nextToken(); 
    } 
  
    public int ni() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nl() { 
      return Long.parseLong(next()); 
    } 
  
    public double nd() { 
      return Double.parseDouble(next()); 
    } 
  
    public String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        throw new RuntimeException(e);
      } 
      return str; 
    }
  }
}