package Stock;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StockPortfolio {

	private List<Stock> stocks = new ArrayList<Stock>();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		StockPortfolio portfolio = new StockPortfolio();
		portfolio.readFile();
		portfolio.stockReport();
	}
	
	/**
	 * Method to read the data from the JSON file and store in local list
	 */
	private void readFile() {
		try {
			FileReader reader = new FileReader("src/Stock/stocks.json");
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(reader);
			JSONArray stocksArray = (JSONArray) object.get("stocks"); 
			
			Iterator<JSONObject> iterator = stocksArray.iterator();
			
			while(iterator.hasNext()) {
				JSONObject stock = iterator.next();
				String stockName = (String) stock.get("stockName");
				long noOfShares = (long) stock.get("noOfShares");
				double sharePrice = (double) stock.get("sharePrice");
				Stock newStock = new Stock(stockName,noOfShares,sharePrice);
				stocks.add(newStock);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to calculate and display the stock report
	 */
	private void stockReport() {
		double totalValue = 0;
		System.out.println("Stock Report");
		for(Stock stock: stocks) {
			double value = stock.getNoOfShares() * stock.getSharePrice();
			totalValue += value;
			System.out.println("Stock Name = " + stock.getStockName());
			System.out.println("Value = " + value);
		}
		System.out.println("Total Value = " + totalValue);
	}
}
