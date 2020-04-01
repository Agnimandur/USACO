import java.util.*;
import java.io.*;

class Main {
  static int node;
  static ArrayList<Node>[] tree;
  static ArrayList<Integer>[] newTree;
  static int[][] bounds;
  static int[] map;
  static Node[] nodes;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("promote.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("promote.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.ni();
    tree = new ArrayList[N];
    nodes = new Node[N];
    for (int i = 0; i < N; i++) {
      tree[i] = new ArrayList<Node>();
      int profiency = sc.ni();
      nodes[i] = new Node(i,profiency);
    }
    for (int i = 1; i < N; i++) {
      int parent = sc.ni()-1;
      tree[parent].add(nodes[i]);
    }

    //pre-order traversal.
    nodes[0].preOrder = 0;
    node = 1;
    preOrderTraversal(0);

    //Create a new tree based on the pre-order
    newTree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      newTree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      for (Node child: tree[i]) {
        //Old tree has an edge (nodes[i].input,child.input)
        //New tree has an edge (nodes[i].preOrder,nodes[child].preOrder)
        newTree[nodes[i].preOrder].add(child.preOrder);
      }
    }

    //Determine bounds (the range over the pre-order that a node's children takes up)
    bounds = new int[N][2];
    for (int i = 0; i < N; i++) {
      bounds[i] = new int[]{-1,-1};
    }
    node = 1;
    dfs(0);

    //Iterate through the cows in the order of highestProfiency to lowest profiency.
    Arrays.sort(nodes);
    int[] ans = new int[N];

    //use a bit to count the number of subordinates with a higher profiency (in the range of a cow's children)
    BinaryIndexedTree bit = new BinaryIndexedTree(N);
    for (int i = 0; i < N; i++) {
      int subs = bit.sum(bounds[nodes[i].preOrder][0],bounds[nodes[i].preOrder][1]);
      ans[nodes[i].input] = subs;
      bit.add(1,nodes[i].preOrder);
    }

    for (int a: ans)
      pw.println(a);
    pw.close();
  }

  static class BinaryIndexedTree {
    public int[] arr;

    public BinaryIndexedTree (int N) {
      arr = new int[N+1];
      arr[0] = 0;
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

  public static void preOrderTraversal(int n) {
    for (Node neighbor: tree[n]) {
      if (neighbor.preOrder == -1) {
        neighbor.preOrder = node;
        node++;
        preOrderTraversal(neighbor.input);
      }
    }
  }

  public static void dfs(int n) {
    bounds[n][0] = node;
    for (int child: newTree[n]) {
      node++;
      dfs(child);
    }
    bounds[n][1] = node;
  }

  static class Node implements Comparable<Node> {
    public int input;
    public int preOrder;
    public int prof;
    public Node (int i, int p) {
      input = i;
      preOrder = -1;
      prof = p;
    }

    public String toString() {
      return ("[" + input + "," + preOrder + "]");
    }

    public int compareTo(Node n) {
      return (n.prof - this.prof);
    }
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