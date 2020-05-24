import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "snowcow";

  static ArrayList<Integer>[] old;
  static int preIndex;
  static int[] preMap;
  static ArrayList<Integer>[] tree;
  static int[][] ranges;
  static int[] children;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int Q = sc.ni();

    //preorder traversal
    old = new ArrayList[N];
    for (int i = 0; i < N; i++)
      old[i] = new ArrayList<Integer>();
    for (int i = 0; i < N-1; i++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      old[n1].add(n2);
      old[n2].add(n1);
    }
    ranges = new int[N][2];
    preMap = new int[N];
    children = new int[N];
    Arrays.fill(preMap,-1);
    preIndex = 0;
    preOrder(0);
    tree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      tree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      for (int j: old[i]) {
        tree[preMap[i]].add(preMap[j]);
      }
    }
    TreeSet<Integer>[] active = new TreeSet[100000]; //nodes active in color i.
    for (int i = 0; i  < 100000; i++)
      active[i] = new TreeSet<Integer>();

    //the first BIT is used to keep track of the number of "active" colors at a given node. The second BIT is used to keep track of the number of children covered by said "active colors"
    BinaryIndexedTree bit1 = new BinaryIndexedTree(N+1);
    BinaryIndexedTree bit2 = new BinaryIndexedTree(N);

    for (int q = 0; q < Q; q++) {
      int type = sc.ni();
      int node = preMap[sc.ni()-1];

      if (type == 1) {
        //update with paint bucket of color c.
        int c = sc.ni()-1;

        //if an ancestor of this node has been painted this color already, then skip.
        Integer prev = active[c].lower(node);
        if (prev != null && ranges[prev][1] >= node)
          continue;
        
        //remove descendants of the current node painted in the same color that are still active.
        ArrayList<Integer> removal = new ArrayList<Integer>();
        Integer r = active[c].higher(ranges[node][0]);
        while (r != null && r <= ranges[node][1]) {
          removal.add(r);
          r = active[c].higher(r);
        }

        for (int remove: removal) {
          //remove it from the first BIT
          bit1.add(-1,ranges[remove][0]);
          bit1.add(1,ranges[remove][1]+1);
          //remove it from the second BIT
          bit2.add((-1)*children[remove],remove);

          //remove it from the list of active nodes of color c.
          active[c].remove(remove);
        }


        //add the current node to the BITs.
        bit1.add(1,ranges[node][0]);
        bit1.add(-1,ranges[node][1]+1);
        bit2.add(children[node],node);

        //add it to the list of active nodes of color c.
        active[c].add(node);
      } else {
        //query

        //to answer the query, all the children of the node are painting x colors (given by the first BIT). Also, some individual subtrees that are descendants of node might have some extra painted children (given by the second BIT).
        long ans = children[node]*bit1.sum(ranges[node][0]+1) + bit2.sum(ranges[node][0]+1,ranges[node][1]+1);

        pw.println(ans);
      }
    }
    pw.close();
  }

  public static void preOrder(int node) {
    preMap[node] = preIndex;
    ranges[preMap[node]][0] = preIndex;
    preIndex++;
    int c = 1;
    for (int child: old[node]) {
      if (preMap[child] == -1) {
        preOrder(child);
        c += children[preMap[child]];
      }
    }
    children[preMap[node]] = c;
    ranges[preMap[node]][1] = preIndex-1;
  }

  //0 indexed
  static class BinaryIndexedTree {
    public long[] arr;

    public BinaryIndexedTree (int N) {
      arr = new long[N+1];
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