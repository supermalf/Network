package com.visiansystems;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Stack;

/**
 * Represents a simple undirected Graph class with unweighted edges.
 * It allows cyclic and disconnected group of vertices.
 */
public class Network {
    private int nNodes;
    private Map<Integer, LinkedHashSet<Integer>> adjacencyMap;

    /**
     * Constructor. Creates a Network object.
     *
     * @param nNodes Number of nodes
     */
    public Network(int nNodes) {
        if (nNodes < 0) {
            throw new IllegalArgumentException("Number of nodes can't be negative");
        }

        this.nNodes = nNodes;
        this.adjacencyMap = new HashMap<>();
    }

    /**
     * Connects two nodes with an unweighted link.
     *
     * @param n1 Node 1
     * @param n2 Node 2
     */
    public void connect(int n1, int n2) {
        validateNode(n1);
        validateNode(n2);

        if (n1 == n2) {
            throw new IllegalArgumentException("Cannot connect the same node.");
        }

        addLink(n1, n2);
        addLink(n2, n1);
    }

    /**
     * Verifies if the two given nodes are connected.
     *
     * @param n1 Node 1
     * @param n2 Node 2
     * @return {true} if the given nodes are connected, or {false} otherwise
     */
    public boolean query(int n1, int n2) {
        validateNode(n1);
        validateNode(n2);

        if (n1 == n2) {
            return true;
        }

        return depthFirstSearch(n1, n2);
    }

    private void validateNode(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Parameter nodes can't be negative");
        }
        if (n >= nNodes) {
            throw new IllegalArgumentException(
                    "Parameter nodes can't be greater than the network total nodes: " + nNodes);
        }
    }

    private void addLink(int n1, int n2) {
        LinkedHashSet<Integer> connectivitySet = adjacencyMap.get(n1);

        if (connectivitySet == null) {
            connectivitySet = new LinkedHashSet<>();
            adjacencyMap.put(n1, connectivitySet);
        }
        connectivitySet.add(n2);
    }

    private boolean depthFirstSearch(int startNode, int findNode) {
        Stack<Integer> stack = new Stack<>();
        stack.add(startNode);

        LinkedHashSet<Integer> visitedNodes = new LinkedHashSet<>();

        while (!stack.isEmpty()) {
            int element = stack.pop();

            if (!adjacencyMap.containsKey(element)) {
                continue;
            }

            for (int node : adjacencyMap.get(element)) {
                if (!visitedNodes.contains(node)) {
                    if (node == findNode) {
                        return true;
                    }
                    stack.add(node);
                    visitedNodes.add(element);
                }
            }
        }

        return false;
    }
}
