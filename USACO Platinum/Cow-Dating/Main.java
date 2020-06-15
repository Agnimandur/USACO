import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "cowdate";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    double[] opp = new double[N];
    double[] nums = new double[N];
    for (int i = 0; i < N; i++) {
      double d = sc.nd();
      opp[i] = 1-(d/1000000);
      nums[i] = d/(1000000-d);
    }
    //System.out.println(Arrays.toString(opp));
    //System.out.println(Arrays.toString(nums));
    double runProd = 1;
    double runSum = 0;
    double ans = 0;
    int L = 0;
    int R = 0;
    while (L < N) {
      while (R < N && runSum < 1.000001) {
        runProd *= opp[R];
        runSum += nums[R];
        R++;
        ans = Math.max(ans,runProd*runSum);
      }
      runProd /= opp[L];
      runSum -= nums[L];
      L++;
    }
    pw.println((int)Math.round(1000000*ans));
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