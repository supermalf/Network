package com.visiansystems;

import org.junit.Assert;
import org.junit.Test;

public class NetworkTest {

    @Test(expected = IllegalArgumentException.class)
    public void negativeNVerticesWhenCreatingNetworkMustThrowException() throws Exception {
        new Network(-20);
    }

    @Test
    public void validNVerticesWhenCreatingNetworkMustNotThrowException() throws Exception {
        new Network(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeVertexWhenConnectingMustThrowException() throws Exception {
        Network network = new Network(10);
        network.connect(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidVertexWhenConnectingMustThrowException() throws Exception {
        Network network = new Network(10);
        network.connect(0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectSameNodeMustThrowException() throws Exception {
        Network network = new Network(10);
        network.connect(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeVertexWhenQueryingMustThrowException() throws Exception {
        Network network = new Network(10);
        network.query(-4, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidVertexWhenQueryingMustThrowException() throws Exception {
        Network network = new Network(10);
        network.connect(20, 1);
    }

    @Test
    public void afterCreatingNetworkVerticesMustNotBeConnected() throws Exception {
        int nVertices = 40;
        Network network = new Network(nVertices);

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                if (i == j) {
                    continue;
                }
                Assert.assertFalse(network.query(i, j));
            }
        }
    }

    @Test
    public void afterConnectingTwoVerticesTheyMustBeLinked() throws Exception {
        int nVertices = 40;
        Network network = new Network(nVertices);

        Assert.assertFalse(network.query(10, 20));
        network.connect(10, 20);
        Assert.assertTrue(network.query(10, 20));

        Assert.assertFalse(network.query(4, 8));
        network.connect(4, 8);
        Assert.assertTrue(network.query(4, 8));
    }

    @Test
    public void connectedNodesMustBeLinkedInBothDirections() throws Exception {
        int nVertices = 9;
        Network network = new Network(nVertices);
        network.connect(1, 2);
        network.connect(1, 3);

        Assert.assertTrue(network.query(1, 2));
        Assert.assertTrue(network.query(2, 1));

        Assert.assertTrue(network.query(1, 3));
        Assert.assertTrue(network.query(3, 1));
    }

    @Test
    public void verticesIndirectLinkedMustBeConnected() throws Exception {
        int nVertices = 9;
        Network network = new Network(nVertices);

        network.connect(1, 2);
        network.connect(1, 6);
        network.connect(2, 4);
        network.connect(2, 6);
        network.connect(5, 8);
        network.connect(4, 7);

        Assert.assertTrue(network.query(1, 2));
        Assert.assertTrue(network.query(1, 4));
        Assert.assertTrue(network.query(1, 6));
        Assert.assertTrue(network.query(2, 4));
        Assert.assertTrue(network.query(5, 8));
        Assert.assertTrue(network.query(1, 7));
    }

    @Test
    public void cyclicNetworkMustHaveAllNodesLinked() throws Exception {
        int nVertices = 4;
        Network network = new Network(nVertices);

        network.connect(0, 1);
        network.connect(1, 2);
        network.connect(2, 3);
        network.connect(3, 0);

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                if (i == j) {
                    continue;
                }
                Assert.assertTrue(network.query(i, j));
            }
        }
    }

    @Test
    public void verticesInLineIndirectLinkedMustBeConnected() throws Exception {
        int nVertices = 9;
        Network network = new Network(nVertices);

        network.connect(0, 1);
        network.connect(1, 2);
        network.connect(2, 3);
        network.connect(3, 4);
        network.connect(4, 5);
        network.connect(5, 6);

        Assert.assertTrue(network.query(0, 1));
        Assert.assertTrue(network.query(0, 2));
        Assert.assertTrue(network.query(0, 3));
        Assert.assertTrue(network.query(0, 4));
        Assert.assertTrue(network.query(0, 5));
        Assert.assertTrue(network.query(0, 6));
    }
}
