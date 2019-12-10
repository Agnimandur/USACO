import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("nocross.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nocross.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int[] left = new int[N];
    for (int i = 0; i < N; i++) {
      left[i] = sc.nextInt()-1;
    }
    int[] indices = new int[N];
    int[] right = new int[N];
    for (int i = 0; i < N; i++) {
      right[i] = sc.nextInt()-1;
      indices[right[i]]=i;
    }

    long[] arr = new long[N];
    SegmentTree dp = new SegmentTree(arr);
    //the j-th value of the seg tree stores the currently calculated "dp" value for the first i on the left. Note that the runtime can be significantly shrunk since each cow on the left can only connect to a maximum of nine cows on the right.
    for (int i = 0; i < N; i++) {
      //consider connecting the i-th cow on the left with someone on the right.
      ArrayList<Integer> validIndices = new ArrayList<Integer>();
      for (int j = Math.max(left[i]-4,0); j <= Math.min(left[i]+4,N-1); j++) {
        validIndices.add(indices[j]);
      }
      //Proceeding in reverse order guarantees that you don't end up connecting a cow on the left to two cows on the right
      Collections.sort(validIndices,Collections.reverseOrder());

      for (int j: validIndices)  {
        //The seg tree currently stores the state of the gold version of dp[i-1]. to go to the next state, find the maximum of all of values in first j-1 elements of the segtree, and (since i-j is a valid connection, add 1)
        if (j > 0) {
          long newVal = dp.maxQuery(1,1,N,1,j)+1;
          long v = dp.maxQuery(1,1,N,j+1,j+1);
          if (newVal > v) {
            dp.add(1,1,N,j+1,j+1,newVal-v);
          }
        }
      }
    }
    pw.println(dp.maxQuery(1,1,N,1,N));
    pw.close();
  }

  static class SegmentTree {

    public long[][] ranges;

    public SegmentTree(long[] array) {
      ranges = new long[4*array.length+1][2];
      for (int i = 0; i < array.length; i++) {
        add(1,1,array.length,i+1,i+1,array[i]);
      }
    }

    //range update nums[updateL..updateR] += val;
    public void add(int n, int L, int R, int updateL, int updateR, long val) {
      push(n,L,R);
      if (updateL <= L && R <= updateR) {
        //fully contained
        ranges[n][1] += val;
        push(n,L,R);
        return;
      } else if (L>updateR || R<updateL || L==R) {
        //not contained at all or leaf
        return;
      }
      add(2*n,L,(L+R)/2,updateL,updateR,val); 
      add(2*n+1,(L+R)/2+1,R,updateL,updateR,val);
      ranges[n][0] = Math.max(ranges[2*n][0],ranges[2*n+1][0]);
    }

    public long maxQuery(int n, int L, int R, int Lq, int Rq) {
      push(n,L,R);
      if (Lq <= L && R <= Rq) {
        return ranges[n][0];
      } else if (R < Lq || Rq < L || L==R) {
        return -1L;
      } else {
        return Math.max(maxQuery(2*n,L,(L+R)/2,Lq,Rq),maxQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
      }
    }

    public void push(int n, int L, int R) {
      ranges[n][0] += ranges[n][1];
      
      if (L < R) {
        ranges[2*n][1] += ranges[n][1];
        ranges[2*n+1][1] += ranges[n][1];
      }

      ranges[n][1] = 0;
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