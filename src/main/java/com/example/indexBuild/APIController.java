// The Controller class has the aim of Managing the transactions

package com.example.indexBuild;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.indexBuild.customExceptions.IndexAlreadyExistsException;
import com.example.indexBuild.customExceptions.IndexNotFoundException;
import com.example.indexBuild.customExceptions.StockAlreadyExistsException;
import com.example.indexBuild.customExceptions.StockNotFoundException;
import com.example.indexBuild.customExceptions.ValidationException;

import Objects.Index;
import Objects.IndexOperations;
import Objects.Stock;

@Controller
public class APIController {

    @Autowired
    private DataClass data;

    // TODO: Delete
    @Transactional
    public String processAPIData(String whatToPrint) throws Exception {
        return data.getAPIData(whatToPrint);
    }
    
    public ArrayList<Stock> getStocks() {
        try {
            return data.getStocksFromJsonData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // API Create Index
    public Index createIndex(int stockNumber, String stockPickingMethod, long indexTotalValue) throws IndexAlreadyExistsException, ValidationException {
        try {
            return data.createIndex(stockNumber, stockPickingMethod, indexTotalValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // API Delete Index
    public void deleteIndex(String indexName) {
        try {
            data.deleteIndex(indexName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // API Add Stock to existing Index
    public IndexOperations addStockToIndex(@RequestBody Stock stockToAdd, @RequestParam String indexName) throws IndexNotFoundException, StockAlreadyExistsException, ValidationException {
        try {
        	return data.addStockToIndex(stockToAdd, indexName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // API Add Stock to existing Index
    public IndexOperations deleteStockFromIndex(@RequestBody Stock stockToDelete, @RequestParam String indexName) throws IndexNotFoundException, StockNotFoundException, ValidationException {
        try {
            return data.deleteStockFromIndex(stockToDelete, indexName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // API AdjustForDividends
    public IndexOperations adjustIndexForDividend(@RequestParam String indexName) throws StockNotFoundException, ValidationException {
        try {
            return data.adjustIndexForDividend(indexName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // API Add Stock to existing Index
    public void computeAllIndexLevel() {
        try {
            data.computeAllIndexLevel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}