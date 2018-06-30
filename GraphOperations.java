import java.util.StringTokenizer;

//Υλοποίηση όλων των δευτερευουσων πραξεων πανω σε γράφους.
public class GraphOperations {

	// ένωση δύο γράφων
	public static Graph union(Graph graph1, Graph graph2) {
		Graph graphUnited = new Graph(0);
		for (int i = 0; i < graph1.getHead().size(); i++) {
			graphUnited.addVertex(graph1.getHead().get(i).getV_id());
		}
		for (int i = 0; i < graph2.getHead().size(); i++) {
			graphUnited.addVertex(graph2.getHead().get(i).getV_id());
		}

		for (int i = 0; i < graph2.getHead().size(); i++) {
			Edge e = graph2.getHead().get(i).getSuccessor();
			while (e != null) {
				graphUnited.addEdge(graph2.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}
		for (int i = 0; i < graph1.getHead().size(); i++) {
			Edge e = graph1.getHead().get(i).getSuccessor();
			while (e != null) {
				graphUnited.addEdge(graph1.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}

		return graphUnited;
	}

	// τομή δύο γράφων
	public static Graph intersection(Graph graph1, Graph graph2) {
		Graph graph = new Graph(0);
		for (int i = 0; i < graph1.getHead().size(); i++) {
			// καλω την contains για να δω αν περιλαμβανεται στον γραφο 2, η
			// κορυφη του γραφου 1
			if (graph2.containsVertex(graph1.getHead().get(i).getV_id()) != -1) {
				graph.addVertex(graph1.getHead().get(i).getV_id());

			}
		}
		for (int i = 0; i < graph1.getHead().size(); i++) {
			if (graph2.containsVertex(graph1.getHead().get(i).getV_id()) != -1) {

				Edge e = graph1.getHead().get(i).getSuccessor();
				int vIndex = graph2.containsVertex(graph1.getHead().get(i).getV_id());
				if (vIndex != -1) {
					while (e != null) {
						if (graph2.getHead().get(vIndex).containsEdge(e.getTarget_vertex())
								&& graph1.getHead().get(i).containsEdge(e.getTarget_vertex()))
							graph.addEdge(graph1.getHead().get(i).getV_id(), e.getTarget_vertex());
						e = e.getSuccessor();
					}

				}
			}

		}

		return graph;
	}

	// άθροισμα δακτυλίου δύο γράφων
	public static Graph ringSum(Graph graph1, Graph graph2) {
		Graph graph = new Graph(0);
		// τοποθέτηση όλων των κορυφών στο γράφο
		for (int i = 0; i < graph1.getHead().size(); i++) {
			graph.addVertex(graph1.getHead().get(i).getV_id());
		}
		for (int i = 0; i < graph2.getHead().size(); i++) {
			graph.addVertex(graph2.getHead().get(i).getV_id());
		}
		// τοποθέτηση ακμών στο γράφο, προσθέτω ακμὲς που ανήκουν μόνο σε έναν
		// από τους δύο αρχικούς γράφους
		for (int i = 0; i < graph2.getHead().size(); i++) {
			Edge e = graph2.getHead().get(i).getSuccessor();
			while (e != null) {
				// ελέγχω πρώτα για την ύπαρξη κορυφής(index), και για καθε ακμή
				// αμα υπάρχει στον γραφο 1 με την containsEdge στην κορυφή που
				// βρήκα
				// αν δεν υπαρχει ακμή στο γραφο 1, οσο διασχιζω τον γραφο 2,
				// την προσθετω στον τελικο
				int index = graph1.containsVertex(graph2.getHead().get(i).getV_id());
				if (index == -1 || !graph1.getHead().get(index).containsEdge(e.getTarget_vertex()))
					graph.addEdge(graph2.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}
		for (int i = 0; i < graph1.getHead().size(); i++) {
			Edge e = graph1.getHead().get(i).getSuccessor();
			while (e != null) {
				// ελέγχω πρώτα για την ύπαρξη κορυφής(index), και για καθε ακμή
				// αμα υπάρχει στον γραφο 2 με την containsEdge στην κορυφή που
				// βρήκα
				// αν δεν υπαρχει ακμή στο γραφο 2, οσο διασχιζω τον γραφο 1,
				// την προσθετω στον τελικο
				int index = graph2.containsVertex(graph1.getHead().get(i).getV_id());
				if (index == -1 || !graph2.getHead().get(index).containsEdge(e.getTarget_vertex()))
					graph.addEdge(graph1.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}

		return graph;

	}

	// σύνδεση γράφων
	public static Graph join(Graph graph1, Graph graph2) {
		Graph graph = new Graph(0);
		// τοποθετω τις κορυφές του γράφου 1, και ονομάζω τις κορυφες του 2 οταν
		// τις τοποθετω με id + μεγεθος του πρωτου, για να χω μοναδικα id
		for (int i = 0; i < graph1.getHead().size(); i++) {
			graph.addVertex(graph1.getHead().get(i).getV_id());
		}
		for (int i = 0; i < graph1.getHead().size(); i++) {
			Edge e = graph1.getHead().get(i).getSuccessor();
			while (e != null) {
				graph.addEdge(graph1.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}
		for (int i = 0; i < graph2.getHead().size(); i++) {

			graph.addVertex(
					String.valueOf((Integer.parseInt(graph2.getHead().get(i).getV_id()) + graph1.getHead().size())));
		}
		for (int i = 0; i < graph2.getHead().size(); i++) {
			Edge e = graph2.getHead().get(i).getSuccessor();
			while (e != null) {
				graph.addEdge(
						String.valueOf((Integer.parseInt(graph2.getHead().get(i).getV_id()) + graph1.getHead().size())),
						String.valueOf(Integer.parseInt(e.getTarget_vertex()) + graph1.getHead().size()));
				e = e.getSuccessor();
			}
		}
		// τοποθετώ όλες τις ακμές ανάμεσα σε κορυφές των δύο γράφων
		for (int i = 0; i < graph1.getHead().size(); i++) {
			for (int j = graph1.getHead().size(); j < graph.getHead().size(); j++) {
				graph.addEdge(graph.getHead().get(i).getV_id(), graph.getHead().get(j).getV_id());
				graph.addEdge(graph.getHead().get(j).getV_id(), graph.getHead().get(i).getV_id());
			}

		}

		return graph;
	}

	// καρτεσιανό γινόμενο
	public static Graph cartesianProduct(Graph graph1, Graph graph2) {
		Graph graph = new Graph(0);

		for (int i = 0; i < graph1.getHead().size(); i++) {
			for (int j = 0; j < graph2.getHead().size(); j++) {
				graph.addVertex(graph1.getHead().get(i).getV_id() + "," + graph2.getHead().get(j).getV_id());

			}
		}

		for (int i = 0; i < graph.getHead().size(); i++) {
			// σπαω την κορυφη σε αριστερο δεξι μερος
			StringTokenizer tok = new StringTokenizer(graph.getHead().get(i).getV_id(), ",");
			String left = tok.nextToken();
			String right = tok.nextToken();
			int v_lindex = graph1.containsVertex(left);
			int v_rindex = graph2.containsVertex(right);

			for (int j = 0; j < graph.getHead().size(); j++) {
				StringTokenizer tok1 = new StringTokenizer(graph.getHead().get(j).getV_id(), ",");
				String templeft = tok1.nextToken();
				String tempright = tok1.nextToken();
				// για καθε αλλη κορυφη στο γραφο, αν εχουν κοινο αριστερο μερος
				// (κοινη κορυφη στον g1) και τα δεξια μερη τους ενωνονται στο
				// g2 (ακμη στον g2),
				// προσθετω την ακμή.
				if (left.compareTo(templeft) == 0) {
					if (graph2.getHead().get(v_rindex).containsEdge(tempright)) {
						graph.addEdge(graph.getHead().get(i).getV_id(), graph.getHead().get(j).getV_id());
					}

				}
				// για καθε αλλη κορυφη στο γραφο, αν εχουν κοινο δεξι μερος
				// (κοινη κορυφη στον g2) και τα αριστερα μερη τους ενωνονται
				// στο g1 (ακμη στον g1),
				// προσθετω την ακμή.
				if (right.compareTo(tempright) == 0) {
					if (graph1.getHead().get(v_lindex).containsEdge(templeft)) {
						graph.addEdge(graph.getHead().get(i).getV_id(), graph.getHead().get(j).getV_id());
					}
				}

			}

		}

		return graph;
	}

	// λεξικογραφικό γινόμενο
	public static Graph lexicographicalProduct(Graph gleft, Graph gright) {
		Graph graph = new Graph(0);

		for (int i = 0; i < gleft.getHead().size(); i++) {
			for (int j = 0; j < gright.getHead().size(); j++) {
				graph.addVertex(gleft.getHead().get(i).getV_id() + "," + gright.getHead().get(j).getV_id());
			}
		}

		for (int i = 0; i < graph.getHead().size(); i++) {
			// σπαω την κορυφη σε αριστερο δεξι μερος
			StringTokenizer tok = new StringTokenizer(graph.getHead().get(i).getV_id(), ",");
			String left = tok.nextToken();
			String right = tok.nextToken();
			int v_lindex = gleft.containsVertex(left);
			int v_rindex = gright.containsVertex(right);

			for (int j = 0; j < graph.getHead().size(); j++) {
				StringTokenizer tok1 = new StringTokenizer(graph.getHead().get(j).getV_id(), ",");
				String templeft = tok1.nextToken();
				String tempright = tok1.nextToken();
				// για καθε αλλη κορυφη στο γραφο, αν εχουν κοινο αριστερο μερος
				// (κοινη κορυφη στον g1) και τα δεξια μερη τους ενωνονται στο
				// g2 (ακμη στον g2),
				// προσθετω την ακμή.
				if (left.compareTo(templeft) == 0) {
					if (gright.getHead().get(v_rindex).containsEdge(tempright)) {
						graph.addEdge(graph.getHead().get(i).getV_id(), graph.getHead().get(j).getV_id());
					}

				}
				// για καθε αλλη κορυφη στο γραφο, αν εχουν κοινο δεξι μερος
				// (κοινη κορυφη στον g2) και τα αριστερα μερη τους ενωνονται
				// στο g1 (ακμη στον g1),
				// προσθετω την ακμή.
				if (gleft.getHead().get(v_lindex).containsEdge(templeft)) {

					graph.addEdge(graph.getHead().get(i).getV_id(), graph.getHead().get(j).getV_id());
				}
			}

		}

		return graph;
	}

	// αμάλγαμα κορυφών, δεχεται ως ορισμα δυο γραφους, και αντιστοιχα τις
	// κορυφες που θελω να ενωσω
	public static Graph amalgamation(Graph graph1, Graph graph2, String v1, String v2) {

		Graph graph = new Graph(0);

		// τοποθετω τις κορυφές του γράφου 1, και ονομάζω τις κορυφες του 2 οταν
		// τις τοποθετω με id + μεγεθος του πρωτου, για να χω μοναδικα id
		for (int i = 0; i < graph1.getHead().size(); i++) {
			graph.addVertex(graph1.getHead().get(i).getV_id());
		}
		// προσθετω ακμες του γραφου 1
		for (int i = 0; i < graph1.getHead().size(); i++) {
			Edge e = graph1.getHead().get(i).getSuccessor();
			while (e != null) {
				graph.addEdge(graph1.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}
		// προσθετω ολες τις κορυφες του 2, εκτος απο αυτην που συμπιπτει με την
		// v1 (δλδ την v2)
		for (int i = 0; i < graph2.getHead().size(); i++) {
			if (graph2.getHead().get(i).getV_id().compareTo(v2) != 0) {
				graph.addVertex(String
						.valueOf((Integer.parseInt(graph2.getHead().get(i).getV_id()) + graph1.getHead().size())));
			}
		}
		// προσθετω ακμες απο το γραφο 2
		for (int i = 0; i < graph2.getHead().size(); i++) {
			if (graph2.getHead().get(i).getV_id().compareTo(v2) == 0) {
				Edge e = graph2.getHead().get(i).getSuccessor();
				while (e != null) {
					graph.addEdge(v1,
							String.valueOf((Integer.parseInt(e.getTarget_vertex()) + graph1.getHead().size())));
					e = e.getSuccessor();
				}

			}
			Edge e = graph2.getHead().get(i).getSuccessor();
			while (e != null) {
				if (e.getTarget_vertex().compareTo(v2) == 0) {
					graph.addEdge(e.getSource_vertex(), v1);
				} else {
					graph.addEdge(String.valueOf((Integer.parseInt(e.getSource_vertex()) + graph1.getHead().size())),
							String.valueOf((Integer.parseInt(e.getTarget_vertex()) + graph1.getHead().size())));
				}
				e = e.getSuccessor();
			}

		}
		return graph;
	}

	// συμπλήρωμα ενός γράφου
	public static Graph complement(Graph graph1) {

		Graph graph = new Graph(0);

		// προσθετω τις κορυφες οπως ειναι
		for (int i = 0; i < graph1.getHead().size(); i++) {
			graph.addVertex(graph1.getHead().get(i).getV_id());
		}
		//αν δεν περιλαμβανεται μια ακμη στον γραφο 1, την προσθετω στο τελικο γραφο (ελεγχοντας να μην βαζω ακμες που ξεκιναν και τελειωνουν στην ιδια κορυφη)
		for (int i = 0; i < graph1.getHead().size(); i++) {

			for (int j = 0; j < graph1.getHead().size(); j++) {
				if (!graph1.getHead().get(i).containsEdge(graph1.getHead().get(j).getV_id()) && 
						graph1.getHead().get(i).getV_id().compareTo(graph1.getHead().get(j).getV_id())!=0) {
					graph.addEdge(graph1.getHead().get(i).getV_id(), graph1.getHead().get(j).getV_id());
				}
			}
		}
		return graph;

	}
	
	//ανταλλαγή ακμών, οι ακμές : (v1,v2) και (v3,v4), τις σβηνω και δημιουργώ τις (v1,v3) και (v2,v4)
	public static Graph edgeInterchange(Graph graph1, String v1, String v2, String v3, String v4){
		
		Graph graph= new Graph(0);
		
		for (int i = 0; i < graph1.getHead().size(); i++) {
			graph.addVertex(graph1.getHead().get(i).getV_id());
		}
		

		for (int i = 0; i < graph1.getHead().size(); i++) {
			Edge e = graph1.getHead().get(i).getSuccessor();
			while (e != null) {
				graph.addEdge(graph1.getHead().get(i).getV_id(), e.getTarget_vertex());
				e = e.getSuccessor();
			}
		}
		
		
		int index1 =graph.containsVertex(v1);
		int index2 =graph.containsVertex(v3);
		if(graph.getHead().get(index1).containsEdge(v2) && graph.getHead().get(index2).containsEdge(v4)){
			graph.deleteEdge(v1, v2);
			graph.deleteEdge(v3, v4);
		}
			graph.addEdge(v1,v3);
			graph.addEdge(v2,v4);
		
				
		return graph;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
