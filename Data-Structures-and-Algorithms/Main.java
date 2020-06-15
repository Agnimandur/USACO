class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }

  //USEFUL METHODS

  public static int[][] sort(int[][] arr) {
    //Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < 3*arr.length; i++) {
      int randomPosition = rgen.nextInt(arr.length);
      int[] temp = arr[i];
      arr[i] = arr[randomPosition];
      arr[randomPosition] = temp;
		}
    Arrays.sort(arr, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[1]-arr2[1];
        //Ascending order.
      }
    });
    return arr;
  }

  public static long[][] sort(long[][] arr) {
    //Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < 3*arr.length; i++) {
      int randomPosition = rgen.nextInt(arr.length);
      long[] temp = arr[i];
      arr[i] = arr[randomPosition];
      arr[randomPosition] = temp;
		}
    Arrays.sort(arr, new Comparator<long[]>() {
      @Override
      public int compare(long[] arr1, long[] arr2) {
        if (arr1[0] > arr2[0])
          return 1;
        else
          return -1;

        //Ascending order.
      }
    });
    return arr;
  }

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