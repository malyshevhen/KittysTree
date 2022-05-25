import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

class Solution {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> tree = new ArrayList<>();

        IntStream.range(0, n - 1).forEach(i -> {
            try {
                tree.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<List<Integer>> querySet = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int j = Integer.parseInt(bufferedReader.readLine());
            querySet.add(Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt).collect(toList()));

        }

        String s = "";

        for (List<Integer> query : querySet) {

            long result = calcFollAnswer(toGraph(tree), getPathPoints(query));

            s += result + "\n";

        }

        System.out.println(s.strip());

        bufferedReader.close();

    }

    public static long calcFollAnswer(Map<Integer, List<Integer>> graph, List<int[]> q) {

        long mod = 1000000007;
        long result = 0;

        for (int[] js : q) {
            result += ((js[0] * js[1]) * getDistance(graph, js));
        }

        return result % mod;
    }

    public static int getDistance(Map<Integer, List<Integer>> graph, int[] pathPoints) {

        int start = pathPoints[0];
        int end = pathPoints[1];

        List<Integer> visited = new ArrayList<>();
        visited.add(start);

        Queue<int[]> queue = new LinkedList<>();

        int[] i = { start, 0 };
        queue.add(i);

        List<Integer> neighbors = new ArrayList<>();

        while (!queue.isEmpty()) {
            int[] child = queue.poll();

            if (child[0] == end) {
                return child[1];
            }

            neighbors = graph.get(child[0]);

            for (int neighbor : neighbors) {

                if (!visited.contains(neighbor)) {

                    visited.add(neighbor);
                    int[] j = { neighbor, child[1] + 1 };
                    queue.add(j);
                }

            }
        }

        return 0;
    }

    public static List<int[]> getPathPoints(List<Integer> q) {

        List<int[]> list = new LinkedList<>();
        for (int i = 0; i < q.size() - 1; i++) {

            list.add(new int[] { q.get(i), q.get(i + 1) });

        }
        list.add(new int[] { q.get(0), q.get(q.size() - 1) });

        return list;

    }

    public static Map<Integer, List<Integer>> toGraph(List<List<Integer>> tree) {

        Map<Integer, List<Integer>> resultGraph = new HashMap<>();

        for (List<Integer> list : tree) {
            if (!resultGraph.containsKey(list.get(0))) {
                resultGraph.put(list.get(0), new ArrayList<Integer>());
            }
            if (!resultGraph.containsKey(list.get(1))) {
                resultGraph.put(list.get(1), new ArrayList<Integer>());
            }
            resultGraph.get(list.get(0)).add(list.get(1));
            resultGraph.get(list.get(1)).add(list.get(0));

        }
        return resultGraph;

    }

}
