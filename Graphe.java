import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
public class Graphe {
	public int[][] poids;
	public int nbville;
	public ArrayList<String> ville;
	LinkedList<State> frontier=new LinkedList<State>();
	private int eqinf=0;
	public Graphe() {
		Scanner sc = new Scanner(System.in);
		System.out.println("-tapez 1 pour un graphe que vous g¨¦n¨¦rez \n-tapez 2 pour un graphe g¨¦n¨¦rer al¨¦atoirement \n-tapez autre chose pour quitter");
		int choix=sc.nextInt();
		if(choix==1) {
			this.Graphemain();
		}
		if(choix==2) {
			this.GrapheRandom();
		}
	}
	private void  Graphemain(){
		Scanner sc = new Scanner(System.in);
		System.out.println("combien de ville ?");
		nbville =sc.nextInt();
		ville=new ArrayList<>();
		for(int i=0;i<nbville+1;i++) {
			String nomVille=sc.nextLine();
			if(i!=nbville) {
			System.out.println("Quel est le nom de la ville "+i+" ?");
		}
			ville.add(nomVille);	
		}
		ville.remove(0);
		System.out.println(ville);
		poids=new int[nbville][nbville];
		for(int i=0;i<nbville;i++) {
			for(int j=nbville-1;j>i;j--) {
				System.out.println("Quel est le poids/distance entre "+ville.get(i)+" et "+ville.get(j) +" ?");
				int distance=sc.nextInt();
				poids[i][j]=distance;
				poids[j][i]=distance;
			}
		}
		for(int i=0;i<nbville;i++) {
			poids[i][i]=0;
		}
		

	}
	
	private void GrapheRandom() {
		Scanner sc = new Scanner(System.in);
		System.out.println("combien de ville voulez vous g¨¦n¨¦rer?");
		nbville =sc.nextInt();
		System.out.println("Quel poids/distance maximale voulez vous entre deux villes?");
		int maxpoids =sc.nextInt();
		ville=new ArrayList<>();
		for(int i=0;i<nbville;i++) {
			String nomVille="ville"+i;
			System.out.println(nomVille);
			ville.add(nomVille);	
		}
		poids=new int[nbville][nbville];
		for(int i=0;i<nbville;i++) {
			for(int j=nbville-1;j>i;j--) {
				int distance=1 + (int)(Math.random() * ((maxpoids-1) + 1));
				System.out.println(distance);
				poids[i][j]=distance;
				poids[j][i]=distance;
			}
		}
		for(int i=0;i<nbville;i++) {
			poids[i][i]=0;
		}
		System.out.println("on est la");
	}
	
	public LinkedList<State> A_star(){
		equivalentinfini();
		int min_tour=eqinf;
		LinkedList<State>  explored=new LinkedList<State>();
		
		ArrayList<Integer> unexplored=new ArrayList<Integer>();
		for(int i=0;i<nbville;i++) {
			unexplored.add(i);
		}
		State racine=new State(0,0,0,unexplored,explored);
		//frontier.add(racine);
		racine.unexplored.remove(0);
		racine.explored.add(racine);
			for(int i=0;i<racine.unexplored.size();i++) {
				int [] MST=prim(racine.unexplored,racine.unexplored.get(i));
				frontier.add(new State(racine.unexplored.get(i),racine.cout+poids[racine.ville][racine.unexplored.get(i)],heuristique(MST,racine.unexplored.get(i),racine.unexplored),racine.unexplored,racine.explored));
			}
			int a=choice(frontier);
			State racine2=frontier.get(a);
			while(racine2.unexplored.size()!=0||frontier.size()!=0) {
				//for(int k=0;k<frontier.size();k++) {
				//	System.out.println("ville "+frontier.get(k).ville +" explored "+frontier.get(k).explored.size() +" unexplored "+frontier.get(k).unexplored.size()+" cout+heuristique "+frontier.get(k).cout+" "+frontier.get(k).heuristique);
				//}
				racine2.explored.add(racine2);
				System.out.println("taille frontiere "+frontier.size());
				System.out.println(racine2.explored.size());
				if(frontier.size()==0) {
					break;
				}
				frontier.remove(a);
				for(int i=0;i<racine2.unexplored.size();i++) {
					if(racine2.ville==racine2.unexplored.get(i)) {
						
						racine2.unexplored.remove(i);
						break;
					}
				}
				if(racine2.unexplored.size()==0) {
					if(racine2.cout+racine2.heuristique<min_tour) {
						min_tour=racine2.cout+racine2.heuristique;
						System.out.println("tour minimal: "+min_tour);
					}
					for(int h=0;h<frontier.size();h++) {
						if(racine2.cout+racine2.heuristique>frontier.get(h).cout+frontier.get(h).heuristique) {
							break;
						}
						if(h==frontier.size()-1) {
							System.out.println(racine2.cout+racine2.heuristique);
							return racine2.explored;
						}
					}
				}
				for(int i=0;i<racine2.unexplored.size();i++) {
					int [] MST2=prim(racine2.unexplored,racine2.unexplored.get(i));
					State t=new State(racine2.unexplored.get(i),racine2.cout+poids[racine2.ville][racine2.unexplored.get(i)],heuristique(MST2,racine2.unexplored.get(i),racine2.unexplored),racine2.unexplored,racine2.explored);
					int pred=t.cout+t.heuristique;
					
					if(t.cout+t.heuristique<min_tour) {
						if(add(frontier,t)==1) {
						frontier.add(t);
						}
					}
				}

				
				if(frontier.size()!=0) {
					a=choice(frontier);
					racine2=frontier.get(a);
				}
				
			}
			System.out.println(racine2.cout);
			return racine2.explored;
	}
	
	public int[] prim(ArrayList<Integer> unexplored,int racine){
		
		int [] cout = new int[nbville];
		int [] pred = new int[nbville];
		for(int i=0;i<nbville;i++) {
			pred[i]=-1;
			cout[i]=eqinf;
		}
		cout[racine]=0;
		ArrayList<Integer> F=new ArrayList<Integer>();
		for(int i=0;i<unexplored.size();i++) {
			F.add(unexplored.get(i));
		}
		//F.add(0);
		while(F.size()!=0) {
			int m=minF(F,cout);
			int t=F.get(m);
			F.remove(m);
			for(int i=0;i<F.size();i++) {
				if(cout[F.get(i)]>poids[F.get(i)][t]){
					cout[F.get(i)]=poids[F.get(i)][t];
					pred[F.get(i)]=t;
				}
			}
		}
		//for(int i=0;i<nbville;i++) {
		//	System.out.println(pred[i]);
//		}
		
		return pred;
	}
	
	public int minF(ArrayList<Integer> F,int[] cout) {
		int min=0;
		for(int i=0;i<F.size();i++) {
			if(cout[F.get(i)]<cout[F.get(min)]) {
				min=i;
			}
		}
		
		return min;
		
	}
	
	public void equivalentinfini() {
		for(int i=0;i<nbville;i++) {
			for(int j=0;j<nbville;j++) {
				eqinf=eqinf+poids[i][j]*2;
			}
		}
		
	}
	public int heuristique(int[] mst, int noeudAct,ArrayList<Integer> unexplored) {
		int h=0;
		int h2=poids[0][noeudAct];
		int nb=noeudAct;
		for(int i=0;i<nbville;i++) {
			if(mst[i]!=-1) {
				h=h+poids[i][mst[i]];
				if(poids[i][0]<h2) {
					nb=i;
					h2=poids[i][0];
					
				}
			}
		}
		//if(nb!=0) {h2=h2/nb;}
		//System.out.println("h2 "+h2+" point "+nb);
		return h+h2;//+poids[0][noeudAct];
		
	}
	public int choice(LinkedList<State> frontier) {
		int choix=0;
		for(int i=0;i<frontier.size();i++) {
			if((frontier.get(i).cout+frontier.get(i).heuristique)<(frontier.get(choix).cout+frontier.get(choix).heuristique)) {
				choix=i;
			}
		}
		
		return choix;
	}
	
	public int add(LinkedList<State> frontier,State t) {
		int ret=1;
		for(int i=0;i<frontier.size();i++) {
			if(t.ville==frontier.get(i).ville) {
				int s=0;
				for(int j=0;j<t.unexplored.size();j++) {
					boolean a=frontier.get(i).unexplored.contains(t.unexplored.get((j)));
					if(a==true) {
						s=s+1;
					}
					else {
						break;
					}
				}
				if(s==t.unexplored.size()&&t.explored.size()==frontier.get(i).explored.size()) {
					if(t.cout+t.heuristique>=frontier.get(i).cout) {
						ret=0;
					}

				}
			}
		}
		
		return ret;
	}
	
/***
 * 	Simulated annealing	
 */
	private ArrayList<String> previousTravel = new ArrayList<>();


	public ArrayList<String> swapCities() {
	    int a = generateRandomIndex();
	    int b = generateRandomIndex();
	    previousTravel = ville;
	    ArrayList<String> ville2=(ArrayList<String>) ville.clone();
	    String x = ville2.get(a);
	    String y = ville2.get(b);
	    ville2.set(a, y);
	    ville2.set(b, x);
	    return ville2;
	}

	public void revertSwap() {
	    ville = previousTravel;
	}
	private int generateRandomIndex() {
	    return (int) (Math.random() * ville.size());
	}
	public String getVille(int index) {
	    return ville.get(index);
	}

	public int getDistance(ArrayList<String> ville2) {
		
		int distance = 0;
	    for (int index = 0; index < ville2.size()-1; index++) {
	        distance += poids[ville.indexOf(ville2.get(index))][ville.indexOf(ville2.get(index+1))];
	    }
	    distance += poids[ville.indexOf(ville2.get(ville2.size()-1))][0];
	    return distance;
	}
	public double simulateAnnealing(double startingTemperature, int numberOfIterations, double coolingRate) {
	    System.out.println("Starting SA with temperature: " + startingTemperature + ", # of iterations: " + numberOfIterations + " and colling rate: " + coolingRate);
	    double t = startingTemperature;
	    //ville.generateInitialTravel();
	    double bestDistance = getDistance(ville);
	    System.out.println("Initial distance of travel: " + bestDistance);
	    double bestSolution = bestDistance;
	    double currentSolution = bestSolution;
	    double taux=0;
	    for (int i = 0; i < numberOfIterations; i++) {
	        if (t > 0.1) {
	            ArrayList<String> newVille = swapCities();
	            double currentDistance = getDistance(newVille);
	            System.out.println("Iteration #" + i);
	            System.out.println("Distance actuel : "+ currentDistance);
	            if (currentDistance < bestDistance) {
	            	taux = (bestDistance-currentDistance)/bestDistance;
	                bestDistance = currentDistance;
	            } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
	                revertSwap();
	            }
	            t *= coolingRate;
	            
	            for(int j=0;j<newVille.size();j++)
            	{
            		System.out.print(newVille.get(j)+"->");
            		
            	}
	            System.out.println(newVille.get(0));
	            //System.out.println();
	        } else {
	            continue;
	        }
	     
	    }
	    System.out.println("La taux d¡¯am¨¦lioration = "+(taux*100)+"%");
	    return bestDistance;
	}
}
