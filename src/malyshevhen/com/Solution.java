package malyshevhen.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Solution {
    private static Node[] nodes;
    private static int[] distance;
    private static boolean[] visited;


    static class Node {
        final int data;
        Node[] children = new Node[0];
        long[] cache;

        public Node(int data, int nodesMaxCount) {
            this.data = data;
            this.cache = new long[nodesMaxCount];
        }

        public int getData() {
            return data;
        }

        public Node[] getChildren() {
            return children;
        }

        public void addChild(Node child) {
            if (this.children.length == 0) {
                this.children = new Node[]{child};
            } else {
                var temp = this.children;

                var updatedArray = new Node[temp.length + 1];

                for (int i = 0; i < temp.length; i++) {
                    updatedArray[i] = temp[i];
                }
                updatedArray[updatedArray.length - 1] = child;

                this.children = updatedArray;
            }
        }

        public void addCache (int nodeData, long cache) {
            this.cache[nodeData] = cache;
        }
        public long getCache (int nodeData) {
            return this.cache[nodeData];
        }

        public boolean ifCached (int nodeData) {
            return this.cache[nodeData] != 0;
        }
    }

    public static void main(String[] args) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        var stringTokenizer = new StringTokenizer(bufferedReader.readLine());

        int edgesCount = Integer.parseInt(stringTokenizer.nextToken());
        int wayPointsSetCount = Integer.parseInt(stringTokenizer.nextToken());

        nodes = new Node[edgesCount + 1];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i, edgesCount + 1);
        }

        visited = new boolean[edgesCount + 1];

        Arrays.fill(visited, false);

        distance = new int[edgesCount + 1];

        Arrays.fill(distance, 0);


        for (int i = 0; i < edgesCount - 1; i++) {
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());

            int firstNum = Integer.parseInt(stringTokenizer.nextToken());
            int secondNum = Integer.parseInt(stringTokenizer.nextToken());

            addNodeToGraph(firstNum, secondNum);
        }

        for (int i = 0; i < wayPointsSetCount; i++) {
            int sizeOfSet = Integer.parseInt(bufferedReader.readLine());

            var set = new int[sizeOfSet];

            stringTokenizer = new StringTokenizer(bufferedReader.readLine());

            for (int j = 0; j < set.length; j++) {
                set[j] = Integer.parseInt(stringTokenizer.nextToken());
            }

            var wayPoints = getEndWayPoints(set);
            var computation = calcFollAnswer(wayPoints);
            System.out.println(computation);
        }
    }

    private static void addNodeToGraph(int firstNodeData, int secondNodeData) {
        nodes[firstNodeData].addChild(nodes[secondNodeData]);
        nodes[secondNodeData].addChild(nodes[firstNodeData]);
    }

    public static int[][] getEndWayPoints(int[] wayPoints) {
        int arrayLength = (((wayPoints.length - 1) * wayPoints.length) / 2);
        var list = new int[arrayLength][];

        if (wayPoints.length == 1) {
            return list;
        } else if (wayPoints.length == 2) {
            list[0] = wayPoints;
        } else if (wayPoints.length > 2) {
            int counter = 0;

            for (int i = 0; i < wayPoints.length - 1; i++) {
                for (int y = i + 1; y < wayPoints.length; y++) {
                    int[] points = {wayPoints[i], wayPoints[y]};
                    list[counter] = points;
                    counter++;
                }
            }
        }

        return list;
    }

    public static long calcFollAnswer(int[][] wayPointsSet) {
        long mod = 1_000_000_007L;
        long temp = 0L;

        if (wayPointsSet.length == 0) {
            return 0L;
        }

        for (var wayPoints : wayPointsSet) {
            int startPoint = wayPoints[0];
            int endPoint = wayPoints[1];

            if (startPoint > endPoint) {
                int tmp = startPoint;
                startPoint = endPoint;
                endPoint = tmp;
            }

            if (nodes[startPoint].ifCached(endPoint)) {
                long tempData = nodes[startPoint].getCache(endPoint);
                temp = temp + tempData;
            } else {
            long distance = getDistance(startPoint, endPoint);

            long calculation = ((long) startPoint * (long) endPoint) * distance;

            temp += calculation;

                nodes[startPoint].addCache(endPoint, calculation);
            }
        }
        return temp % mod;
    }

    public static long getDistance(int start, int end) {
        if (start == end) {
            return 0L;
        }

        visited[start] = true;

        var bfsQueue = new LinkedList<Node>();

        bfsQueue.add(nodes[start]);

        while (!bfsQueue.isEmpty()) {
            var currentNode = bfsQueue.poll();
            int currentNodeDistance = distance[currentNode.data];

            if (currentNode.data == end) {
                Arrays.fill(distance, 0);
                Arrays.fill(visited, false);

                return currentNodeDistance;
            }

            var childrenSet = nodes[currentNode.data].getChildren();

            for (var child : childrenSet) {
                if (!visited[child.getData()]) {
                    visited[child.getData()] = true;
                    distance[child.getData()] = currentNodeDistance + 1;
                    bfsQueue.add(child);
                }
            }
        }

        return 0;
    }
}
