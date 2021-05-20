//fast primality test
public class MillerRabin {
    public boolean isPrime(long n) {
        if (n<2) return false;
        int r = 0;
        long d = n-1;
        while (d%2==0) {
            d >>= 1;
            r++;
        }

        for (int p: new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            if (n == p)
                return true;
            if (composite(n, p, d, r))
                return false;
        }
        return true;
    }

    public boolean composite(long n, long p, long d, int s) {
        long x = power(p, d, n);
        if (x == 1 || x == n - 1) return false;

        for (int r = 1; r < s; r++) {
            BigInteger b = new BigInteger(Long.toString(x));
            b = b.multiply(b).mod(new BigInteger(Long.toString(n)));
            x = b.longValue();
            if (x == n - 1)
                return false;
        }
        return true;
    }

    public long power(long x, long y, long m) {
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
}