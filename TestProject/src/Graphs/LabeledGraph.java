package Graphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;

public class LabeledGraph<E, T> {
	
	HashMap<E, Vertex> vertices;
	
	public LabeledGraph() {
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}
	
	public void connect(E info1, E info2, T label) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge(label, v1, v2);
		
		v1.edges.add(e);
		v2.edges.add(e);
	}

	
	
	private class Vertex {
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info) {
			this.info = info;
			edges = new HashSet<Edge>();
		}
		
		@SuppressWarnings("unused")
		public boolean equals(Vertex v) {
			return info.equals(v.info);
		}
	}
	
	public class Edge {
		T label;
		Vertex v1, v2;
		
		public Edge(T label, Vertex v1, Vertex v2)
		{
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public Vertex getNeighbor(Vertex v)
		{
			if (v.info.equals(v1.info))
			{
				return v2;
			}
			return v1;
		}
	}
	
	public ArrayList<Object> BFS(E start, E end) {

		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
				
		while (!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0);
			
			for (Edge e : curr.edges) {
												
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;

				leadsTo.put(neighbor, e);
				
				if (neighbor.info.equals(end)) {
					return backtrace(neighbor, leadsTo);
				}
				
				else {
					toVisit.add(neighbor);
					visited.add(neighbor);
				}
			}
		}
		return null;
	}
	
	public HashMap<Object, Integer> connectivity (E start) {
		
		HashMap<Object, Integer> distance = new HashMap<Object, Integer>();
		distance.put(vertices.get(start), 0);

		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
				
		while (!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0);
			
			for (Edge e : curr.edges) {
												
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;

				leadsTo.put(neighbor, e);
				distance.put(neighbor, distance.get(curr)+1);

				toVisit.add(neighbor);
				visited.add(neighbor);
			}
			
		}
		
		return distance;
		
	}
	
	private ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
				
		Vertex curr = target;
		ArrayList<Object> path = new ArrayList<Object>();
		
		while (leadsTo.get(curr) != null) {
			path.add(0, leadsTo.get(curr).label);
			path.add(0, curr.info);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		return path;
		
	}
	
	ArrayList<String> findMutuals(String actor1, String actor2) {
		
		HashSet<String> neighbors = new HashSet<String>();
		ArrayList<String> mutuals = new ArrayList<String>();
		
		Vertex curr = vertices.get(actor1);
		
		for (Edge e : curr.edges) {	
			Vertex neighbor = e.getNeighbor(curr);
			neighbors.add((String) neighbor.info);	
		}
		
		curr = vertices.get(actor2);
				
		for(Edge e : curr.edges) {
			if(neighbors.contains(e.getNeighbor(curr).info)) {
				mutuals.add((String)e.getNeighbor(curr).info);
			}
		}
		
		return mutuals;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		new LabeledGraph<String, String>();
		
	}
}