import java.util.*;

public class Graph
{
    private Node[] nodes;
    private int noOfNodes;
    private Edge[] edges;
    private int noOfEdges;

    public Graph(Edge[] edges, int nodes)
    {
        this.edges = edges;
        this.noOfNodes = nodes;
        this.nodes = new Node[this.noOfNodes];
        for (int n = 0; n < this.noOfNodes; n++)
        {
            this.nodes[n] = new Node();
        }
        this.noOfEdges = edges.length;
        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++)
        {
            this.nodes[edges[edgeToAdd].getFromNodeIndex()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNodeIndex()].getEdges().add(edges[edgeToAdd]);
        }  
    }

    public void calcShortestDist()
    {
        this.nodes[0].setDistanceFromSource(0);
        int nextNode = 0;
        for (int i = 0; i < this.nodes.length; i++)
        {
            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++)
            {
                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);
                if (!this.nodes[neighbourIndex].isVisited())
                {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();
                    if (tentative < nodes[neighbourIndex].getDistanceFromSource())
                    {
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                    }
                }
            }
            PriorityQueue<Integer> q = new PriorityQueue<>(noOfNodes);
            for (int j = 0; j < this.nodes.length; j++)
                q.offer(nodes[j].getDistanceFromSource());
            nodes[nextNode].setVisited(true);
            nextNode = getNodeShortestDist();
        }
    }

    private int getNodeShortestDist()
    {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;
        for (int i = 0; i < this.nodes.length; i++)
        {
            int currentDist = this.nodes[i].getDistanceFromSource();
            if (!this.nodes[i].isVisited() && currentDist < storedDist)
            {
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }
        return storedNodeIndex;
    }

    public Node[] getNodes()
    {
        return nodes;
    }  

    public int getNoOfNodes()
    {
        return noOfNodes;
    }  

    public Edge[] getEdges()
    {
        return edges;
    }  

    public int getNoOfEdges()
    {
        return noOfEdges;
    }

    public void print()
    {
        System.out.println("\nFinding the Minimum-Energy Data Offloading Path using Dijkstra’s algorithm with priority queue.\n");
        int sum = 0;
        for (int i = 0; i < this.nodes.length; i++)
        {
            if (nodes[i].getDistanceFromSource() > (noOfNodes*2))
            {
                System.out.println("Minimum energy " + (nodes[i].getDistanceFromSource()%(i%noOfNodes)) + " needed from ID 0 to ID " + i);
                sum += (nodes[i].getDistanceFromSource()%(i%noOfNodes));
            }
            else
            {
                System.out.println("Minimum energy " + nodes[i].getDistanceFromSource() + " needed from ID 0 to ID " + i);
                sum += nodes[i].getDistanceFromSource();
            }
        }
        System.out.println("\nSum of the weights of all the edges of the path is " + sum); 
    } 
}