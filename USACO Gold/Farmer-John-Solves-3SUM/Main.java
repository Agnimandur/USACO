import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("threesum.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("threesum.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int Q = sc.ni();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++) {
      nums[i] = sc.ni();
    }

    //dp transition: dp[i][j] = dp[i][j-1]+dp[i+1][j]-dp[i+1][j-1]+CNT.
    //CNT is the number of 3-SUMS that include index i, index j, and an index between i and j.
    HashSet<Integer> unique = new HashSet<Integer>();
    for (int num: nums)
      unique.add(num);
    HashMap<Integer,ArrayList<Integer>> prefixMap = new HashMap<Integer,ArrayList<Integer>>();
    for (int num: unique) {
      ArrayList<Integer> prefixSum = new ArrayList<Integer>(N+1);
      prefixSum.add(0);
      for (int i = 1; i <= N; i++) {
        if (nums[i-1]==num) {
          prefixSum.add(prefixSum.get(i-1)+1);
        } else {
          prefixSum.add(prefixSum.get(i-1));
        }
      }
      prefixMap.put(num,prefixSum);
    }

    long[][] dp = new long[N][N];
    for (int k = 2; k < N; k++) {
      for (int i = 0; i < N-k; i++) {
        int j = i+k;
        int target = -1*(nums[i]+nums[j]);
        int CNT = 0;
        if (prefixMap.containsKey(target)) {
          CNT = prefixMap.get(target).get(j)-prefixMap.get(target).get(i+1);
        }
        dp[i][j]=dp[i][j-1]+dp[i+1][j]-dp[i+1][j-1]+CNT;
      }
    }

    for (int q = 0; q < Q; q++) {
      int a = sc.ni()-1;
      int b = sc.ni()-1;
      pw.println(dp[a][b]);
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