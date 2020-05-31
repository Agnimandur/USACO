//Useful in matrix multiplacation for linear relationships
public class Matrix {
  public long[][] matrix;
  public long MOD;
  public int M;
  
  public Matrix(long[][] matrix, long MOD) {
    this.matrix = matrix;
    this.MOD = MOD;
    M = matrix.length;
  }
  
  public Matrix matmul(Matrix m1, Matrix m2) {
    long[][] ans = new long[M][M]; 
    for (int i = 0; i < M; i++) { 
      for (int j = 0; j < M; j++) { 
        for (int k = 0; k < M; k++) {
          ans[i][j] += m1.matrix[i][k]*m2.matrix[k][j];
          ans[i][j] %= MOD;
        }
      }
    }
    return new Matrix(ans,MOD);
  }
  
  public Matrix matpower(Matrix mat, long pow) {
    long[][] ans = new long[M][M];
    for (int i = 0; i < M; i++)
      ans[i][i] = 1;
    while (pow > 0) {
      if (pow % 2 == 1) {
        ans = matmul(new Matrix(ans,MOD),mat);
      }
      pow /= 2;
      mat = matmul(mat,mat);
    }
    return new Matrix(ans,MOD);
  }
}