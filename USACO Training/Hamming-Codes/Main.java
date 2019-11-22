/*
ID: shivara2
LANG: JAVA
TASK: hamming
*/

import java.util.*;
import java.io.*;

class hamming {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("hamming.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int B = sc.nextInt();
    int D = sc.nextInt();
    int[][] dists = new int[256][256];
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        int d = 0;
        for (int k = 0; k < 8; k++) {
          if ((i&(1<<k))!=(j&(1<<k))) {
            d++;
          }
        }
        dists[i][j] = d;
      }
    }

    TreeSet<Integer> nums = new TreeSet<Integer>();
    for (int i = 0; i < (1<<B); i++)
      nums.add(i);
    
    int[] ans = new int[N];
    for (int i = 0; i < N; i++) {
      ans[i] = nums.pollFirst();
      ArrayList<Integer> removals = new ArrayList<Integer>();
      for (int num: nums) {
        if (dists[ans[i]][num] < D) {
          removals.add(num);
        }
      }
      for (int removal: removals) {
        nums.remove(removal);
      }
    }

    for (int i = 1; i <= N; i++) {
      pw.print(ans[i-1]);
      if (i==N) {
        break;
      }
      if (i % 10 != 0) {
        pw.print(" ");
      } else {
        pw.println();
      }
    }
    pw.println();
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