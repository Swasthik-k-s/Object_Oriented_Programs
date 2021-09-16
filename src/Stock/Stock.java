package Stock;

public class Stock {
	private String stockName;
	private long noOfShares;
	private double sharePrice;
	
	Stock(String stockName, long noOfShares, double sharePrice){
		this.stockName = stockName;
		this.noOfShares = noOfShares;
		this.sharePrice = sharePrice;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public long getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(long noOfShares) {
		this.noOfShares = noOfShares;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

	@Override
	public String toString() {
		return "Stock [Stock Name=" + stockName + ", Number of Shares=" + noOfShares + ", Share Price=" + sharePrice + "]";
	}
	
}
