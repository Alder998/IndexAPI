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

import Objects.Index;
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
    public Index createIndex(int stockNumber, String stockPickingMethod, long indexTotalValue) {
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
    public void addStockToIndex(@RequestBody Stock stockToAdd, @RequestParam String indexName) {
        try {
            data.addStockToIndex(stockToAdd, indexName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // API Add Stock to existing Index
    public void deleteStockFromIndex(@RequestBody Stock stockToDelete, @RequestParam String indexName) {
        try {
            data.deleteStockFromIndex(stockToDelete, indexName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}