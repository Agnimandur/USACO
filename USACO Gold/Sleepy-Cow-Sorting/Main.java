import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("sleepy.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[] order = new int[N];
    for (int i = 0; i < N; i++)
      order[i] = sc.nextInt()-1;
    
    BinaryIndexedTree bit = new BinaryIndexedTree(N);
    //The binary indexed tree will hold an array of 0s and 1s. A one representations a cow present in the sorted list at the back.
    int cow = order[N-1];
    bit.add(1,cow);
    int T = 0;
    for (int i = N-2; i >= 0; i--) {
      if (order[i] < cow) {
        cow = order[i];
        bit.add(1,cow);
      } else {
        T = i+1;
        break;
      }
    }
    pw.println(T);
    for (int i = 0; i < T; i++) {
      int ans = (T-1-i)+bit.sum(0,order[i]+1);
      if (i < T-1)
        pw.print(ans + " ");
      else
        pw.println(ans);
      bit.add(1,order[i]);
    }
    pw.close();
  }

  static class BinaryIndexedTree {
    public int[] arr;

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
  
    public int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    public double nextDouble() { 
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