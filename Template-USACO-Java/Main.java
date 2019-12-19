import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("taskname.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taskName.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();

    pw.println(0);
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
Dijkstra (shortest path), Kruskal (disjoint set union MST), Prims (Dijkstra-esque MST distance)
Disjoint Set Union is good at "connecting" separated components together of a graph together.
Floyd Wurshall Algorithm (get shortest paths between all pairs of nodes in O(V^3) time.

Binary Indexed Tree (effectively a prefix sum with logN queries and logN updates).
Java Segment Tree (log N updates and logN range sum/min/max queries) - lazy propagation
*/