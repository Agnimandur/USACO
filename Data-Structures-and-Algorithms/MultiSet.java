public class MultiSet<E> {
  private TreeMap<E,Integer> tm;
  private int sz;

  public MultiSet() {
    tm = new TreeMap<E,Integer>();
    sz = 0;
  }

  public void add(E x) {
    if (!tm.containsKey(x))
      tm.put(x,0);
    tm.put(x,tm.get(x)+1);
    sz++;
  }
  public void remove(E x) {
    if (!tm.containsKey(x)) return;
    sz--;
    int c = tm.get(x);
    if (c > 1)
      tm.put(x,c-1);
    else
      tm.remove(x);
  }

  public int count(E x) {
    return tm.getOrDefault(x,0);
  }

  public void clear() {
    tm.clear();
    sz = 0;
  }

  public int size() {
    return sz;
  }

  public boolean isEmpty() {
    return (sz==0);
  }

  public E first() {
    return tm.firstKey();
  }

  public E last() {
    return tm.lastKey();
  }

  public E floor(E x) {
    return tm.floorKey(x);
  }

  public E lower(E x) {
    return tm.lowerKey(x);
  }

  public E higher(E x) {
    return tm.higherKey(x);
  }
}
