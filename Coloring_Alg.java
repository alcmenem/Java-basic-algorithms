import java.util.ArrayList;
import java.util.*;
/*
 * Στην κλάση Coloring_Alg , με ενδιαφέρει η υλοποίηση των δύο ζητούμενων 
 * αλγορίθμων χρωματισμού.
 */
public class Coloring_Alg {
	
	/*Η serialColoring εκτελεί τον σειριακό χρωματισμό γράφου (7.1). Διατρέχω όλο το γραφο μεσα στην while,
	 * και για κάθε κορυφή δημιουργώ μια λίστα χρωμάτων, που περιλαμβάνει τα χρώματα των γειτόνων.
	 *Μελετάω το χρώμα c (αν περιλαμβάνεται στην λίστα το αυξάνω) και το τοποθετώ στη κορυφή.
	 */
	
	static void serialColoring(Graph graph){
		int i=0;
		while(i<graph.getNum_V()){
			//αρχικοποίηση χρώματος
			int c=1;
			//λαμβάνω την λίστα με τα χρώματα των γειτόνων
			ArrayList<Integer> neig_colors=createColorlist(graph.getHead()[i],graph);
		
			while(neig_colors.contains(c)){
				c++;
			}
		graph.getHead()[i].setColor(c);	
		i++;
		}
	}
	//Για μια κορυφη του γράφου, η συνάρτηση δημιουργεί λίστα με τα χρώματα που έχουν οι γείτονες. Αν έχουν.
	static ArrayList<Integer> createColorlist(Vertex v,Graph g){
		ArrayList<Integer> chromaticList = new ArrayList<Integer>();
		Edge e = v.getSuccessor();
		
		while(e!=null){
			
			if(g.getHead()[e.getN_vertex()].getColor()!= -1){
				chromaticList.add(g.getHead()[e.getN_vertex()].getColor());
						
			}
			e=e.getSuccessor();
		}
		//ταξινόμηση της λίστας, πρέπει να επιστραφεί ταξινομημένη για να έχει νόημα ο αλγόριθμος.
		Collections.sort(chromaticList);
		return chromaticList;
		
	}
	/*
	 * Η brelazColoring υλοποιεί τον αλγόριθμο 7.2, χρωματισμό του Brelaz
	 */
	
	static void brelazColoring(Graph graph){
		//δημιουργώ μια λίστα με τις κορυφές του γράφου και την γεμίζω.
		ArrayList<Vertex> Vertices = new ArrayList<Vertex>();
		
		for(int i=0; i<graph.getNum_V();i++){
			Vertices.add(graph.getHead()[i]);
		}
		//ταξινομώ την λίστα κορυφών 
		Collections.sort(Vertices);
		//θέτω το χρώμα της πρώτης κορυφής =1
		Vertices.get(0).setColor(1);
		
		while(allColored(graph)==false){
			Vertex best = findBest(graph);
			
			best.setColor(bestColor(best,graph));
			
			}
			
	}
	/*
	 * στην συνάρτηση findBest, βρίσκω την καλύτερη (σύμφωνα με τον αλγόριθμο) κορυφή για να χρωματισθεί
	 * στο κάθε βήμα. Η καλύτερη είναι αυτή που έχει το μέγιστο βαθμό χρώματος, και τους περισσότερους 
	 * γείτονες χωρίς χρώμα.
	 */
		static Vertex findBest(Graph graph){
			//υπλογίζω το βαθμό χρώματος για κάθε κορυφή
		for(int i=0; i<graph.getNum_V();i++){
			graph.getHead()[i].computeCDegree(graph);
		}
		//βρίσκω το μέγιστο βαθμό χρώματος σε μη χρωματισμένη κορυφή
		int max = -1;
		for(int i=0; i<graph.getNum_V();i++){
			if(graph.getHead()[i].getC_degree()>max & graph.getHead()[i].getColor()==-1 ){
				max=graph.getHead()[i].getC_degree();
			}
		}
		//βρίσκω όλες τις κορυφές που είναι χωρίς χρώμα και έχουν το max βαθμό χρώματος
		ArrayList<Vertex> v_list = new ArrayList<Vertex>();
		for(int i=0; i<graph.getNum_V();i++){
			if(graph.getHead()[i].getC_degree()==max && graph.getHead()[i].getColor()==-1 ){
				v_list.add(graph.getHead()[i]);
			}
		}
		//η v_list έχει κορυφές,θέλω να επιστρέψω την καταλληλότερη σύμφωνα με τον αλγόριθμο
		// θα ναι αυτή με τους περισσότερους γείτονες χωρίς χρώμα.Βρίσκω σε ποια θέση ισχύει αυτό και επιστρέφω την κορυφή.
		if(v_list.size()>1){
			int temp = -1;
			int max_pos=-1;
			for(int i=0;i<v_list.size();i++){
				if(v_list.get(i).getColor()==-1 && v_list.get(i).getDegreeUncolored(graph)>temp){
					max_pos=i;
					temp=v_list.get(i).getDegreeUncolored(graph);
				}
			}
			return v_list.get(max_pos);
			
		}else 
			return v_list.get(0);
	}
	//βρίσκω το καλύτερο χρώμα για μια κορυφη(εδώ εννοούμε το μέγιστο +1 όπου μέγιστο το μεγαλύτερο στους γείτονες)
   	static int bestColor(Vertex vertex,Graph graph){
		int max=0;
		Edge e=vertex.getSuccessor();
		while(e!=null){
			
			if(max<graph.getHead()[e.getN_vertex()].getColor()){
				max=graph.getHead()[e.getN_vertex()].getColor();
			}
			e=e.getSuccessor();
		}
		max++;
		return max;
	}
	//η συνάρτηση all colored ελέγχει αν έχει χρωματιστεί κάθε κορυφή του γράφου
	static boolean allColored(Graph graph){
		for(int i=0;i<graph.getNum_V();i++){
			if(graph.getHead()[i].getColor()==-1)
				return false;
		}
		return true;
	}
}
