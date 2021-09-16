package Inventory;

import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class InventoryFactory {
	private List<JSONArray> inventories = new ArrayList<>();

	/**
	 * Method to read the JSON file and return data from the file
	 * @return
	 */
	public List<JSONArray> getInventories() {
		try {
			FileReader reader = new FileReader("src/Inventory/data.json");
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			JSONArray inventoryList = (JSONArray) obj.get("inventories");

			Iterator<JSONArray> itr = inventoryList.iterator();
			while (itr.hasNext()) {
				JSONArray inventory = itr.next();
				inventories.add(inventory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inventories;
	}
}
