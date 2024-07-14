package com.example.indexBuild;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Objects.Index;
import Objects.Stock;

@Component
public class DataClass {

	// Here we are storing the methods to Complete the requested Tasks. Therefore, the create update and Calculate Methods for indexes

	// This Method is to test that the Information is passing Correctly from one class to another 
	public String getAPIData(String whatToPrint) throws Exception {
		// Print Testing
		System.out.println(whatToPrint);
		return whatToPrint;
	}
	
	// This ObjectMapper is essential to take data from JSON
    private static final ObjectMapper objectMapper = new ObjectMapper();
	
    // Method to get Stocks from the In-Memory Storage
	public ArrayList<Stock> getStocksFromJsonData() throws Exception {
        ArrayList<Stock> stocks = new ArrayList<Stock>();
        ClassPathResource resource = new ClassPathResource("StockSample.json");
		
        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(inputStream);

            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode stockNode = field.getValue();
                Stock stock = objectMapper.treeToValue(stockNode, Stock.class);
                
                // Set the Stock Ticker
                String symbol = field.getKey();
                stock.setTicker(symbol);
                
                stocks.add(stock);
            }
        }
        return stocks;
    }
		
    // Method to get Stocks from the In-Memory Storage
	public ArrayList<Index> getIndexesFromJsonData() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        ClassPathResource resource = new ClassPathResource("Indexes.json");
		
        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(inputStream);

            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode indexNode = field.getValue();
                Index index = objectMapper.treeToValue(indexNode, Index.class);
                
                indexes.add(index);
            }
        }
        return indexes;
    }
	
	public ArrayList<Stock> getStockPicking(String method, int stockNumber) throws Exception {
		ArrayList<Stock> stockSample = this.getStocksFromJsonData();
		
		if (method == "TopS&P500") {
	        // Sort the JSON Values for MarketCap
			stockSample.sort(Comparator.comparingLong(Stock::getMarketCap).reversed());
	        // Take the highest Market Caps stocks
	        List<Stock> topStocksList = stockSample.subList(0, Math.min(stockNumber, stockSample.size()));
	        ArrayList<Stock> topStocks = new ArrayList<>(topStocksList);
	        return topStocks;
		}
		else if (method == "LowS&P500") {
	        // Sort the JSON Values for MarketCap
			stockSample.sort(Comparator.comparingLong(Stock::getMarketCap).reversed());
	        // Take the highest Market Caps stocks
	        List<Stock> lowStocksList = stockSample.subList(0, Math.max(stockNumber, stockSample.size()));
	        ArrayList<Stock> lowStocks = new ArrayList<>(lowStocksList);
	        return lowStocks;
		}
		else {
           throw new IllegalArgumentException("Invalid method specified: " + method);
		}
	}
	
	public Index createIndex(int stockNumber, String stockPickingMethod) throws Exception {
		Index newIndex = new Index();
		
		// Stock Picking
		ArrayList<Stock> selectedStocks = this.getStockPicking("TopS&P500", stockNumber);
		
		// Add stocks to index and set index parameters
		newIndex.setIndexShares(selectedStocks);
		// set the name in a fixed way so that the Duplicated Indexes are easier to see
		String indexName = stockPickingMethod + "_" + stockNumber;
		newIndex.setIndexName(indexName);
		
		// TODO: Set the right quantity of each stock in the index, according to the total Budget and to
		// the last price of stocks
		
		// Before saving the index, a check that an index with the same name is already existing
		// Get the indexes
		ArrayList<Index> existingIndexes = this.getIndexesFromJsonData();
		// Check the presence of the index created now among the existing Indexes
		for (Index index : existingIndexes) {
			if (index.getIndexName().equals(indexName)) {
				throw new Exception("ERROR Index " + indexName + " already Existing!");
			}
		}
        // Convert newIndex to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(newIndex);
        // Save jsonString to Indexes.json file
        DataUtils.updateJSONFile(indexName, jsonString, "create");
		return newIndex;
	}
	
	// Method to delete one Index from the index list, given the name of the index
	public void deleteIndex(String indexName) throws Exception {
        // Convert newIndex to JSON string
        DataUtils.updateJSONFile(indexName, null, "delete");
	}
	
	
	
}
