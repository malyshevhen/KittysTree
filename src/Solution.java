import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

class Solution {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        Map<Long, List<Long>> graph = new HashMap<>();

        for (int i = 1; i < n; i++) {
            String[] MultipleInput = bufferedReader.readLine().split(" ");

            long firstInt = Long.parseLong(MultipleInput[0]);
            long secondInt = Long.parseLong(MultipleInput[1]);

            if (!graph.containsKey(firstInt)) {
                graph.put(firstInt, new ArrayList<Long>());
            }
            if (!graph.containsKey(secondInt)) {
                graph.put(secondInt, new ArrayList<Long>());
            }
            graph.get(firstInt).add(secondInt);
            graph.get(secondInt).add(firstInt);
        }

        List<List<Long>> querySet = new ArrayList<>();

        for (int i = 0; i < k; i++) {

            int j = Integer.parseInt(bufferedReader.readLine());

            querySet.add(Stream.of(bufferedReader.readLine().split(" "))
                    .map(Long::parseLong)
                    .collect(toList()));

        }

        for (List<Long> query : querySet) {

            long result = calcFollAnswer(graph, getPathPoints(query));

            System.out.println(result);
        }

        bufferedReader.close();

    }

    public static List<List<Long>> getPathPoints(List<Long> q) {

        List<List<Long>> list = new LinkedList<>();

        if (q.size() > 2) {

            for (int i = 0; i < q.size() - 1; i++) {

                for (int y = i + 1; y < q.size(); y++) {
                    list.add(List.of(q.get(i), q.get(y)));
                }

            }
        } else if (q.size() == 2) {
            list.add(List.of(q.get(0), q.get(1)));
        }

        return list;

    }

    public static long calcFollAnswer(Map<Long, List<Long>> graph, List<List<Long>> q) {

        long mod = 1000000007L;
        long result = 0L;

        for (List<Long> js : q) {

            long distance = getDistance(graph, js);

            result += ((js.get(0) * js.get(1)) * distance);
        }

        return result % mod;
    }

    public static long getDistance(Map<Long, List<Long>> graph, List<Long> pathPoints) {

        long start = pathPoints.get(0);
        long end = pathPoints.get(1);

        List<Long> visited = new ArrayList<>();
        visited.add(start);

        Queue<long[]> queue = new LinkedList<>();

        long[] i = { start, 0 };
        queue.add(i);

        List<Long> neighbors = new ArrayList<>();

        while (!queue.isEmpty()) {
            long[] child = queue.poll();

            if (child[0] == end) {
                return child[1];
            }

            neighbors = graph.get(child[0]);

            for (long neighbor : neighbors) {

                if (!visited.contains(neighbor)) {

                    visited.add(neighbor);
                    long[] j = { neighbor, child[1] + 1 };
                    queue.add(j);
                }

            }
        }

        return 0;
    }

}
