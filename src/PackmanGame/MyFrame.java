package PackmanGame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Geom.Point3D;

public class MyFrame extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int isGamer = 0;
	private int Radius = 1;
	private int speed = 1;
	private int id = 1;
	Map map = new Map();
	ArrayList<Fruit> Fruits = new ArrayList<Fruit>();
	ArrayList<Packman> Packmans = new ArrayList<Packman>();

	ShortestPathAlgo algo=new ShortestPathAlgo();
	ArrayList<Packman> PackLine=new ArrayList<Packman>(); 

	public MyFrame() {
		initGUI();		
		this.addMouseListener(this); 
	}

	private void initGUI() {
		creatMenu();
	}

	/**
	 * here we creat a menu for the game.
	 */
	public void creatMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Game Menu"); 
		Menu menu1 = new Menu("Import and Export");  
		MenuItem CsvImport = new MenuItem("Import to csv");
		MenuItem CsvExport = new MenuItem("Export to csv");
		MenuItem Kml = new MenuItem("Export to kml");
		MenuItem Pack = new MenuItem("Packman");
		MenuItem Fruit = new MenuItem("Fruit");
		MenuItem Start = new MenuItem("Start");
		MenuItem Clear = new MenuItem("Clear");
		MenuItem RadiusMenu = new MenuItem("Radius");
		MenuItem Speed = new MenuItem("Speed");
		menuBar.add(menu);
		menu.add(Pack);
		menu.add(Fruit);
		menu.add(RadiusMenu);
		menu.add(Speed);
		menu.add(Clear);
		menu.add(Start);
		menuBar.add(menu1);
		menu1.add(CsvImport);
		menu1.add(CsvExport);
		menu1.add(Kml);

		/**
		 * when we press the packman button, a image of packman will appears on the map.
		 */
		Pack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isGamer = 0;
			}
		});

		/**
		 * when we press the fruit button, a image of fruit will appears on the map.
		 */
		Fruit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isGamer = 1;
			}
		});

		/**
		 * when we press the radius button, a window appears where we write the radius we want.
		 */
		RadiusMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String R = JOptionPane.showInputDialog("Enter your Radius: ");
				Radius = Integer.parseInt(R);
			}
		});

		/**
		 * when we press the speed button, a window appears where we write the speed we want.
		 */
		Speed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String S = JOptionPane.showInputDialog("Enter your Speed: ");
				speed = Integer.parseInt(S);
			}
		});

		/**
		 * when we press the clear button, we clear all ths fruit and packman from the game.
		 */
		Clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Fruits.clear();
				Packmans.clear();
				id = 0;
				repaint();
			}
		});

		/**
		 * when we press the strat button, will get the path for each packman.
		 */
		Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!algo.paths.isEmpty()) {
					algo.paths.clear();
					PackLine.clear();
				}
				threadPackman();
				isGamer=3;
				repaint();
			}
		});

		/**
		 * when we press the csvImport button we creat a new csv file and add to ths file all of the data from ths game.
		 */
		CsvImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select an Csv File");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				fileChooser.addChoosableFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(null);
				Game game = null;
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					game = new Game(fileChooser.getSelectedFile().getPath());	
				}
				try {
					Fruits = game.Fruits;
					Packmans = game.Packmans;
					Iterator<Fruit> ItF = Fruits.iterator();
					Iterator<Packman> ItP = Packmans.iterator();
					while(ItF.hasNext()) {
						Fruit F = ItF.next();
						F.locationF = map.toPixel(F.locationF);
						F.locationF = new Point3D(F.locationF.x()/getWidth(),F.locationF.y()/getHeight());
					}
					while(ItP.hasNext()) {
						Packman P = ItP.next();
						P.locationP = map.toPixel(P.locationP);
						P.locationP = new Point3D(P.locationP.x()/getWidth(),P.locationP.y()/getHeight());
					}
				}catch(NullPointerException e) {}
				isGamer=2;
				repaint();
			}
		});


		/**
		 * when we press the csvExport button, a window appears where we could choose the csv file we want to use in the game.
		 */
		CsvExport.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select a csv file");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				fileChooser.addChoosableFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					Game game = new Game(Fruits,Packmans);
					Iterator<Fruit> ItF = game.Fruits.iterator();
					Iterator<Packman> ItP = game.Packmans.iterator();
					while(ItF.hasNext()) {
						Fruit F = ItF.next();
						double x_fru = F.locationF.x()*getWidth();
						double y_fru = F.locationF.y()*getHeight();
						F.locationF = new Point3D(map.toGps(new Point3D((int)x_fru,(int)y_fru)));
					}
					while(ItP.hasNext()) {
						Packman P = ItP.next();
						double x_pac = P.locationP.x()*getWidth();
						double y_pac = P.locationP.y()*getHeight();
						P.locationP=new Point3D(map.toGps(new Point3D((int)x_pac,(int)y_pac)));
					}
					game.writeCsv(fileChooser.getSelectedFile().getPath(), game);
					Fruits.clear();
					Packmans.clear();
					id=0;
				}
			}
		});

		/**
		 * When we press the Export to kml button, we create an kml file which with we could use to see each packman path in google earth.
		 */
		Kml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select a Kml File");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("kml","KML");
				fileChooser.addChoosableFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					Game game = new Game(Fruits,Packmans);
					Iterator<Fruit> ItF = game.Fruits.iterator();
					Iterator<Packman> ItP = game.Packmans.iterator();
					while(ItF.hasNext()) {
						Fruit F = ItF.next();
						double x_fru = F.locationF.x()*getWidth();
						double y_fru = F.locationF.y()*getHeight();
						F.locationF = new Point3D(map.toGps(new Point3D((int)x_fru,(int)y_fru)));
					}
					while(ItP.hasNext()){
						Packman P = ItP.next();
						double x_pac = P.locationP.x()*getWidth();
						double y_pac = P.locationP.y()*getHeight();
						P.locationP = new Point3D(map.toGps(new Point3D((int)x_pac,(int)y_pac)));
					}
					ShortestPathAlgo Algo = new ShortestPathAlgo(game);
					Path2KML kmlPath = new Path2KML();
					kmlPath.writeFileKML(Algo, fileChooser.getSelectedFile().getPath());
					Fruits.clear();
					Packmans.clear();
					id = 0;
				}
			}
		});
		this.setMenuBar(menuBar);
	}

	/**
	 * This function create a different thread for each pacman path, so that in the game we can display all the paths of the Pacman together.
	 * We run all the paths in parallel, and each time we update the location of the Pacman to the location of the fruit to which it will advance.
	 */

	public void threadPackman() {
		ShortestPathAlgo Algo = new ShortestPathAlgo(new Game(Fruits,Packmans));
		Iterator<Path> ItPath = Algo.paths.iterator();

		while(ItPath.hasNext()) {
			Path path=ItPath.next();
			algo.paths.add(path);
			PackLine.add(new Packman(path.pack));
		}

		ItPath = Algo.paths.iterator();
		while(ItPath.hasNext()) {
			Path path = ItPath.next();
			Packman p = path.pack;
			Thread thread = new Thread(){
				@Override
				public void run() {
					Iterator<Fruit> ItF = path.Fruits.iterator();
					while(ItF.hasNext()) {
						Fruit F = ItF.next();
						double dis = map.disToGUI(F.locationF, p.locationP);
						double time = (dis-p.radius)/p.speed;
						for (double i=0; i < time; i=i+1/time+0.01) {
							Point3D vec = new Point3D(F.locationF.x()-p.locationP.x(),F.locationF.y()-p.locationP.y());
							double x = p.locationP.x()+(vec.x()/time);
							double y = p.locationP.y()+(vec.y()/time);
							p.setLocationF(x, y, 0);
							repaint();
							try {
								sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						Fruits.remove(F);
					}
				}
			};
			thread.start();
			repaint();
		}
	} 

	/**
	 * here we draw the game and setting the size of the fruit and packman image and for the size and color for the lines 
	 * that represent the path that each packman does.
	 * 
	 */
	public void paint(Graphics g) {	
		Map temp =new Map();
		g.drawImage(temp.myImage, 0, 0, getWidth(), getHeight(), this);

		Iterator<Packman> ItP = Packmans.iterator();
		Iterator<Fruit> ItF = Fruits.iterator();

		Iterator<Path> ItPath=algo.paths.iterator();


		if(isGamer == 3) {				
			Graphics2D g2 = (Graphics2D)g;
			g.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(5));
			int j=0;
			while(ItPath.hasNext()) {
				Path path = ItPath.next();
				double x_pack=PackLine.get(j).locationP.x()*getWidth();
				double y_pack=PackLine.get(j).locationP.y()*getHeight();
				for(int i = 0; i < path.Fruits.size(); i++) {

					double x1=path.Fruits.get(i).locationF.x()*getWidth();
					double y1=path.Fruits.get(i).locationF.y()*getHeight();
					if(i==0) {
						g.drawLine((int)x_pack, (int)y_pack,(int)x1, (int)y1);
					}
					else {
						double x=path.Fruits.get(i-1).locationF.x()*getWidth();
						double y=path.Fruits.get(i-1).locationF.y()*getHeight();
						g.drawLine((int)x, (int)y,(int)x1, (int)y1);
					}
				}
				j++;
			}
		}
		if(isGamer == 1||isGamer == 0||isGamer == 2||isGamer==3) {
			try {
				while(ItF.hasNext()) {
					Fruit fru = ItF.next();
					double x_fru = fru.locationF.x()*getWidth();
					double y_fru = fru.locationF.y()*getHeight();
					g.drawImage(map.Fruit,(int)(x_fru), (int)(y_fru), 20, 20, null);	
				}
				while(ItP.hasNext()) {
					Packman pac = (Packman) ItP.next();
					double x_pac = pac.locationP.x()*getWidth();
					double y_pac = pac.locationP.y()*getHeight();
					g.drawImage(map.Packman, (int)(x_pac), (int)(y_pac), 25, 25, null);
				}
			}
			catch(ConcurrentModificationException e) {}
		}
	}


	@Override
	public void mouseClicked(MouseEvent arg) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	/**
	 * when we creat new packman or fruit each mouse click we get the location of the variable and print the image of the variable.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		double x_temp = arg0.getX();
		double y_temp = arg0.getY();
		x_temp = x_temp/getWidth();
		y_temp = y_temp/getHeight();
		if(isGamer == 0) {
			Packman pac = new Packman(new Point3D(x_temp,y_temp,0),speed,id,Radius,"P");
			Packmans.add(pac);	
			id++;
		}
		if(isGamer == 1) {
			Fruit fru = new Fruit(speed,id,new Point3D(x_temp,y_temp,0),"F");
			Fruits.add(fru);	
			id++;
		}
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	public static void main(String[] args){
		MyFrame window = new MyFrame();
		Map map = new Map();
		window.setVisible(true);
		window.setSize(map.myImage.getWidth(),map.myImage.getHeight());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}