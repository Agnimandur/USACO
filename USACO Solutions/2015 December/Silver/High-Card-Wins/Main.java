import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("highcard.in"));
    int N = Integer.parseInt(br.readLine());
    int[] cards = new int[2*N]; // 1 = Elsie card.
    for (int i = 0; i < N; i++)
      cards[Integer.parseInt(br.readLine())-1] = 1;
    br.close();

    int cardsToBeat = 0;
    int points = 0;
    for (int i = 0; i < 2*N; i++) {
      if (cards[i] == 1)
        cardsToBeat++;
      else if (cards[i] == 0 && cardsToBeat > 0) {
        points++;
        cardsToBeat--;
      }
    }

    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("highcard.out")));
    pw.println(points);
    pw.close();
  }
}