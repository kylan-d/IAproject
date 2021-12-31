import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	public static void main (String[] args) throws InterruptedException {
	Graphe G=new Graphe();
	LinkedList<State> a=null;
	Scanner sc = new Scanner(System.in);
	System.out.println("Vous voulez choisir quelle algorithme? /nTapez 1 pour A*(Attentions! Cele prends trop de temps ¨¤ excuter pour plus de 15 villes); Tapez 2 pour Simulated Annealing");
	int choix=sc.nextInt();
	if(choix==1) {
		System.out.println("*****A* Algo******");
		a=G.A_star();
		G.afficherA(G.nbville,a);
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Vous voulez continuer ¨¤ faire l'algo Simulated Annealing? Si oui tapez 1");
		int choix1=sc1.nextInt();
		if(choix1==1)
		{
			System.out.println("*****Simulated Annealing******");
			double b = G.simulateAnnealing(100,20,0.5);
			System.out.println("Poids totoal apr¨¨s am¨¦lioration par Simulated Annealing : "+b);
		}
	}
	if(choix==2) {
		System.out.println("*****Simulated Annealing******");
		double b = G.simulateAnnealing(100,20,0.5);
		System.out.println("Poids totoal apr¨¨s am¨¦lioration par Simulated Annealing : "+b);
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Vous voulez continuer ¨¤ faire l'algo A*(Attentions! Cele prends trop de temps ¨¤ excuter pour plus de 15 villes)? Si oui tapez 1 ");
		int choix2=sc2.nextInt();
		if(choix2==1)
		{
			System.out.println("*****A* Algo******");
			a=G.A_star();
			/**int tot=0;
			String parcours="";
			for(int i=0;i<G.nbville-1;i++) {
				parcours=parcours+"-"+G.ville.get(a.get(i).ville);
				tot=tot+G.poids[a.get(i).ville][a.get(i+1).ville];
			}
			parcours=parcours+"-"+G.ville.get(a.get(G.nbville-1).ville);
			System.out.println(parcours+"-"+G.ville.get(0));
			tot=tot+G.poids[a.get(G.nbville-1).ville][a.get(0).ville];
			System.out.println("poids total du circuit minimal: "+tot);**/
			G.afficherA(G.nbville,a);
		}
		}
	
}
}