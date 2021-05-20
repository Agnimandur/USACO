public class Pair implements Comparable<Pair> {
  int x;
  int y;
  int i;

  public Pair(int x, int y, int i) {
    this.x=x;
    this.y=y;
    this.i=i;
  }

  public Pair(int x, int i) {
    this.x=x;
    this.y=0;
    this.i=i;
  }

  public int compareTo(Pair p) {
    return x-p.x;
  }

  public String toString() {
    return "("+x+","+y+","+i")";
  }
}