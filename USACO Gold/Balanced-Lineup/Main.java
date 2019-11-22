import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = System.in;
    PrintWriter pw = new PrintWriter(System.out);
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int Q = sc.nextInt();
    long[] heights = new long[N];
    for (int i = 0; i < N; i++)
      heights[i] = sc.nextLong();
    SegmentTree st = new SegmentTree(heights);
    for (int q = 0; q < Q; q++) {
      int L = sc.nextInt();
      int R = sc.nextInt();
      pw.println(st.maxQuery(1,1,N,L,R) - st.minQuery(1,1,N,L,R));
    }
    pw.close();
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

class SegmentTree {
  public Node[] ranges;

  public SegmentTree(long[] array) {
    ranges = new Node[4*array.length+1];
    for (int i = 0; i < ranges.length; i++) {
      ranges[i] = new Node();
    }
    for (int i = 0; i < array.length; i++) {
      add(1,i+1,1,array.length,array[i]);
    }
  }

  //Add val to idx
  public void add(int n, int idx, int L, int R, long val) {
    if (L <= idx && idx <= R) {
      if (L==R) {
        ranges[n].min += val;
        ranges[n].max += val;
        ranges[n].sum += val;
      } else {
        add(2*n,idx,L,(L+R)/2,val);
        add(2*n+1,idx,(L+R)/2+1,R,val);
        ranges[n].min = Math.min(ranges[2*n].min,ranges[2*n+1].min);
        ranges[n].max = Math.max(ranges[2*n].max,ranges[2*n+1].max);
        ranges[n].sum = ranges[2*n].sum + ranges[2*n+1].sum;
      }
    }
  }

  public long minQuery(int n, int L, int R, int Lq, int Rq) {
    if (Lq <= L && R <= Rq) {
      return ranges[n].min;
    } else if (R < Lq || Rq < L || L==R) {
      return 1000000000007L;
    } else {
      return Math.min(minQuery(2*n,L,(L+R)/2,Lq,Rq),minQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
    }
  }

  public long maxQuery(int n, int L, int R, int Lq, int Rq) {
    if (Lq <= L && R <= Rq) {
      return ranges[n].max;
    } else if (R < Lq || Rq < L || L==R) {
      return -1L;
    } else {
      return Math.max(maxQuery(2*n,L,(L+R)/2,Lq,Rq),maxQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
    }
  }

  public long sumQuery(int n, int L, int R, int Lq, int Rq) {
    if (Lq <= L && R <= Rq) {
      return ranges[n].sum;
    } else if (R < Lq || Rq < L || L==R) {
      return 0L;
    } else {
      return (sumQuery(2*n,L,(L+R)/2,Lq,Rq) + sumQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
    }
  }

  static class Node {
    public long min;
    public long max;
    public long sum;

    public Node() {
      min = 0;
      max = 0;
      sum = 0;
    }

    public String toString() {
      return ("[" + min + "," + max + "," + sum + "]");
    }
  }
}