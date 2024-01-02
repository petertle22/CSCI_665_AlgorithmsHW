import java.util.Scanner;
import java.math.*;
import java.io.*;

class MaxRectangle {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int[] startIndexes = new int[N / 2];  //{0, 1, 3, 7, 10, 14, 15, 17}; // {0, 1, 3, 6, 10, 12}; {0, 2, 4, 5};
        int[] height       = new int[N / 2];  //{1, 3, 6, 2,  5,  1,  8, 8 }; // {1, 2, 3, 4, 2, 2}; {3, 2, 1, 1}; 

        for(int i = 0; i < N / 2; i++) {
            if(i == N / 2 - 1) {
                startIndexes[i] = input.nextInt();
                height[i]      = input.nextInt();
                input.nextInt(); input.nextInt();
            } else {
                input.nextInt(); input.nextInt();
                startIndexes[i] = input.nextInt();
                height[i]       = input.nextInt();
            }
        }

        int maxArea = -1;

        RectanglePairStack stack = new RectanglePairStack(height.length);
        stack.push( new RectanglePair( startIndexes[0], height[0] ) );

        for(int i = 1; i < height.length; i++) {
            if ( stack.peek().getHeight() < height[i] ) {
                stack.push( new RectanglePair( startIndexes[i], height[i] ) );
            } else {
                RectanglePair topElementToRemove = null;
                while( stack.peek() != null && height[i] < stack.peek().getHeight() ) {
                    topElementToRemove =  stack.pop();
                    maxArea = Math.max( maxArea, (startIndexes[i] - topElementToRemove.getStartIndex()) * topElementToRemove.getHeight());
                }
                if(topElementToRemove == null) {
                    continue;
                }
                stack.push(new RectanglePair( topElementToRemove.getStartIndex(), height[i] ) );
            }
        }

        while (stack.peek() != null) {
            RectanglePair top = stack.pop();
            maxArea = Math.max(maxArea, (startIndexes[startIndexes.length - 1] - top.getStartIndex()) * top.getHeight());
        }
        
        System.out.println(maxArea);
    }
}

class RectanglePairStack {
    private RectanglePair[] stack;
    private int size;

    public RectanglePairStack(int num) {
        this.size = 0;
        this.stack = new RectanglePair[num];
    }

    public RectanglePair peek() {
        if (size == 0) {
            return null;
        }
        return stack[size - 1];
    }

    public void push(RectanglePair pair) {
        stack[size++] = pair;
        return;
    }

    public RectanglePair pop() {
        if (size == 0) {
            return null;
        }
        RectanglePair temp = stack[size - 1];
        stack[size--] = null;
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

class RectanglePair {
    private int startIndex;
    private int height;

    public RectanglePair(int startIndex, int height) {
        this.startIndex = startIndex;
        this.height = height;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "(" + startIndex + ", " + height + ")";
    }
}