package Objects;

import java.util.ArrayList;

public class Index {
	
	private String indexName;
	private ArrayList <Stock> indexShares;
	private float indexLevel;
	
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
}