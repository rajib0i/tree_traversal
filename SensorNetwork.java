import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class SensorNetwork {

    private Map<Integer, Axis> nodes = new LinkedHashMap<Integer, Axis>();
    Map<Integer, Boolean> discovered = new HashMap<Integer, Boolean>();
    Map<Integer, Boolean> explored = new HashMap<Integer, Boolean>();
    Map<Integer, Integer> parent = new HashMap<Integer, Integer>();
    Map<Integer, Integer> connectedNodes = new HashMap<Integer, Integer>();
    Stack<Integer> s = new Stack<Integer>();
    ArrayList<String> str = new ArrayList<String>();

    public static void main(String[] args) {
        SensorNetwork sensor = new SensorNetwork();
        Scanner scan = new Scanner(System.in);
        Random r = new Random();

        System.out.println("Enter the width:");
        double width = scan.nextDouble();

        System.out.println("Enter the height:");
        double height = scan.nextDouble();

        System.out.println("Enter the number of nodes:");
        int numberOfNodes = scan.nextInt();

        System.out.println("Enter the Transmission range in meters:");
        int transmissionRange = scan.nextInt();

        System.out.println("\nEnter 0 for Dijkstra's shortest path algorithm,\nEnter 1 for Bellman-Ford dynamic programming algorithm,\nEnter 2 for finding the shortest path between two edges.");
        int mode = scan.nextInt();

        int[] num = new int[numberOfNodes];
        for(int i=0;i<num.length;i++)
            num[i] = i;
        sensor.makeCombination(num,num.length,3);

        switch(mode)
        {
            case 0:
            System.out.println("\nRunning Dijkstra's shortest path algorithm\n");
            Edge[] edges = new Edge[numberOfNodes];
            for(int i = 0; i < edges.length; i++)
            {
                int n = r.nextInt(900)+100; String s = ""+n;
                char c0 = s.charAt(0),c1= s.charAt(1),c2= s.charAt(2);
                edges[i] = new Edge(Integer.parseInt(""+c0),Integer.parseInt(""+c1),Integer.parseInt(""+c2));
            }
            Dijkstra d = new Dijkstra(edges, numberOfNodes);
            d.calcShortestDist();
            d.print();
            break;
            case 1:
            System.out.println("\nRunning Bellman-Ford dynamic programming algorithm\n");
            int g[][] = new int[numberOfNodes][3];
            for(int i=0;i<numberOfNodes;i++)
            {
                String gs = sensor.str.get(i);
                for(int j=0;j<3;j++)
                {
                    if(j == 0)
                        g[i][j]=Integer.parseInt(Character.toString(gs.charAt(0)));
                    else if (j == 1)
                        g[i][j]=Integer.parseInt(Character.toString(gs.charAt(1)));
                    else
                        g[i][j]=Integer.parseInt(Character.toString(gs.charAt(2)));
                }
            }
            BellmanFord bf = new BellmanFord();
            bf.BellmanFord(g, numberOfNodes, numberOfNodes, 0);
            break;
            case 2:
            System.out.println("\nRunning to find the shortest path between two edges\n");
            System.out.println("Enter an edge to find path from:");
            int from = scan.nextInt();
            System.out.println("Enter an edge to find path to:");
            int to = scan.nextInt();
            if(from >= 0 && to <= numberOfNodes)
            {
                ShortestPath sp = new ShortestPath(numberOfNodes);
                for(int i = 0; i < numberOfNodes; i++)
                {
                    String text = sensor.str.get(i);
                    sp.addEdge(Integer.parseInt(Character.toString(text.charAt(0))),Integer.parseInt(Character.toString(text.charAt(1))),Integer.parseInt(Character.toString(text.charAt(2))));
                }
                System.out.printf("\nShortest Distance between" +" %d and %d is %d\n", from,to, sp.findShortestPath(from, to));
            }
            else
            {
                System.err.println("Invalid Input");
                System.exit(1);
            }
            break;
            default:
            System.err.println("Invalid Entry");
            System.exit(1);
        }
        scan.close();

        sensor.populateNodes(numberOfNodes, width, height);
        Map<Integer, Set<Integer>> adjacencyList1 = new LinkedHashMap<Integer, Set<Integer>> ();
        sensor.populateAdjacencyList(numberOfNodes, transmissionRange, adjacencyList1);
        sensor.executeDepthFirstSearchAlg(width, height, adjacencyList1);
    }

    void combinationUtil(int arr[], int data[], int start,int end, int index, int r)
    {
        String s = "";
        if (index == r)
        {
            for (int j=0; j<r; j++)
                s += data[j];
            str.add(s);
            s = "";
            return;
        }
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }

    void makeCombination(int arr[], int n, int r)
    {
        int data[]=new int[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }

    void executeDepthFirstSearchAlg(double width, double height, Map<Integer, Set<Integer>> adjList) {
        List<Set<Integer>> connectedNodes = new ArrayList<Set<Integer>>();
        for(int node: adjList.keySet()) {
            Set<Integer> connectedNode = new LinkedHashSet<Integer>();
            recursiveDFS(node, connectedNode, adjList);

            if(!connectedNode.isEmpty()) {
                connectedNodes.add(connectedNode);
            }
        }

        //Draw sensor network graph
        SensorNetworkGraph graph = new SensorNetworkGraph();
        graph.setGraphWidth(width);
        graph.setGraphHeight(height);
        graph.setNodes(nodes);
        graph.setAdjList(adjList);
        graph.setPreferredSize(new Dimension(960, 800));
        Thread graphThread = new Thread(graph);
        graphThread.start(); 
    }

    void recursiveDFS(int u, Set<Integer> connectedNode, Map<Integer, Set<Integer>> adjList) {

        if(!s.contains(u) && !explored.containsKey(u)) {
            s.add(u);
            discovered.put(u, true);
        } 

        while(!s.isEmpty()) {
            if(!explored.containsKey(u)) {
                List<Integer> list = new ArrayList<Integer>(adjList.get(u));
                for(int v: list) {

                    if(!discovered.containsKey(v)) {
                        s.add(v);
                        discovered.put(v, true);

                        if(parent.get(v) == null) {
                            parent.put(v, u);
                        }
                        recursiveDFS(v, connectedNode, adjList);
                    } else if(list.get(list.size()-1) == v) {
                        if( parent.containsKey(u)) {
                            explored.put(u, true);
                            s.removeElement(u);

                            connectedNode.add(u);
                            recursiveDFS(parent.get(u), connectedNode, adjList);
                        }
                    }
                }
                if(!explored.containsKey(u))
                    explored.put(u, true);
                s.removeElement(u);
                connectedNode.add(u);
            }
        }

    }

    void populateNodes(int nodeCount, double width, double height) {
        Random random = new Random();

        for(int i = 1; i <= nodeCount; i++) {
            Axis axis = new Axis();
            int scale = (int) Math.pow(10, 1);
            double xAxis =(0 + random.nextDouble() * (width - 0));
            double yAxis = 0 + random.nextDouble() * (height - 0);

            xAxis = (double)Math.floor(xAxis * scale) / scale;
            yAxis = (double)Math.floor(yAxis * scale) / scale;

            axis.setxAxis(xAxis);
            axis.setyAxis(yAxis);

            nodes.put(i, axis);    
        }
    }

    void populateAdjacencyList(int nodeCount, int tr, Map<Integer, Set<Integer>> adjList) {
        for(int i=1; i<= nodeCount; i++) {
            adjList.put(i, new HashSet<Integer>());
        }

        for(int node1: nodes.keySet()) {
            Axis axis1 = nodes.get(node1);
            for(int node2: nodes.keySet()) {
                Axis axis2 = nodes.get(node2);

                if(node1 == node2) {
                    continue;
                }
                double xAxis1 = axis1.getxAxis();
                double yAxis1 = axis1.getyAxis();

                double xAxis2 = axis2.getxAxis();
                double yAxis2 = axis2.getyAxis();

                double distance =  Math.sqrt(((xAxis1-xAxis2)*(xAxis1-xAxis2)) + ((yAxis1-yAxis2)*(yAxis1-yAxis2)));

                if(distance <= tr) {
                    Set<Integer> tempList = adjList.get(node1);
                    tempList.add(node2);
                    adjList.put(node1, tempList);

                    tempList = adjList.get(node2);
                    tempList.add(node1);
                    adjList.put(node2, tempList);
                }
            }
        }
    }
}