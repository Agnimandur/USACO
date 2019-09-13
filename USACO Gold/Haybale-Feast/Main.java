import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("hayfeast.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hayfeast.out")));
    int N = sc.nextInt();
    long M = sc.nextLong();
    long[] flavorPrefix = new long[N+1];
    int[] spicy = new int[N];
    for (int i = 1; i <= N; i++) {
      flavorPrefix[i] = flavorPrefix[i-1] + sc.nextLong();
      spicy[i-1] = sc.nextInt();
    }

    int left = 0;
    int right = 1;
    int minSpicy = Integer.MAX_VALUE;
    TreeMap<Integer,Integer> spicyness = new TreeMap<Integer,Integer>(); //the spicyness of the foods in the meal
    while (true) {
      if (right == N+1) break;
      long flavor = flavorPrefix[right] - flavorPrefix[left];
      if (flavor < M) {
        if (spicyness.containsKey(spicy[right-1])) {
          spicyness.put(spicy[right-1],spicyness.get(spicy[right-1])+1);
        } else {
          spicyness.put(spicy[right-1],1);
        }
        right++;
      } else {
        minSpicy = Math.min(minSpicy,spicyness.lastKey());
        if (spicyness.get(spicy[left]) > 1) {
          spicyness.put(spicy[left],spicyness.get(spicy[left])-1);
        } else {
          spicyness.remove(spicy[left]);
        }
        left++;
      }
    }
    System.out.println(minSpicy);
    pw.println(minSpicy);
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

class Node {
	public HashSet<Node> children;
	public int n;
	
	public Node(int n) {
		this.n = n;
		children = new HashSet<Node>();
	}
	
	public void addChild(Node node) {
		children.add(node);
	}
	
	public void removeChild(Node node) {
		children.remove(node);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return n;
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Node)) {
			return false;
		} else {
			Node node = (Node) obj;
			return (n == node.n);
		}
	}
}