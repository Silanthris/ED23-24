package ed.Utils.Graph;


import ed.Utils.Graph.GraphADT;
import ed.Utils.List.UnorderedArrayList;
import ed.Utils.List.UnorderedListADT;
import ed.Utils.Queue.QueueADT;
import ed.Utils.Queue.LinkedQueue;
import ed.Utils.Stack.LinkedStack;
import ed.Utils.Stack.StackADT;
import ed.Utils.Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
    protected final int DEFAULT_CAPACITY = 16;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected double[][] adjWeightMatrix;
    protected T[] vertices;

    public Graph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.adjWeightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandCapacity() {
        T[] largerVertices = (T[]) new Object[this.vertices.length * 2];
        boolean[][] largerAdjMatrix = new boolean[this.vertices.length * 2][this.vertices.length * 2];
        double[][] largerAdjWeightMatrix = new double[this.vertices.length * 2][this.vertices.length * 2];

        for (int i = 0; i < this.numVertices; i++) {
            System.arraycopy(this.adjMatrix[i], 0, largerAdjMatrix[i], 0, this.numVertices);
            largerVertices[i] = this.vertices[i];
            System.arraycopy(this.adjWeightMatrix[i], 0, largerAdjWeightMatrix[i], 0, this.numVertices);
        }

        this.vertices = largerVertices;
        this.adjMatrix = largerAdjMatrix;
        this.adjWeightMatrix = largerAdjWeightMatrix;
    }

    protected int getIndex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    protected boolean indexInvalid(int index) {
        return index < 0 || index >= this.numVertices;
    }

    public void addVertex(T vertex) {
        if (this.numVertices == this.vertices.length) this.expandCapacity();

        this.vertices[this.numVertices] = vertex;

        for (int i = 0; i < this.numVertices; i++) {
            this.adjMatrix[this.numVertices][i] = false;
            this.adjMatrix[i][this.numVertices] = false;
        }

        this.numVertices++;
    }

    public void removeVertex(T vertex) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        int pos = this.getIndex(vertex);
        if (this.indexInvalid(pos)) throw new IllegalArgumentException();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.adjMatrix[pos][i]) this.adjMatrix[pos][i] = false;
            if (this.adjMatrix[i][pos]) this.adjMatrix[i][pos] = false;
            if (this.adjWeightMatrix[pos][i]!=0) this.adjWeightMatrix[pos][i] = 0;
            if (this.adjWeightMatrix[i][pos]!=0) this.adjWeightMatrix[i][pos] = 0;
        }

        for (int i = pos; i < this.numVertices - 1; i++) {
            this.vertices[i] = this.vertices[i + 1];
            for (int j = 0; j < this.numVertices; j++) {
                this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
                this.adjMatrix[j][i] = this.adjMatrix[j][i + 1];
                this.adjWeightMatrix[i][j] = this.adjWeightMatrix[i + 1][j];
                this.adjWeightMatrix[i][j] = this.adjWeightMatrix[i + 1][j];
            }
        }

        this.numVertices--;
    }

    public void addEdge(T vertex1, T vertex2, boolean bidirectional, double density, double weight) {
        int index1 = this.getIndex(vertex1);
        int index2 = this.getIndex(vertex2);
        if (this.indexInvalid(index1) || this.indexInvalid(index2)) throw new IllegalArgumentException();

        if (!this.adjMatrix[index1][index2] || !this.adjMatrix[index2][index1]) {
            this.adjMatrix[index1][index2] = true;
            this.adjWeightMatrix[index1][index2] = weight;
        }

        if(bidirectional){
            double random = Math.random();
            if(random>density) {
                this.adjMatrix[index2][index1] = true;
                this.adjWeightMatrix[index1][index2] = weight;
            }
        }
        else {
            this.adjMatrix[index2][index1] = true;
            this.adjWeightMatrix[index1][index2] = weight;
        }
    }

    public void removeEdge(T vertex1, T vertex2) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        int pos1 = this.getIndex(vertex1);
        int pos2 = this.getIndex(vertex2);
        if (this.indexInvalid(pos1) || this.indexInvalid(pos2)) throw new IllegalArgumentException();
        if (!this.adjMatrix[pos1][pos2] || !this.adjMatrix[pos2][pos2]) throw new IllegalArgumentException();

        this.adjMatrix[pos1][pos2] = false;
        this.adjMatrix[pos2][pos1] = false;
        this.adjWeightMatrix[pos1][pos2] = 0;
        this.adjWeightMatrix[pos2][pos1] = 0;
    }

    public Iterator<T> iteratorBFS(T startVertex) {
        QueueADT<Integer> queue = new LinkedQueue<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        boolean[] visited = new boolean[this.numVertices];

        int startIndex = startVertex == null ? 0 : this.getIndex(startVertex);
        if (this.indexInvalid(startIndex)) return resultList.iterator();

        queue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.dequeue();
            resultList.addToRear(this.vertices[currentVertex]);

            for (int i = 0; i < this.numVertices; i++) {
                if (this.adjMatrix[currentVertex][i] && !visited[i]) {
                    visited[i] = true;
                    queue.enqueue(i);
                }
            }
        }

        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(T startVertex) {
        StackADT<Integer> stack = new LinkedStack<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        boolean[] visited = new boolean[this.numVertices];

        int startIndex = startVertex == null ? 0 : this.getIndex(startVertex);
        if (this.indexInvalid(startIndex)) return resultList.iterator();

        stack.push(startIndex);

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();

            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                resultList.addToRear(this.vertices[currentVertex]);
            }

            for (int i = this.numVertices - 1; i >= 0; i--) {
                if (this.adjMatrix[currentVertex][i] && !visited[i]) {
                    stack.push(i);
                }
            }
        }

        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        int startIndex = startVertex == null ? 0 : getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        if (indexInvalid(startIndex) || indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        double[] distances = new double[numVertices];
        int[] predecessors = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[startIndex] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int currentVertex = getMinDistanceVertex(distances, visited);

            if (currentVertex == -1) {
               currentVertex = 1;
            }

           // System.out.println(currentVertex);
            //System.out.println(numVertices);
            visited[currentVertex] = true;

            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && adjMatrix[currentVertex][j] && distances[currentVertex] != Double.POSITIVE_INFINITY
                        && distances[currentVertex] + adjWeightMatrix[currentVertex][j] < distances[j]) {
                    distances[j] = distances[currentVertex] + adjWeightMatrix[currentVertex][j];
                    predecessors[j] = currentVertex;
                }
            }
        }

        int currentVertex = targetIndex;
        while (currentVertex != startIndex) {
            resultList.addToFront(vertices[currentVertex]);
            currentVertex = predecessors[currentVertex];
        }
        resultList.addToFront(vertices[startIndex]);

        return resultList.iterator();
    }

    public Iterator<T> iteratorLongestPath(T startVertex, T targetVertex) {
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        int startIndex = startVertex == null ? 0 : getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        if (indexInvalid(startIndex) || indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        double[] distances = new double[numVertices];
        int[] predecessors = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.NEGATIVE_INFINITY; // Set initial distances to negative infinity
        }
        distances[startIndex] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int currentVertex = getMaxDistanceVertex(distances, visited); // Change the method to find max distance

            if (currentVertex == -1) {
                currentVertex = 1;
            }

            visited[currentVertex] = true;

            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && adjMatrix[currentVertex][j] && distances[currentVertex] != Double.NEGATIVE_INFINITY
                        && distances[currentVertex] - adjWeightMatrix[currentVertex][j] > distances[j]) {
                    distances[j] = distances[currentVertex] - adjWeightMatrix[currentVertex][j];
                    predecessors[j] = currentVertex;
                }
            }
        }

        int currentVertex = targetIndex;
        while (currentVertex != startIndex) {
            resultList.addToFront(vertices[currentVertex]);
            currentVertex = predecessors[currentVertex];
        }
        resultList.addToFront(vertices[startIndex]);

        return resultList.iterator();
    }


    // Calculates the length of the shortest path from startVertex to targetVertex based on edge weights
    public double shortestPathLength(T startVertex, T targetVertex) {
        int startIndex = startVertex == null ? 0 : getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        if (indexInvalid(startIndex) || indexInvalid(targetIndex) || (startIndex == targetIndex))
            return -1;

        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[startIndex] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int currentVertex = getMinDistanceVertex(distances, visited);
            visited[currentVertex] = true;

            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && adjMatrix[currentVertex][j] && distances[currentVertex] != Double.POSITIVE_INFINITY
                        && distances[currentVertex] + adjWeightMatrix[currentVertex][j] < distances[j]) {
                    distances[j] = distances[currentVertex] + adjWeightMatrix[currentVertex][j];
                }
            }
        }

        return distances[targetIndex] == Double.POSITIVE_INFINITY ? -1 : distances[targetIndex];
    }


    private int getMinDistanceVertex(double[] distances, boolean[] visited) {
        double minDistance = Double.POSITIVE_INFINITY;
        int minDistanceVertex = -1;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minDistanceVertex = i;
            }
        }

        return minDistanceVertex;
    }

    private int getMaxDistanceVertex(double[] distances, boolean[] visited) {
        double maxDistance = Double.NEGATIVE_INFINITY;
        int maxDistanceVertex = -1;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && distances[i] > maxDistance) {
                maxDistance = distances[i];
                maxDistanceVertex = i;
            }
        }

        return maxDistanceVertex;
    }

    public Iterator<T> getAdjacentVertices(T vertex) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        int pos = this.getIndex(vertex);
        if (this.indexInvalid(pos)) return resultList.iterator();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.adjMatrix[pos][i]) {
                resultList.addToRear(this.vertices[i]);
            }
        }

        return resultList.iterator();
    }

    public Iterator<T> getVertices() {
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        for (int i = 0; i < this.numVertices; i++) {
            resultList.addToRear(this.vertices[i]);
        }

        return resultList.iterator();
    }

    public boolean isConnected() {
        if (this.isEmpty()) return false;

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (this.adjMatrix[i][j] != this.adjMatrix[j][i]) return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        return this.numVertices == 0;
    }

    public int size() {
        return this.numVertices;
    }

    public String toString() {
        if (this.numVertices == 0) return "Graph is empty";

        StringBuilder result = new StringBuilder();

        result.append("Adjacency Matrix\n");
        result.append("----------------\n");
        result.append("index\t");

        for (int i = 0; i < this.numVertices; i++) {
            result.append(i);
            if (i < 10)
                result.append(" ");
        }
        result.append("\n\n");

        for (int i = 0; i < this.numVertices; i++) {
            result.append(i).append("\t");

            for (int j = 0; j < this.numVertices; j++) {
                if (this.adjMatrix[i][j])
                    result.append("1 ");
                else
                    result.append("0 ");
            }
            result.append("\n");
        }

        result.append("\n\nVertex Values");
        result.append("\n-------------\n");
        result.append("index\tvalue\n\n");

        for (int i = 0; i < this.numVertices; i++) {
            result.append(i).append("\t");
            result.append(this.vertices[i].toString()).append("\n");
        }
        result.append("\n");

        return result.toString();
    }
}
