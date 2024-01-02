import java.util.Scanner;
import java.math.*;
import java.io.*;

class ConnectGraph {
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
        int[][] adj = new int[N + 1][N + 1];
        for(int i = 0; i < M; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            adj[u][v] = adj[v][u] = 1;
        }

        boolean[] visited = new boolean[N + 1];
        int numConnectedComponents = 0;
        for(int i = 1; i < N + 1; i++) {
            if(!visited[i]) {
                numConnectedComponents++;
                dfs(i, visited, adj);
            }
        }
        System.out.println(numConnectedComponents - 1);
    }
    public static void dfs(int root, boolean[] visited, int[][] adj) {
        visited[root] = true;
        for(int nei = 1; nei < adj[root].length; nei++) {
            if(adj[root][nei] == 1 && !visited[nei]) {
                dfs(nei, visited, adj);
            }
        }
    }
}