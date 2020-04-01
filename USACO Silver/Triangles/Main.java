import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("triangles.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("triangles.out")));
    FastScanner sc = new FastScanner(is);
    long MOD = 1000000007L;

    int N = sc.ni();
    ArrayList<Integer>[] Xs = new ArrayList[20001];
    ArrayList<Integer>[] Ys = new ArrayList[20001];
    ArrayList<Integer>[] xPref = new ArrayList[20001];
    ArrayList<Integer>[] yPref = new ArrayList[20001];
    for (int i = 0; i <= 20000; i++) {
      Xs[i] = new ArrayList<Integer>(); //all y-coordinates at x.
      Ys[i] = new ArrayList<Integer>(); //all x-coordinates at y.
      xPref[i] = new ArrayList<Integer>();
      yPref[i] = new ArrayList<Integer>();
    }
    int[][] points = new int[N][2];
    for (int i = 0; i < N; i++) {
      points[i][0] = sc.ni()+10000;
      points[i][1] = sc.ni()+10000;
      Xs[points[i][0]].add(points[i][1]);
      Ys[points[i][1]].add(points[i][0]);
    }

    for (int i = 0; i <= 20000; i++) {
      Collections.sort(Xs[i]);
      xPref[i].add(0);
      for (int j = 0; j < Xs[i].size(); j++) {
        xPref[i].add((xPref[i].get(j)+Xs[i].get(j)));
      }

      Collections.sort(Ys[i]);
      yPref[i].add(0);
      for (int j = 0; j < Ys[i].size(); j++) {
        yPref[i].add((yPref[i].get(j)+Ys[i].get(j)));
      }
    }

    long ans = 0;
    for (int i = 0; i < N; i++) {
      //let points[i] be the "right-angle" of the triangle.
      int xSize = Xs[points[i][0]].size();
      int ySize = Ys[points[i][1]].size();
      if (xSize == 1 || ySize == 1) {
        continue;
      }

      int xInd = binarySearch(0,xSize-1,points[i][1],Xs[points[i][0]]);
      int yInd = binarySearch(0,ySize-1,points[i][0],Ys[points[i][1]]);

      int ySum = ((yInd*points[i][0])-yPref[points[i][1]].get(yInd))+(yPref[points[i][1]].get(ySize)-yPref[points[i][1]].get(yInd)-(ySize-yInd)*points[i][0]);

      int xSum = ((xInd*points[i][1])-xPref[points[i][0]].get(xInd))+(xPref[points[i][0]].get(xSize)-xPref[points[i][0]].get(xInd)-(xSize-xInd)*points[i][1]);

      ans += ((xSum+0L)*(ySum+0L));
      ans %= MOD;
    }

    pw.println(ans);
    pw.close();
  }

  public static int binarySearch(int low, int high, int target, ArrayList<Integer> search) {
    if (low < high) {
      int med = (low+high)/2;
      int L = low;
      int H = high;
      if (search.get(med) < target) {
        L = med+1;
      } else  {
        H = med;
      }
      return binarySearch(L,H,target,search);
    } else {
      return low;
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