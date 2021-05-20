import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "diamond";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int K = sc.ni();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++)
      nums[i] = sc.ni();
    Arrays.sort(nums);

    int[] last = new int[N];
    for (int i = 0; i < N; i++) {
      int lo = i;
      int hi = N-1;
      while (lo < hi) {
        int m = (lo+hi+1)/2; //last diamond that goes into a case whose first diamond is i.
        if (nums[m]-nums[i] <= K)
          lo = m;
        else
          hi = m-1;
      }
      last[i] = lo;
    }

    int[] maxsz = new int[N+1];
    for (int i = N-1; i >= 0; i--) {
      maxsz[i] = Math.max(maxsz[i+1],last[i]-i+1);
    }

    int ans = 0;
    for (int i = 0; i < N; i++) {
      ans = Math.max(ans,(last[i]-i+1)+maxsz[last[i]+1]);
    }

    pw.println(ans);
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