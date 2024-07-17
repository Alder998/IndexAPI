package com.example.indexBuild;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Objects.Index;
import Objects.IndexOperations;
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
    public List<Index> getIndexesFromJsonData() throws Exception {
        List<Index> indexes = new ArrayList<>();
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
	
	public Index getIndexFromJsonDataByIndexName (String indexName) throws Exception {
		 ArrayList<Index> allIndexes = new ArrayList<Index>(this.getIndexesFromJsonData());
		 Index filteredIndex = allIndexes.stream().filter(x -> x.getIndexName().equals(indexName)).findFirst().orElse(null);
		 if (filteredIndex != null) {
			 return filteredIndex;
		 }
		 else {
	         throw new customExceptions.IndexNotFoundException("ERROR: The Index:" + indexName + " has not been found!");
		 }
	}
	
	// TODO: for this Method, same as above, replace the for loop with a .stream, faster and better
	public Stock getStockFromJsonDataByStockName (String stockName) throws Exception {
		 ArrayList<Stock> allStocks = this.getStocksFromJsonData();
		 Stock filteredStock = new Stock();
		 // TODO for spare time: this method to filter is super slow, may look for other alternatives, for example, look for
		 // the applicability of .Stream
		 for (Stock stock : allStocks) {
			 if (stock.getTicker().equals(stockName)) {
				 filteredStock = stock;
			 }
		 }
		 if (filteredStock.getTicker().equals(stockName)) {
			 return filteredStock;
		 }
		 else {
	         throw new customExceptions.IndexNotFoundException("ERROR: The Index:" + stockName + " has not been found!");
		 }
	}
	
	public ArrayList<Stock> getStockPicking(String method, int stockNumber) throws Exception {
		ArrayList<Stock> stockSample = this.getStocksFromJsonData();
		
		if (method.equals("TopS&P500")) {
	        // Sort the JSON Values for MarketCap
			stockSample.sort(Comparator.comparingLong(Stock::getMarketCap).reversed());
	        // Take the highest Market Caps stocks
	        List<Stock> topStocksList = stockSample.subList(0, Math.min(stockNumber, stockSample.size()));
	        ArrayList<Stock> topStocks = new ArrayList<>(topStocksList);
	        return topStocks;
		}
		else if (method.equals("LowS&P500")) {
	        // Sort the JSON Values for MarketCap
			stockSample.sort(Comparator.comparingLong(Stock::getMarketCap));
	        // Take the highest Market Caps stocks
	        List<Stock> lowStocksList = stockSample.subList(0, Math.min(stockNumber, stockSample.size()));
	        ArrayList<Stock> lowStocks = new ArrayList<>(lowStocksList);
	        return lowStocks;
		}
		else {
           throw new IllegalArgumentException("Invalid method specified: " + method);
		}
	}
	
	// Method to balance the stocks quantities within an index
	
	public ArrayList<Stock> createStockQuantitiesForIndex (ArrayList<Stock> stocksInIndex, long indexTotalValue) {
		// Stock Quantities in the index, according to the Index total Value
		// Uniform composition: compute the price that correspond to the uniform percentage
		float stockPercentagePrice = (indexTotalValue / stocksInIndex.size());
		// compute the equivalent number of stocks according to the price
		ArrayList<Stock> selectedStocksWithQuantities = new ArrayList<Stock>();
		for (Stock stock : stocksInIndex) {
			// Take the last price and compute the quantities
			float quantity = stockPercentagePrice / stock.getLastPrice();
			// As shown in the example, a share quantity can be lower than 1, assume it 
			stock.setStockNumber(quantity);
			selectedStocksWithQuantities.add(stock);
		}	
		return selectedStocksWithQuantities;
	}
	
	public Index createIndex(int stockNumber, String stockPickingMethod, long indexTotalValue) throws Exception {
		Index newIndex = new Index();
		
		// Stock Picking
		ArrayList<Stock> selectedStocks = this.createStockQuantitiesForIndex(
				this.getStockPicking(stockPickingMethod, stockNumber), indexTotalValue);
		
		// Any index must have at least two members, so if the total value is low, it could happen that the stock present in the
		// Index would be less than two
		if (selectedStocks.size() <= 2) {
			throw new customExceptions.TwoMembersException ("ERROR: the number of stocks in the index is less than two!");
		}
		
		// Add stocks to index and set index parameters
		newIndex.setIndexShares(selectedStocks);
		newIndex.setIndexTotalValue(indexTotalValue);
		// set the name in a fixed way so that the Duplicated Indexes are easier to see
		String indexName = stockPickingMethod + "_" + stockNumber;
		newIndex.setIndexName(indexName);
			
		// Before saving the index, a check that an index with the same name is already existing
		// Get the indexes
		ArrayList<Index> existingIndexes = new ArrayList<Index>(this.getIndexesFromJsonData());
		// Check the presence of the index created now among the existing Indexes
		for (Index index : existingIndexes) {
			if (index.getIndexName().equals(indexName)) {
				throw new customExceptions.IndexAlreadyExistsException ("ERROR Index " + indexName + " already Existing!");
			}
		}
        // Convert newIndex to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(newIndex);
        // Save jsonString to Indexes.json file
        DataUtils.updateJSONFile(indexName, jsonString, "create");
		return newIndex;
	}
	
	public Index createIndexByStocks (ArrayList<Stock> stocks, long indexTotalValue) {
		Index newIndex = new Index();
		// Computation of stock weights (uniform)
		ArrayList<Stock> newShareQuantities = this.createStockQuantitiesForIndex(stocks, indexTotalValue);
		newIndex.setIndexShares(newShareQuantities);
		return newIndex;
	}
	
	// Method to delete one Index from the index list, given the name of the index
	public void deleteIndex(String indexName) throws Exception {
        // Convert newIndex to JSON string
        DataUtils.updateJSONFile(indexName, null, "delete");
	}
	
	// Method to add a Stock to one given index and keep the uniformity of the stock quantity
	public IndexOperations addStockToIndex (Stock stockToAdd, String indexName) throws Exception {
		
		// Take the index using the indexName
		Index index = this.getIndexFromJsonDataByIndexName(indexName);
		
		// add the stock to the index, preserving the other that are already present in it, and checking that
		// the stock is not already present in the stock
		for (Stock stock : index.getIndexShares()) {
			// Check presence of the stock in the index
			if (stock.getTicker().equals(stockToAdd.getTicker())) {
				throw new customExceptions.StockAlreadyExistsException("ERROR Stock " + stockToAdd.getTicker() + " already Present in Index:" + indexName);
			}
		}
		// Add the stock to the Index
		index.getIndexShares().add(stockToAdd);

		// Update the JSON file containing the Indexes
		this.deleteIndex(indexName);
		// compute the updated Index and save if back
		Index updatedIndex = this.createIndexByStocks(index.getIndexShares(), index.getIndexTotalValue());
		updatedIndex.setIndexName(indexName);
		updatedIndex.setIndexTotalValue(index.getIndexTotalValue());
		
		// save the updated Index to JSON
		ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(updatedIndex);
        // Save jsonString to Indexes.json file
        DataUtils.updateJSONFile(indexName, jsonString, "create");
        
        // configure the response with the Object IndexOperations
        IndexOperations operationResponse = new IndexOperations();
        operationResponse.setOperation("Stock-Addiction");
        ArrayList<Stock> stockResponse = new ArrayList<Stock>();        
        Stock response = updatedIndex.getIndexShares().stream().filter(x -> x.getTicker() == stockToAdd.getTicker()).findFirst().orElse(null);
        stockResponse.add(response);
        operationResponse.setSharesAffected(stockResponse);
        operationResponse.setIndexName(indexName);
        
        return operationResponse;
	}
	
	// Method to remove a Stock from Index
	
	public IndexOperations deleteStockFromIndex (Stock stockToDelete, String indexName) throws Exception {
		Index updatedIndex = new Index();
		// Take the index using the indexName
		Index selectedIndex = this.getIndexFromJsonDataByIndexName(indexName);
		ArrayList<Stock> updatedShares = selectedIndex.getIndexShares().stream().filter(x -> !x.getTicker().equals(stockToDelete.getTicker())).collect(Collectors.toCollection(ArrayList::new));
		
		if (updatedShares.size()==selectedIndex.getIndexShares().size()) {
			throw new customExceptions.StockNotFoundException("ERROR Stock " + stockToDelete.getTicker() + " Not Present in Index:" + indexName);
		}
		
		// Set the other Index Parameters
		updatedIndex.setIndexShares(updatedShares);
		updatedIndex.setIndexName(indexName);
		updatedIndex.setIndexTotalValue(selectedIndex.getIndexTotalValue());
		
		// save the updated Index to JSON
		ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(updatedIndex);
        // Save jsonString to Indexes.json file
        DataUtils.updateJSONFile(indexName, jsonString, "create");
        
        // configure the response with the Object IndexOperations
        IndexOperations operationResponse = new IndexOperations();
        operationResponse.setOperation("Stock-Deletion");
        ArrayList<Stock> stockResponse = new ArrayList<Stock>();        
        Stock response = updatedIndex.getIndexShares().stream().filter(x -> x.getTicker() == stockToDelete.getTicker()).findFirst().orElse(null);
        stockResponse.add(response);
        operationResponse.setSharesAffected(stockResponse);
        operationResponse.setIndexName(indexName);
        
        return operationResponse;
	}
	
	public IndexOperations adjustIndexForDividend (String indexName) throws Exception {
		
		// get the index
		Index selectedIndex = this.getIndexFromJsonDataByIndexName(indexName);
		
		// get all the stocks in the index and check who gave dividends
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = OffsetDateTime.now().format(formatter);
		ArrayList<Stock> dividendStocks = selectedIndex.getIndexShares().stream().filter(x -> x.getDividendLastDate().equals(dateString)).collect(Collectors.toCollection(ArrayList::new));;
		
		// Suppose we have a total Return index, and therefore, the dividend would be reinvested in the Index itself. The dividend value needs to be added to the index Total Budget
		double totalDividends = 0.0;
		for (Stock stock : dividendStocks) {
			float dividendValue = stock.getLastDividend() * stock.getStockNumber();
			totalDividends += dividendValue;
		}
		long newIndexTotalValue = (long) (selectedIndex.getIndexTotalValue() + totalDividends);
		
		// Update the index
		selectedIndex.setIndexTotalValue(newIndexTotalValue);
		// Update the weights to the new Total Value
		ArrayList<Stock> newComposition = this.createStockQuantitiesForIndex(selectedIndex.getIndexShares(), newIndexTotalValue);
		selectedIndex.setIndexShares(newComposition);
		
		// save the updated Index to JSON
		ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(selectedIndex);
        // Save jsonString to Indexes.json file
        DataUtils.updateJSONFile(indexName, jsonString, "create");
        
        // configure the response with the Object IndexOperations
        
        // get the precise stock with the Updated Values
        ArrayList<Stock> responseStock = new ArrayList<Stock>();
        for (Stock singleDividendStock : dividendStocks) {
            Stock response = selectedIndex.getIndexShares().stream().filter(x -> x.getTicker() == singleDividendStock.getTicker()).findFirst().orElse(null);
            responseStock.add(response);
            }
        IndexOperations operationResponse = new IndexOperations();
        operationResponse.setOperation("Share-Dividend");
        operationResponse.setSharesAffected(responseStock);
        operationResponse.setIndexName(indexName);
        
        return operationResponse;
	}
	
	public Index computeIndexLevelByIndexName (String indexName) throws Exception {
		
		// get the target Index
		Index selectedIndex = this.getIndexFromJsonDataByIndexName(indexName);

		// if the level has not been computed, compute it keeping the base level as 100 for simplicity)
		// Sum all the stock last Prices multiplied for the relative values
		float indexRawValue = 0;
		for (Stock stock : selectedIndex.getIndexShares()) {
			indexRawValue += stock.getLastPrice();
		}
		// if it has never been computed, compute the divisor
		float indexValue = 0;
		if (selectedIndex.getIndexLevel() == 0) {
			float firstDivisor = indexRawValue / 100;
			selectedIndex.setIndexDivisor(firstDivisor);
			indexValue = indexRawValue / firstDivisor;
		}
		else {
			indexValue = indexRawValue / selectedIndex.getIndexDivisor();
		}
	
		selectedIndex.setIndexLevel(indexValue);
		
		// save the updated Index to JSON
		ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(selectedIndex);
        // Save jsonString to Indexes.json file
        DataUtils.updateJSONFile(indexName, jsonString, "create");
        
        return selectedIndex;
		
	}
	
	public ArrayList<Index> computeAllIndexLevel () throws Exception {
		
		// first of all, Update Stock Prices within the indexes
		this.updateIndexStockPrices();
		
		// get All the Indexes
		ArrayList<Index> allIndexes = new ArrayList<Index>(this.getIndexesFromJsonData());
		
		// iterate for all the indexes, compute the values
		ArrayList<Index> indexesWithState = new ArrayList<Index>();
		for (Index index : allIndexes) {
			Index indexWithState = this.computeIndexLevelByIndexName(index.getIndexName());
			indexesWithState.add(indexWithState);
			
			// Update the values in the JSON
			ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(indexWithState);
	        // Save jsonString to Indexes.json file
	        DataUtils.updateJSONFile(indexWithState.getIndexName(), jsonString, "create");
	        
	        // Added Logging
	        System.out.println("Index: " + indexWithState.getIndexName() + " Level: " + indexWithState.getIndexLevel());
		}
		// save series to JSON
		DataUtils.createIndexSeries(indexesWithState);
		
        return indexesWithState;
	}
	
	public void updateIndexStockPrices() throws Exception {
		// Take all the indexes
		ArrayList<Index> allIndexes = new ArrayList<Index>(this.getIndexesFromJsonData());
		
		// Iterate for each Index
		for (Index index : allIndexes) {
			// Iterate for each Stock in the Index
			ArrayList<Stock> updatedStocks = new ArrayList<Stock>();
			for (Stock stock : index.getIndexShares()) {
				float newPrice = this.getStockFromJsonDataByStockName(stock.getTicker()).getLastPrice();
				stock.setLastPrice(newPrice);
				updatedStocks.add(stock);
			}
			index.setIndexShares(updatedStocks);
			
			// save the Updated Index Back to JSON
			ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(index);
	        // Save jsonString to Indexes.json file
	        DataUtils.updateJSONFile(index.getIndexName(), jsonString, "create");
		}
		
	}
		
}
