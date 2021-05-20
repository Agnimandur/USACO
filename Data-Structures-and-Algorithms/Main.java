class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }

  //USEFUL METHODS
  
  public static void sort(int[][] arr) {
    //Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < arr.length; i++) {
      int r = rgen.nextInt(arr.length);
      int[] temp = arr[i];
      arr[i] = arr[r];
      arr[r] = temp;
		}
    Arrays.sort(arr, new Comparator<int[]>() {
      @Override
      public int compare(int[] a, int[] b) {
        return a[1]-b[1];
        //Ascending order.
      }
    });
  }

  public static void sort(long[][] arr) {
    //Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < arr.length; i++) {
      int r = rgen.nextInt(arr.length);
      long[] temp = arr[i];
      arr[i] = arr[r];
      arr[r] = temp;
		}
    Arrays.sort(arr, new Comparator<long[]>() {
      @Override
      public int compare(long[] a, long[] b) {
        if (a[0] > b[0])
          return 1;
        else if (a[0] < b[0])
          return -1;
        else
          return 0;
        //Ascending order.
      }
    });
  }

  //Fast exponentiation (x^y mod m)
  public static long power(long x, long y, long m) {
    long ans = 1;
    x %= m;
    while (y > 0) {
      if((y&1)==1)
        ans = (ans * x) % m;
      y /= 2;
      x = (x * x) % m;
    }
    return ans;
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