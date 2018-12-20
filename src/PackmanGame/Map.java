package PackmanGame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Coords.MyCoords;
import Geom.Point3D;

public class Map{
	public BufferedImage myImage;
	public BufferedImage Packman;
	public BufferedImage Fruit;
	public Point3D Start = new Point3D(35.202574,32.106046);
	public Point3D End = new Point3D(35.212405,32.101858);
	
	/**
	 * A constructor that creat the map image, fruit image and packman image.
	 */
	public Map() {
		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		try {
			Packman = ImageIO.read(new File("Pack.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		try {
			Fruit = ImageIO.read(new File("Fruit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * This function gets a gps point and convert it to a pixel point by using the height and width of the image.
	 * @param gps
	 * @return
	 */
	public Point3D toPixel(Point3D gps){
		double Width =  End.x()-Start.x();
		double high = Start.y()-End.y();
		double x = gps.x()-Start.x();
		double y = Start.y()-gps.y();
		x = (myImage.getWidth())*(x/Width);
		y = (myImage.getHeight())*(y/high);    
		return new Point3D( (int)x,  (int)y);
	}
	
	/**
	 * This function gets a pixel point and convert it to a gps point.
	 * @param pixel
	 */
	public Point3D toGps(Point3D pixel){
	double GpsX = (pixel.x()/myImage.getWidth())*(End.x()-Start.x())+Start.x();
	double GpsY = -((pixel.y()/myImage.getHeight())*(Start.y()-End.y())-Start.y());
		return new Point3D(GpsX,GpsY,0);
	}
	
	/**
	 * This function calculates the distance between 2 points by using the function we already created at MyCoords class.
	 * @param p1
	 * @param p2
	 */
	public double distancePixels(Point3D p1, Point3D p2) {
		Point3D X = toGps(p1);
		Point3D Y = toGps(p2);
		MyCoords m = new MyCoords();
		return m.distance3d(X, Y);
	}
	
	/**
	 * This function calculates the distance between 2 points, we use this function when in the 
	 * threadPackman function so that we could adjust the fruit location and the pacman location,
	 * to the width and the height of the map image.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public double disToGUI(Point3D p1, Point3D p2) {
		p1= new Point3D(p1.x()*myImage.getWidth(),p1.y()*myImage.getHeight());
		p2= new Point3D(p2.x()*myImage.getWidth(),p2.y()*myImage.getHeight());
		Point3D X = toGps(p1);
		Point3D Y = toGps(p2);
		MyCoords m = new MyCoords();
		return m.distance3d(X, Y);
	}
}
