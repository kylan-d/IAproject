import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
public class Graphe {
	private int[][] poids;
	private int nbville;
	private ArrayList<String> ville;
	private int eqinf=0;
	public Graphe() {
		Scanner sc = new Scanner(System.in);
		System.out.println("-tapez 1 pour un graphe que vous générez \n-tapez 2 pour un graphe générer aléatoirement \n-tapez autre chose pour quitter");
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
		
	//	LinkedList<State> a=A_star();
	//	int tot=0;
	//	for(int i=0;i<nbville-1;i++) {
		//	System.out.println(a.get(i).ville);
		//	tot=tot+poids[a.get(i).ville][a.get(i+1).ville];
		//}
		//System.out.println(a.get(nbville-1).ville);
		//System.out.println(tot+poids[a.get(nbville-1).ville][a.get(0).ville]);
	}
	
	private void GrapheRandom() {
		Scanner sc = new Scanner(System.in);
		System.out.println("combien de ville voulez vous générer?");
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
		
		//LinkedList<State> a=A_star();
		//int tot=0;
		//for(int i=0;i<nbville-1;i++) {
		//	System.out.println(a.get(i).ville);
		//	tot=tot+poids[a.get(i).ville][a.get(i+1).ville];
		//}
	//	System.out.println(a.get(nbville-1).ville);
		//System.out.println(tot+poids[a.get(nbville-1).ville][a.get(0).ville]);
	}
	
	public LinkedList<State> A_star(){
		equivalentinfini();
		State racine=new State(0,0,0);
		LinkedList<State>  explored=new LinkedList<State>();
		LinkedList<State> frontier=new LinkedList<State>();
		ArrayList<Integer> unexplored=new ArrayList<Integer>();
		for(int i=0;i<nbville;i++) {
			unexplored.add(i);
		}
		//frontier.add(racine);
			
			for(int i=1;i<unexplored.size();i++) {
				int [] MST=prim(unexplored,unexplored.get(i));
				frontier.add(new State(unexplored.get(i),racine.cout+poids[racine.ville][unexplored.get(i)],heuristique(MST,unexplored.get(i),unexplored)));
			}
			explored.add(racine);
			unexplored.remove(0);
			while(frontier.size()!=0) {
				
				int a=choice(frontier);
				State racine2=frontier.get(a);
				System.out.println("choix"+racine2.ville);
				frontier.remove(a);
				for(int i=0;i<unexplored.size();i++) {
					if(racine2.ville==unexplored.get(i)) {
						unexplored.remove(i);
						break;
					}
				}
				for(int i=0;i<unexplored.size();i++) {
					int [] MST2=prim(unexplored,unexplored.get(i));
					State t=new State(unexplored.get(i),racine2.cout+poids[racine2.ville][unexplored.get(i)],heuristique(MST2,unexplored.get(i),unexplored));
					int pred=t.cout+t.heuristique;
					System.out.println("ville "+t.ville+" cout "+t.cout+" heur "+t.heuristique);
					for(int j=0;j<frontier.size();j++) {
						if(frontier.get(j).ville==t.ville) {
							if(t.cout<(frontier.get(j).cout)) {
								frontier.remove(j);
								frontier.add(t);
								break;
							}
						}
					}
					
				}
				explored.add(racine2);

			}
		//int [] MST=prim();
		//for(int i=0;i<MST.length;i++) {
		//	System.out.println(i+"-"+MST[i]);
		//}
			return explored;
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
		for(int i=0;i<nbville;i++) {
			System.out.println(pred[i]);
		}
		
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
				if(poids[i][noeudAct]<h2) {
					nb=i;
					h2=poids[i][noeudAct];
					
				}
			}
		}
		//if(nb!=0) {h2=h2/nb;}
		//System.out.println(h2);
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
}
