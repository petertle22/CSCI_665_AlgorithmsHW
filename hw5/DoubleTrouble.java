import java.util.Scanner;
import java.math.*;
import java.io.*;

class Position {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Position(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }
    public int getX2() {
        return x2;
    }
    public int getY1() {
        return y1;
    }
    public int getY2() {
        return y2;
    }

    @Override 
    public String toString() {
        return "[(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")]";
    }
}

class Queue {
    private int start;
    private int end;
    private Position[] queueArray;

    public Queue(int size) {
        this.queueArray =  new Position[size];
        this.start = 0;
        this.end = 0;
    }
    public void add(Position n) {
        queueArray[end++] = n;
        return;
    }
    public Position pop() {
        if (start == end) {
            return null;
        }
        return queueArray[start++];
    }

    public int size() {
        return end - start;
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = start; i < end; i++) {
            str += queueArray[i] + "   ";
        }
        return str;
    }
}

class DoubleTrouble {
    static Scanner input = new Scanner(System.in);
    static int a;
    static int b;
    static char[][] maze;
                            //  UP       RIGHT    DOWN     LEFT
    final static int[] dir = {-1, 0,    0, 1,    1, 0,    0, -1}; 
    static boolean[][][][] visited;


    public static boolean isOutOfBounds(int x, int y) {
        return x == -1 || x == a || y == -1 || y == b;
    }

    public static int bfs(int x1, int y1, int x2, int y2) {
        Queue q = new Queue(a * b * a * b); 
        Position start = new Position(x1, y1, x2, y2);
        
         q.add(start); int depth = 0;

         visited[x1][y1][x2][y2] = true;

        while(q.size() > 0) {
            depth++;
            int breadthSize = q.size();
            for(int i = 0; i < breadthSize; i++) {
                Position now  = q.pop();
                int x1old = now.getX1(); int y1old = now.getY1();
                int x2old = now.getX2(); int y2old = now.getY2();

                for(int j = 0; j < 8; j += 2) {
                    int x1new = x1old + dir[j]; int y1new = y1old + dir[j + 1];
                    int x2new = x2old + dir[j]; int y2new = y2old + dir[j + 1];

                    if(isOutOfBounds(x1new, y1new) && isOutOfBounds(x2new, y2new)) {
                        return depth;
                    } else if ( (isOutOfBounds(x1new, y1new) && !isOutOfBounds(x2new, y2new)) ||
                                !isOutOfBounds(x1new, y1new) && isOutOfBounds(x2new, y2new) ) {
                        continue;
                    } else {
                        if(maze[x1new][y1new] == 'x' && maze[x2new][y2new] == 'x') { // both walls
                            x1new = x1old;
                            y1new = y1old;     
                            x2new = x2old;
                            y2new = y2old;       
                        } else if(maze[x1new][y1new] == 'x') { // One wall one free space
                            x1new = x1old;
                            y1new = y1old;
                            x2new = (x2new == x1old && y2new == y1old) ? x2old : x2new;
                            y2new = (x2new == x1old && y2new == y1old) ? y2old : y2new;
                        } else if(maze[x2new][y2new] == 'x') { // One wall one free space
                            x2new = x2old;
                            y2new = y2old;
                            x1new = (x1new == x2old && y1new == y2old) ? x1old : x1new;
                            y1new = (x1new == x2old && y1new == y2old) ? y1old : y1new;
                        }

                        if(visited[x1new][y1new][x2new][y2new]) {
                            continue;
                        } else {
                            visited[x1new][y1new][x2new][y2new] = true;
                            Position newPosition = new Position(x1new, y1new, x2new, y2new);
                            q.add(newPosition);
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        a = input.nextInt(); b = input.nextInt();
        input.nextLine();

        visited = new boolean[a][b][a][b];

        int x1 = -1, y1 = -1;
        int x2 = -1, y2 = -1;

        maze = new char[a][b]; 
        for(int i = 0; i < a; i++) {
            String line = input.nextLine();
            for(int j = 0; j < b; j++) {
                maze[i][j] = line.charAt(j);
                if(maze[i][j] == '1') {
                    maze[i][j] = '.';
                    x1 = i; y1 = j;
                } else if(maze[i][j] == '2') {
                    maze[i][j] = '.';
                    x2 = i; y2 = j;
                }
            }
        }

        int res = bfs(x1, y1, x2, y2);
        System.out.println( (res == -1) ? "STUCK" : res);
    }
}