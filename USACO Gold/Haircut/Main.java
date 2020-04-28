import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("haircut.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("haircut.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[] nums = new int[N];
    ArrayList<Integer>[] indices = new ArrayList[N+1];
    for (int i = 0; i <= N; i++)
      indices[i] = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      nums[i] = sc.ni();
      indices[nums[i]].add(i);
    }
    BinaryIndexedTree bit = new BinaryIndexedTree(N);
    for (int i = 0; i < N; i++)
      bit.add(1,i);
    
    long cur = 0;
    for (int n = 0; n < N; n++) {
      pw.println(cur);
      for (int i: indices[n]) {
        bit.add(-1,i);
      }
      for (int i: indices[n]) {
        cur += bit.sum(0,i);
      }
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