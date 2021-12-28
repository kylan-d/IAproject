import java.util.LinkedList;

public class Main {
	public static void main (String[] args) throws InterruptedException {
	Graphe G=new Graphe();
	System.out.println("la");
	LinkedList<State> a=G.A_star();
	System.out.println("la2");
	int tot=0;
	String parcours="";
	for(int i=0;i<G.nbville-1;i++) {
		parcours=parcours+"-"+G.ville.get(a.get(i).ville);
		tot=tot+G.poids[a.get(i).ville][a.get(i+1).ville];
	}
	parcours=parcours+"-"+G.ville.get(a.get(G.nbville-1).ville);
	System.out.println(parcours+"-"+G.ville.get(0));
	tot=tot+G.poids[a.get(G.nbville-1).ville][a.get(0).ville];
	System.out.println("poids total du circuit minimal: "+tot);
}
}