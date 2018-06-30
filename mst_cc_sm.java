
package algorithms;

import integrationproject.algorithms.Algorithms;
import integrationproject.model.BlackAnt;
import integrationproject.model.Edge;
import integrationproject.model.RedAnt;
import integrationproject.utils.InputHandler;
import integrationproject.utils.Visualize;
import java.util.ArrayList;


/**
 * I have completed three methods named : findMST, findStableMarriage and coinChange. Details about each one are presented in their own javadocs.
 * @author Alkmini Michaloglou
 * @aem 2376
 * @email amichalo@csd.auth.gr
 */
public class IP_2376 extends Algorithms {

    
    
    
    public static void main(String[] args) {
        checkParameters(args);
        
        //create Lists of Red and Black Ants
        int flag = Integer.parseInt(args[1]);
        ArrayList<RedAnt> redAnts = new ArrayList<>();
        ArrayList<BlackAnt> blackAnts = new ArrayList<>();
        if (flag == 0) {
            InputHandler.createRandomInput(args[0], Integer.parseInt(args[2]));
        }
        InputHandler.readInput(args[0], redAnts, blackAnts);

        
        IP_2376 algs = new IP_2376();
        
        //debugging options
        boolean visualizeMST = false;
        boolean visualizeSM = false;
        boolean printCC = false;
        boolean evaluateResults = true;

        if(visualizeMST){
            int[][] mst = algs.findMST(redAnts, blackAnts);
            if (mst != null) {
                Visualize sd = new Visualize(redAnts, blackAnts, mst, null, "Minimum Spanning Tree");
                sd.drawInitialPoints();
            }
        }

        if(visualizeSM){
            int[][] matchings = algs.findStableMarriage(redAnts, blackAnts);
            if (matchings != null) {
                Visualize sd = new Visualize(redAnts, blackAnts, null, matchings, "Stable Marriage");
                sd.drawInitialPoints();
            }
        }

        if(printCC){
            int[] coinChange = algs.coinChange(redAnts.get(0), blackAnts.get(0)); 
            System.out.println("Capacity: " + redAnts.get(0).getCapacity());
            for(int i = 0; i < blackAnts.get(0).getObjects().length; i++){
                System.out.println(blackAnts.get(0).getObjects()[i] + ": " + coinChange[i]);
            }
        }
        
        if(evaluateResults){
            System.out.println("\nEvaluation Results");
            algs.evaluateAll(redAnts, blackAnts);
        }
    }
    
    

  /**
   * The findMST procedure finds the minimum spanning tree using the kruskal algorithm (along with the union find "method").The arraylist named "pointers" contains
   * ints (1,2...) that represent to which group each ant belongs. The tempArray contains all possible edges between the ants. In order to distinguish the black ants from
   * the red ones, i just add to their ID the number of the red ants. I use this type of IDs when i create the edges,(variables "From" and "To").
   * The Union-Find method begins and happens with the kruskalArray. Each time an edge is inserted in the kruskalArray, i check whether it forms a circle using the pointers array. If two edges have the same
   * value in the pointers array it means they belong to the same set, forming a circle. Also i merge sets when it is needed giving them a new "name" (the counter variable).
   * When the kruskalArray is finished , it contains the MST but i do convert it in the proper form in order to do the evaluation. Array2D is the final result. 
   * @param redAnts
   * @param blackAnts
   * @return One ArrayList which is in the right format in order to evaluate.
   */
    @Override
    public int[][] findMST(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        
        
       ArrayList<Edge> tempArray = new ArrayList<>();
        
        int []pointers = new int[redAnts.size() * 2];
        
        for(int i=0; i<redAnts.size(); i++) { 
           
            pointers[i] = 0; //initial state of this array: each cell contains "0".
            pointers[i + redAnts.size()] = 0;
            
            for(int j=0; j<redAnts.size(); j++) {
                
                
                if(!compare(tempArray, redAnts.get(i).getID(), blackAnts.get(j).getID()+redAnts.size()))
                    tempArray.add(new Edge(redAnts.get(i).getID(), blackAnts.get(j).getID()+redAnts.size(), redAnts.get(i).getDistanceFrom(blackAnts.get(j))));
                
                if(i != j) {
                    if(!compare(tempArray, redAnts.get(i).getID(), redAnts.get(j).getID()))
                        tempArray.add(new Edge(redAnts.get(i).getID(), redAnts.get(j).getID(), redAnts.get(i).getDistanceFrom(redAnts.get(j))));
                    if(!compare(tempArray, blackAnts.get(i).getID()+redAnts.size(), blackAnts.get(j).getID()+redAnts.size()))
                        tempArray.add(new Edge(blackAnts.get(i).getID()+redAnts.size(), blackAnts.get(j).getID()+redAnts.size(), blackAnts.get(i).getDistanceFrom(blackAnts.get(j))));
                }
                               
            }
                    
        }               
        for(int i=0; i<tempArray.size()-1; i++) //Bubblesort for the edges. I get them in ascending order. 
            for(int j=0; j<tempArray.size()-1; j++)
                if(tempArray.get(j).getDistance() > tempArray.get(j+1).getDistance()) {
                    Edge temp = tempArray.get(j+1);
                    tempArray.set(j+1, tempArray.get(j));
                    tempArray.set(j, temp);
                }
        
        int counter = 1; //first set of edges. The counter represents the name of each set.
        //UNION-FIND
        ArrayList<Edge> kruskalArray = new ArrayList<>(); 
        for(int i=0; i<tempArray.size() && kruskalArray.size() < redAnts.size()*2-1; i++) {
            if( (pointers[tempArray.get(i).getFrom()] == 0) && (pointers[tempArray.get(i).getTo()] == 0) ) {
                pointers[tempArray.get(i).getFrom()] = counter;
                pointers[tempArray.get(i).getTo()] = counter;
                kruskalArray.add(tempArray.get(i));
                counter++;
            }
            else if( (pointers[tempArray.get(i).getFrom()] == 0) && (pointers[tempArray.get(i).getTo()] != 0) ) {
                pointers[tempArray.get(i).getFrom()] = pointers[tempArray.get(i).getTo()];
                kruskalArray.add(tempArray.get(i));
            }
            else if( (pointers[tempArray.get(i).getTo()] == 0) && (pointers[tempArray.get(i).getFrom()] != 0) ) {
                pointers[tempArray.get(i).getTo()] = pointers[tempArray.get(i).getFrom()];
                kruskalArray.add(tempArray.get(i));
            }
            else if( (pointers[tempArray.get(i).getFrom()] !=0) && (pointers[tempArray.get(i).getTo()] != 0) && (pointers[tempArray.get(i).getFrom()] != pointers[tempArray.get(i).getTo()]) ) {
                int temp1 = pointers[tempArray.get(i).getFrom()];
                int temp2 = pointers[tempArray.get(i).getTo()];
                for(int j=0; j<pointers.length; j++)
                    if(pointers[j] == temp1 || pointers[j]==temp2)
                        pointers[j] = counter;
                kruskalArray.add(tempArray.get(i));
                counter++;
            }
        }
                
        int [][]Array2D = new int[kruskalArray.size()][4]; //it's the needed format in order for the evaluation to work. First and third columns:ant ID. Second and fourth : color.
                      
        for(int i=0; i<kruskalArray.size(); i++) {         //each line represents an edge.
             
            if(kruskalArray.get(i).getTo() >= redAnts.size()) {
                Array2D[i][2] = kruskalArray.get(i).getTo() - redAnts.size();
                Array2D[i][3] = 1;
            }
            
            else if(kruskalArray.get(i).getTo() < redAnts.size()) {
                Array2D[i][2] = kruskalArray.get(i).getTo();
                Array2D[i][3] = 0;
            }
            if(kruskalArray.get(i).getFrom() >= redAnts.size()) {
                Array2D[i][0] = kruskalArray.get(i).getFrom() - redAnts.size();
                Array2D[i][1] = 1;
            }
            
            else if(kruskalArray.get(i).getFrom() < redAnts.size()) {
                Array2D[i][0] = kruskalArray.get(i).getFrom();
                Array2D[i][1] = 0;
            }
                  
        }
        return Array2D;
    }
    /**
     * Checks whether a specific edge has been used before. Thats because the edge AB is exactly the same as BA.
     * @param array - The ArrayList
     * @param start - Ant ID (beginning of an edge)
     * @param finish - Ant ID (ending of an edge)
     * @return - boolean, true if the edge has already been used. (It's in the ArrayList).
     */
    public boolean compare(ArrayList<Edge> array, int start, int finish) {
        boolean result = false;
        for(int i=0; i<array.size() && result==false; i++)
            if((array.get(i).getFrom() == finish) && (array.get(i).getTo() == start))
                result = true;
        return result;
    }
    
    /**
     * In this method i find the stable couples for the ants. First i find the distances between the different colored ants and with this info i create the priority list 
     * for everyone. I begin with the red ant with the first ID (0) and begin proposing to black ants. There are various loops in the code which we enter depending 
     * on the state of the black ant we have just proposed to. The variable "done" represents whether the whole stable marriage algorithm has finished. 
     * The algorithm goes in circles until each and every ant is enganged to someone (properly).
     * @param redAnts
     * @param blackAnts
     * @return an Array of ints that contains the pair of ants after the stable marriage procedure.
     */
       
    @Override
    public int[][] findStableMarriage(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        
        int i, j;
        double [][]redDistances = new double [redAnts.size()][blackAnts.size()]; //distance list for red ants.
        double [][]blackDistances = new double [blackAnts.size()][redAnts.size()]; // distance list for black ants.
        
        for(i = 0; i < redAnts.size(); i++) {
            for(j = 0; j < blackAnts.size(); j++) {
                redDistances[i][j] = redAnts.get(i).getDistanceFrom( blackAnts.get(j) );
                blackDistances[i][j] = blackAnts.get(i).getDistanceFrom( redAnts.get(j) );
            }
        }
        
        int [][]redPreferences = new int[redAnts.size()][blackAnts.size()]; //priority list for red ants
        int [][]blackPreferences = new int[redAnts.size()][redAnts.size()]; //priority list for black ants
        double []tempArray = new double[redAnts.size()];
        int k, l;
        double temporal;
        for(i = 0; i < blackAnts.size(); i++) {
            for(j = 0; j < blackAnts.size(); j++)
                tempArray[ j ] = blackDistances[i][j];
            for(k = 0; k < tempArray.length; k++) //BubbleSort
                for(l = 0; l < tempArray.length - 1; l++)
                    if(tempArray[l] > tempArray[l+1]) {
                        temporal = tempArray[l+1];
                        tempArray[l+1] = tempArray[l];
                        tempArray[l] = temporal;
                    }
            for(j = 0; j < redAnts.size(); j++)  // Search for the ID (column of the 2D array) of the red ant .
                for(k = 0; k < redAnts.size(); k++)
                    if(tempArray[j] == blackDistances[i][k])
                        blackPreferences[i][j] = k;
        }
        for(i = 0; i < redAnts.size(); i++) {
            for(j = 0; j < redAnts.size(); j++)
                tempArray[j] = redDistances[i][j];
            for(k = 0; k < tempArray.length; k++)
                for(l = 0; l < tempArray.length - 1; l++)
                    if(tempArray[ l ] > tempArray[l + 1]) {
                        temporal = tempArray[ l + 1 ];
                        tempArray[l+1] = tempArray[ l ];
                        tempArray[l] = temporal;
                    }
            for(j = 0; j < redAnts.size(); j++)          // Search for the ID (column of the 2D array) of the black ant .
                for(k = 0; k < redAnts.size(); k++)
                    if(tempArray[j] == redDistances[i][k]) 
                        redPreferences[i][j] = k;
        }
        
        boolean done, proposals, engagedPref;
        done = false;
        int []partner = new int[redAnts.size()];
        int ant, marriedAnt, wanted;
        ant = 0;
        for(i = 0; i < partner.length; i++)
            partner[i] = -999;
        do {
             proposals = false;
            int antPreference = 0;
            if(partner[ant] == -999) {
                do {
                    proposals = false;
                    engagedPref = false;
                    for(i = 0; i < partner.length; i++) {
                        if(redPreferences[ant][antPreference] == partner[i]) {
                            engagedPref = true;
                        }
                    }
                    
                    if(engagedPref == true) {
                        marriedAnt = -999;
                        for(i = 0; i<partner.length; i++) {
                            if(partner[i] == redPreferences[ant][antPreference]) {
                                marriedAnt = i; 
                            }
                        }
                        wanted = -999;
                        for(i = 0; i<redAnts.size(); i++) {
                            if(wanted == -999) {
                                if(blackPreferences[redPreferences[ant][antPreference]][i] == marriedAnt)
                                    wanted = marriedAnt;
                                else if(blackPreferences[redPreferences[ant][antPreference]][i] == ant)
                                    wanted = ant; // wanted ends up with the ID of the FIRST ant it will encounter (of the two i m searching for)
                                
                            }
                        }
                        if(wanted == ant) {
                            partner[ant] = redPreferences[ant][antPreference];
                            partner[marriedAnt] = -999;
                            proposals = true;
                            ant++;
                            if(ant == redAnts.size()) {
                                ant = 0;
                            }
                        }
                        else if(wanted == marriedAnt) {
                            antPreference++;
                        }
                    }
                    else if(engagedPref == false) {
                        partner[ant] = redPreferences[ant][antPreference];
                        proposals = true;
                        ant++;
                        if(ant == redAnts.size()) {
                            ant = 0;
                        }
                    }
                } while(proposals == false);
            }
            else if(partner[ant] != 999) {
                ant++;
                if(ant == redAnts.size()) {
                    ant = 0;
                }
            }
            
            done = true;
            for(i = 0; i < partner.length; i++) {
                if(partner[i] == -999) {
                    done = false;
                }
            }
        } while(done == false);
        
        int [][]finalArray = new int[redAnts.size()][2]; //First column: red ants, second column : black ants. Each cell contains an ID. Every duo of cells represents a couple.
        for(i = 0; i<finalArray.length; i++) {
            finalArray[i][0] = i;
            finalArray[i][1] = partner[i];
        }
        return finalArray;
    }
    /**
     * coinChange is a rather simpler procedure when it comes to coding. The most important aspects of this procedure are the two Arrays (int type),
     * named C, and S respectively.There is a  pair of red and black ant.Each red ant wants to fully use its capacity using its black pair's seeds.
     * The array named C contains the amount of seeds each red ant carries, while the S array can be used to also help us find out which type
     * of seeds the red ant ended up having. I use the S array later on, alongside with the coins array and the seeds array in order to get proper results.
     * Backtracking in the S array allows us to count how many seeds of each type of available ones the red ant chose. The final result for one pair of ants is
     * represented by the seeds array which is the procedures output.
     * @param redAnt
     * @param blackAnt
     * @return an array (type int) that contains the amount of each type of seed the ant should carry in the right format in order to do the evaluation .
     */
    
    @Override
    public int[] coinChange(RedAnt redAnt, BlackAnt blackAnt) {
        int []coins = blackAnt.getObjects();  // the coins array contains the data of the seeds the black ant carries.
        int []C = new int[1 + redAnt.getCapacity()];
        int []S = new int[redAnt.getCapacity()+1];
        C[0] = 0;
        S[0] = -999;
        int p, i, min, newSeed;
        for(p = 1; p <= redAnt.getCapacity(); p++) {
            min = redAnt.getCapacity() * 999;
            newSeed = -999;
            for(i = 0; i < coins.length; i++) {
                if(coins[i] <= p) {
                    if( 1 + C[ p-coins[i] ] < min) {
                        min = 1 + C[ p-coins[i] ];
                        newSeed = coins[i];
                    }
                }
            }
            S[p] = newSeed;
            C[p] = min;
        }
        int []seeds;  //the array i want to return as an output.
        int seedPointer, temp;
        seeds = new int[5];
        seedPointer = redAnt.getCapacity(); 
        for(i = 0; i < 5; i++) {
            seeds[i] = 0; //initial state
            
        }
        do {                                         //I use the S array in order to backtrack and keep counters of the seeds of each black ant. I store these counters in the seeds array.
            temp = -1;
            for(i = 0; i < coins.length; i++) {
                if(coins[i] == S[seedPointer]) {
                    temp = i;
                }
            }
            seedPointer = seedPointer - S[seedPointer];
            seeds[temp]++;
        } while(seedPointer > 0);
        return seeds;
    }
    
    private static void checkParameters(String[] args) {
        if (args.length == 0 || args.length < 2 || (args[1].equals("0") && args.length < 3)) {
            if (args.length > 0 && args[1].equals("0") && args.length < 3) {
                System.out.println("3rd argument is mandatory. Represents the population of the Ants");
            }
            System.out.println("Usage:");
            System.out.println("1st argument: name of filename");
            System.out.println("2nd argument: 0 create random file, 1 input file is given as input");
            System.out.println("3rd argument: number of ants to create (optional if 1 is given in the 2nd argument)");
            System.exit(-1);
        }
    }
    
}
