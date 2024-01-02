import java.util.Scanner;
import java.math.*;
import java.io.*;

class SwitchDirection {
    static Scanner input = new Scanner(System.in);

    public static void printAdjMatrix(int[][] adj) {
        for(int i = 1; i < adj.length; i++) {
            for(int j = 1; j < adj[i].length; j++) {
                System.out.print(adj[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static int[][] transposeMatrix(int[][] adj) {
        int[][] adjTranspose = new int[adj.length][adj[0].length];
        for(int i = 0; i < adj.length; i++) {
            for(int j = 0; j < adj[i].length; j++) {
                if(adj[i][j] == 1) {
                    adjTranspose[j][i] = 1;
                }
            }
        }
        return adjTranspose; 
    }

    public static void main(String[] args) {
        int N = input.nextInt(); 
        int[][] adj = new int[N + 1][N + 1];
        for(int i = 0; i < N; i++) {
            int nei;
            while((nei = input.nextInt()) != 0) {
                adj[i + 1][nei] = 1;
            }
        }

        Stack stack = new Stack(adj.length);
        boolean[] visited = new boolean[N + 1];
        for(int i = 1; i < adj.length; i++) {
            if(!visited[i]) {
                dfs(i, adj, stack, visited);
            }
        }


        int[][] transAdj = transposeMatrix(adj);
        int[] stronglyConnectedComponentOf = new int[N + 1];
        int currentConnectedComponentCount = 0;
        visited = new boolean[N + 1];
        while(stack.size() > 0) {
            int node = stack.pop();
            if(!visited[node]) {
                currentConnectedComponentCount++;
                dfs(node, transAdj, visited, currentConnectedComponentCount, stronglyConnectedComponentOf);
            }
        }

        int[][] sscAdj = new int[currentConnectedComponentCount + 1][currentConnectedComponentCount + 1];

        for(int i = 1; i < adj.length; i++) {
            for(int j = 1; j < adj[i].length; j++) {
                if(adj[i][j] == 1 && stronglyConnectedComponentOf[i] != stronglyConnectedComponentOf[j]) {
                    sscAdj[stronglyConnectedComponentOf[i]][stronglyConnectedComponentOf[j]] = 1;
                }
            }
        }
        for(int i = 1; i < N + 1; i++) {
            //System.out.println("Node " + i + " is in strongly connected component " + stronglyConnectedComponentOf[i]);
        }
        List topLogicalSort = sortTopological(sscAdj);
        //System.out.println(topLogicalSort);

        int smallestLexicographicalEdgeStart = 999999999;
        int smallestLexicographicalEdgeEnd  = 999999999;
        for(int i = 1; i < adj.length; i++) {
            for(int j = 1; j < adj[i].length; j++) {
                if(adj[i][j] == 1 && stronglyConnectedComponentOf[i] == topLogicalSort.get(0) && 
                   stronglyConnectedComponentOf[j] == topLogicalSort.get(topLogicalSort.size() - 1)) {
                    if(i < smallestLexicographicalEdgeStart) {
                        smallestLexicographicalEdgeStart = i;
                        smallestLexicographicalEdgeEnd = j;
                    } else if(i == smallestLexicographicalEdgeStart) {
                        smallestLexicographicalEdgeEnd = Math.min(smallestLexicographicalEdgeEnd, j);
                    }
                }
            }
        }

        if(smallestLexicographicalEdgeEnd != 999999999 && smallestLexicographicalEdgeStart != 999999999) {
            sscAdj[currentConnectedComponentCount][1] = 1;
        }

        stack = new Stack(sscAdj.length);
        visited = new boolean[sscAdj.length];
        for(int i = 1; i < sscAdj.length; i++) {
            if(!visited[i]) {
                dfs(i, sscAdj, stack, visited);
            }
        }


        transAdj = transposeMatrix(sscAdj);
        stronglyConnectedComponentOf = new int[sscAdj.length];
        currentConnectedComponentCount = 0;
        visited = new boolean[sscAdj.length];
        while(stack.size() > 0) {
            int node = stack.pop();
            if(!visited[node]) {
                currentConnectedComponentCount++;
                if(currentConnectedComponentCount > 1) {
                    System.out.println("NO");
                    return;
                }
                dfs(node, transAdj, visited, currentConnectedComponentCount, stronglyConnectedComponentOf);
            }
        }

        System.out.println("YES");
        System.out.println(smallestLexicographicalEdgeStart + " " + smallestLexicographicalEdgeEnd);


        
    }

    public static List sortTopological(int[][] adj) {
        Stack stack = new Stack(adj.length);
        boolean[] visited = new boolean[adj.length];

        for(int i = 1; i < adj.length; i++) {
            if(!visited[i]) {
                dfs(i, adj, stack, visited);
            }
        }

        List result = new List(adj.length);
        while(stack.size() > 0) {
            result.add(stack.pop());
        }
        return result;
    }

    public static void dfs(int node, int[][] adj, boolean[] visited, int stronglyConnectedCount, int[] stronglyConnectedComponentOf) {
        if(visited[node]) {
            return;
        }
        visited[node] = true;
        stronglyConnectedComponentOf[node] = stronglyConnectedCount;
        for(int nei = 1; nei < adj[node].length; nei++) {
            if(adj[node][nei] == 1) {
                dfs(nei, adj, visited, stronglyConnectedCount, stronglyConnectedComponentOf);
            }
        }
    }

    public static void dfs(int node, int[][] adj, Stack stack, boolean[] visited) {
        if (visited[node]) {
            return;
        }
        visited[node] = true;
        for(int nei = 1; nei < adj[node].length; nei++) {
            if(adj[node][nei] == 1) {
                dfs(nei, adj, stack, visited);
            }
        }
        stack.push(node);
    }
}

class Stack {
    private int[] stack;
    private int size;

    public Stack(int num) {
        this.size = 0;
        this.stack = new int[num];
    }

    public int size() {
        return size;
    }

    public int peek() {
        if (size == 0) {
            return -1;
        }
        return stack[size - 1];
    }

    public void push(int pair) {
        stack[size++] = pair;
        return;
    }

    public int pop() {
        if (size == 0) {
            return -1;
        }
        int temp = stack[size - 1];
        stack[size--] = -1;
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("(startIndex, height)" + "\n");
        for(int i = size - 1; i  >= 0; i--) {
            build.append(stack[i] + "\n");
        }
        return build.toString();
    }
}

class List {
    private int[] array;
    private int size;

    public List(int length) {
        this.array = new int[length];
    }

    public int get(int idx) {
        if(size <= idx) {
            return -1;
        }
        return array[idx];
    }

    public void add(int n) {
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