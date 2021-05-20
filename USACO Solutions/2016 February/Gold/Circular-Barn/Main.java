import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cbarn.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[] barns = new int[2*N];
    for (int i = 0; i  < N; i++) {
      barns[i] = sc.nextInt();
      barns[N+i] = barns[i];
    }

    int[] prefix = new int[N+1];
    prefix[N] = 0;
    for (int i = N-1; i >= 0; i--) {
      prefix[i] = prefix[i+1]+(barns[i]-1);
    }
    int maxPrefix = 0;
    int index = -1;
    for (int i = 0; i < N; i++) {
      if (prefix[i] > maxPrefix) {
        maxPrefix = prefix[i];
        index = i;
      }
    }

    int[] newBarns = new int[N];
    for (int i = index; i < index+N; i++)
      newBarns[i-index] = barns[i];
    //System.out.println(Arrays.toString(newBarns));\
    Queue<Integer> pending = new LinkedList<Integer>();
    long ans = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < newBarns[i]; j++)
        pending.add(i);
      long barn = (long)pending.remove();
      ans += ((barn-i)*(barn-i));
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