import java.util.Scanner;
import java.math.*;
import java.io.*;

class Edge {
    private int u;
    private int v;
    private int w;

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    @Override
    public String toString() {
        return "(" + u + ", " + v + ", " + w + ")";
    }
}

class EdgeArrayList {
    private Edge[] items;
    private int size;

    public EdgeArrayList(int size) {
        this.size = 0;
        items = new Edge[size];
    }

    public EdgeArrayList(Edge[] items) {
        this.items = items;
    }

    public Edge[] getItems() {
        return items;
    }

    public void add(Edge n) {
        items[size] = n;
        size++;
        return;
    }

    public int size() {
        return size;
    }
}

class BudgetArrayList {
    private int[] items;
    private int size;

    public BudgetArrayList(int size) {
        this.size = 0;
        items = new int[size];
    }

    public int[] getItems() {
        return items;
    }

    public void add(int n) {
        items[size] = n;
        size++;
        return;
    }

    private void reverse() {
        int left = 0;
        int right = size - 1;
        while(left < right) {
            int temp = items[left];
            items[left] = items[right];
            items[right] = temp;
            left++;
            right--;
        }
    }

    public int size() {
        return size;
    }
}

class UnionFind {
    private int[] boss;
    private int[] size;
    private BudgetArrayList[] set;
    private int numberOfConnectedGroups;

    public int getNumberOfConnectedGroups() {
        return numberOfConnectedGroups - 1;
    }

    private void init() {
        for(int i = 1; i < boss.length; i++) {
            boss[i] = i;
            size[i] = 1;
            set[i] = new BudgetArrayList(boss.length);
            set[i].add(i);
        }
    }

    public UnionFind(int size) {
        this.numberOfConnectedGroups = size;
        this.boss = new int[size];
        this.size = new int[size];
        this.set =  new BudgetArrayList[size];
        init();
    }

    public int find(int node) {
        return boss[node];
    }

    public void union(int u, int v) {
        if(find(u) == find(v)) {
            return;
        }
        numberOfConnectedGroups--;
        if(size[boss[u]] >= size[boss[v]]) {
            int[] membersOfParentV = set[boss[v]].getItems();
            for(int i = 0; i < size[boss[v]]; i++) {
                set[boss[u]].add(membersOfParentV[i]);
            }
            size[boss[u]] += size[boss[v]];
            for(int i = 0; i < size[boss[v]]; i++) {
                boss[membersOfParentV[i]] = boss[u];
            }

        } else {
            int[] membersOfParentU = set[boss[u]].getItems();
            for(int i = 0; i < size[boss[u]]; i++) {
                set[boss[v]].add(membersOfParentU[i]);
            }
            size[boss[v]] += size[boss[u]];
            for(int i = 0; i < size[boss[u]]; i++) {
                boss[membersOfParentU[i]] = boss[v];
            }
        }
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 1; i < boss.length; i++) {
            str += "Node " + i + " belongs to parent component " + boss[i] + "\n";
        }
        return str;
    }
}

class SpanTree {
    static Scanner input = new Scanner(System.in);

    public static Edge[] truncateEdgeArray(EdgeArrayList edgeList) {
        Edge[] smallList = new Edge[edgeList.size()];

        Edge[] bigList = edgeList.getItems();

        for(int i = 0; i < edgeList.size(); i++) {
            smallList[i] = bigList[i];
        }
        return smallList;
    }

    public static void main(String[] args) {
        int N = input.nextInt(); int M = input.nextInt();

        EdgeArrayList edgeList = new EdgeArrayList(9999);

        int res = 0;

        UnionFind uf = new UnionFind(N + 1);
        for(int i = 0; i < M; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();
            int isProtected = input.nextInt();
            if(isProtected == 1) {
                if(uf.find(u) == uf.find(v)) {
                    System.out.println(-1);
                    return;
                }
                res += w;
                uf.union(u, v);

            } else {
                edgeList.add(new Edge(u, v, w));
            }
        }

        Edge[] smallEdgeList = truncateEdgeArray(edgeList);
        mergeSort(smallEdgeList);

        int numberOfConnectedGroups = uf.getNumberOfConnectedGroups();
        for(int i = 0; i < smallEdgeList.length; i++) {
            if(uf.find(smallEdgeList[i].getU()) != uf.find(smallEdgeList[i].getV())) {
                uf.union(smallEdgeList[i].getU(), smallEdgeList[i].getV());
                res += smallEdgeList[i].getW();
                numberOfConnectedGroups--;
            }
        }
        System.out.println((numberOfConnectedGroups == 1) ? res : -1);

    }


    public static void mergeSort(Edge[] array) {
        if(array.length == 1) {
            return;
        }
        int mid = array.length / 2;

        Edge[] left = new Edge[mid];
        for(int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }

        Edge[] right = new Edge[array.length - mid];
        for(int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }

        mergeSort(left);
        mergeSort(right);
        merge(left, right, array);
        return;
    }

    public static void merge(Edge[] left, Edge[] right, Edge[] res) {
        int i = 0, j = 0, k = 0;
        while(i < left.length &&  j < right.length) {
            if(left[i].getW() <= right[j].getW()) {
                res[k] = left[i++];
            } else {
                res[k] = right[j++];
            }
            k++;
        }
        while(i < left.length) {
            res[k++] = left[i++];
        }
        while(j < right.length) {
            res[k++] = right[j++];
        }
    }
}