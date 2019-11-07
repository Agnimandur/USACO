import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("circlecross.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[] firstSeen = new int[N];
    Arrays.fill(firstSeen,-1);
    long ans = 0;
    BinaryIndexedTree bit = new BinaryIndexedTree(2*N);
    for (int i = 0; i < 2*N; i++) {
      int cow = sc.nextInt()-1;
      if (firstSeen[cow] == -1) {
        firstSeen[cow] = i;
        bit.add(1,i);
      } else {
        ans += (bit.sum(firstSeen[cow],i) - 1);
        bit.add(-1,firstSeen[cow]);
      }
    }

    pw.println(ans);
    pw.close();
  }

  static class BinaryIndexedTree {
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