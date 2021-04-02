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
		
		//creates edge using correct distance formula
		Edge e = new Edge(Math.sqrt(Math.pow(v1.xLoc-v2.xLoc,2) + Math.pow(v1.yLoc-v2.yLoc, 2)), v1, v2); //creates an edge with length
		
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
		
		//isOn method
		public boolean isOn(int mouseX, int mouseY) {
			
			//distance formula for isOn
			if(Math.pow(mouseX-xLoc,2) + Math.pow(mouseY-yLoc, 2) < 100) {
				return true;
			} else {
				return false;
			}
			
		}
		
		public boolean equals(Vertex v) {
			return info.equals(v.info);
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
	
	//priority queue
	public class PriorityQueue<E> {
		
		//stores priority queue
		private ArrayList<Node<E>> queue = new ArrayList<Node<E>>();

		public void put(double priority, E info) {
			
			Node<E> v = new Node<E>(priority, info);
			
			//if no element exists, add element
			if(queue.size() == 0) {
				queue.add(v);
			} else if (queue.contains(v)) { //if element already exists keep the one that has lower priority
				
				int index = queue.indexOf(v);
				
				if(queue.get(index).priority > priority) {
					queue.remove(queue.get(index));
					queue.add(v);
				}
				
			} else { //use binary search to find the location in the queue
				if(queue.get(0).priority > priority) {
					queue.add(0, v);
				} else if(queue.get(queue.size()-1).priority < priority) {
					queue.add(v);
				} else {
					int low = 0;
					int up = queue.size()-1;
					
					while(low < up) {
						int mid = (low+up)/2;
						
						if(mid < priority) {
							low = mid+1;
						} else {
							up = mid;
						}
					}
					
					queue.add(low,v);

				}
			}	
			
		}
		
		//remove first element
		public E pop() {
			return queue.remove(0).info;
		}
		
		//returns size of queue
		public int size() {
			return queue.size();
		}
		
	}
	
	//djikstra algorithm
	public ArrayList<Object> Djikstra(E start, E target) {
		
		//queue of things to visit
		PriorityQueue<Vertex> toVisit = new PriorityQueue<Vertex>();
		toVisit.put(0.0, vertices.get(start)); //put start in toVisit
		
		HashSet<Vertex> visited = new HashSet<Vertex>(); //set to store vertices already visited
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex,Edge>(); //map to store connected vertices
		HashMap<Vertex, Double> distance = new HashMap<Vertex, Double>(); //map to store distances
		
		//assigns each vertex with the maximum value possible
		for(Vertex v : vertices.values()){
			distance.put(v, Double.MAX_VALUE);
		}
		
		//assigning distance 0 to start from start
		distance.put(vertices.get(start), 0.0);
		
		//goes until nothing to visit
		while(toVisit.size() != 0) {
						
			Vertex curr = toVisit.pop();
			
			//if target is found, backtrace to find the path
			if(curr.info == target) {
				return backtrace(curr, leadsTo);
				
			} else {
				
				//cycles through all of current vertices edges
				for(Edge e : curr.edges) {
										
					Vertex neighbor = e.getNeighbor(curr);
										
					if(visited.contains(neighbor)) continue;
					
					//finds the distance from curr to its neighbor
					double currDist = distance.get(curr) + e.length;
					
					//if new distance is less, update distances
					if(currDist < distance.get(neighbor)) {
						toVisit.put(currDist, neighbor);
						leadsTo.put(neighbor, e);	
						distance.put(neighbor, currDist);
					}
					
				}
				
				visited.add(curr);
				
			}
		}
		
		//returning empty list if no path is found
		return new ArrayList<Object>();
		
	}
	
	//backtrace
	public ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
		
		Vertex curr = target;
		ArrayList<Object> path = new ArrayList<Object>();
		
		//goes back until there is nothing to lead to
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
		
	}
}