package PackmanGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import Geom.Point3D;

public class ShortestPathAlgo {
	
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	ArrayList<Path> paths = new ArrayList<Path>();
	
	/**
	 * A defult constructor.
	 */
	public ShortestPathAlgo() {
		paths = new ArrayList<Path>();
	}
	
	/**
	 * This function gets one packman and list of fruits and calculates for the packman for which fruit it arrive in the shortest time.
	 * @param f
	 * @param p
	 * @return array with to fruit that the packman arrive in the shortest time and ths time it take him.
	 */
	public double[] fastest(ArrayList<Fruit> f, Packman p) {
		Map map = new Map();
		double[] fast_index=new double[3];
		Iterator<Fruit> ItF = f.iterator();
		Fruit minF = ItF.next();
		double disF = map.distancePixels(minF.locationF, p.locationP);
		double fast = disF -p.radius/p.speed;
		while(ItF.hasNext()) {
			Fruit minF1 = ItF.next();
			double disF1 = map.distancePixels(minF1.locationF, p.locationP);
			double fast1 = disF1-p.radius/p.speed;
			if(fast1 < fast) {
				fast = fast1;
				minF = minF1;
			}
		}
		fast_index[0]=f.indexOf(minF);
		fast_index[1]=fast;
		return fast_index;
	}
	
	/**
	 * This function gets a game (list of fruit and list of packman). first it copy each packman to array list of paths.
	 * then as long as the list of fruit is not empty its send one packman and ths list of fruit to the function "fastest"
	 * when we gets for each of the packman we have in the game its closest fruit we chooses the packman for him it took 
	 * the shortest time to get to his fruit and add it to the packman path list, updating the location of ths packman 
	 * and removing this fruit from the list.
	 * @param g
	 */
	public ShortestPathAlgo(Game g) {
		Iterator<Packman> ItP = g.Packmans.iterator();
		ArrayList<Point3D> tempPoint = new ArrayList<Point3D>();
		while(ItP.hasNext()) {
			Packman toPaths = ItP.next();
			paths.add(new Path(toPaths));
			tempPoint.add(toPaths.locationP);
		}
		ArrayList<Fruit> temp=new ArrayList<Fruit>(g.Fruits);
		while(!temp.isEmpty()) {
			double i = 0;
			ItP = g.Packmans.iterator();
			ArrayList<double[]> Arr = new ArrayList<double[]>();
			while(ItP.hasNext()) {
				Packman pac = ItP.next();
				double[] indF_fast_indP = fastest(temp,pac);
				indF_fast_indP[2] = i;
				Arr.add(indF_fast_indP);
				i++;
			}	
			Iterator<double[]> ItArr = Arr.iterator();
			double[] fast = new double[3];
			if(ItArr.hasNext()) {
				fast=ItArr.next();
			}
			while(ItArr.hasNext()) {
				double[] fast1 = ItArr.next();
				if(fast[1] > fast1[1])
				{
					fast[0] = fast1[0];
					fast[1] = fast1[1];
					fast[2] = fast1[2];
				}
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			paths.get((int)fast[2]).time.add(sdf.format(timestamp));
			paths.get((int)fast[2]).Fruits.add(temp.get((int)fast[0]));
			g.Packmans.get((int)fast[2]).setLocationF(temp.get((int)fast[0]).locationF.x()
					,temp.get((int)fast[0]).locationF.y(), 0);
			temp.remove((int)fast[0]);
		}	
		Iterator<Point3D> ItPoint = tempPoint.iterator();
		int j = 0;
		while(ItPoint.hasNext()) {
			paths.get(j).pack.locationP = new Point3D(ItPoint.next());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			paths.get(j).time.add(sdf.format(timestamp));
			j++;
		}
	}
	public String toString() {
		String s = "";
		Iterator<Path> ItPaths = paths.iterator();
		while(ItPaths.hasNext()) {
			s += ItPaths.next().toString()+" \n";
		}
		return s;
	}
}
