import java.util.HashSet;
import java.util.Set;

public class Vertex implements Comparable {
	
	
	private Edge successor;
	private Edge positional;
	private String v_id;
	
	
	public String getV_id() {
		return v_id;
	}
	public void setV_id(String v_id) {
		this.v_id = v_id;
	}

	public Edge getSuccessor() {
		return successor;
	}
	public void setSuccessor(Edge successor) {
		this.successor = successor;
	}
	public Edge getPositional() {
		return positional;
	}
	public void setPositional(Edge positional) {
		this.positional = positional;
	}
	public Vertex(String id) {
		this.successor = null;
		this.positional = null;
		this.v_id= id;
			
	}
	
	public void add_edge(Edge e){
		if(this.successor==null){
			this.successor=e;
			e.setSuccessor(null);
	}else{
		Edge temp=this.successor;
		this.successor=e;
		e.setSuccessor(temp);
		}
	}
	int getDegree(){
		int count=0;
		Edge e= this.successor;
		while(e!=null){
			count++;
			e=e.getSuccessor();
		}
		return count;
	}
	
	
	public int compareTo(Vertex v) {
		
        return v.getDegree()-this.getDegree();
        		
	}
	@Override
	public int compareTo(Object o) {
		return ((Vertex)o).getDegree()-this.getDegree();
		
	}
	public boolean containsEdge(String target_id){
		Edge e=this.successor;
		while(e!=null){
			if(e.getTarget_vertex().compareTo(target_id)==0)
				return true;
			e=e.getSuccessor();
		}return false;
	}
	public void delEdge(String target_id){
		
		Edge e=this.successor;
		if(e.getTarget_vertex().compareTo(target_id)==0)
		{
			this.setSuccessor(e.getSuccessor());
			return;
		}
		
		while(e.getSuccessor()!=null){
			
			
			if(e.getSuccessor().getTarget_vertex().compareTo(target_id)==0){
				e.setSuccessor(e.getSuccessor().getSuccessor());
				
				return;
			}e=e.getSuccessor();
		}
	}
	
}
