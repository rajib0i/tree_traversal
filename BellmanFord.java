import java.util.*;

public class BellmanFord
{
    public void BellmanFord(int graph[][], int V, int E, int src)
    {
        int []dis = new int[V];
        for (int i = 0; i < V; i++)
            dis[i] = Integer.MAX_VALUE;
            
        dis[src] = 0;
        for (int i = 0; i < V - 1; i++)
        {
            for (int j = 0; j < E; j++)
            {
                if (dis[graph[j][0]] != Integer.MAX_VALUE && dis[graph[j][0]] + graph[j][2] <
                dis[graph[j][1]])
                    dis[graph[j][1]] =
                    dis[graph[j][0]] + graph[j][2];
            }
        }
        
        for (int i = 0; i < E; i++)
        {
            int x = graph[i][0];
            int y = graph[i][1];
            int weight = graph[i][2];
            if (dis[x] != Integer.MAX_VALUE && dis[x] + weight < dis[y])
                System.out.println("Graph contains negative weight cycle");
        }
        
        int tDist = 0;
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; i++)
        {
            if(dis[i]>E)
            {
                System.out.println(i + "\t\t" + dis[i]%i);
                tDist += dis[i]%i;
            }
            else
            {
                System.out.println(i + "\t\t" + dis[i]);
                tDist += dis[i];
            }
        }
        System.out.println("\nTotal Distance of all edges is: "+tDist);
    }
}
