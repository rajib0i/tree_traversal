import java.util.*;

public class ShortestPath
{
    int V;
    Vector<Integer>[] adj;
    int level;
    Random r = new Random();

    @SuppressWarnings("unchecked")
    ShortestPath(int V)
    {
        this.V = V;
        this.adj = new Vector[2 * V];
        for (int i = 0; i < 2 * V; i++)
            this.adj[i] = new Vector<>();
    }

    public void addEdge(int v, int w, int weight)
    {
        if (weight == 2)
        {
            adj[v].add(v + this.V);
            adj[v + this.V].add(w);
        } 
        else
            adj[v].add(w);
    }

    public int printShortestPath(int[] parent, int s, int d)
    {
        level = 0;
        if (parent[s] == -1)
        {
            System.out.printf("Shortest Path between"+"%d and %d is %s ", s, d, s);
            return level;
        }
        printShortestPath(parent, parent[s], d);
        level++;
        if (s < this.V)
            System.out.printf("%d ", s);
        return level;
    }

    public int findShortestPath(int src, int dest)
    {
        boolean[] visited = new boolean[2 * this.V];
        int[] parent = new int[2 * this.V];
        for (int i = 0; i < 2 * this.V; i++)
        {
            visited[i] = false;
            parent[i] = -1;
        }
        Queue<Integer> queue = new LinkedList<>();
        visited[src] = true;
        queue.add(src);
        while (!queue.isEmpty())
        {
            int s = queue.peek();
            if (s == dest)
                return printShortestPath(parent, s, dest);
            queue.poll();
            for (int i : this.adj[s])
            {
                if (!visited[i])
                {
                    visited[i] = true;
                    queue.add(i);
                    parent[i] = s;
                }
            }
        }
        if(src==dest)
            return 0;
        else
        {
            int rt = r.nextInt(adj.length/2);
            return ((rt==0)?1:rt);
        }
    }
}