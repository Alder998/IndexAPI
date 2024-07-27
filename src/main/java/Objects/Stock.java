package Objects;

import com.example.indexBuild.DataUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
	
    @JsonProperty("longName")
	public String getLongName() {
		return longName;
	}

    @JsonProperty("longName")
	public void setLongName(String longName) {
		this.longName = longName;
	}

    @JsonProperty("lastPrice")
	public float getLastPrice() {
		return lastPrice;
	}

    @JsonProperty("lastPrice")
	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}

    @JsonProperty("lastDividend")
	public float getLastDividend() {
		return lastDividend;
	}
    
    @JsonProperty("lastDividend")
	public void setLastDividend(float lastDividend) {
		this.lastDividend = lastDividend;
	}

    @JsonProperty("dividendLastDate")
	public String getDividendLastDate() {
		return dividendLastDate;
	}

    @JsonProperty("dividendLastDate")
	public void setDividendLastDate(String dividendLastDate) {
		this.dividendLastDate = dividendLastDate;
	}

    @JsonProperty("lastSplitFactor")
	public String getLastSplitFactor() {
		return lastSplitFactor;
	}

    @JsonProperty("lastSplitFactor")
	public void setLastSplitFactor(String lastSplitFactor) {
		this.lastSplitFactor = lastSplitFactor;
	}

    @JsonProperty("lastSplitDate")
	public String getLastSplitDate() {
		return lastSplitDate;
	}
    
    @JsonProperty("lastSplitDate")
	public void setLastSplitDate(String lastSplitDate) {
		this.lastSplitDate = lastSplitDate;
	}
    
    @JsonProperty("marketCap")
	public long getMarketCap() {
		return marketCap;
	}

    @JsonProperty("marketCap")
	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}
    
    @JsonProperty("ticker")
	public String getTicker() {
		return ticker;
	}
    
    @JsonProperty("ticker")
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

    @JsonProperty("stockNumber")
	public float getStockNumber() {
		return stockNumber;
	}

    @JsonProperty("stockNumber")
	public void setStockNumber(float stockNumber) {
		this.stockNumber = stockNumber;
	}

    @JsonProperty("sector")
	public String getSector() {
		return sector;
	}
    
    @JsonProperty("sector")
	public void setSector(String sector) {
		this.sector = sector;
	}

}