package CommercialDataProcessing;

import java.util.*;

public class CompanyShares {

	private String stockSymbol;
	private long noOfShares;
	private List<Transaction> transactions = new ArrayList<Transaction>();

	CompanyShares(String stockSymbol){
		this.stockSymbol = stockSymbol;
		this.noOfShares = 0;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public long getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(long noOfShares) {
		this.noOfShares = noOfShares;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
}
