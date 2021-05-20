import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "balancing";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);
	
	//O(Nlog^2(N))
    int N = sc.ni();
    int M = 500000;
    //code to map Y coordinates to their new values
    int[] Ymap = new int[M];
    int[][] cows = new int[N][2];
    for (int i = 0; i < N; i++) {
      cows[i][0] = sc.ni();
      cows[i][1] = (sc.ni()-1)/2;
      if (Ymap[cows[i][1]]==0)
        Ymap[cows[i][1]] = 1;
    }
    for (int i = 1; i < M; i++) {
      Ymap[i] += Ymap[i-1];
    }
    int Y = Ymap[M-1]+1;
    //sweep a "red line" from left to right.
    BinaryIndexedTree left = new BinaryIndexedTree(Y);
    BinaryIndexedTree right = new BinaryIndexedTree(Y);
    for (int[] cow: cows) {
      right.add(1,Ymap[cow[1]]);
    }

    cows = sort(cows);
    int index = 0;
    int L = 0; //number of cows in left
    int R = N; //^ (right)
    int ans = N;

    while (index<N) {
      int curX = cows[index][0];
      while (index<N && cows[index][0]==curX) {
        left.add(1,Ymap[cows[index][1]]);
        right.add(-1,Ymap[cows[index][1]]);
        L++;
        R--;
        index++;
      }

      //now binary search for the position of the horizontal "green line". The MAX of the number of cows below the green line on the left and on the right is an increasing function. The MAX of the number of cows above the green line on the left and right is a decreasing function. Thus, we can binary search for the point of intersection.
      int low = 0;
      int high = Y-1;
      while (low < high-1) {
        int med = (low+high)/2;
        int p1 = left.sum(med+1);
        int p2 = right.sum(med+1);
        int below = Math.max(p1,p2);
        int above = Math.max(L-p1,R-p2);
        if (above > below) {
          low = med;
        } else {
          high = med;
        }
      }
      for (int g = low; g <= high; g++) {
        int p1 = left.sum(g+1);
        int p2 = right.sum(g+1);
        int below = Math.max(p1,p2);
        int above = Math.max(L-p1,R-p2);
        ans = Math.min(ans,Math.max(below,above));
      }
    }

    pw.println(ans);
    pw.close();
  }

  //0 indexed
  static class BinaryIndexedTree {
    public int[] arr;

    public BinaryIndexedTree (int N) {
      arr = new int[N+1];
    }

    //add k to the i-th element.
    public void add(int k, int i) {
      int node = i+1;
      while (node < arr.length) {
        arr[node] += k;
        node += node & (-node);
      }
    }

    //sum up the elements from input[s_i] to input[e_i], from [s_i,e_i).
    public int sum(int s_i, int e_i) {
      return sum(e_i) - sum(s_i);
    }

    public int sum(int i) {
      int total = 0;
      int node = i;
      while (node > 0) {
        total += arr[node];
        node -= node & (-node);
      }
      return total;
    }
  }

  public static int[][] sort(int[][] arr) {
    //Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < arr.length; i++) {
      int randomPosition = rgen.nextInt(arr.length);
      int[] temp = arr[i];
      arr[i] = arr[randomPosition];
      arr[randomPosition] = temp;
		}
    Arrays.sort(arr, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
        //sort the points by x-coordinate
      }
    });
    return arr;
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