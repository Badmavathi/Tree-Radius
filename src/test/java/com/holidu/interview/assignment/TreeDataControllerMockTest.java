package com.holidu.interview.assignment;

import com.holidu.interview.assignment.controller.TreeDataController;
import com.holidu.interview.assignment.model.SearchParam;
import com.holidu.interview.assignment.service.TreeDataService;
import com.holidu.interview.assignment.util.Utils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TreeDataControllerMockTest {
    private MockMvc mockMvc;

    @Mock
    TreeDataService treeDataService;

    public void setTreeDataService(TreeDataService treeDataService){
        this.treeDataService = treeDataService;
    }

    @InjectMocks
    TreeDataController treeDataController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(treeDataController).build();
    }

    @Test
    public void testFetchingTreesThrowsException() throws Exception{
        JSONObject jsonObj = new JSONObject();
        // x_coord, y_coord, radius;
        jsonObj.put("x_coord", 913368);
        jsonObj.put("y_coord", 124270);
        jsonObj.put("radius", 1000);
        jsonObj.put("common_name", "");

        URISyntaxException exceptionToThrow = mock(URISyntaxException.class);
        Mockito.when(treeDataService.fetchTreeData(any(SearchParam.class), any(Utils.class))).thenThrow(exceptionToThrow);
        this.mockMvc.perform(post("/trees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObj.toString()))
                .andExpect(status().is(500));
        verify(exceptionToThrow, times(1)).printStackTrace();
    }

    @Test
    public void testFetchingTreesSuccess() throws Exception {
        JSONObject jsonObj = new JSONObject();
        // x_coord, y_coord, radius;
        jsonObj.put("x_coord", 913368);
        jsonObj.put("y_coord", 124270);
        jsonObj.put("radius", 1000);
        jsonObj.put("common_name", "");

        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("maple",30);
        returnMap.put("sweetgum", 30);

        Mockito.when(treeDataService.fetchTreeData(any(SearchParam.class), any(Utils.class))).thenReturn(returnMap);
        this.mockMvc.perform(post("/trees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObj.toString()))
                .andExpect(content().string("{\"sweetgum\":30,\"maple\":30}"))
                .andExpect(status().is(200));
    }

    @Test
    public void testFetchingTreesSuccessForGetRequest() throws Exception {
        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("maple",30);
        returnMap.put("sweetgum", 30);

        Mockito.when(treeDataService.fetchTreeData(any(SearchParam.class), any(Utils.class))).thenReturn(returnMap);
        this.mockMvc.perform(get("/trees?x_coord=913368&y_coord=124270&radius=1000&common_name=maple"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"sweetgum\":30,\"maple\":30}"));
    }
}
