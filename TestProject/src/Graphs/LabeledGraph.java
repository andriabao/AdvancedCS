package Graphs;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
	
	public ArrayList<Object> BFS(E start, E target) {
		
		if (vertices.get(target) == null) {
			System.out.println("no");
			return null;
		}
		else if (vertices.get(start) == null) {
			System.out.println("also no");
			return null;
		}

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
				
				if (neighbor.info.equals(target)) {
					System.out.println("found");
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
	
	public static void main(String[] args) throws FileNotFoundException {
		
		LabeledGraph<String, String> g = new LabeledGraph<String, String>();
		
		HashMap<String,String> actors = new HashMap<String,String>();
		HashMap<String,String> movies = new HashMap<String,String>();
		
		ArrayList<String> actorsInMovie = new ArrayList<String>();
		
		String lineA, lineM, lineMA;
		BufferedReader ar = new BufferedReader(new FileReader("actors.txt"));
		BufferedReader mr = new BufferedReader(new FileReader("movies.txt"));
		BufferedReader mar = new BufferedReader(new FileReader("movie-actors.txt"));
		
		try {
			while((lineA = ar.readLine()) != null) {
				int index = lineA.indexOf('~');
				actors.put(lineA.substring(0,index), lineA.substring(index+1));
			}
			while((lineM = mr.readLine()) != null) {
				int index = lineM.indexOf('~');
				movies.put(lineM.substring(0,index), lineM.substring(index+1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		actors.forEach((k,v) -> g.addVertex(v));
		
		try {
			lineMA = mar.readLine();
			String movieName = lineMA.substring(0,lineMA.indexOf('~'));
			actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
						
			while((lineMA = mar.readLine()) != null) {
				if(lineMA.substring(0,lineMA.indexOf('~')).equals(movieName)) {
					
					for(int i = 0; i < actorsInMovie.size(); i++) {
						g.connect(actors.get(lineMA.substring(lineMA.indexOf('~')+1)), actors.get(actorsInMovie.get(i)), movies.get(movieName));
					}
					
					actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
				
				} else {
					movieName = lineMA.substring(0,lineMA.indexOf('~'));
					actorsInMovie.clear();
					actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String start = "Sam Worthington";
		ArrayList<Object> path = g.BFS(start, "Patrick Jordan");
		
		for(int i = 0; i < path.size(); i+=2) {
			if(i == 0) {
				System.out.println(start + " and " + path.get(i) + " are in the movie " + path.get(i+1));
			} else {
				System.out.println(path.get(i-2) + " and " + path.get(i) + " are in the movie " + path.get(i+1));
			}
		}
	}
}