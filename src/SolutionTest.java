
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.junit.jupiter.api.Test;

// public class SolutionTest {

//     @Test
//     public void calcFollAnswerTest() {
//         Map<Integer, List<Integer>> graph = new HashMap<>();
//         graph.put(1, new ArrayList<>(Arrays.asList(2, 3, 4)));
//         graph.put(2, new ArrayList<>(Arrays.asList(1)));
//         graph.put(3, new ArrayList<>(Arrays.asList(1, 5, 6, 7)));
//         graph.put(4, new ArrayList<>(Arrays.asList(1)));
//         graph.put(5, new ArrayList<>(Arrays.asList(3)));
//         graph.put(6, new ArrayList<>(Arrays.asList(3)));
//         graph.put(7, new ArrayList<>(Arrays.asList(3)));

//         int[] a = { 2, 4 };
//         // int[] b = { 2, 5 };
//         // int[] c = { 4, 5 };

//         List<int[]> q = new ArrayList<>(Arrays.asList(a));

//         long actual = Solution.calcFollAnswer(graph, q);

//         assertEquals(16L, actual);
//     }

//     @Test
//     public void getDistanceTest() {

//         Map<Integer, List<Integer>> graph = new HashMap<>();
//         graph.put(1, List.of(2, 3, 4));
//         graph.put(2, List.of(1));
//         graph.put(3, List.of(1, 5, 6, 7));
//         graph.put(4, List.of(1));
//         graph.put(5, List.of(3));
//         graph.put(6, List.of(3));
//         graph.put(7, List.of(3));

//         int[] points = { 2, 4 };

//         int expected = 2;

//         int actual = Solution.getDistance(graph, points);

//         assertEquals(expected, actual);
//     }

//     // @Test
//     // public void getPathPointsTest() {

//     // int[] a = { 2, 4 };
//     // int[] b = { 2, 5 };
//     // int[] c = { 4, 5 };

//     // List<int[]> exp = new ArrayList<>(Arrays.asList(a, b, c));

//     // List<Integer> q = new ArrayList<>(Arrays.asList(2, 4, 5));
//     // List<int[]> act = Solution.getPathPoints(q);

//     // String expected = "";
//     // for (int[] arr : exp) {
//     // expected += " " + arr[0] + " " + arr[1] + " ";
//     // ;
//     // }

//     // String actual = "";
//     // for (int[] arr : act) {
//     // actual += " " + arr[0] + " " + arr[1] + " ";
//     // }

//     // assertEquals(expected, actual);
//     // }

//     // @Test
//     // public void toGraphTest() {
//     //     Map<Integer, List<Integer>> expected = new HashMap<>();
//     //     expected.put(1, List.of(2, 3, 4));
//     //     expected.put(2, List.of(1));
//     //     expected.put(3, List.of(1, 5, 6, 7));
//     //     expected.put(4, List.of(1));
//     //     expected.put(5, List.of(3));
//     //     expected.put(6, List.of(3));
//     //     expected.put(7, List.of(3));
    

//     //     Map<Integer, List<Integer>> actual = Solution.toGraph(new ArrayList<List<Integer>>(
//     //             Arrays.asList(
//     //                 List.of(1, 2),
//     //                 List.of(1, 3),
//     //                 List.of(1, 4),
//     //                 List.of(3, 5),
//     //                 List.of(3, 6),
//     //                 List.of(3, 7)
//     //                     )));

//     //     assertEquals(expected, actual);
//     // }

// }