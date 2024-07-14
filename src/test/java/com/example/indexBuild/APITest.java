package com.example.indexBuild;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    
    @Test
    public void createIndex() throws Exception {
        mockMvc.perform(get("/api/createIndex").param("stockNumber", String.valueOf(200))
        								 	   .param("stockPickingMethod", "TopS&P500"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void deleteIndex() throws Exception {
        mockMvc.perform(get("/api/deleteIndex").param("indexName", "TopS&P500_200"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}