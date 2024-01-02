import java.util.Scanner;
import java.math.*;
import java.io.*;

class NumPaths {
    static Scanner input = new Scanner(System.in);

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
        int S = input.nextInt(); int T = input.nextInt();
        int[][] adj = new int[N + 1][N + 1];
        for(int i = 0; i < M; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            adj[u][v] = adj[v][u] = 1;
        }
        Queue q = new Queue(N + 1);
        boolean[] visited = new boolean[N + 1];
        int[] memo = new int[N + 1];

        q.add(S);
        visited[S] = true;
        memo[S] = 1;

        while(q.size() > 0) {
            int breadthSize = q.size();
            boolean[] visitedNow = new boolean[N + 1];
            for(int i = 0; i < breadthSize; i++) {
                int node = q.pop();
                for(int nei = 1; nei < adj[node].length; nei++) {
                    if(adj[node][nei] == 1) {
                        if(!visited[nei]) {
                            visited[nei] = true;
                            visitedNow[nei] = true;
                            q.add(nei);
                            memo[nei] = memo[node];
                        } else if(visitedNow[nei]) {
                            memo[nei] += memo[node];
                        }
                    } 
                }
            }
        }
        System.out.println(memo[T]);
    }
}

class Queue {
    private int start;
    private int end;
    private int[] queueArray;

    public Queue(int size) {
        this.queueArray =  new int[size];
        this.start = 0;
        this.end = 0;
    }
    public void add(int n) {
        queueArray[end++] = n;
        return;
    }
    public int pop() {
        if (start == end) {
            return -1;
        }
        return queueArray[start++];
    }

    public int size() {
        return end - start;
    }
}