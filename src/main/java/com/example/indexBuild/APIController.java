// The Controller class has the aim of Managing the transactions

package com.example.indexBuild;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

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
    public Index createIndex(int stockNumber, String stockPickingMethod) {
        try {
            return data.createIndex(stockNumber, stockPickingMethod);
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
}