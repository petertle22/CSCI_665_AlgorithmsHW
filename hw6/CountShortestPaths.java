import java.util.Scanner;
import java.math.*;
import java.io.*;

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

class Heap {
    private int capacity;
    private int size = 0;
    public Pair[] array;

    public Heap(int size) {
        this.array = new Pair[size];
    }

    public int size() {
        return size;
    }

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    private int getParentIndex(int currentIndex) {
        return (currentIndex - 1) / 2;
    }

    public Pair peek() {
        if (size == 0) {
            throw new IllegalArgumentException("No elments to peek");
        }
        return array[0];
    }

    public Pair poll() {
        if (size == 0) {
            throw new IllegalArgumentException("No elments to peek");
        }
        Pair root = array[0];
        array[0] = array[size - 1];
        size--;
        heapifyDown(0);
        return root;
    }

    public void add(Pair element) {
        array[size] = element;
        size++;
        heapifyUp(size - 1);
    }

    public void heapifyDown(int currentIndex) {
        int N = size - 1;
        while( ( getLeftChildIndex( currentIndex ) <= N ) && ( array[currentIndex].getDistance() > array[getLeftChildIndex(currentIndex)].getDistance() ) ||
               ( getRightChildIndex( currentIndex ) <= N ) && ( array[currentIndex].getDistance() > array[getRightChildIndex(currentIndex)].getDistance() ) ) {
            int j;
            if( getRightChildIndex(currentIndex) > N || ( array[getLeftChildIndex(currentIndex)].getDistance() < array[getRightChildIndex( currentIndex )].getDistance() ) ) {
                j = getLeftChildIndex( currentIndex );
            } else {
                j = getRightChildIndex( currentIndex );
            }
            Pair temp = array[currentIndex];
            array[currentIndex] = array[j];
            array[j] = temp;
            currentIndex = j;
        }
    }

    public void heapifyUp(int currentIndex) {
        while((currentIndex > 0) && (array[currentIndex].getDistance() < array[getParentIndex( currentIndex ) ].getDistance() ) ) {
            Pair temp = array[currentIndex];
            array[currentIndex] = array[getParentIndex( currentIndex )];
            array[getParentIndex(currentIndex)] = temp;
            currentIndex = getParentIndex( currentIndex );
        }
    }
}

class CountShortestPaths {
    static Scanner input = new Scanner(System.in);
    static int[] dist;
    static int[] memo;

    public static void printAdjMatrix(int[][] adj) {
        for(int i = 1; i < adj.length; i++) {
            for(int j = 1; j < adj[i].length; j++) {
                System.out.print(adj[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int N = input.nextInt(); int M = input.nextInt(); 
        int S = input.nextInt(); int[][] adj = new int[N + 1][N + 1];

        dist = new int[N + 1];
        for(int i = 1; i < N + 1; i++) {
            dist[i] = 999999999;
        }

        memo = new int[N + 1];

        dist[S] = 0;
        memo[S] = 1;

        for(int i = 0; i < M; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();
            adj[u][v] = w;
        }

        shortestPathsCount(adj, S);

        for(int i = 1; i < N + 1; i++) {
            System.out.println((dist[i] == 999999999) ? "inf" : dist[i] + " " + memo[i]);
        }
    }

    public static void shortestPathsCount(int[][] adj, int s) {
        Heap heap = new Heap(9999);

        heap.add(new Pair(0, s));

        while(heap.size() > 0) {
            Pair node = heap.poll();
            int currentDistance = node.getDistance(); int currentVertex = node.getVertex();

            if(currentDistance > dist[currentVertex]) {
                continue;
            }

            for(int nei = 1; nei < adj.length; nei++) {
                if(adj[currentVertex][nei] != 0) {
                    int distance = currentDistance + adj[currentVertex][nei];

                    if(distance < dist[nei]) {
                        dist[nei] = distance;
                        memo[nei] = memo[currentVertex];
                        heap.add(new Pair(distance, nei));
                    } else if(distance == dist[nei]) {
                        memo[nei] += memo[currentVertex];
                    }
                }
            }
        }
    }
}