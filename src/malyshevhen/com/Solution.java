package malyshevhen.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


class Solution {
    private static final Map<Long, Node> graph = new HashMap<>();

        static class Node {
        private final long data;
        private final Set<Node> children;
        private Status status;
        private int distance;

        private enum Status {
            VISITED {
                @Override
                public boolean isVisited() {
                    return true;
                }
            },
            NOT_VISITED {
                @Override
                public boolean isNotVisited() {
                    return true;
                }
            };

            public boolean isVisited() {
                return false;
            }

            public boolean isNotVisited() {
                return true;
            }

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return data == node.data;
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }

        public Node(long data) {
            this.data = data;
            this.children = new HashSet<>();
            this.status = Status.NOT_VISITED;
            this.distance = 0;
        }

        public void addChild(Node child) {
            this.children.add(child);
        }

        public void incrementDistance() {
            this.distance += 1;
        }

        public void resetNode() {
            this.status = Status.NOT_VISITED;
            this.distance = 0;
        }

        public Set<Node> getChildren() {
            return children;
        }

        public Status getStatus() {
            return status;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }

    public static void main(String[] args) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        var settingNumbers = bufferedReader.readLine().split(" ");

        int edgesCount = Integer.parseInt(settingNumbers[0]);
        int querySetCount = Integer.parseInt(settingNumbers[1]);

        for (int i = 0; i < edgesCount - 1; i++) {
            var multipleInput = bufferedReader.readLine().split(" ");

            long firstNum = Long.parseLong(multipleInput[0]);
            long secondNum = Long.parseLong(multipleInput[1]);

            addNodeToGraph(firstNum, secondNum);
        }

        var querySet = new long[querySetCount][];

        for (int i = 0; i < querySetCount; i++) {
            var sizeOfSet = Integer.parseInt(bufferedReader.readLine());
            var set = new long[sizeOfSet];

            var multipleInput = bufferedReader.readLine().split(" ");

            for (int j = 0; j < set.length; j++) {
                set[j] = Long.parseLong(multipleInput[j]);
            }
            querySet[i] = set;
        }

        for (var query : querySet) {
            var pathPointsSet = getPathPoints(query);
            var calculateResult = calcFollAnswer(graph,pathPointsSet);
            System.out.println(calculateResult);
        }
    }

    private static void addNodeToGraph(long firstNodeData, long secondNodeData) {
        if (graph.containsKey(firstNodeData)) {
            if (graph.containsKey(secondNodeData)) {
                var firstNode = graph.get(firstNodeData);
                var secondNode = graph.get(secondNodeData);

                firstNode.addChild(secondNode);
                secondNode.addChild(firstNode);

                graph.replace(firstNodeData, firstNode);
                graph.replace(secondNodeData, secondNode);
            } else {
                var firstNode = graph.get(firstNodeData);
                var secondNode = new Node(secondNodeData);

                firstNode.addChild(secondNode);
                secondNode.addChild(firstNode);

                graph.replace(firstNodeData, firstNode);
                graph.put(secondNodeData, secondNode);
            }
        } else {
            if (graph.containsKey(secondNodeData)) {
                var firstNode = new Node(firstNodeData);
                var secondNode = graph.get(secondNodeData);

                firstNode.addChild(secondNode);
                secondNode.addChild(firstNode);

                graph.put(firstNodeData, firstNode);
                graph.replace(secondNodeData, secondNode);
            } else {
                var firstNode = new Node(firstNodeData);
                var secondNode = new Node(secondNodeData);

                firstNode.addChild(secondNode);
                secondNode.addChild(firstNode);

                graph.put(firstNodeData, firstNode);
                graph.put(secondNodeData, secondNode);
            }
        }
    }

    public static List<long[]> getPathPoints(long[] q) {
        var list = new LinkedList<long[]>();

        if (q.length > 2) {
            for (int i = 0; i < q.length - 1; i++) {
                for (int y = i + 1; y < q.length; y++) {
                    long[] points = {q[i], q[y]};
                    list.add(points);
                }
            }
        } else if (q.length == 2) {
            list.add(q);
        }

        return list;
    }

    public static long calcFollAnswer(Map<Long, Node> graph, List<long[]> q) {
        long mod = 1_000_000_007L;
        long result = 0L;

        for (var js : q) {
            long distance = getDistance(graph.get(js[0]), graph.get(js[1]));

            result += ((js[0] * js[1]) * distance);
        }
        result = result % mod;

        return result;
    }

    public static long getDistance(Node start, Node end) {
        start.setStatus(Node.Status.VISITED);

        var bfsQueue = new LinkedList<Node>();

        bfsQueue.add(start);

        while (!bfsQueue.isEmpty()) {
            var currentNode = bfsQueue.poll();

            if (currentNode.equals(end)) {
                resetNodes();
                return currentNode.getDistance();
            }

            var children = currentNode.getChildren();

            for (var child : children) {
                if (child.getStatus().isNotVisited()) {
                    child.setStatus(Node.Status.VISITED);
                    child.setDistance(currentNode.getDistance());
                    child.incrementDistance();
                    bfsQueue.add(child);
                }
            }
        }

        return 0;
    }

    private static void resetNodes() {
        graph.values().forEach(Node::resetNode);
    }
}
