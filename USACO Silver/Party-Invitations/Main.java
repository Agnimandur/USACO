import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("invite.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("invite.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int G = sc.nextInt();
    ArrayList<Integer>[] membership = new ArrayList[N];
    HashSet<Integer>[] groups = new HashSet[G];
    for (int i = 0; i < N; i++) {
      membership[i] = new ArrayList<Integer>();
    }
    for (int i = 0; i < G; i++) {
      int S = sc.nextInt();
      groups[i] = new HashSet<Integer>();
      for (int j = 0; j < S; j++) {
        int cow = sc.nextInt()-1;
        membership[cow].add(i);
        groups[i].add(cow);
      }
    }
    int ans = 0;
    Queue<Integer> q = new LinkedList<Integer>();
    boolean[] visited = new boolean[N];
    q.add(0);
    visited[0] = true;
    for (int i = 0; i < G; i++) {
      if (groups[i].size() == 1) {
        for (int last: groups[i]) {
          q.add(last);
          visited[last] = true;
          break;
        }
      }
    }
    //basically BFS
    while (! q.isEmpty()) {
      ans++;
      int cow = q.poll();
      for (int g: membership[cow]) {
        groups[g].remove(cow);
        if (groups[g].size() == 1) {
          for (int last: groups[g]) {
            if (! visited[last]) {
              q.add(last);
              visited[last] = true;
            }
            break;
          }
        }
      }
    }
    //System.out.println(ans);
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