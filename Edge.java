
public class Edge {
	//additional field : source vertex, in this case we have a Directed Graph
	private String source_vertex;
	private String target_vertex;
	public String getSource_vertex() {
		return source_vertex;
	}
	public void setSource_vertex(String source_vertex) {
		this.source_vertex = source_vertex;
	}
	public String getTarget_vertex() {
		return target_vertex;
	}
	public void setTarget_vertex(String target_vertex) {
		this.target_vertex = target_vertex;
	}
	private Edge successor;
	
	public Edge getSuccessor() {
		return successor;
	}
	public void setSuccessor(Edge successor) {
		this.successor = successor;
	}
	public Edge(String source,String target) {
		
		this.source_vertex=source;
		this.target_vertex=target;
		this.successor = null;
	}
	public boolean contains(String vertex_id){
		//System.out.println("Source:"+source_vertex+" target:"+target_vertex+" checking for:"+vertex_id);
		if(this.source_vertex.compareTo(vertex_id)==0 || this.target_vertex.compareTo(vertex_id)==0){
			return true;
		}
		return false;		
	}
	

}
