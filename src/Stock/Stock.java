package Stock;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Stock {

	static Scanner scanner = new Scanner(System.in);
	public static JSONArray array = new JSONArray();

	public static void main(String[] args) {
		
		while(true) {
			System.out.println("1)Add Stock\t2)Print Stocks\t3)Exit\nEnter your Choice");
			int choice = scanner.nextInt();

			switch(choice) {
			case 1:
				addStock();
				break;
			case 2:
				printStock();
				break;
			default:
				return;
			}
		}
	}

	private static void addStock() {

		System.out.println("Enter the Share Name");
		String name = scanner.next();
		System.out.println("Enter the Number of Shares");
		int noOfShares = scanner.nextInt();
		System.out.println("Enter the Share Price");
		int sharePrice = scanner.nextInt();
		int totalPrice = noOfShares * sharePrice;
		JSONObject object = new JSONObject();
		object.put("name", name);
		object.put("Total Price", totalPrice);

		array.add(object);

		JSONObject obj = new JSONObject();
		obj.put("Stocks", array);

		try {
			FileWriter writer = new FileWriter("G:/Assignments/Object_Oriented_Programs/data/stock.json");
			writer.write(obj.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printStock() {
		JSONParser parser = new JSONParser();
		try {
			Reader reader = new FileReader("G:/Assignments/Object_Oriented_Programs/data/stock.json");
			JSONObject object = (JSONObject) parser.parse(reader);
			JSONArray array = (JSONArray) object.get("Stocks");
			System.out.println(array);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
