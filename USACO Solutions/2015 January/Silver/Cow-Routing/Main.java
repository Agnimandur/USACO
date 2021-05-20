import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cowroute.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowroute.out")));
    FastScanner sc = new FastScanner(is);

    int A = sc.nextInt()-1;
    int B = sc.nextInt()-1;
    int N = sc.nextInt();
    long[][] dists = new long[1000][1000];
    int[][] flights = new int[1000][1000];
    for (int i = 0; i < 1000; i++) {
      Arrays.fill(dists[i],Long.MAX_VALUE);
      Arrays.fill(flights[i],Integer.MAX_VALUE);
    }
    for (int r = 0; r < N; r++) {
      long cost = sc.nextLong();
      int numCities = sc.nextInt();
      int[] cities = new int[numCities];
      for (int i = 0; i < cities.length; i++)
        cities[i] = sc.nextInt()-1;
      for (int c1 = 0; c1 < numCities-1; c1++) {
        for (int c2 = c1+1; c2 < numCities; c2++) {
          if (cost < dists[cities[c1]][cities[c2]]) {
            dists[cities[c1]][cities[c2]] = cost;
            flights[cities[c1]][cities[c2]] = c2-c1;
          } else if (cost == dists[cities[c1]][cities[c2]]) {
            flights[cities[c1]][cities[c2]] = Math.min(flights[cities[c1]][cities[c2]],c2-c1);
          }
        }
      }
    }

    ArrayList<Integer>[] reachable = new ArrayList[1000];
    for (int i = 0; i < 1000; i++)
      reachable[i] = new ArrayList<Integer>();
    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 1000; j++) {
        if (i != j && dists[i][j] < Long.MAX_VALUE) {
          reachable[i].add(j);
        }
      }
    }

    //Dijkstra's Shortest Path Algorithm
    PriorityQueue<long[]> pq = new PriorityQueue<long[]>(new Comparator<long[]>(){
      public int compare(long[] a, long[] b) {
        if (a[1] > b[1])
          return 1;
        else if (a[1] < b[1])
          return -1;
        else
          return 0;
      }
    });
    long[] minDists = new long[1000];
    int[] minFlights = new int[1000];
    Arrays.fill(minDists,Long.MAX_VALUE);
    Arrays.fill(minFlights,Integer.MAX_VALUE);
    minDists[A] = 0L;
    minFlights[A] = 0;
    long[] temp = {A,0L};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = (int)temp[0];
      for (int child: reachable[parent]) {
        if (minDists[child] > minDists[parent] + dists[parent][child]) {
          minDists[child] = minDists[parent] + dists[parent][child];
          minFlights[child] = minFlights[parent] + flights[parent][child];
          long[] temp2 = {(long)child,minDists[child]};
          pq.add(temp2);
        } else if (minDists[child] == minDists[parent] + dists[parent][child]) {
          minFlights[child] = Math.min(minFlights[child],minFlights[parent] + flights[parent][child]);
        }
      }
    }
    if (minDists[B] == Long.MAX_VALUE)
      pw.println("-1 -1");
    else
      pw.println(minDists[B] + " " + minFlights[B]);
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