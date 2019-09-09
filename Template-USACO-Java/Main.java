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
    FastScanner sc = new FastScanner("taskName.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taskName.out")));
    int N = sc.nextInt();

    pw.println(0);
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

class BinaryIndexedTree {
	  public long[] arr;
 
	  public BinaryIndexedTree (int N) {
	    arr = new long[N+1];
	    arr[0] = 0;
	  }
 
	  //add k to the i-th element.
	  public void add(long k, int i) {
	    int node = i+1;
	    while (node < arr.length) {
	      arr[node] += k;
	      node += node & (-node);
	    }
	  }
 
	  //sum up the elements from input[s_i] to input[e_i], from [s_i,e_i).
	  public long sum(int s_i, int e_i) {
	    return sum(e_i) - sum(s_i);
	  }
 
	  public long sum(int i) {
	    long total = 0;
	    int node = i;
	    while (node > 0) {
	      total += arr[node];
	      node -= node & (-node);
	    }
	    return total;
	  }
}

class DisjointSetUnion {
	public int N;
	public int[] parent;
	public int[] rank;
	public int count;
	
	public DisjointSetUnion(int numNodes) {
		N = numNodes;
    parent = new int[N];
    rank = new int[N];
    for (int i = 0; i < N; i++) {
      parent[i] = i;
      rank[i] = 1;
    }
    count = numNodes;
	}
	
	public boolean isConnected(int p, int q) {
	  return root(p) == root(q);
	}
	
	public int root(int p) {
		while (p != parent[p]) {
      p = parent[p];
    }
	  return p;
	}
	
	//only connect p and q if they are disjointed.
	public void connect(int p, int q) {
		int rootP = root(p);
	  int rootQ = root(q);
	  if (rank[rootP] >= rank[rootQ]) {
	   	parent[rootQ] = rootP;
	   	rank[rootP] += rank[rootQ];
	  } else if (rank[rootQ] > rank[rootP]) {
	   	parent[rootP] = rootQ;
	   	rank[rootQ] += rank[rootP];
	  }
	  count--;
	}
}

/*Algorithms
DFS/BFS (Searching through a graph)
Binary Search (finding something in a sorted list. Commonly used to search through possibilities in a more efficient way.)
Floodfill (connected regions in 2D array)
Prefix sums (cumulative arrays, useful in dealing with contiguous blocks of stuff)

Stacks,Queues,Priority Queues, Objects (Class), HashMap, HashSet, Array List, Arrays
Sorting w/ custom comparator
Arrays.sort(arr, new Comparator<int[]>() {
  @Override
  public int compare(int[] arr1, int[] arr2) {
    return arr1[1]-arr2[1];
    //Ascending order.
  }
});


Prefix sums: 
Imagine there is a road and you are keeping track of whether there is a stop sign at each mile mark:
[0, 1, 1, 0, 1, 0, 0]

Now we want to efficiently count how many stop signs there are between various mile marks. We compute a cumulative frequency array:

cumFreq = [0, 1 ,2, 2, 3, 3, 3]

How many stop signs are there between index 1 and 3?

cumFreq[3]-cumFreq[0] (note that to make this inclusive you have to look at the indx below)

Graph Algorithms
Dijkstra, Kruskal, Prims (starred Repls)
*/