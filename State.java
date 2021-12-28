import java.util.ArrayList;
import java.util.LinkedList;

public class State {
	public int ville;
	public int cout;
	public int heuristique;
	LinkedList<State>  explored;
	ArrayList<Integer> unexplored;
	public State(int v,int c,int h,ArrayList<Integer> unexplored,LinkedList<State>  explored) {
		ville=v;
		cout=c;
		heuristique=h;
		this.unexplored=(ArrayList<Integer>) unexplored.clone();
		this.explored=(LinkedList<State>) explored.clone();
	}

}
