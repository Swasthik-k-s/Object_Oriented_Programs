package Inventory;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Inventory {

	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		Map<String, Double> map = new HashMap<String, Double>();

		try {
			Reader reader = new FileReader("G:/Assignments/Object_Oriented_Programs/data/data.json");
			JSONObject object = (JSONObject) jsonParser.parse(reader);
			JSONArray array = (JSONArray) object.get("inventory");
			//System.out.println(array);
			Iterator<JSONObject> iterator = array.iterator();
			while(iterator.hasNext()) {
				JSONObject item = iterator.next();
				System.out.println(item);
				String name = (String) item.get("name");
				double weight = (double) item.get("weight");
				double price = (double) item.get("pricePerKg");

				map.put(name, weight * price);
			}
			writeJson(map);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	//Method to write the result to the JSON file
	private static void writeJson(Map<String, Double> map) {

		JSONArray array = new JSONArray();

		for(Entry<String, Double> entry: map.entrySet()) {
			JSONObject object = new JSONObject();
			object.put("name", entry.getKey());
			object.put("Total Price", entry.getValue());
			array.add(object);
		}

		JSONObject result = new JSONObject();
		result.put("Result", array);

		try {
			FileWriter writer = new FileWriter("G:/Assignments/Object_Oriented_Programs/data/result.json");
			writer.write(result.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
