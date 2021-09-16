package Inventory;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InventoryManagement {

	public static void main(String args[]) {
		InventoryManagement.inventoryManager();
	}

	/**
	 * Method to store the name and total price of all inventories in the map and display the result
	 */
	public static void inventoryManager() {
		InventoryFactory inventoryFactory = new InventoryFactory();
		List<JSONObject> inventories = new ArrayList<JSONObject>();

		for (JSONArray inventory : inventoryFactory.getInventories()) {
			Map<String, Double> map = new HashMap<String, Double>();
			Iterator<JSONObject> itr = inventory.iterator();
			while (itr.hasNext()) {
				JSONObject item = (JSONObject) itr.next();
				String name = (String) item.get("name");
				double weight = (double) item.get("weight");
				double pricePerKg = (double) item.get("pricePerKg");
				double totalPrice = weight * pricePerKg;
				map.put(name, totalPrice);
			}

			JSONObject object = new JSONObject();
			object.putAll(map);
			inventories.add(object);
		}

		int count = 1;
		for (JSONObject inventory : inventories) {
			System.out.println("Inventory " + count);
			double sum = 0;
			for (Object item : inventory.keySet()) {
				sum += (double) inventory.get(item);
			}
			System.out.println("Total Cost: " + sum);
			System.out.println("Inventory Items");
			System.out.println(inventory.toJSONString());
			System.out.println();
			count++;
		}
	}
}