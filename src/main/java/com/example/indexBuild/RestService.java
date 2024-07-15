// RestService is where the APIs Endpoints are stored and ready to be used

package com.example.indexBuild;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Objects.Index;
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
    public Index createIndex(int stockNumber, String stockPickingMethod, long indexTotalValue) throws Exception {
        return apiController.createIndex(stockNumber, stockPickingMethod, indexTotalValue);
    }
    
    @GetMapping("/api/deleteIndex")
    public void deleteIndex(String indexName) throws Exception {
        apiController.deleteIndex(indexName);
    }
    
    @PostMapping("/api/addStockToIndex")
    public void addStockToIndex(@RequestBody Stock stockToAdd, @RequestParam String indexName) throws Exception {
    	apiController.addStockToIndex(stockToAdd, indexName);
    }
    
    @PostMapping("/api/deleteStockFromIndex")
    public void deleteStockFromIndex(@RequestBody Stock stockToDelete, @RequestParam String indexName) throws Exception {
    	apiController.deleteStockFromIndex(stockToDelete, indexName);
    }
}