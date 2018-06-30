import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Graph {
	int num_V;
	ArrayList<Vertex> Head;
	public int getNum_V() {
		return num_V;
	}
	public void setNum_V(int num_V) {
		this.num_V = num_V;
	}
	public ArrayList<Vertex> getHead() {
		return Head;
	}
	public Graph(int num_V) {
		
		this.num_V = num_V;
		Head = new ArrayList<>();
	}
	
	public Graph(String Filename) throws IOException{
			FileInputStream fis = new FileInputStream(new File(Filename));
			 
			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			if((line=br.readLine()) !=null){
				StringTokenizer st = new StringTokenizer(line);
				num_V = Integer.parseInt(st.nextToken());
				int num_edges = Integer.parseInt(st.nextToken());
				Head = new ArrayList<>();
				for(int i =0; i<num_V;i++){
					Vertex v= new Vertex(String.valueOf(i));
					Head.add(v);
				}
			
			}
			
			int i=0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				while(st.hasMoreTokens()){
					int vertexid=Integer.parseInt(st.nextToken());
					Edge e= new Edge(String.valueOf(i),String.valueOf(vertexid));
					Head.get(i).add_edge(e);
					
				}
				i++;			
			}
		 
			br.close();
			
		}
	public int containsVertex(String v_id){
		for(int i=0; i<getHead().size();i++){
			if(getHead().get(i).getV_id().compareTo(v_id)==0)
				return i;
		}return -1;
	}
	//primary operations
	
	//προσθήκη κορυφής
	public void addVertex(String vertex_id){
		if(containsVertex(vertex_id)== -1){
			Head.add(new Vertex(vertex_id));
			num_V++;
		}
	}
	
	//προσθήκη ακμής
	public void addEdge(String source, String target){
		int source_index=containsVertex(source);
		int target_index=containsVertex(target);
		if(source_index!=-1 && target_index!= -1){
			if(!Head.get(source_index).containsEdge(target)){
				Edge e = new Edge(source,target);
				Head.get(source_index).add_edge(e);
			}
			if(!Head.get(target_index).containsEdge(source)){
				Edge e = new Edge(target,source);
				Head.get(target_index).add_edge(e);
			}
		}
		
		
		
	}
	//διαγραφή ακμής
	public void deleteEdge(String source, String target){
		int source_index=containsVertex(source);
		int target_index=containsVertex(target);
		if(source_index!=-1){
			Head.get(source_index).delEdge(target);
		}
		if(target_index!=-1){
			Head.get(target_index).delEdge(source);
		}
		
	}
	
	//διαγραφή κορυφής
	public void deleteVertex(String vertex_id){
		int vertex_index=containsVertex(vertex_id);
		if(vertex_index!= -1){
			Head.remove(vertex_index);
			for(int i =0; i<Head.size(); i++){
				Head.get(i).delEdge(vertex_id);	
				num_V--;
			}
		}
	}
	
}
