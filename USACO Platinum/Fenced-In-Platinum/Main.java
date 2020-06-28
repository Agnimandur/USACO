import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "fencedin";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int A = sc.ni();
    int B = sc.ni();
    int N = sc.ni();
    int M = sc.ni();
    ArrayList<Integer> verLines = new ArrayList<Integer>();
    verLines.add(0);
    verLines.add(A);
    for (int i = 0; i < N; i++)
      verLines.add(sc.ni());
    Collections.sort(verLines);
    ArrayList<Integer> horLines = new ArrayList<Integer>();
    horLines.add(0);
    horLines.add(B);
    for (int i = 0; i < M; i++)
      horLines.add(sc.ni());
    Collections.sort(horLines);

    int[][] nums = new int[N+M+2][2];
    for (int i = 0; i <= N; i++) {
      nums[i][0] = verLines.get(i+1)-verLines.get(i);
      nums[i][1] = 0;
    }
    for (int i = 0; i <= M; i++) {
      nums[N+1+i][0] = horLines.get(i+1)-horLines.get(i);
      nums[N+1+i][1] = 1;
    }

    Arrays.sort(nums, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
      }
    });
    //System.out.println(Arrays.deepToString(nums));

    long ans = 0;
    int[] cnt = new int[2];
    long[] size = new long[]{(long)N,(long)M};
    for (int[] num: nums) {
      if (cnt[0]==0||cnt[1]==0)
        ans += size[1-num[1]]*num[0];
      else
        ans += (size[1-num[1]]-cnt[1-num[1]]+1)*num[0];
      cnt[num[1]] += 1;
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