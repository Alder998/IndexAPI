// RestService is where the APIs Endpoints are stored and ready to be used

package com.example.indexBuild;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Objects.IndexOperations;
import Objects.Stock;

@RestController
public class RestService {

    @Autowired
    private APIController apiController;

    @GetMapping("/api/data")
    public String getAPIData(@RequestParam String whatToPrint) throws Exception {
        return apiController.processAPIData(whatToPrint);
    }
    
    @GetMapping("/api/stock")
    public ArrayList<Stock> getStocksFromJsonData() throws Exception {
        return apiController.getStocks();
    }
    
    @GetMapping("/api/createIndex")
    public ResponseEntity<String> createIndex(int stockNumber, String stockPickingMethod, long indexTotalValue) throws Exception {
    	try {
            apiController.createIndex(stockNumber, stockPickingMethod, indexTotalValue);
            return ResponseEntity.status(HttpStatus.CREATED).body("Index Created");
    	} catch (customExceptions.IndexAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Index Already Exists");
        } catch (customExceptions.ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Exception");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    
    @GetMapping("/api/deleteIndex")
    public void deleteIndex(String indexName) throws Exception {
        apiController.deleteIndex(indexName);
    }
    
    @PostMapping("/api/addStockToIndex")
    public ResponseEntity<String> addStockToIndex(@RequestBody Stock stockToAdd, @RequestParam String indexName) {
        try {
            apiController.addStockToIndex(stockToAdd, indexName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Stock added to index.");
        } catch (customExceptions.IndexNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Index Not Found");
        } catch (customExceptions.StockAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Index Already Exists");
        } catch (customExceptions.ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Exception");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    
    @PostMapping("/api/deleteStockFromIndex")
    public ResponseEntity<String> deleteStockFromIndex(@RequestBody Stock stockToDelete, @RequestParam String indexName) throws Exception {
    	try {
    	apiController.deleteStockFromIndex(stockToDelete, indexName);
    	return ResponseEntity.status(HttpStatus.CREATED).body("Stock added to index.");
	    } catch (customExceptions.IndexNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Index not found");
	    } catch (customExceptions.StockNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Stock Not Found");
	    } catch (customExceptions.ValidationException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Exception");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
	    }
    }
    
    @PostMapping("/api/adjustIndexForDividend")
    public IndexOperations adjustIndexForDividend(@RequestParam String indexName) throws Exception {
    	return apiController.adjustIndexForDividend(indexName);
    }
    
    @GetMapping("/api/indexesStateAll")
    public void computeAllIndexLevel() throws Exception {
    	apiController.computeAllIndexLevel();
    }
}