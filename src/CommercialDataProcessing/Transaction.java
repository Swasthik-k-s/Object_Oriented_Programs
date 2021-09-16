package CommercialDataProcessing;

public class Transaction {
	final static String BUY = "Buy";
	final static String SELL = "Sell";

	private String dateTime;
	private long noOfShares;
	private String state;
	
	public Transaction(String dateTime, long noOfShares, String state) {
		this.dateTime = dateTime;
		this.noOfShares = noOfShares;
		this.state = state;
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public long getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(long noOfShares) {
		this.noOfShares = noOfShares;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
