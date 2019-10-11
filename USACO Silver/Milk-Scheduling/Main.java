import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  static int[] whenMilked;
  static ArrayList<Integer>[] beforeMe;
  static int[] times;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("msched.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("msched.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    times = new int[N];
    for (int i = 0; i < N; i++)
      times[i] = sc.nextInt();
    beforeMe = new ArrayList[N];
    for (int i = 0; i < N; i++)
      beforeMe[i] = new ArrayList<Integer>();
    for (int i = 0; i < M; i++) {
      int before = sc.nextInt()-1;
      int after = sc.nextInt()-1;
      beforeMe[after].add(before);
    }
    whenMilked = new int[N];
    Arrays.fill(whenMilked,-1);
    for (int i = 0; i < N; i++) {
      if (whenMilked[i] == -1)
        milk(i);
    }
    int ans = 0;
    for (int i = 0; i < N; i++)
      ans = Math.max(ans,whenMilked[i]);
    pw.println(ans);
    pw.close();
  }

  public static int milk(int i) {
    if (whenMilked[i] >= 0) {
      return whenMilked[i];
    } else {
      int maxWait = 0;
      for (int before: beforeMe[i]) {
        maxWait = Math.max(maxWait,milk(before));
      }
      //If beforeMe[i].size()==1 then maxWait=0
      whenMilked[i] = maxWait+times[i];
      return (maxWait+times[i]);
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