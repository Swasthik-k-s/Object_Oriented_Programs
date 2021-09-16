package CommercialDataProcessing;

import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CommercialDataProcessingMain {

	final static String STOCKFILE = "src/CommercialDataProcessing/stocks.json";
	final static String ACCOUNTFILE = "src/CommercialDataProcessing/accounts.json";

	private static StockAccount account = new StockAccount(ACCOUNTFILE);

	public static void main(String[] args) {
		loadData();
	}

	private static void loadData() {
		account.initializeAccountFromFile();

		Scanner in = new Scanner(System.in);

		System.out.println("Welcome to the Stock program");

		while (true) {
			System.out.println("Select a Option");
			System.out.println("1) Buy Stocks\n2) Sell Stocks\n3) Print Stock Report\n4) Exit");
			int choice = in.nextInt();
			in.nextLine();

			switch (choice) {
			case 1:
				buyStocks();
				break;
			case 2:
				sellStocks();
				break;
			case 3:
				account.printReport();
				break;
			case 4:
				System.out.println("Thank you");
				return;
			default:
				System.out.println("Invalid Option");
				break;
			}
		}
	}

	private static void buyStocks() {
		Scanner in = new Scanner(System.in);

		System.out.println("Select the stock you want to buy");
        JSONArray stocks = readJSON();
        Iterator<JSONObject> itr = stocks.iterator();
        int count = 1;
        while (itr.hasNext()) {
        	System.out.println(count + ":");
            JSONObject stock = itr.next();
            System.out.println("Stock Name: " + stock.get("stockName"));
            System.out.println("Stock Symbol: " + stock.get("stockSymbol"));
            System.out.println("Share price: " + stock.get("sharePrice"));
            System.out.println("Number Of Shares: " + stock.get("noOfShares"));
            System.out.println();
            count++;
        }
        
        int stockChoice = in.nextInt();
        while (stockChoice >= count) {
            System.out.println("Invalid option");
            stockChoice = in.nextInt();
        }

        System.out.println("Enter the amount to buy");
        int amount = in.nextInt();
        JSONObject selectedStock = (JSONObject) stocks.get(stockChoice - 1);
        while (amount > (long) selectedStock.get("noOfShares") || amount<=0)
        {
        	System.out.println("Enter a valid amount");
            amount = in.nextInt();
        }

        account.buy(amount, (String) selectedStock.get("stockSymbol"));
        account.save(ACCOUNTFILE);
	}

	private static void sellStocks() {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Select the stock you want to Sell");
        int count = 1;
        for (CompanyShares companyShare : account.getCompanyShares()) {
        	System.out.println(count + ":");
        	System.out.println("Stock Symbol : " + companyShare.getStockSymbol());
        	System.out.println("Number Of Shares : " + companyShare.getNoOfShares());
        	System.out.println();
            count++;
        }

        int stockChoice = in.nextInt();
        while (stockChoice >= count) {
        	System.out.println("Invalid option");
            stockChoice = in.nextInt();
        }

        System.out.println("Enter the amount to sell");
        int amount = in.nextInt();
        CompanyShares selectedStock = account.getCompanyShares().get(stockChoice - 1);
        while (amount > (long) selectedStock.getNoOfShares() || amount<=0)
        {
        	System.out.println("Enter a valid amount");
            amount = in.nextInt();
        }

        account.sell(amount, selectedStock.getStockSymbol());
        account.save(ACCOUNTFILE);
	}
	
	private static JSONArray readJSON() {
        try {
            FileReader reader = new FileReader(STOCKFILE);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray stocks = (JSONArray) obj.get("stocks");
            return stocks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
