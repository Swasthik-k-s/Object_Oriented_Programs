package CommercialDataProcessing;

public interface IStockAccount {
	
	double valueOf();
	
	void buy(int amount, String symbol);
	
	void sell(int amount, String symbol);
	
	void save(String filename);
	
	void printReport();
}
