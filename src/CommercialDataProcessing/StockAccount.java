package CommercialDataProcessing;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StockAccount implements IStockAccount{

	private final String STOCKFILE = "src/CommercialDataProcessing/stocks.json";
	private String fileName;
	private JSONArray stocksData;
	List<CompanyShares> companyShares = new ArrayList<CompanyShares>();

	StockAccount(String fileName){
		this.fileName = fileName;
	}
	public List<CompanyShares> getCompanyShares(){
		return companyShares;
	}

	public void setCompanyShares(List<CompanyShares> companyShares) {
		this.companyShares = companyShares;
	}

	public void initializeAccountFromFile(){
		try {
			List<CompanyShares> companySharesList = new ArrayList<CompanyShares>();
			FileReader reader = new FileReader(fileName);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			JSONArray companyShares = (JSONArray) obj.get("companyShares");
			if (companyShares == null) {
				return;
			}
			Iterator<JSONObject> itr = companyShares.iterator();
			while (itr.hasNext()) {
				JSONObject compShare = itr.next();
				String stockSymbol = compShare.get("stockSymbol").toString();
				long noOfShares = (long) compShare.get("noOfShares");
				CompanyShares companyShare = new CompanyShares(stockSymbol);
				companyShare.setNoOfShares(noOfShares);

				JSONArray transactions = (JSONArray) compShare.get("transactions");
				Iterator<JSONObject> itr2 = transactions.iterator();

				List<Transaction> transactionList = new ArrayList<Transaction>();
				while (itr2.hasNext()) {
					JSONObject transaction = itr2.next();
					String dateTime = transaction.get("dateTime").toString();
					long noOfShare = (long) transaction.get("noOfShares");
					String state = (String) transaction.get("state");
					Transaction transact = new Transaction(dateTime, noOfShare, state);
					transactionList.add(transact);
				}

				companyShare.setTransactions(transactionList);
				companySharesList.add(companyShare);
			}
			this.companyShares = companySharesList;
			System.out.println("Data restored from file");
		} catch (Exception e) {
			System.out.println("Data not restored from file");
			return;
		}
	}

	@Override
	public double valueOf() {
		double value = 0;
		for (CompanyShares companyShare : companyShares) {
			value += valueOf(companyShare);
		}  
		return value;
	}

	public double valueOf(CompanyShares companyShare) {
		readJSON();
		Iterator<JSONObject> itr = stocksData.iterator();
		double sharePrice = 0.0;
		while (itr.hasNext()) {
			JSONObject stock = itr.next();
			if (stock.get("stockSymbol").equals(companyShare.getStockSymbol())) {
				sharePrice = (double) stock.get("sharePrice");
			}
		}

		return sharePrice * companyShare.getNoOfShares();
	}

	private void readJSON(){
		try{
			FileReader reader = new FileReader(STOCKFILE);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			stocksData = (JSONArray) obj.get("stocks");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buy(int amount, String symbol) {
		readJSON();
		Iterator<JSONObject> itr = stocksData.iterator();

		long numberOfShares = 0;
		while (itr.hasNext()) {
			JSONObject stock = itr.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				numberOfShares = (long) stock.get("noOfShares");
			}
		}

		if (amount > numberOfShares) {
			System.out.println("Insufficient Shares Available");
		}
		else {
			CompanyShares newCompanyShare = null;
			for (CompanyShares companyShare : companyShares) {
				if (companyShare.getStockSymbol().equals(symbol)) {
					newCompanyShare = companyShare;
					companyShares.remove(companyShare);
					break;
				}
			}
			if (newCompanyShare == null) {
				newCompanyShare = new CompanyShares(symbol);
			}

			updateValue(symbol, amount, newCompanyShare ,Transaction.BUY);
		}
	}



	@Override
	public void sell(int amount, String symbol) {
		readJSON();
		long numberOfShares = 0;

		for (CompanyShares companyShare : companyShares) {
			if (companyShare.getStockSymbol().equals(symbol)) {
				numberOfShares = companyShare.getNoOfShares();
			}
		}

		if (numberOfShares == 0 || amount > numberOfShares) {
			System.out.println("Insufficient Shares available");
		}
		else {
			CompanyShares selectedShare = null;
			for (CompanyShares companyShare : companyShares) {
				if (companyShare.getStockSymbol().equals(symbol)) {
					selectedShare = companyShare;
					companyShares.remove(companyShare);
					break;
				}
			}

			if (selectedShare != null) {
				updateValue(symbol, amount, selectedShare, Transaction.SELL);
			}
		}
	}

	private void updateValue(String symbol, long numberOfShares, CompanyShares companyShare, String state) {
		readJSON();
		//Add transaction to CompanyShare Object
		long prevShares = companyShare.getNoOfShares();
		if (state == Transaction.BUY) {
			companyShare.setNoOfShares(prevShares + numberOfShares);
		}
		else {
			companyShare.setNoOfShares(prevShares - numberOfShares);
		}
		long millis = System.currentTimeMillis();
		Date dateTime = new Date(millis);
		Transaction transaction = new Transaction(dateTime.toString(), numberOfShares, state);
		companyShare.addTransaction(transaction);
		companyShares.add(companyShare);

		//Update stocks.json file
		Iterator<JSONObject> itr = stocksData.iterator();

		while (itr.hasNext()) {
			JSONObject stock = itr.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				prevShares = (long)stock.get("noOfShares");
				stock.remove("noOfShares");
				if (state == Transaction.BUY) {
					stock.put("noOfShares", prevShares - numberOfShares);
				}
				else {
					stock.put("noOfShares", prevShares + numberOfShares);
				}
			}
		}

		try {
			FileWriter writer = new FileWriter(STOCKFILE);
			JSONObject result = new JSONObject();
			result.put("stocks", stocksData);
			writer.write(result.toJSONString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (state == Transaction.BUY) {
			System.out.println("Buy Successfull");
		}
		else {
			System.out.println("Sell Successfull");
		}

	}

	@Override
	public void save(String filename) {
		JSONArray compShares = new JSONArray();
		for (CompanyShares companyShare : companyShares) {
			String stockSymbol = companyShare.getStockSymbol();
			long numberOfShares = companyShare.getNoOfShares();
			JSONArray transactions = new JSONArray();
			for (Transaction transaction : companyShare.getTransactions()) {
				JSONObject transactionObject = new JSONObject();
				transactionObject.put("dateTime", transaction.getDateTime().toString());
				transactionObject.put("noOfShares", transaction.getNoOfShares());
				transactionObject.put("state", transaction.getState());
				transactions.add(transactionObject);
			}
			JSONObject obj = new JSONObject();
			obj.put("stockSymbol", stockSymbol);
			obj.put("noOfShares", numberOfShares);
			obj.put("transactions", transactions);
			compShares.add(obj);
		}

		JSONObject finalJSON = new JSONObject();
		finalJSON.put("companyShares", compShares);

		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(finalJSON.toJSONString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}     
	}

	@Override
	public void printReport() {
        System.out.println("Stock Report");
        System.out.println("Holding Shares\n");
        for (CompanyShares companyShare : companyShares) {
        	System.out.println("Share Symbol : " + companyShare.getStockSymbol());
        	System.out.println("Number of Shares Holding : " + companyShare.getNoOfShares());
            double valueEach = 0;
            if (companyShare.getNoOfShares() != 0) {
                valueEach = valueOf(companyShare) / companyShare.getNoOfShares();
            }
            System.out.println("Value of each share : " + valueEach);
            System.out.println("Total Share Value : " + valueOf(companyShare));
            System.out.println();
        }
        System.out.println("Total Value of portfolio: " + valueOf());
	}

}
