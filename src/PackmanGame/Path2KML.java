package PackmanGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Path2KML {
	
	/**
	 * This function gets a ShortestPathAlgo variable that includes a list of fruits, a packman list and a simple date format,
	 * and a string variable which containing the paths that each PacMan does.
	 * Then we create the kml file so we could see the path that each packman does on google earth.
	 * 
	 * @param S_P_A
	 * @param Kml
	 */
	public void writeFileKML(ShortestPathAlgo S_P_A, String Kml) {
		ArrayList<String> content = new ArrayList<String>();
		String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
				"<Document>" + "<Style id=\"s_ylw-pushpin_hl\">"+
				"<IconStyle>"+		"<color>ff0000ff</color>"+
				"<scale>1</scale>"+
				"<Icon>"+
				"<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>"+
				"</Icon>"+
				"</IconStyle>"+
				"</Style>";
		content.add(kmlstart);
		try{
			FileWriter fw = new FileWriter(Kml);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator<Path> It = S_P_A.paths.iterator();
			while (It.hasNext()) {
				Path path = It.next();
				Packman pac = path.pack;
				Iterator<String> ItTime = path.time.iterator();
				String kmlelementp ="<Placemark>\n" +" <TimeStamp>\r\n" + 
						"<when>"+ ItTime.next().replace(" ", "T") +"Z</when>\r\n" + 
						"</TimeStamp>"+
						"<name>"+"Type: "+pac.type+"</name>\n" +
						"<description>"+"ID: "+pac.ID+", radius: "+pac.radius+", Speed: "+pac.speed+
						"</description>\n" +
						"<Point>\n" +
						"<coordinates>"+pac.locationP.x()+","+pac.locationP.y()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n";
				content.add(kmlelementp);
				Iterator<Fruit> ItF = path.Fruits.iterator();
				while(ItF.hasNext()) {
					Fruit fru = ItF.next();
					String kmlelementF ="<Placemark>\n" +" <TimeStamp>\r\n" + 
							"<when>"+ ItTime.next().replace(" ", "T") +"Z</when>\r\n" + 
							"</TimeStamp>"+
							"<name>"+"Type: "+fru.type+"</name>\n" +
							"<description>"+"ID: "+fru.ID+
							"</description>\n" +"<styleUrl>#s_ylw-pushpin_hl</styleUrl>"
							+ "\n" +
							"<Point>\n"+
							"<coordinates>"+fru.locationF.x()+","+fru.locationF.y()+"</coordinates>" +
							"</Point>\n" +
							"</Placemark>\n";
					content.add(kmlelementF);
				}
			}
			It = S_P_A.paths.iterator();
			while(It.hasNext()) {
				Path path = It.next();
				Packman pac = path.pack;
				String lines = "<Placemark> \r\n" + 
						" <LineString>\r\n" + 
						"  <coordinates>\n" +
						pac.locationP.x() + "," + pac.locationP.y() +","+ 0 + "\n";
				Iterator<Fruit> ItF = path.Fruits.iterator();
				while(ItF.hasNext()) {
					Fruit fru = ItF.next();
					lines += fru.locationF.x() + "," + fru.locationF.y() +","+ 0 + "\n";
				}
				lines += "</coordinates>\r\n" + 
						" </LineString>\r\n" + 
						" <Style> \r\n" + 
						"  <LineStyle>  \r\n" + 
						"   <color>ffff0000</color>\r\n" + 
						"<width>6</width>"+
						"  </LineStyle> \r\n" + 
						" </Style>"+
						"</Placemark>\r\n";
				content.add(lines);
			}
			String end = "</Document> </kml>"; 
			content.add(end);
			String kml = content.toString().replace("[", "").replace("]", "");
			bw.write(kml);
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
