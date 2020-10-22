//Combinatorics template class
public class Combo {
  //cache factorials
  long[] facs;
  long[] invfacs;
  long MOD;
  
  public Combo(int N, long M) {
    MOD = M;
    facs = new long[N+1];
    invfacs = new long[N+1];
    facs[0] = 1;
    for (int i = 1; i <= N; i++) {
      facs[i] = (facs[i-1]*i)%MOD;
    }
    invfacs[N] = power(facs[N],MOD-2);
    for (int i = N-1; i >= 0; i--)
      invfacs[i] = ((i+1)*invfacs[i+1])%MOD;
  }
  
  public long choose(int n, int k) {
    if (n<0||k<0||n<k) return 0;
    long denInv = (invfacs[n]*invfacs[n-k])%MOD;
    long ans = (facs[n]*denInv)%MOD;
    return ans;
  }
  
  public long factorial(int n) {
    return facs[n];
  }
  
  //binary exponentation
  public long power(long x, long y) {
    long ans = 1;
    x %= MOD;
    while (y > 0) {
      if((y&1)==1)
        ans = (ans * x) % MOD;
      y /= 2;
      x = (x * x) % MOD;
    }
    return ans;
  }
}