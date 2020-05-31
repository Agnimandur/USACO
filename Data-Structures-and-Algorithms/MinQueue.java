//As a queue, but it can query the minimum/maximum value in the queue in O(1) time. 
public class MinQueue {
  private ArrayDeque<Integer> s1Num;
  private ArrayDeque<Integer> s1Min;
  private ArrayDeque<Integer> s2Num;
  private ArrayDeque<Integer> s2Min;
  
  public MinQueue() {
    s1Num = new ArrayDeque<Integer>();
    s1Min = new ArrayDeque<Integer>();
    s2Num = new ArrayDeque<Integer>();
    s2Min = new ArrayDeque<Integer>();
  }
  
  public int queryMin() {
    int min;
    if (s1Num.isEmpty()) {
        min = s2Min.peek();
    } else if (s2Num.isEmpty()) {
        min = s1Min.peek();
    } else {
        min = Math.min(s1Min.peek(),s2Min.peek()); 
    }
    return min;
  }
  
  public void add(int n) {
    int min = s1Num.isEmpty()? n : Math.min(n,s1Min.peek());
    s1Num.push(n);
    s1Min.push(min);
  }
  
  public void remove() {
    if (s2Num.isEmpty()) {
      while (!s1Num.isEmpty()) {
        int n = s1Num.pop();
        s1Min.pop();
        int min = s2Num.isEmpty() ? n : Math.min(n, s2Min.peek());
        s2Num.push(n);
        s2Min.push(min);
      }
    }
    s2Num.pop();
    s2Min.pop();
  }
}