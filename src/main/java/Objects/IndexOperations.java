package Objects;

import java.util.ArrayList;

import com.example.indexBuild.DataUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// First of financial Products we are looking at is the stock traded in a regulated Market
public class IndexOperations {
	
	private String operation; // can be, for simplicity, "Share-Addiction" | "Share-Deletion" | "Share-Dividend"
	private String indexName;
	private ArrayList<Stock> sharesAffected;
	
	public IndexOperations() {	
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public ArrayList<Stock> getSharesAffected() {
		return sharesAffected;
	}

	public void setSharesAffected(ArrayList<Stock> sharesAffected) {
		this.sharesAffected = sharesAffected;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
}