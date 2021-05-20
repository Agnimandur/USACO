import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("auto.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("auto.out")));
    FastScanner sc = new FastScanner(is);
    int W = sc.nextInt();
    int N = sc.nextInt();
    String[][] words = new String[W][2];
    for (int i = 0; i < W; i++) {
      words[i][0] = sc.next();
      words[i][1] = Integer.toString(i+1);
    }
    Arrays.sort(words, new Comparator<String[]>() {
      @Override
      public int compare(String[] arr1, String[] arr2) {
        return arr1[0].compareTo(arr2[0]);
      }
    });
    //System.out.println(Arrays.deepToString(words));
    for (int i = 0; i < N; i++) {
      int K = sc.nextInt();
      String find = sc.next();

      //Binary Search for first finishing word.
      int low = 0;
      int high = W;
      while (low < high) {
        int mid = (low+high)/2;
        int val = words[mid][0].indexOf(find);
        if (val == 0) {
          high = mid;
        } else {
          int comp = words[mid][0].compareTo(find);
          if (comp > 0) {
            high = mid-1;
          } else {
            low = mid+1;
          }
        }
      }
      int ans = low+K-1;
      if (ans >= W) {
        pw.println(-1);
      } else {
        if (words[ans][0].indexOf(find) == 0) {
          pw.println(words[ans][1]);
        } else {
          pw.println(-1);
        }
      }
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