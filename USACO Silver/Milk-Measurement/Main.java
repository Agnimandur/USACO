import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("measurement.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));
    int N = sc.nextInt();
    int G = sc.nextInt();
    HashMap<Integer,Integer> idToNum = new HashMap<Integer,Integer>();
    int num = 1;
    int[][] events = new int[N][3];
    for (int i = 0; i < N; i++) {
      events[i][0] = sc.nextInt(); //day
      events[i][1] = sc.nextInt(); //id
      if (! idToNum.containsKey(events[i][1])) {
        idToNum.put(events[i][1],num);
        num++;
      }
      events[i][1] = idToNum.get(events[i][1]);
      String change = sc.next();
      events[i][2] = Integer.parseInt(change.substring(1));
      if (change.charAt(0)=='-') {
        events[i][2] *= -1;
      }
    }
    events = sort(events);
    int[] cows = new int[num];
    for (int i = 0; i < num; i++) {
      cows[i] = G;
    }
    TreeMap<Integer,Integer> measurements = new TreeMap<Integer,Integer>();
    //key is the milk amount
    //val is the number of cows at that amount
    //imagine a cow numbered "0" whose milk is always equal to G.
    measurements.put(G,num);
    int numAtMax = num;
    int max = G;
    int ans = 0;
    for (int[] event: events) {
      int cow = event[1];
      int change = event[2];
      if (change > 0) {
        if (cows[cow] == max && numAtMax > 1) {
          ans++;
        } else if (cows[cow] < max && cows[cow] + change >= max) {
          ans++;
        }
      } else if (change < 0) {
        if (cows[cow] == max && numAtMax > 1) {
          ans++;
        } else if (cows[cow] == max && numAtMax == 1 && cows[cow] + change <= measurements.floorKey(max-1)) {
          ans++;
        }
      }

      //Update measurements and cows
      int numAtRemoval = measurements.get(cows[cow]);
      if (numAtRemoval == 1) {
        measurements.remove(cows[cow]);
      } else {
        measurements.put(cows[cow],numAtRemoval-1);
      }

      cows[cow] += change;
      if (measurements.containsKey(cows[cow])) {
        measurements.put(cows[cow],measurements.get(cows[cow])+1);
      } else {
        measurements.put(cows[cow],1);
      }

      max = measurements.lastKey();
      numAtMax = measurements.get(max);
    }

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