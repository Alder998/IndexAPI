package Objects;

// First of financial Products we are looking at is the stock traded in a regulated Market
public class Stock {
	
	private String ticker;
	private String longName;
	private float lastPrice;  // price cannot be null
	private float lastDividend;
	private String dividendLastDate;
	private String lastSplitFactor;
	private String lastSplitDate;
	private long marketCap;
	private float stockNumber;
	private String sector;
	
	public Stock() {	
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public float getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(float lastDividend) {
		this.lastDividend = lastDividend;
	}

	public String getDividendLastDate() {
		return dividendLastDate;
	}

	public void setDividendLastDate(String dividendLastDate) {
		this.dividendLastDate = dividendLastDate;
	}

	public String getLastSplitFactor() {
		return lastSplitFactor;
	}

	public void setLastSplitFactor(String lastSplitFactor) {
		this.lastSplitFactor = lastSplitFactor;
	}

	public String getLastSplitDate() {
		return lastSplitDate;
	}

	public void setLastSplitDate(String lastSplitDate) {
		this.lastSplitDate = lastSplitDate;
	}

	public long getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public float getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(float stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

}