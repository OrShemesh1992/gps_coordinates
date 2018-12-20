package PackmanGame;

import java.util.ArrayList;
import java.util.Iterator;

public class Path {

	ArrayList<Fruit> Fruits;
	ArrayList<String> time;
	Packman pack;

	/**
	 * A fefult constractor.
	 */
	public Path() {
		this.Fruits = new ArrayList<Fruit>();
		this.time=new ArrayList<String>();
		this.pack = new Packman();
	}
	
	/**
	 * A constractor that gets only one element and initializing the variable.
	 * @param p
	 */
	public Path(Packman p) {
		this.Fruits = new ArrayList<Fruit>();
		this.pack = p;
		this.time = new ArrayList<String>();
	}
	
	/**
	 * A constractor that gets 2 element and initializing the variables.
	 * @param f
	 * @param p
	 */
	public Path(ArrayList<Fruit> f, Packman p) {
		this.pack = p;
		this.Fruits = f;
		this.time = new ArrayList<String>();
	}
	
	/**
	 * A copy constructor.
	 * @param other
	 */
	public Path(Path other) {
		this.pack = other.pack;
		Iterator<Fruit> ItFruit = other.Fruits.iterator();
		while(ItFruit.hasNext()) {
			Fruits.add(ItFruit.next());
		}
	}
	
	public String toString() {
		String s = pack.toString()+" \n";
		Iterator<Fruit> ItF = Fruits.iterator();
		while(ItF.hasNext()) {
			s += ItF.next().toString()+" \n";
		}
		return s;
	}
}
