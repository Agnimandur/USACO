import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("paint.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("paint.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int K = sc.nextInt();
    //int K = 2; (bronze variant of problem)
    int[][] lines = new int[N][2];
    int pos = 0;
    for (int i = 0; i < N; i++) {
      int move = sc.nextInt();
      char dir = sc.next().charAt(0);
      lines[i][0] = pos;
      if (dir == 'L') {
        pos -= move;
      } else {
        pos += move;
      }
      lines[i][1] = pos;
    }
    ArrayList<Integer> begin = new ArrayList<Integer>(N+1);
    ArrayList<Integer> end = new ArrayList<Integer>(N+1);
    for (int i = 0; i < N; i++) {
      begin.add(Math.min(lines[i][0],lines[i][1]));
      end.add(Math.max(lines[i][0],lines[i][1]));
    }

    Collections.sort(begin);
    begin.add(Integer.MAX_VALUE);
    Collections.sort(end);
    end.add(Integer.MAX_VALUE);
    int b = 0;
    int e = 0;
    int k = 0;
    int area = 0;
    //System.out.println(begin);
    //System.out.println(end);
    while (! (b == N && e == N)) {
      int event = Math.min(begin.get(b),end.get(e));
      //System.out.print(event);
      while (begin.get(b)==event) {
        b++;
        k++;
      }
      while (end.get(e)==event) {
        e++;
        k--;
      }
      //System.out.println(" K = " + k);
      if (k >= K) {
        //This strip is good
        int nextEvent = Math.min(begin.get(b),end.get(e));
        area += (nextEvent - event);
      }
    }
    //System.out.println(area);

    pw.println(area);
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
  
    public int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    public double nextDouble() { 
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