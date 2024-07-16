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
class APITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAPIData() throws Exception {
        mockMvc.perform(get("/api/data").param("whatToPrint", "API Passage is Working Correctly!"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testGetStock() throws Exception {
        mockMvc.perform(get("/api/stock"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
//    @Test
//    public void createIndex() throws Exception {
//     mockMvc.perform(get("/api/createIndex").param("stockNumber", String.valueOf(75))
//       								 	   .param("stockPickingMethod", "LowS&P500")
//       								 	   .param("indexTotalValue", String.valueOf(10000)))
//               .andExpect(MockMvcResultMatchers.status().isOk());
//    }
    
//    @Test
//    public void deleteIndex() throws Exception {
//        mockMvc.perform(get("/api/deleteIndex").param("indexName", "TopS&P500_200"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
    
    // Test add stock to Index
    @InjectMocks
    private RestService restService;
    @InjectMocks
    private DataClass data;
    
//    @Test
//    public void addStockToIndex() throws Exception {
//        // Create Stock with the desired Values
//    	
//    	Stock stockToAdd = data.getStockFromJsonDataByIndexName("AMZN");
//        // get the Index Name
//        String indexName = "LowS&P500_75";
//
//        // Convert the Object just created in JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        String stockToAddJson = objectMapper.writeValueAsString(stockToAdd);
//
//        // Test the API call
//        mockMvc.perform(post("/api/addStockToIndex")
//                .param("indexName", indexName)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(stockToAddJson))
//                .andExpect(MockMvcResultMatchers.status().isOk());
// 
//    }
    

    @Test
    public void computeAllIndexLevel() throws Exception {
        mockMvc.perform(get("/api/indexesStateAll"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}