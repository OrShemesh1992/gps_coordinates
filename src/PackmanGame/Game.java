package PackmanGame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import PackmanGame.Fruit;
import PackmanGame.Packman;

public class Game {
	ArrayList<Fruit> Fruits = new ArrayList<Fruit>();
	ArrayList<Packman> Packmans = new ArrayList<Packman>();
	
	public Game(ArrayList<Fruit> Fruits,ArrayList<Packman> Packmans) {
		this.Fruits = Fruits;
		this.Packmans=Packmans;
	}
	/**
	 * A defult constructor
	 */
	public Game() {
		Fruits = new ArrayList<Fruit>();
		Packmans = new ArrayList<Packman>();
	}
	
	/**
	 * A constructor that gets a line from csv file and initializing the variables.
	 * @param LineCsv
	 */
	public Game (String filePath) {
		BufferedReader reader = null;
		try {
			String line = "";
			reader = new BufferedReader(new FileReader(filePath));
			reader.readLine();
			while((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				if(fields[0].equals("F")) {
					Fruit f = new Fruit(line);
					Fruits.add(f);
				}
				else {
					Packman p = new Packman(line);
					Packmans.add(p);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				reader.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * This function creat a csv file from values that its gets from the game we creat.
	 * @param filePath
	 * @param Creat
	 */
	public static void writeCsv(String filePath,Game Creat) {
		ArrayList<Packman> Packmans = Creat.Packmans;
		ArrayList<Fruit> Fruits = Creat.Fruits;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath);
			fileWriter.append("Type, id, Lat,Lon,Alt,Speed/Weight,Radius,"+String.valueOf(Packmans.size())+","+String.valueOf(Fruits.size()));
			fileWriter.append("\n");
			for(Packman p: Packmans) {

				fileWriter.append(p.type);
				fileWriter.append(",");
				fileWriter.append(String.valueOf(p.getID()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(p.locationP.y()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(p.locationP.x()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(p.locationP.z()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(p.speed));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(p.radius));
				fileWriter.append('\n');
			}
			for(Fruit f: Fruits) {
				fileWriter.append(f.type);
				fileWriter.append(",");
				fileWriter.append(String.valueOf(f.getID()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(f.locationF.y()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(f.locationF.x()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(f.locationF.z()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(f.weight));
				fileWriter.append('\n');
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public String toString() {
		String s = "";
		Iterator<Packman> ItP = Packmans.iterator();
		Iterator<Fruit> ItF = Fruits.iterator();
		while(ItP.hasNext()) {
			s += ItP.next().toString()+"\n";
		}
		while(ItF.hasNext()) {
			s += ItF.next().toString()+"\n";
		}
		return s;
	}	
}
