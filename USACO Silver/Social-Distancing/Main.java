import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("socdist.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("socdist.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    long[][] nums = new long[M][2];
    for (int i = 0; i < M; i++) {
      nums[i][0] = sc.nl();
      nums[i][1] = sc.nl();
    }
    Arrays.sort(nums, new Comparator<long[]>() {
      @Override
      public int compare(long[] arr1, long[] arr2) {
        if (arr1[0] < arr2[0])
          return -1;
        else
          return 1;
      }
    });

    long low = 1;
    long high = (long)(1e18);
    while (low < high) {
      long D = (low+high+1)/2;
      long pos = Long.MIN_VALUE;
      long cnt = 0;

      for (long[] range: nums) {
        long cows = 0;
        if (range[0] >= pos+D) {
          cows = (range[1]-range[0]+D)/D;
          cnt += cows;
          pos = range[0]+(cows-1)*D;
        } else if (pos+D <= range[1]) {
          cows = (range[1]-(pos+D)+D)/D;
          cnt += cows;
          pos += cows*D;
        } else {
          //skip this range
          continue;
        }
      }

      if (cnt >= N) {
        low = D;
      } else {
        high = D-1;
      }
    }

    pw.println(low);
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