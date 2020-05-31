class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }

  //USEFUL METHODS

  //Fast exponentiation (x^y mod m)
  public static long power(long x, long y, long m) { 
    long ans = 1;
    x %= m;
    while (y > 0) { 
      if(y % 2 == 1) 
        ans = (ans * x) % m; 
      y /= 2;  
      x = (x * x) % m;
    } 
    return ans; 
  }

  //Find the distance between two points
  public static long dist(int[] point, int[] point2) {
    return (long)(Math.pow((point2[1]-point[1]),2)+Math.pow((point2[0]-point[0]),2));
  }
  
  //Find the GCD of two numbers
  public static long gcd(long a, long b) {
    if (a < b)
      return gcd(b,a);
    if (b == 0)
      return a;
    else
      return gcd(b,a%b);
  }
}