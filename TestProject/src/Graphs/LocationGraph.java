package Graphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LocationGraph<E> {
	
	HashMap<E, Vertex> vertices; //map stores all vertices and information
	
	//constructor
	public LocationGraph() {
		vertices = new HashMap<E, Vertex>();
	}
	
	//adding new vertex to map
	public void addVertex(E info, int xLoc, int yLoc) {
		vertices.put(info, new Vertex(info, xLoc, yLoc));
	}
	
	//connects to vertices with length
	public void connect(E info1, E info2) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge(Math.pow(v1.xLoc-v2.xLoc,2) + Math.pow(v2.yLoc-v2.yLoc, 2), v1, v2); //creates an edge with length
		
		v1.edges.add(e); //adds new edge to vertices
		v2.edges.add(e);
	}
	
	//vertex object class
	public class Vertex {
		E info; //vertex information
		int xLoc, yLoc;
		HashSet<Edge> edges; //list of edges vertex is connected to
		
		//constructor
		public Vertex(E info, int xLoc, int yLoc) {
			this.info = info;
			this.xLoc = xLoc;
			this.yLoc = yLoc;
			edges = new HashSet<Edge>();
		}
		
	}
	
	//edge object class
	public class Edge {
		double length; //edge information
		Vertex v1, v2; //vertices it's connected to
		
		//constructor
		public Edge(double d, Vertex v1, Vertex v2) {
			this.length = d;
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
	
	public class PriorityQueue<E> {

		private ArrayList<Node<E>> queue = new ArrayList<Node<E>>();

		public void put(double priority, E info) {
			
			Node<E> v = new Node<E>(priority, info);
			
			
			if(queue.size() == 0) {
				queue.add(v);
			} else if (queue.contains(v)) {
				
				int index = queue.indexOf(v);
				
				if(queue.get(index).priority > priority) {
					queue.remove(queue.get(index));
					queue.add(v);
				}
				
			} else {
				if(queue.get(0).priority > priority) {
					queue.add(0, v);
				} else if(queue.get(queue.size()-1).priority < priority) {
					queue.add(v);
				} else {
					int low = 0;
					int up = queue.size()-1;
					
					while(low < up) {
						int mid = (low+up)/2;
						
						if(queue.get(mid).priority < priority) {
							low = mid+1;
						} else {
							up = mid;
						}
					}
				}
			}	
			
		}
		
		public E pop() {
			return queue.remove(0).info;
		}
		
		public int size() {
			return queue.size();
		}
		
	}
	
	public ArrayList<Object> Djikstra(E start, E target) {
		
		PriorityQueue<Vertex> toVisit = new PriorityQueue<Vertex>();
		toVisit.put(0.0, vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex,Edge>();
		HashMap<Vertex, Double> distance = new HashMap<Vertex, Double>();
		
		for(Vertex v : vertices.values()){
			distance.put(v, Double.MAX_VALUE);
		}
		
		distance.put(vertices.get(start), 0.0);


		while(toVisit.size() != 0) {
			
			Vertex curr = toVisit.pop();
			
			if(curr.info == target) {
				return backtrace(curr, leadsTo);
				
			} else {
								
				for(Edge e : curr.edges) {
					
					Vertex neighbor = e.getNeighbor(curr);
					
					if(visited.contains(neighbor)) continue;
					
					double currDist = distance.get(curr) + e.length;
										
					if(currDist < distance.get(neighbor)) {
						toVisit.put(currDist, neighbor);
						leadsTo.put(neighbor, e);	
						distance.put(neighbor, currDist);
					}
					
				}
				
				visited.add(curr);
				
			}
			
		}
		
		return null;
		
	}
	
	public ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
		
		Vertex curr = target;
		ArrayList<Object> path = new ArrayList<Object>();
		
		while (leadsTo.get(curr) != null) {
			path.add(0, curr.info);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		path.add(0, curr.info);
		
		return path;
		
	}
	
	//main
	public static void main(String[] args) throws FileNotFoundException {
		
		LocationGraph<String> g = new LocationGraph<String>();
		
		g.addVertex("A", 1, 1);
		g.addVertex("B", 3, 8);
		g.addVertex("C", 5, 1);
		g.addVertex("D", 2, -2);
		g.addVertex("E", 3, 0);
		
		g.connect("A", "B");
		g.connect("C", "B");
		g.connect("A", "D");
		g.connect("D", "E");
		g.connect("C", "E");
		
		System.out.println(g.Djikstra("A", "C"));
		
	}
}