import java.util.Scanner;
import java.math.*;
import java.io.*;
/*
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

class NegativeCycle {

    private static Scanner input = new Scanner(System.in);
    private static int N;
    private static int M;

    public static void main(String[] args) {
        N = input.nextInt(); M = input.nextInt();
        Edge[] edgeArray = new Edge[M];
        for(int i = 0; i < M; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();
            edgeArray[i] = new Edge(u, v, w);
        }
        bellmanFordDetectNegativeCycle(edgeArray, 1);
    }

    public static void bellmanFordDetectNegativeCycle(Edge[] edges, int s) {
        int[] d = new int[N + 1];
        for(int i = 0; i < d.length; i++) {
            d[i] = 999999999;
        } 
        d[s] = 0;
        for(int i = 0; i < N - 1; i++) {
            for(int j = 0; j < edges.length; j++) {
                if(d[edges[j].getV()] > d[edges[j].getU()] + edges[j].getW()) {
                    d[edges[j].getV()] = d[edges[j].getU()] + edges[j].getW();
                }
            }
        }

        for(int i = 0; i < edges.length; i++) {
            if(d[edges[i].getV()] > d[edges[i].getU()] + edges[i].getW()) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}*/

class Pair {
    private final int dist;
    private final int vertex;

    public Pair(int dist, int vertex) {
        this.dist = dist;
        this.vertex = vertex;
    }

    public int getDistance() {
        return dist;
    }

    public int getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
        return "(Distance = " + dist + ", Vertex = " + vertex + ")";
    }
}

class List {
    private Pair[] array;
    private int size;

    public List(int length) {
        this.array = new Pair[length];
    }

    public Pair get(int idx) {
        if(size <= idx) {
            return null;
        }
        return array[idx];
    }

    public void add(Pair n) {
        array[size++] = n;
        return;
    }

    public int size() {
        return size;
    }
    
    @Override
    public String toString() {
        String str = "(";
        for(int i = 0; i < size; i++) {
            str += array[i] + ", ";
        }
        return str.substring(0, str.lastIndexOf(",")) + ")";
    }
}

class NegativeCycle {

    private static Scanner input = new Scanner(System.in);
    private static int N;
    private static int M;
    private static int[] dist;
    private static List[] adj;
    private static boolean[] used;

    private static int U;
    private static int V;
    private static int W;

    public static void main(String[] args) {
        N = input.nextInt(); M = input.nextInt();

        dist = new int[N + 1]; 
        adj  = new List[N + 1]; 
        used = new boolean[N + 1];

        for(int i = 1; i < dist.length; i++) {
            dist[i] = 999999999;
            adj[i] = new List(N + 1);
        }

        for(int i = 0; i < M; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();
            if(w < 0) {
                U = u;
                V = v;
                W = w;
            } else {
                adj[u].add(new Pair(w, v));
            }
        }

        dist[V] = 0; used[V] = true;
        update(V);

        for(int i = 0; i < N - 1; i++) {
            int node = getMinVertexDistance();
            used[node] = true;
            update(node);
        }

        if(Math.abs(W) > dist[U]) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    public static int getMinVertexDistance() {
        int minDistance = 999999999;
        int minNode = -1;
        for(int i = 1; i < used.length; i++) {
            if(used[i] || minDistance < dist[i]) {
                continue;
            } else {
                minDistance = dist[i];
                minNode = i;
            }
        }
        return minNode;
    }

    public static void update(int u) {
        for(int i = 0; i < adj[u].size(); i++) {
            if(dist[adj[u].get(i).getVertex()] > dist[u] + adj[u].get(i).getDistance()) {
                dist[adj[u].get(i).getVertex()] = dist[u] + adj[u].get(i).getDistance();
            }
        }
    }
}