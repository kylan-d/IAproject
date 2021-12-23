import java.util.ArrayList;
import java.util.Scanner;
public class Graphe {
	private int[][] poids;
	private int nbville;
	private ArrayList<String> ville;
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
	}
}
