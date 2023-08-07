import java.util.*;
import java.io.*;

public class BFSDFS
{
    public int vertices; 
    public int edges;
    public List<List<Integer>> adjacencyList;

    public BFSDFS(int n) 
    {
        Random random = new Random();
        this.vertices = random.nextInt(n) + 1; 
        this.edges = random.nextInt(computeMaxEdges(vertices)) + 1; 
        adjacencyList = new ArrayList<>(vertices); 
        for (int i = 0; i < vertices; i++) 
            adjacencyList.add(new ArrayList<>()); 
        for (int i = 0; i < edges; i++) {
            int v = random.nextInt(vertices); 
            int w = random.nextInt(vertices);
            if (adjacencyList.get(v).contains(w)) { 
                i = i - 1; 
                continue; 
            } 
            addEdge(v, w); 
        } 
    } 

    int computeMaxEdges(int numOfVertices) 
    {  
        return numOfVertices * ((numOfVertices - 1) / 2); 
    } 

    void addEdge(int v, int w) 
    { 
        adjacencyList.get(v).add(w);
        if (v != w) 
            adjacencyList.get(w).add(v); 
    }

    void BFS()
    {
        print();
        System.out.println("\nExecuting BFS using Queue");
        int s = 0;
        boolean visited[] = new boolean[vertices];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[s]=true;
        queue.add(s);
        System.out.print("[");
        while (queue.size() != 0)
        {
            s = queue.poll();
            System.out.print(s+", ");
            Iterator<Integer> i = adjacencyList.get(s).listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        System.out.print("]");
    }

    void DFS()
    {
        print();
        System.out.println("\nExecuting DFS using Stack");
        Vector<Boolean> visited = new Vector<Boolean>(vertices);
        for (int i = 0; i < vertices; i++)
            visited.add(false);

        for (int i = 0; i < vertices; i++)
            if (!visited.get(i))
                DFSUtil(i, visited);
    }

    void DFSUtil(int s, Vector<Boolean> visited)
    {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        
        System.out.print("[");
        while(stack.empty() == false)
        {
            s = stack.peek();
            stack.pop();
            if(visited.get(s) == false)
            {
                System.out.print(s + ", ");
                visited.set(s, true);
            }
            Iterator<Integer> itr = adjacencyList.get(s).iterator();

            while (itr.hasNext()) 
            {
                int v = itr.next();
                if(!visited.get(v))
                    stack.push(v);
            }
        }
        System.out.print("]");
    }

    void print()
    {
        System.out.println("\nAdjacency List:"); 
        for (int i = 0;i < adjacencyList.size(); i++)
        { 
            System.out.print(i + "->"); 

            List<Integer> list = adjacencyList.get(i); 

            if (list.isEmpty()) 
                System.out.print(""); 
            else
            { 
                int size = list.size(); 
                for (int j = 0; j < size; j++) 
                { 
                    System.out.print(list.get(j)); 
                    if (j < size - 1) 
                        System.out.print("->"); 
                } 
            } 

            System.out.println(); 
        } 
    }
}
