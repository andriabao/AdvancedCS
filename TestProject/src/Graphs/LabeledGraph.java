package Graphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LabeledGraph<E, T> {
	
	HashMap<E, Vertex> vertices; //map stores all vertices and information
	
	//constructor
	public LabeledGraph() {
		vertices = new HashMap<E, Vertex>();
	}
	
	//adding new vertex to map
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}
	
	//connects to vertices with information T
	public void connect(E info1, E info2, T label) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge(label, v1, v2); //creates an edge with stored information
		
		v1.edges.add(e); //adds new edge to vertices
		v2.edges.add(e);
	}
	
	//vertex object class
	private class Vertex {
		E info; //vertex information
		HashSet<Edge> edges; //list of edges vertex is connected to
		
		//constructor
		public Vertex(E info) {
			this.info = info;
			edges = new HashSet<Edge>();
		}
		
		//checks if two vertices are the same
		@SuppressWarnings("unused")
		public boolean equals(Vertex v) {
			return info.equals(v.info);
		}
	}
	
	//edge object class
	public class Edge {
		T label; //edge information
		Vertex v1, v2; //vertices it's connected to
		
		//constructor
		public Edge(T label, Vertex v1, Vertex v2) {
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}
		
		//gets the vertex on the opposite side of an edge
		public Vertex getNeighbor(Vertex v) {
			if (v.info.equals(v1.info)) {
				return v2;
			}
			return v1;
		}
	}
	
	//breadth-first search
	public ArrayList<Object> BFS(E start, E end) {
		
		//list of vertices to check
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(start));
		
		//list of vertices already checked
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		//keeps track of which edges vertices lead to
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
				
		while (!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0); //checks first value in toVisit and removes it
			
			for (Edge e : curr.edges) { //cycles through all edges
												
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue; //if neighbor is already visited, move on

				leadsTo.put(neighbor, e);
				
				if (neighbor.info.equals(end)) { //checks if neighbor is the destination
					return backtrace(neighbor, leadsTo); //backtracing
				}
				
				else {
					toVisit.add(neighbor); //updating lists
					visited.add(neighbor);
				}
			}
		}
		return null;
	}
	
	//class for average connectivity of vertex (can also do other things such as find maximum distance, median distance, etc.)
	public HashMap<Object, Integer> connectivity (E start) { //similar to BFS but has no destination and stores distances
		
		//hashmap to contain distances
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
	
	//backtracing
	private ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
				
		Vertex curr = target;
		ArrayList<Object> path = new ArrayList<Object>(); //stores path
		
		//goes back in path until there is nowhere to go back
		while (leadsTo.get(curr) != null) {
			path.add(0, leadsTo.get(curr).label); 
			path.add(0, curr.info);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		return path;
		
	}
	
	//finds mutual actors
	ArrayList<String> findMutuals(String actor1, String actor2) {
		
		HashSet<String> neighbors = new HashSet<String>(); //list of actor1's neighbors
		ArrayList<String> mutuals = new ArrayList<String>(); //list of mutuals
		
		Vertex curr = vertices.get(actor1);
		
		for (Edge e : curr.edges) {	
			Vertex neighbor = e.getNeighbor(curr);
			neighbors.add((String) neighbor.info);	
		}
		
		curr = vertices.get(actor2);
				
		for(Edge e : curr.edges) {
			if(neighbors.contains(e.getNeighbor(curr).info)) { 
				mutuals.add((String)e.getNeighbor(curr).info); //adds actor to mutuals if the actor is also a neighbor of actor1
			}
		}
		
		return mutuals;
		
	}
	
	//main
	public static void main(String[] args) throws FileNotFoundException {
		new LabeledGraph<String, String>();
	}
}