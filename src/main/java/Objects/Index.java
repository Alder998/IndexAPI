package Objects;

import java.util.ArrayList;

public class Index {
	
	private String indexName;
	private ArrayList <Stock> indexShares;
	private float indexLevel;
	private long indexTotalValue;
	private float indexDivisor;
	
	public Index () {
	}
	
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public ArrayList<Stock> getIndexShares() {
		return indexShares;
	}

	public void setIndexShares(ArrayList<Stock> indexShares) {
		this.indexShares = indexShares;
	}

	public float getIndexLevel() {
		return indexLevel;
	}

	public void setIndexLevel(float indexLevel) {
		this.indexLevel = indexLevel;
	}

	public long getIndexTotalValue() {
		return indexTotalValue;
	}

	public void setIndexTotalValue(long indexTotalValue) {
		this.indexTotalValue = indexTotalValue;
	}

	public float getIndexDivisor() {
		return indexDivisor;
	}

	public void setIndexDivisor(float indexDivisor) {
		this.indexDivisor = indexDivisor;
	}
}