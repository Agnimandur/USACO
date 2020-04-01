import java.util.*;
import java.io.*;

class Main {

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("boards.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("boards.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.ni();
    int P = sc.ni();
    int[][] points = new int[P][4];
    long[] boardLength = new long[P];
    ArrayList<Integer> Y = new ArrayList<Integer>();
    for (int i = 0; i < P; i++) {
      for (int j = 0; j < 4; j++) {
        points[i][j] = sc.ni();
      }
      Y.add(points[i][1]);
      Y.add(points[i][3]);
      boardLength[i] = (long)(points[i][2]+points[i][3]-points[i][0]-points[i][1]);
    }
    Collections.sort(Y);
    //compress y points map
    HashMap<Integer,Integer> yMap = new HashMap<Integer,Integer>();
    yMap.put(Y.get(0),0);
    int M = 1;
    for (int i = 1; i < Y.size(); i++) {
      if (Y.get(i) > Y.get(i-1)) {
        yMap.put(Y.get(i),M);
        M++;
      }
    }

    //the seg tree will effectively store the "distance reduction" caused by taking a certain springboard.
    SegmentTree st = new SegmentTree(M);

    //process all events in a "linesweep" fashion, going from left to right, and then down to up.
    PriorityQueue<Event> pq = new PriorityQueue<Event>();
    for (int i = 0; i < P; i++) {
      pq.add(new Event(0,i,points[i][0],points[i][1]));
    }
    while (! pq.isEmpty()) {
      Event e = pq.poll();
      int yIndex = yMap.get(e.y);
      if (e.type == 0) {
        //this event is a springboard just beginning
        //segment tree RMQ on y coordinates less than or equal to current y coordinate. This represents jumping from the optimal springboard to this one.
        //The storage variable effectively keeps track of the "walking distance saved" by using the springboard.
        long storage = st.minQuery(1,1,M,1,yIndex+1)-boardLength[e.index];
        //add a type 1 event to later put the item in the segtree.
        Event endSpringboard = new Event(1,e.index,points[e.index][2],points[e.index][3]);
        endSpringboard.storage = storage;
        pq.add(endSpringboard);
      } else {
        //at this point in the linesweep, the springboard can be utilized in the segment tree, since we've now reached the board end point.
        long curSTValueAtY = st.minQuery(1,1,M,yIndex+1,yIndex+1);
        if (e.storage < curSTValueAtY) {
          st.add(1,1,M,yIndex+1,yIndex+1,(e.storage-curSTValueAtY));
        }
      }
    }
    //final return is (2*N + RMQ(segtree))
    long ans = 2*N+st.minQuery(1,1,M,1,M);
    pw.println(ans);
    pw.close();
  }

    static class SegmentTree {

    public long[][] ranges;

    public SegmentTree(int M) {
      ranges = new long[4*M+1][2];
    }

    //range update nums[updateL..updateR] += val;
    public void add(int n, int L, int R, int updateL, int updateR, long val) {
      push(n,L,R);
      if (updateL <= L && R <= updateR) {
        //fully contained
        ranges[n][1] += val;
        push(n,L,R);
        return;
      } else if (L>updateR || R<updateL || L==R) {
        //not contained at all or leaf
        return;
      }
      add(2*n,L,(L+R)/2,updateL,updateR,val); 
      add(2*n+1,(L+R)/2+1,R,updateL,updateR,val);
      ranges[n][0] = Math.min(ranges[2*n][0],ranges[2*n+1][0]);
    }

    public long minQuery(int n, int L, int R, int Lq, int Rq) {
      push(n,L,R);
      if (Lq <= L && R <= Rq) {
        return ranges[n][0];
      } else if (R < Lq || Rq < L || L==R) {
        return 1000000000000007L;
      } else {
        return Math.min(minQuery(2*n,L,(L+R)/2,Lq,Rq),minQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
      }
    }

    public void push(int n, int L, int R) {
      ranges[n][0] += ranges[n][1];
      
      if (L < R) {
        ranges[2*n][1] += ranges[n][1];
        ranges[2*n+1][1] += ranges[n][1];
      }

      ranges[n][1] = 0;
    }
  }

  static class Event implements Comparable<Event> {
    public int type; //0 begin, 1 represents end springboard
    public int index;
    public int x;
    public int y;
    public long storage; //dist-x-y
    public Event (int ty, int i, int xC, int yC) {
      type = ty;
      index = i;
      x = xC;
      y = yC;
      storage = 0;
    }

    public int compareTo(Event e) {
      if (this.x != e.x) {
        return (this.x - e.x);
      } else {
        return (this.y - e.y);
      }
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