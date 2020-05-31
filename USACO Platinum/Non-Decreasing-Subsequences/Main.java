import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "nondec";
  public static final long MOD = 1000000007L;
  public static final long NEGH = 500000003L; //-0.5

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int K = sc.ni();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++)
      nums[i] = sc.ni();
    
    long[][][] bases = new long[K+1][K+1][K+1];
    long[][][] invBases = new long[K+1][K+1][K+1];
    for (int i = 1; i <= K; i++) {
      long[][] arr = new long[K+1][K+1];
      long[][] arr2 = new long[K+1][K+1];
      for (int r = 0; r <= K; r++) {
        arr[r][r] = 1;
        arr2[r][r] = 1;
      }
      for (int c = 0; c <= i; c++) {
        arr[i][c] += 1;
        arr2[i][c] += NEGH;
      }
        
      bases[i] = arr;
      invBases[i] = arr2;
    }

    long[][] usefulP = new long[N][K+1];
    long[][] lastP = new long[K+1][K+1];
    //prefs[6] = P6*P5*P4*P3*P2*P1

    long[][] usefulI = new long[N][K+1];
    long[][] lastI = new long[K+1][K+1];
    //invPrefs[4] = iP1*iP2*iP3*iP4

    for (int i = 0; i < N; i++) {
      long[][] curP = new long[K+1][K+1];
      long[][] curI = new long[K+1][K+1];
      if (i > 0) {
        for (int r = 0; r <= K; r++) {
          for (int c = 0; c <= K; c++) {
            curP[r][c] = lastP[r][c];
            curI[r][c] = lastI[r][c];
          }
        }
        for (int c = 0; c <= K; c++) {
          curP[nums[i]][c] = 0;
          for (int r = 0; r <= nums[i]; r++) {
            curP[nums[i]][c] += (bases[nums[i]][nums[i]][r]*lastP[r][c]);
          }
          curP[nums[i]][c] %= MOD;
        }

        for (int r = 0; r <= K; r++) {
          for (int c = 0; c <= nums[i]; c++) {
            curI[r][c] += (NEGH*lastI[r][nums[i]]);
            curI[r][c] %= MOD;
          }
        }
        //curP = matmul(bases[nums[i]],lastP);
        //curI = matmul(lastI,invBases[nums[i]]);
      } else {
        curP = bases[nums[0]];
        curI = invBases[nums[0]];
      }
      for (int r = 0; r <= K; r++) {
        for (int c = 0; c <= K; c++) {
          usefulP[i][c] += curP[r][c];
          usefulP[i][c] %= MOD;
        }
      }

      for (int r = 0; r <= K; r++) {
        usefulI[i][r] = curI[r][0];
      }

      lastP = curP;
      lastI = curI;
    }

    int Q = sc.ni();
    for (int q = 0; q < Q; q++) {
      int L = sc.ni()-1;
      int R = sc.ni()-1;
      long ans = 0;
      if (L == 0) {
        ans = usefulP[R][0];
      } else {
        for (int i = 0; i <= K; i++) {
          ans += (usefulP[R][i]*usefulI[L-1][i]);
          ans %= MOD;
        }
      }
      
      pw.println(ans);
    }
    pw.close();
  }

  static long[][] matmul(long[][] a, long[][] b) {
    int M = a.length;
    long[][] ans = new long[M][M];
    for (int i = 0; i < M; i++) { 
      for (int j = 0; j < M; j++) { 
        for (int k = 0; k < M; k++) {
          ans[i][j] += a[i][k]*b[k][j];
          ans[i][j] %= MOD;
        }
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