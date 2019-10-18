import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("lightsout.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lightsout.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[][] vertex = new int[N][2];
    for (int i = 0; i < N; i++) {
      vertex[i][0] = sc.nextInt();
      vertex[i][1] = sc.nextInt();
    }
    int[][] edgeDirs = new int[N][2];
    //[0] is length // [1] is {0-N,1-E,2-S,3-W}
    int sum = 0;
    for (int i = 0; i < N; i++) {
      int deltaX = vertex[(i+1)%N][0]-vertex[i][0];
      int deltaY = vertex[(i+1)%N][1]-vertex[i][1];
      edgeDirs[i][0] = Math.abs(deltaX)+Math.abs(deltaY);
      sum += edgeDirs[i][0];
      if (deltaY > 0)
        edgeDirs[i][1] = 0;
      else if (deltaX > 0)
        edgeDirs[i][1] = 1;
      else if (deltaY < 0)
        edgeDirs[i][1] = 2;
      else
        edgeDirs[i][1] = 3;
    }
    int[] dist = new int[N];
    for (int i = 1; i < N; i++)
      dist[i] = dist[i-1]+edgeDirs[i-1][0];
    for (int i = 1; i < N; i++)
      dist[i] = Math.min(dist[i],sum-dist[i]);

    ArrayList<Integer> barn = new ArrayList<Integer>(2*N-1);
    for (int i = 0; i < 2*N-1; i++) {
      if (i % 2 == 0) {
        barn.add(edgeDirs[i/2][0]); //edge
      } else {
        int dir1 = edgeDirs[i/2][1];
        int dir2 = edgeDirs[(i+1)/2][1];
        int angle;
        if (dir2 == (dir1 + 1)%4)
          angle = -90; //90 degree turn
        else
          angle = -270; //270 degree turn
        barn.add(angle);
      }
    }

    HashMap<List<Integer>,Integer> hm = new HashMap<List<Integer>,Integer>();
    for (int v1 = 1; v1 < N; v1++) {
      for (int v2 = v1; v2 < N; v2++) {
        List<Integer> sub = barn.subList(2*v1-1,2*v2);
        hm.put(sub,hm.getOrDefault(sub,0)+1);
      }
    }
    
    //System.out.println(barn);
    //System.out.println(hm);
    int ans = 0;
    for (int v = 1; v < N; v++) {
      ArrayList<Integer> path = new ArrayList<Integer>();
      path.add(barn.get(2*v-1));
      int d = 0;
      int curV = v;
      while (hm.get(path) > 1) {
        d += edgeDirs[curV][0];
        curV++;
        if (curV == N) {
          curV = 0;
          break;
        }
        path.add(barn.get(2*curV-2));
        path.add(barn.get(2*curV-1));
      }
      d += dist[curV];
      ans = Math.max(ans,d - dist[v]);
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