/*
ID: shivara2
LANG: JAVA
TASK: taskName
*/

import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("crowded.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("crowded.out")));
    int N = sc.nextInt();
    int D = sc.nextInt();
    TreeMap<Integer,Integer> left = new TreeMap<Integer,Integer>();
    TreeMap<Integer,Integer> right = new TreeMap<Integer,Integer>();
    int[][] cows = new int[N+1][2];
    for (int i = 0; i < N; i++) {
      cows[i][0] = sc.nextInt(); //pos
      cows[i][1] = sc.nextInt(); //height
    }
    cows[N] = new int[]{Integer.MAX_VALUE,0};
    cows = sort(cows);
    int Lindex = 0;
    int Rindex = 1;
    while (cows[Rindex][0] <= cows[0][0]+D) {
      if (! right.containsKey(cows[Rindex][1])) {
        right.put(cows[Rindex][1],1);
      } else {
        right.put(cows[Rindex][1],right.get(cows[Rindex][1])+1);
      }
      Rindex++;
    }
    left.put(cows[0][1],1);

    int crowded = 0;
    for (int i = 1; i < cows.length; i++) {
      if (cows[i-1][0]+D >= cows[i][0]) {
        //current cow needs to be removed from "right".
        if (right.get(cows[i][1]) == 1) {
          right.remove(cows[i][1]);
        } else {
          right.put(cows[i][1],right.get(cows[i][1])-1);
        }
      }

      //Update the "right" treeset.
      while (cows[Rindex][0] <= cows[i][0]+D) {
        if (! right.containsKey(cows[Rindex][1])) {
          right.put(cows[Rindex][1],1);
        } else {
          right.put(cows[Rindex][1],right.get(cows[Rindex][1])+1);
        }
        Rindex++;
      }

      //Update the left treeset
      while (cows[Lindex][0] < cows[i][0]-D) {
        if (left.get(cows[Lindex][1]) == 1) {
          left.remove(cows[Lindex][1]);
        } else {
          left.put(cows[Lindex][1],left.get(cows[Lindex][1])-1);
        }
        Lindex++;
      }

      //Check to see if the current cow is crowded
      if (! left.isEmpty() && ! right.isEmpty() && left.lastKey() >= 2*cows[i][1] && right.lastKey() >= 2*cows[i][1]) {
        crowded++;
      }

      //Add the current cow to the left treeset
      if (! left.containsKey(cows[i][1])) {
        left.put(cows[i][1],1);
      } else {
        left.put(cows[i][1],left.get(cows[i][1])+1);
      }
    }
    System.out.println(crowded);
    pw.println(crowded);
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
        //Ascending order.
        return arr1[0] - arr2[0];
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