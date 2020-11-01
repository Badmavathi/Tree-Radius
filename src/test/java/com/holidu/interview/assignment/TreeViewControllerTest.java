package com.holidu.interview.assignment;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TreeViewControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFetchingTreeDataInGetRequest() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trees?x_coord=913368&y_coord=124270&radius=1000&common_name=Sophora"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchingTreeDataInGetRequestWithoutCommonName() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/trees?x_coord=913368&y_coord=124270&radius=1000&common_name="))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchingTreeDataInPostRequest() throws Exception {
        JSONObject jsonObj = new JSONObject();
        // x_coord, y_coord, radius;
        jsonObj.put("x_coord", 913368);
        jsonObj.put("y_coord", 124270);
        jsonObj.put("radius", 1000);
        jsonObj.put("common_name", "");


        this.mockMvc.perform(post("/trees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObj.toString()))
                .andExpect(status().isOk());
    }
}