import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static HashMap<ArrayList<Integer>,Integer> groupings;
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("cowpatibility.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
    int N = sc.nextInt();
    groupings = new HashMap<ArrayList<Integer>,Integer>();
    for (int c = 0; c < N; c++) {
      int[] cow = new int[5];
      for (int i = 0; i < 5; i++)
        cow[i] = sc.nextInt()-1;
      Arrays.sort(cow);
      updateSubsets(cow);
    }
    long compatibility = 0;
    for (ArrayList<Integer> key: groupings.keySet()) {
      long val = groupings.get(key);
      if (key.size() % 2 == 1)
        compatibility += val*(val-1)/2;
      else
        compatibility -= val*(val-1)/2;
    }
    pw.println(N*((long)(N-1))/2 - compatibility);
    pw.close();
  }

  public static void updateSubsets(int[] cow) {
    for (int i = 1; i < 32; i++) {
      ArrayList<Integer> subset = new ArrayList<Integer>();
      for (int j = 0; j < 5; j++) {
        if ((i & (1 << j)) > 0)
          subset.add(cow[j]);
      }
      groupings.put(subset,groupings.getOrDefault(subset,0)+1);
    }
  }

  public static int[][] sort(int[][] array) {
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
        //Ascending order.
        if (arr1[1]!=arr2[1])
          return arr1[1]-arr2[1];
        else
          return arr2[0]-arr1[0];
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