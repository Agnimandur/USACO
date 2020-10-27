//As a queue, but it can query the minimum/maximum value in the queue in O(1) time. 
//full name of the data structure is "monotonic queue"
public class MonoQueue {
  private ArrayDeque<Integer> s1Num;
  private ArrayDeque<Integer> s1M;
  private ArrayDeque<Integer> s2Num;
  private ArrayDeque<Integer> s2M;
  private int size;

  public int NONE;
  
  public MonoQueue() {
    s1Num = new ArrayDeque<Integer>();
    s1M = new ArrayDeque<Integer>();
    s2Num = new ArrayDeque<Integer>();
    s2M = new ArrayDeque<Integer>();
    size = 0;
    NONE = 0; //set this manually!
  }
  
  public int query() {
    if (size==0) return NONE;
    int m;
    if (s1Num.isEmpty()) {
      m = s2M.peek();
    } else if (s2Num.isEmpty()) {
      m = s1M.peek();
    } else {
      m = merge(s1M.peek(),s2M.peek()); 
    }
    return m;
  }
  
  public void add(int n) {
    int m = s1Num.isEmpty() ? n : merge(n,s1M.peek());
    s1Num.push(n);
    s1M.push(m);
    size++;
  }
  
  public void remove() {
    if (s2Num.isEmpty()) {
      while (!s1Num.isEmpty()) {
        int n = s1Num.pop();
        s1M.pop();
        int m = s2Num.isEmpty() ? n : merge(n, s2M.peek());
        s2Num.push(n);
        s2M.push(m);
      }
    }
    s2Num.pop();
    s2M.pop();
    size--;
  }

  public int size() {
    return size;
  }

  //set this to be either min or max if you want a MinQueue or a MaxQueue
  private int merge(int a, int b) {
    return Math.min(a,b);
    //return Math.max(a,b);
  }
}