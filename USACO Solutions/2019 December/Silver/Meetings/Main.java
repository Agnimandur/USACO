import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("meetings.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("meetings.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int L = sc.ni();
    int[][] cows = new int[N][3];
    for (int i = 0; i < N; i++) {
      cows[i] = new int[]{sc.ni(),sc.ni(),sc.ni()};
    }
    Arrays.sort(cows,(int[] a, int[] b)->a[1]-b[1]);
    ArrayList<Integer> left = new ArrayList<Integer>();
    ArrayList<Integer> right = new ArrayList<Integer>();
    int weight = 0;
    for (int i = 0; i < N; i++) {
      if (cows[i][2]==-1) {
        left.add(cows[i][1]);
      } else {
        right.add(cows[i][1]);
      }
      weight += cows[i][0];
    }
    int[] times = new int[N];
    for (int i = 0; i < left.size(); i++) {
      times[i] = left.get(i);
    }
    for (int i = left.size(); i < N; i++) {
      times[i] = L-right.get(i-left.size());
    }
    int low = 0;
    int high = L;
    while (low < high) {
      int mid = (low+high)/2;
      int w = 0;
      for (int i = 0; i < N; i++) {
        if (times[i] <= mid) {
          //cow i has already reached a barn
          w += cows[i][0];
        }
      }
      if (w >= weight/2.0) {
        high = mid;
      } else {
        low = mid+1;
      }
    }
    int T = low;

    long ans = 0;

    int Lindex = 0;
    int Rindex = 0;
    left.add(Integer.MAX_VALUE);
    for (int i = 0; i < right.size(); i++) {
      while (Lindex < left.size()-1 && left.get(Lindex) < right.get(i)) {
        Lindex++;
      }
      if (Lindex == left.size()-1) break;
      Rindex = Lindex;
      while (left.get(Rindex)-right.get(i) <= 2*T) {
        Rindex++;
      }
      ans += (Rindex-Lindex);
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