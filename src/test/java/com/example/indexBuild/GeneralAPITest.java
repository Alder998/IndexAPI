package com.example.indexBuild;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import Objects.Stock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.xml.crypto.Data;

@SpringBootTest
@AutoConfigureMockMvc
class GeneralAPITest {
	
	@InjectMocks
	private APIRestEndPoints restService;
	@InjectMocks
	private DataClass data;
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void createIndex() throws Exception {
//     mockMvc.perform(get("/api/createIndex").param("stockNumber", String.valueOf(200))
//       								 	   .param("stockPickingMethod", "LowS&P500")
//       								 	   .param("indexTotalValue", String.valueOf(10000)))
//               .andExpect(MockMvcResultMatchers.status().isCreated());
//    }
//    
  // Test add stock to Index
//  @Test
//  public void addStockToIndex() throws Exception {
//      // Create Stock with the desired Values
//  	
//  	Stock stockToAdd = data.getStockFromJsonDataByStockName("AMZN");
//      // get the Index Name
//      String indexName = "LowS&P500_200";
//
//      // Convert the Object just created in JSON
//      ObjectMapper objectMapper = new ObjectMapper();
//      String stockToAddJson = objectMapper.writeValueAsString(stockToAdd);
//
//      // Test the API call
//      mockMvc.perform(post("/api/addStockToIndex")
//              .param("indexName", indexName)
//              .contentType(MediaType.APPLICATION_JSON)
//              .content(stockToAddJson))
//              .andExpect(MockMvcResultMatchers.status().isCreated());
//
//  	}
  
//  	  // Test delete stock to Index
//	  @Test
//	  public void deleteStockFromIndex() throws Exception {
//	      // Create Stock with the desired Values
//	  	
//	  	Stock stockToAdd = data.getStockFromJsonDataByStockName("AMZN");
//	      // get the Index Name
//	      String indexName = "LowS&P500_200";
//	
//	      // Convert the Object just created in JSON
//	      ObjectMapper objectMapper = new ObjectMapper();
//	      String stockToAddJson = objectMapper.writeValueAsString(stockToAdd);
//	
//	      // Test the API call
//	      mockMvc.perform(post("/api/deleteStockFromIndex")
//	              .param("indexName", indexName)
//	              .contentType(MediaType.APPLICATION_JSON)
//	              .content(stockToAddJson))
//	              .andExpect(MockMvcResultMatchers.status().isCreated());
//	
//	  }
//	  
//    @Test
//    public void deleteIndex() throws Exception {
//        mockMvc.perform(get("/api/deleteIndex").param("indexName", "LowS&P500_200"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
	  
  	  // Test for Dividend Adjustment
//	  @Test
//	  public void adjustIndexForDividend() throws Exception {
//	      // Create Stock with the desired Values
//	  	
//	      // get the Index Name
//	      String indexName = "LowS&P500_75";
//	
//	      // Test the API call
//	      mockMvc.perform(post("/api/adjustIndexForDividend")
//	              .param("indexName", indexName)
//	              .contentType(MediaType.APPLICATION_JSON))
//	              .andExpect(MockMvcResultMatchers.status().isCreated());
//	  }
//	  
	    @Test
	    public void computeAllIndexLevel() throws Exception {
	        mockMvc.perform(get("/api/indexesStateAll"))
	                .andExpect(MockMvcResultMatchers.status().isOk());
	    }
    
}