public class SuffixArray {
  public String str;
  public int[][] array;

  public SuffixArray(String s) {
    int p = (int)Math.ceil(Math.log(s.length())/Math.log(2));
    StringBuilder add = new StringBuilder();
    for (int i = 0; i < (1<<p); i++) {
      if (i < s.length()) {
        add.append(s.charAt(i));
      } else {
        add.append('#');
      }
    }
    str = add.toString();
    array = new int[p+1][str.length()];
    initSuffixArray();
  }

  public void initSuffixArray() {
    for (int i = 0; i < str.length(); i++) {
      array[0][i] = str.charAt(i)-'a';
    }

    for (int i = 1; i <= p; i++) {
      Triple[] trips = new Triple[str.length()-(1<<i)+1];
      for (int j = 0; j < trips.length; j++) {
        trips[j] = new Triple(array[i-1][j],array[i-1][j+(1<<(i-1))],j);
      }
      Arrays.sort(trips);
      int ind = 0;
      for (int j = 1; j < trips.length; j++) {
        if (trips[j].compareTo(trips[j-1]) > 0) {
          ind++;
        }
        array[i][j] = ind;
      }
    }
  }

  static class Triple implements Comparable<Pair> {
    public int n1;
    public int n2;
    public int index;
    public Pair (int n1, int n2, int i) {
      this.n1 = n1;
      this.n2 = n2;
      this.index = i;
    }

    public int compareTo(Triple p) {
      if (n1 != p.n1)
        return (n1-p.n1);
      else
        return (n2-p.n2);
    }
  }
}