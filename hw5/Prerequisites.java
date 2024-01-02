import java.util.Scanner;
import java.math.*;
import java.io.*;

class ListNode {
  private Integer value; // the actual object held in the node
  private String key; // the current string representation of the node's content
  private ListNode next; // the next node in the linked list

  /**
   * Constructs a ListNode node while initializing all parameters to null
   *
   */
  public ListNode() {
    this.value    = null;
    this.key      = null;
    this.next     = null;
  }

  /**
   * Constructs a ListNode node while initializing the field variable value
   * to parameter object value. Field variable key is set to the string
   * representation of the parameter value.
   *
   * @param value the value that this current linked list node will hold.
   *
   */

  public ListNode( Integer value ) {
    this.value = value;
    this.key   = value.toString();
    this.next  = null;
  }

  /**
   * Constructs a ListNode node while initializing the field variable value
   * to parameter object value. The next node is also initialized by the
   * parameter node next. The field variable key is set to the string
   * representation of the parameter object
   *
   * @param value   the value that this current linked list node will hold.
   * @param next    the next ListNode node that will the current node points to
   */

  public ListNode( Integer value, ListNode next ) {
    this.value = value;
    this.key   = value.toString();
    this.next  = next;
  }


  /**
   * Returns the string representation of the current ListNode object
   *
   * @return key   the string representation of the current node's content
   */

  @Override
  public String toString() {
    return key;
  }


  /**
   * Returns the current node's value content
   *
   * @return value the current node's value content
   */

  public Integer getValue() {
    return value;
  }

  /**
   * Sets the current node's value content to parameter value
   * while also setting the field variable key to the string
   * representation of the parameter value;
   *
   * @param value
   */
  public void setValue( Integer value ) {
    this.value = value;
    key = value.toString();
  }

  /**
   * Returns the next ListNode object or null if uninitialized.
   *
   * @return  next   the next node in the linked list or null,
   *                 if uninitialized.
   */

  public ListNode getNext() {
    return next;
  }

  /**
   * Sets the next ListNode object
   *
   * @param next   the next node in the linked list
   */
  public void setNext( ListNode next ) {
    this.next = next;
  }

}

class GenericStoreEverything {
  private ListNode head; // the head of the linked list
  private StringBuilder build; // the string representation of the
                               // GenericStoreEverything object that
                               // has the elements sorted

  /**
   * Constructs a GenericStoreEverything object while initializing
   * the head node to a dummy node. All other fields are set to null
   *
   */
  public GenericStoreEverything() {
    this.head = new ListNode();
    this.build = null;
  }

  public ListNode getHead() {
    return head;
  }

  /**
   * searchFor searches for a node with the same string representation as
   * targetString inside GenericStoreEverything. If found the ListNode object
   * containing the target is returned. Otherwise, null is
   * returned. searchFor should not be accessible to the user.
   *
   *
   * @param   targetString  the target's string representation to search for.
   * @return                Returns null if the target is not found.
   *                        Returns the ListNode object containing targetString
   *                        found
   */
  private ListNode searchFor( String targetString ) {
    ListNode curr = head.getNext(); // starts at the beginning
    while( curr != null ) {
      if ( curr.toString().equals( targetString ) ) {
        return curr; // Found target so return
      } else {
        curr = curr.getNext();
      }
    }
    return null; // target was not found
  }

  /**
   * Finds parameter element inside GenericStoreEverything. Parameter element
   * must be of the same type as the other elements in GenericStoreEverything.
   *
   *
   * @param    element   the target object to search for in GenericStoreEverything.
   * @return             Returns true if found inside GenericStoreEverything
   *                     Otherwise, returns false
   *
   */
  public boolean find( Integer element ) {
    if( element == null ) {
      return false;
    }
    return searchFor( element.toString() ) != null;
  }

  /**
   * Adds the parameter element into the GenericStoreEverything as long as it
   * is unique with respect to the other elements inside GenericStoreEverything.
   * String representations of each object defines uniqueness.
   *
   *
   * @param   element   the object to add into the GenericStoreEverything
   * @return            Returns true if the addition was successful
   *                    Returns false otherwise.
   */
  public boolean add(Integer element) {
    if ( element == null || find( element ) ) { // null case: return false
      return false;
    }

    if ( head.getNext() == null ) {
      head.setNext( new ListNode( element ) ); // adds object immediately after head
      return true;
    } else {
      // appends to the head case
      if( element.toString().compareTo( head.getNext().toString() ) < 0 ) {
        head.setValue( element );
        ListNode front = new ListNode(); front.setNext( head );
        head = front; // makes a new linked list dummy head
        return true;
      }

      // appends to either the end or middle of the linked list case

      ListNode curr = head.getNext();
      while( curr != null ) {
        if( curr.toString().compareTo( element.toString() ) < 0 && ( curr.getNext() ==
                null || element.toString().compareTo( curr.getNext().toString() ) < 0 ) ) {

          ListNode tempNext = curr.getNext(); // appends to either the middle or end
          curr.setNext( new ListNode( element, tempNext ) );
          return true;
        }
        curr = curr.getNext();
      }
    }
    return true;
  }


  /**
   * Deletes the parameter element from GenericStoreEverything if it exists.
   * String representations of the object acts as a proxy in order
   * to find and delete objects.
   *
   * @param    element    the object to delete
   * @return              Returns true if the deletion was successful
   *                      Returns false otherwise.
   */
  public boolean delete(Integer element) {
    if( element == null ) { // null case: return false
      return false;
    } else {
      ListNode prevNode = head;
      ListNode curr     = head.getNext();
      while( curr != null ) { // searches for the node to delete
        if ( curr.toString().equals( element.toString() ) ) {
          prevNode.setNext( curr.getNext() );
          return true; // the object is found
        } else {
          prevNode = prevNode.getNext();
          curr     = curr.getNext(); //continues iterating through
        }
      }
    }
    return false;
  }

  /**
   * Returns the string representation of the GenericStoreEverything object
   * with set notation.
   *
   * @return the string representation of the GenericStoreEverything object
   */
  @Override
  public String toString() {
    build = new StringBuilder("{");

    ListNode curr = head;

    while( curr.getNext() != null ) { // adds all elements into the list
      build.append( curr.getNext() + ", ");
      curr = curr.getNext();
    }

    // adds the last curly brace
    if( build.lastIndexOf( "," ) == -1 ) {
      build.append( "}" );
    } else {
      build.delete( build.lastIndexOf( "," ), build.length() );
      build.append( "}" );
    }

    return build.toString();
  }
}

class Prerequisites {
    static Scanner input = new Scanner(System.in);
    static int N;

    public static void printAdjMatrix(int[][] adj) {
        for(int i = 1; i < adj.length; i++) {
            for(int j = 1; j < adj[i].length; j++) {
                System.out.print(adj[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void printAdjMatrix(GenericStoreEverything[] adj) {
        for(int i = 1; i < adj.length; i++) {
            System.out.println(adj[i]);
        }
    }
    
    public static List sortTopological(GenericStoreEverything[] adj) {
        Stack stack = new Stack(adj.length);
        boolean[] visited = new boolean[adj.length];

        for(int i = 1; i < adj.length; i++) {
            if(!visited[i] && adj[i] != null) {
                dfs(i, adj, stack, visited);
            }
        }

        List result = new List(adj.length);
        while(stack.size() > 0) {
            result.add(stack.pop());
        }
        return result;
    }

    public static void dfs(int node, GenericStoreEverything[] adj, Stack stack, boolean[] visited) {
        if (visited[node]) {
            return;
        }
        visited[node] = true;

        if(adj[node] != null) {
            ListNode head = adj[node].getHead();
            while(head.getNext() != null) {
                Integer nei = head.getNext().getValue();
                dfs(nei, adj, stack, visited);
                head = head.getNext();
            }
        }
        stack.push(node);
    }

    public static void main(String[] args) {
        N = input.nextInt();
        GenericStoreEverything[] adj = new GenericStoreEverything[N + 1];
        for(int i = 0; i < N; i++) {
            int nei;
            while((nei = input.nextInt()) != 0) {
                if(adj[nei] == null) {
                    adj[nei] = new GenericStoreEverything();
                }
                adj[nei].add(Integer.valueOf(i + 1));
            }
        }
        int[] memo = new int[N + 1]; int res = 0;
        List topologicalOrdering = sortTopological(adj);
        
        for(int i = 0; i < topologicalOrdering.size(); i++) {
            if(adj[topologicalOrdering.get(i)] != null) {
                ListNode head = adj[topologicalOrdering.get(i)].getHead();
                while(head.getNext() != null) {
                    Integer nei = head.getNext().getValue();
                    memo[nei] = Math.max(memo[nei], memo[topologicalOrdering.get(i)] + 1);
                    res = Math.max(res, memo[nei]);
                    head = head.getNext();
                }
            }
        }
        System.out.println(res + 1);
    }
    
   /*
    public static void main(String[] args) {
        int N = input.nextInt(); 
        int[][] adj = new int[N + 1][N + 1];
        for(int i = 0; i < N; i++) {
            int nei;
            while((nei = input.nextInt()) != 0) {
                adj[nei][i + 1] = 1;
            }
        }
        int[] memo = new int[N + 1]; int res = 0;
        List topologicalOrdering = sortTopological(adj);
        for(int i = 0; i < topologicalOrdering.size(); i++) {
            for(int j = 1; j < adj[topologicalOrdering.get(i)].length; j++) {
                if(adj[topologicalOrdering.get(i)][j] == 1) {
                    memo[j] = Math.max(memo[j], memo[topologicalOrdering.get(i)] + 1);
                    res = Math.max(res, memo[j]);
                }
            }
        }
        System.out.println(res + 1);
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
    */
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
}