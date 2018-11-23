package File_format;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Json_101 {

	   public static void main(String[] args){
		   encode();
		   decode();
	   }
	   public static void encode() {
		  JSONObject obj = new JSONObject();
	      obj.put("name","foo");
	      double d = 10.123;
	      obj.put("num",Integer.parseInt("100"));
	      obj.put("balance",Double.parseDouble(""+d));
	      obj.put("is_vip", Boolean.parseBoolean("true"));

	      System.out.println(obj);
	   }
	   
	   public static void decode() {

		  JSONParser parser = new JSONParser();
		  String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";		
		  try{
		      Object obj = parser.parse(s);
		      JSONArray array = (JSONArray)obj;		
		      System.out.println("The first element of array");
		      System.out.println(array.get(0));
		      System.out.println("The 2nd element of array");
		      System.out.println(array.get(1));
		      System.out.println();

		      JSONObject obj2 = (JSONObject)array.get(1);
		      System.out.println("Field \"1\"");
		      System.out.println(obj2.get("1"));    
		  }
		  catch(Exception pe){
		      System.out.println(pe);    
		  }
	   }
	}