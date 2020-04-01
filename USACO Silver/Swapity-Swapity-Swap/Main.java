import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("swap.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("swap.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int K = sc.ni();

    int[] map = new int[N];
    for (int i = 0; i < N; i++)
      map[i] = i;
    
    for (int i = 0; i < M; i++) {
      int L = sc.ni()-1;
      int R = sc.ni()-1;
      ArrayList<Integer> rev = new ArrayList<Integer>();
      for (int j = L; j <= R; j++)
        rev.add(map[j]);
      Collections.reverse(rev);
      for (int j = L; j <= R; j++) {
        map[j] = rev.get(j-L);
      }
    }

    boolean[] vis = new boolean[N];
    int[][] info = new int[N][2]; //[0] is the cycle index, [1] is the pos in cycle.
    ArrayList<Integer>[] cycles = new ArrayList[N];
    for (int i = 0; i < N; i++)
      cycles[i] = new ArrayList<Integer>();
    
    for (int i = 0; i < N; i++) {
      if (! vis[map[i]]) {
        int num = map[i];
        while (! vis[num]) {
          info[num][0] = map[i];
          info[num][1] = cycles[map[i]].size();
          cycles[map[i]].add(num);
          vis[num] = true;
          num = map[num];
        }
      }
    }

    //System.out.println(Arrays.deepToString(info));

    //System.out.println(Arrays.toString(cycles));

    for (int i = 0; i < N; i++) {
      int ans = cycles[info[i][0]].get((info[i][1]+K)%(cycles[info[i][0]].size()))+1;
      pw.println(ans);
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