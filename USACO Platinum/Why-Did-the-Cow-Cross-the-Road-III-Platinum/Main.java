import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "friendcross";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int K = sc.ni();
    int[] first = new int[N];
    for (int i = 0; i < N; i++) {
      first[i] = sc.ni()-1;
    }

    int[] second = new int[N];
    for (int i = 0; i < N; i++) {
      second[i] = sc.ni()-1;
    }
    int[] indices = new int[N];
    for (int i = 0; i < N; i++)
      indices[second[i]] = i;

    int S = (int)Math.sqrt(N);
    int size = (N+S-1)/S;
    BinaryIndexedTree[] bits = new BinaryIndexedTree[size];
    BinaryIndexedTree total = new BinaryIndexedTree(N);
    boolean[] alive = new boolean[N];
    Arrays.fill(alive,true);

    for (int i = 0; i < size; i++) {
      bits[i] = new BinaryIndexedTree(N);
    }

    for (int i = 0; i < N; i++) {
      bits[second[i]/S].add(1,i);
      total.add(1,i);
    }
    long friendly = 0;
    long crossing = 0;
    for (int f = 0; f < N; f++) {
      int s = indices[first[f]];
      total.add(-1,s);
      int c = total.sum(s+1);
      crossing += c;

      bits[second[s]/S].add(-1,s);
      alive[second[s]] = false;

      int L = Math.max(0,second[s]-K);
      int R = Math.min(N-1,second[s]+K);

      if (L == 0 && R==N-1) {
        friendly += c;
        continue;
      }

      for (int i = 0; i < size; i++) {
        if (S*i <= L && R < S*(i+1)) {
          //[L,R] is entirely within this range
          for (int v = L; v <= R; v++) {
            if (alive[v] && indices[v] < s)
              friendly++;
          }
          break;
        } else if (S*(i+1)-1 < L) {
          //this range is to the left of [L,R] skip it.
          continue;
        } else if (S*i < L) {
          //this range is on the left edge of [L,R]. manually check it.
          for (int v = L; v < Math.min(N,S*(i+1)); v++) {
            if (alive[v] && indices[v] < s)
              friendly++;
          }
        } else if (S*i > R) {
          //this range is to the right of [L,R] skip it.
          continue;
        } else if (Math.min(S*(i+1)-1,N-1) > R) {
          //this range is on the right edge of [L,R]. manually check it.
          for (int v = S*i; v <= R; v++) {
            if (alive[v] && indices[v] < s)
              friendly++;
          }
        } else {
          //this range is entirely within [L,R]. use the seg tree.
          friendly += bits[i].sum(s+1);
        }
      }
    }
    long unfriendly = crossing-friendly;
    pw.println(unfriendly);
    pw.close();
  }

  //0 indexed
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