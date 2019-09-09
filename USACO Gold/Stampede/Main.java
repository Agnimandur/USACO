import java.io.*; 
import java.math.*;
import java.util.*;
 
public class Main {
    public static void main(String[] args) throws IOException {
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("stampede.out")));
    	FastScanner sc = new FastScanner("stampede.in");
    	int N = sc.nextInt();
      int[][] events = new int[2*N][3];
    	//columns: type (s==0,e==1) | time | cow no.
    	for (int i = 0; i < N; i++) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        int r = sc.nextInt();
        events[2*i] = new int[]{0,Math.abs(x+1)*r,y};
        events[2*i+1] = new int[]{1,Math.abs(x)*r,y};
    	}
    	events = sort(events);

    	int yBar = Integer.MAX_VALUE;
    	HashSet<Integer> visible = new HashSet<Integer>();
    	TreeSet<Integer> crossing = new TreeSet<Integer>();
    	int time = 0;
      int index = 0;
      
      outer:
      while (index < 2*N) {
        while (events[index][1] == time) {
          if (events[index][0] == 0)
            crossing.add(events[index][2]);
          else
            crossing.remove(events[index][2]);
          index++;
          if (index == 2*N)
            break outer;
        }
        time = events[index][1];
        if (! crossing.isEmpty()) {
          for (int y: crossing) {
            yBar = y;
            break;
          }
          visible.add(yBar);
        } else {
          yBar = Integer.MAX_VALUE;
        }
      }
    	
    	int ans = visible.size();
    	//stampede.out
      pw.println(ans);
      pw.close();
    }
    
    public static int[][] sort(int[][] array) {
    	//Sort an array (immune to quicksort TLE)
		  Random rgen = new Random();
		  for (int i = 0; i< array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int[] temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
	  	}
		  Arrays.sort(array, new Comparator<int[]>() {
			  @Override
			  public int compare(int[] arr1, int[] arr2) {
          return arr1[1] - arr2[1];
			  }
		  });
		  return array;
	  }
    
  static class FastScanner { 
    BufferedReader br; 
    StringTokenizer st; 
  
    public FastScanner(String filename) throws IOException { 
      br = new BufferedReader(new FileReader(filename));
    }
  
    String next() { 
      while (st == null || !st.hasMoreElements()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          e.printStackTrace(); 
        } 
      } 
      return st.nextToken(); 
    } 
  
    int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    double nextDouble() { 
      return Double.parseDouble(next()); 
    } 
  
    String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        e.printStackTrace(); 
      } 
      return str; 
    }
  }
}