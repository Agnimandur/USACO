import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "cardgame";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[] nums = new int[N]; //the numbers played by elsie
    boolean[] used = new boolean[2*N];
    for (int i = 0; i < N; i++) {
      nums[i] = sc.ni()-1;
      used[nums[i]] = true;
    }
    TreeSet<Integer> low = new TreeSet<Integer>();
    TreeSet<Integer> high = new TreeSet<Integer>(); //the numbers played by Bessie
    for (int i = 0; i < 2*N; i++) {
      if (!used[i]) {
        low.add(i);
        high.add(i);
      }
    }
    int[] highPref = new int[N+1];
    for (int i = 1; i <= N; i++) {
      Integer higher = high.higher(nums[i-1]);
      if (higher == null) {
        highPref[i] = highPref[i-1];
      } else {
        highPref[i] = highPref[i-1]+1;
        high.remove(higher);
      }
    }

    int[] lowPref = new int[N+1];
    for (int i = N-1; i >= 0; i--) {
      Integer lower = low.lower(nums[i]);
      if (lower == null) {
        lowPref[i] = lowPref[i+1];
      } else {
        lowPref[i] = lowPref[i+1]+1;
        low.remove(lower);
      }
    }

    int ans = 0;
    for (int i = 0; i <= N; i++)
      ans = Math.max(ans,highPref[i]+lowPref[i]);

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