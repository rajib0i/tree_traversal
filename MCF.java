import java.util.*;

public class MCF
{
    String print = "\nTail   Head   Cost\n";
    public class Edge
    {
        int to, f, cap, cost, rev;

        Edge (int to, int cap, int cost, int rev)
        {
            this.to = to;
            this.cap = cap;
            this.cost = cost;
            this.rev = rev;
        }
    }

    public List<Edge>[] createGraph(int n)
    {
        List<Edge>[] graph = new List[n];
        for(int i=0; i<n; i++)
            graph[i] = new ArrayList<Edge>();
        return graph;
    }

    public void addEdge(List<Edge>[] graph, int s, int t, int cap, int cost)
    {
        graph[s].add(new Edge(t, cap, cost, graph[t].size()));
        graph[t].add(new Edge(s, 0, -cost, graph[s].size() - 1));
    }

    public void bellmanFord(List<Edge>[] graph, int s, int[] dist, int[] prevnode, int[] prevedge, int[] curflow)
    {
        int n = graph.length;
        Arrays.fill(dist, 0, n, Integer.MAX_VALUE);
        dist[s] = 0;
        curflow[s] = Integer.MAX_VALUE;
        boolean[] inqueue = new boolean[n];
        int[] q = new int[n];
        int qt = 0;
        q[qt++] = s;
        for (int qh = 0; (qh - qt) % n != 0; qh++)
        {
            int u = q[qh % n];
            inqueue[u] = false;
            for (int i = 0; i < graph[u].size(); i++)
            {
                Edge e = graph[u].get(i);
                if (e.f >= e.cap)
                    continue;
                int v = e.to;
                int ndist = dist[u] + e.cost;
                if (dist[v] > ndist)
                {
                    dist[v] = ndist;
                    prevnode[v] = u;
                    prevedge[v] = i;
                    curflow[v] = Math.min(curflow[u], e.cap - e.f);
                    if (!inqueue[v])
                    {
                        inqueue[v] = true;
                        q[qt++ % n] = v;
                    }
                }
            }
        }
    }

    public int minCostFlow(List<Edge>[] graph, int s, int t, int maxf)
    {
        int n = graph.length;
        int[] dist = new int[n];
        int[] curflow = new int[n];
        int[] prevedge = new int[n];
        int[] prevnode = new int[n];

        int flow = 0;
        int flowCost = 0;
        while (flow < maxf)
        {
            bellmanFord(graph, s, dist, prevnode, prevedge, curflow);
            if (dist[t] == Integer.MAX_VALUE)
                break;
            int df = Math.min(curflow[t], maxf - flow);
            flow += df;
            for (int v = t; v != s; v = prevnode[v])
            {
                Edge e = graph[prevnode[v]].get(prevedge[v]);
                e.f += df;
                graph[v].get(e.rev).f -= df;
                flowCost += df * e.cost;
                print += (prevnode[v]+1)+"      "+(v+1)+"      "+df * e.cost+"\n";
            }
        }
        return flowCost;
    }
    
    public void find(int node, int cap, int flow, int dn, int sn)
    {
        List<Edge>[] graph = createGraph(node);
        for(int i=0; i < node; i++)
            for(int j=i+1; j < node; j++)
                addEdge(graph,i,j,(int)Math.floor(Math.random()*(cap-0+1)+0),1);
        System.out.println("\nCost: "+minCostFlow(graph, dn, sn, Integer.MAX_VALUE)+print);
    }
}
