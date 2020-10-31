import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "deleg";
  static ArrayList<Integer>[] tree;

  static int[] dp;
  static boolean bad;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);


    int N = sc.ni();
    tree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      tree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N-1; i++) {
      int a = sc.ni()-1;
      int b = sc.ni()-1;
      tree[a].add(b);
      tree[b].add(a);
    }

    //If the graph is a star then solve it as an edge case.
    int star = -1;
    for (int i = 0; i < N; i++) {
      if (tree[i].size() > 2) {
        if (star==-1) {
          star = i;
        } else {
          star = -1;
          break;
        }
      }
    }

    ArrayList<Integer> sizes = new ArrayList<Integer>();
    if (star >= 0) {
      for (int r: tree[star]) {
        int p = star;
        int cur = r;
        int sz = 1;
        while (tree[cur].size()==2) {
          for (int next: tree[cur]) {
            if (next==p)continue;
            p = cur;
            cur = next;
            sz++;
            break;
          }
        }
        sizes.add(sz);
      }
    }

    dp = new int[N];
    int[] ans = new int[N-1];
    for (int k = 1; k < N; k++) {
      if ((N-1)%k!=0) continue;
      if (star >= 0) {
        ArrayList<Integer> match = new ArrayList<Integer>();
        for (int s: sizes) {
          int red = s%k;
          if (red>0)match.add(red);
        }
        int all = eval(match,0,k);
        if (all==0) ans[k-1] = 1;
        continue;
      }
      Arrays.fill(dp,-2);
      bad = false;
      int res = solve(0,-1,k);
      if (!bad && res==0)
        ans[k-1] = 1;
    }
    for (int a: ans)
      pw.print(a);
    pw.println();
    pw.close();
  }

  public static int solve(int n, int p, int k) {
    if (bad) return -1;
    if (dp[n] > -2) return dp[n];
    
    ArrayList<Integer> match = new ArrayList<Integer>();
    int ans = 0;
    for (int c: tree[n]) {
      if (c==p)continue;
      int s = (1+solve(c,n,k))%k;
      ans = (ans+s)%k;
      if (s>0) match.add(s);
    }
    if (bad) {
      dp[n] = -1;
      return -1;
    }
    ans = eval(match,ans,k);
    dp[n] = ans;
    if (ans==-1)bad = true;
    return ans;
  }

  public static int eval(ArrayList<Integer> match, int ans, int k) {
    //pair up loose paths
    boolean skipped = false;
    if (ans==0)skipped = true;
    Collections.sort(match);
    int L = 0;
    int R = match.size()-1;
    while (L < R) {
      if (!skipped) {
        if (match.get(L)==ans) {
          skipped = true;
          L++;
        } else if (match.get(R)==ans) {
          skipped = true;
          R--;
        }
        if (skipped)continue;
      }
      if (match.get(L)+match.get(R)==k) {
        L++;
        R--;
      } else {
        ans = -1;
        break;
      }
    }
    return ans;
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