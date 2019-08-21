import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("multimoo.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] gameboard = new int[N][N];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        gameboard[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();
    ArrayList<Region> regions = new ArrayList<Region>();

    //Floodfill through the gameboard to assign each square to a region
    int[][] regionMap = new int[N][N];
    HashSet<ArrayList<Integer>> squaresLeft = new HashSet<ArrayList<Integer>>();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        ArrayList<Integer> p = new ArrayList<Integer>();
        p.add(i);
        p.add(j);
        squaresLeft.add(p);
      }
    }
    int curRegion = 0;
    int maxSingleSize = 0;

    while (true) {
      if (squaresLeft.isEmpty())
        break;
      ArrayList<Integer> root = new ArrayList<Integer>();
      for (ArrayList<Integer> p: squaresLeft) {
        root = p;
        break;
      }
      Stack<ArrayList<Integer>> s = new Stack<ArrayList<Integer>>();
      int regID = gameboard[root.get(0)][root.get(1)];
      int regSize = 0;
      squaresLeft.remove(root);
      s.push(root);

      while (! s.isEmpty()) {
        ArrayList<Integer> p = s.pop();
        regionMap[p.get(0)][p.get(1)] = curRegion;
        regSize++;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir: dirs) {
          int newR = p.get(0) + dir[0];
          int newC = p.get(1) + dir[1];
          if (newR >= 0 && newR < N && newC >= 0 && newC < N) {
            ArrayList<Integer> pushRoot = new ArrayList<Integer>();
            pushRoot.add(newR);
            pushRoot.add(newC);
            if (squaresLeft.contains(pushRoot) && gameboard[newR][newC] == regID) {
              squaresLeft.remove(pushRoot);
              s.push(pushRoot);
            }
          }
        }
      }
      Region r = new Region(regSize,regID,curRegion);
      regions.add(r);
      maxSingleSize = Math.max(maxSingleSize,regSize);
      curRegion++;
    }

    //Add Connections between Regions
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N-1; col++) {
        Region r1 = regions.get(regionMap[row][col]);
        Region r2 = regions.get(regionMap[row][col+1]);
        if (r1.id != r2.id) {
          if (! r1.children.contains(r2))
            r1.addChild(r2);
          if (! r2.children.contains(r1))
            r2.addChild(r1);
        }
      }
    }
    for (int col = 0; col < N; col++) {
      for (int row = 0; row < N-1; row++) {
        Region r1 = regions.get(regionMap[row][col]);
        Region r2 = regions.get(regionMap[row+1][col]);
        if (r1.id != r2.id) {
          if (! r1.children.contains(r2))
            r1.addChild(r2);
          if (! r2.children.contains(r1))
            r2.addChild(r1);
        }
      }
    }
    //Calculate which regions can pair together
    boolean[] regionsVisited = new boolean[regions.size()];
    int maxSize = 0;
    for (int i = 0; i < regions.size(); i++) {
      Region root = regions.get(i);
      regionsVisited[root.regNum] = true;
      Stack<Region> regs = new Stack<Region>();
      boolean[] tempVis = new boolean[regions.size()];
      for (Region root2: root.children) {
        if (regionsVisited[root2.regNum] || tempVis[root2.regNum])
          continue;
        int combinedSize = 0;
        tempVis[root.regNum] = true;
        regs.push(root);
        int id1 = root.id;
        int id2 = root2.id;
        //Loop through DFS Algorithm
        while (! regs.isEmpty()) {
          Region reg = regs.pop();
          combinedSize += reg.size;
          for (Region r: reg.children) {
            int rn = r.regNum;
            if (((!tempVis[rn]) && (!regionsVisited[rn])) && (r.id == id1 || r.id == id2)) {
              tempVis[rn] = true;
              regs.push(r);
            }
          }
        }
        maxSize = Math.max(combinedSize,maxSize);
      }
    }

    //multimoo.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("multimoo.out")));
    pw.println(maxSingleSize);
    pw.println(maxSize);
    pw.close();
  }
}

class Region {
  public int size;
  public int id;
  public int regNum;
  public HashSet<Region> children;

  public Region(int s, int i, int rn) {
    size = s;
    id = i;
    regNum = rn;
    children = new HashSet<Region>();
  }

  public void addChild(Region r) {
    children.add(r);
  }
}