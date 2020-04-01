import java.util.*;
import java.io.*;

class Main {
	//seed = 22 gets 9/10
	public static final int seed = 22;
  public static void main(String[] args) throws IOException {
    long time = System.currentTimeMillis();
    InputStream is = new FileInputStream("cbarn.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int K = sc.nextInt();
    long[] nums = new long[N];
    for (int i = 0; i < N; i++) {
      nums[i] = sc.nextLong();
    }
    //Measure the distance from i to j in clockwise order.
    long[][] dist = new long[N][N];
    for (int i = 0; i < N; i++) {
      int j = i;
      while (true) {
        if (j == i) {
          dist[i][j] = 0;
        } else {
          dist[i][j] = dist[i][(j-1+N)%N] + nums[j]*((j-i+N)%N);
        }
        j = (j+1)%N;
        if (j==i) break;
      }
    }
    
    long ans = Long.MAX_VALUE;
	int[] pos = new int[N];
	for (int i = 0; i < N; i++)
		pos[i] = i;
	pos = shuffle(pos);
    //n is the index of the "first door"
	int index = 0;
    while (index < N && System.currentTimeMillis()-time <= 3750) {
	  int n = pos[index];
	  index++;
      //dp[i][j] is the minimal distance to get all cows to the first "i" rooms (starting and including room n), while opening "j+1" doors.
      long[][] dp = new long[N][K];
      for (int i = 0; i < N; i++)
        dp[i][0] = dist[n][(n+i)%N];
      for (int j = 1; j < K; j++) {
        for (int i = j+1; i < N; i++) {
          //check all possible indices for where the "last opened door could be"
          int lastDoor = (n+1)%N;
          long val = dist[n][(n+i-1)%N];
          while (true) {
            //the dp transition is, knowing the last door, the cost is the dp of filling up the grid before the last door with one less door, plus the precomputed distance of bringing all cows past the last door to their rooms, up to the final room (n+i)%n.
            val = Math.min(val,dp[(lastDoor-n+N)%N-1][j-1]+dist[lastDoor][(n+i)%N]);
            lastDoor = (lastDoor+1)%N;
            if (lastDoor == (n+i)%N) break;
          }
          dp[i][j] = val;
        }
      }
      ans = Math.min(ans,dp[N-1][K-1]);
    }

    //The runtime of the code is O(N^3 * K)
    pw.println(ans);
    pw.close();
  }
  
	public static int[] shuffle(int[] array){
		Random rgen = new Random(seed);  // Random number generator			
 
		for (int i = 0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
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