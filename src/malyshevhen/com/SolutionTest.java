package malyshevhen.com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import malyshevhen.com.Solution.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    Map<Long, Node> graph = new HashMap<>();
    Node head;
    Node tail;

    @BeforeEach
    void setUp() {
        var node1 = new Node(1);
        var node2 = new Node(2);
        var node3 = new Node(3);
        var node4 = new Node(4);
        var node5 = new Node(5);
        var node6 = new Node(6);
        var node7 = new Node(7);

        node1.addChild(node2);
        node1.addChild(node3);
        node1.addChild(node4);
        node2.addChild(node1);
        node3.addChild(node1);
        node4.addChild(node1);
        node3.addChild(node5);
        node3.addChild(node6);
        node3.addChild(node7);
        node5.addChild(node3);
        node6.addChild(node3);
        node7.addChild(node3);

        graph.put(1L, node1);
        graph.put(2L, node2);
        graph.put(3L, node3);
        graph.put(4L, node4);
        graph.put(5L, node5);
        graph.put(6L, node6);
        graph.put(7L, node7);

        head = node4;
        tail = node5;
    }

    @Test
    void getPathPoints() {
    }

    @Test
    void calcFollAnswerTest() {
        // given
        var pathPoints = List.of(
                new long[]{2L, 4L},
                new long[]{4L, 5L},
                new long[]{2L, 5L}
        );

        // when
        var expected = 106L;
        var actual = Solution.calcFollAnswer(graph, pathPoints);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void getDistanceTest() {
        // when
        long expected = 3L;
        long actual = Solution.getDistance(head, tail);

        // then
        assertEquals(expected, actual);
    }
}