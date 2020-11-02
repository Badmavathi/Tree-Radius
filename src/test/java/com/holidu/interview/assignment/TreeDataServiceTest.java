package com.holidu.interview.assignment;

import com.holidu.interview.assignment.model.SearchParam;
import com.holidu.interview.assignment.request.HttpProxy;
import com.holidu.interview.assignment.service.TreeDataService;
import com.holidu.interview.assignment.util.Utils;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeDataServiceTest {

    TreeDataService serviceToBeTested = new TreeDataService();

    @Test
    public void testFetchTreeDataThrowsException() throws URISyntaxException {
        SearchParam searchParam = new SearchParam();
        searchParam.setX_coord(56789);
        searchParam.setY_coord(12345);
        searchParam.setRadius(1000);
        searchParam.setCommon_name("maple");

        Utils utilMock = Mockito.mock(Utils.class);
        HttpProxy httpProxyMock = Mockito.mock(HttpProxy.class);
        JSONArray jsonArrayMock = Mockito.mock(JSONArray.class);
        JSONException jsonExceptionMock = Mockito.mock(JSONException.class);

        Mockito.when(utilMock.getHttpProxyInstance()).thenReturn(httpProxyMock);
        Mockito.when(jsonArrayMock.length()).thenReturn(3);
        Mockito.when(jsonArrayMock.getJSONObject(anyInt())).thenThrow(jsonExceptionMock);
        Mockito.when(httpProxyMock.getData(any(HttpGet.class))).thenReturn(jsonArrayMock);

        Map<String, Integer> result = serviceToBeTested.fetchTreeData(searchParam,utilMock);
        assert result.size() == 0;
        verify(jsonExceptionMock, times(1)).printStackTrace();
    }

    @Test
    public void testFetchTreeDataSuccess() throws URISyntaxException {
        SearchParam searchParam = new SearchParam();
        searchParam.setX_coord(913368);
        searchParam.setY_coord(124270);
        searchParam.setRadius(3200);
        searchParam.setCommon_name("");

        Utils utilMock = Mockito.mock(Utils.class);
        HttpProxy httpProxyMock = Mockito.mock(HttpProxy.class);

        String jsonString = "[    {\n" +
                "        \"spc_common\": \"pin oak\",\n" +
                "        \"x_sp\": \"914129.1996\",\n" +
                "        \"y_sp\": \"123890.4885\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"spc_common\": \"Norway maple\",\n" +
                "        \"x_sp\": \"914294.322\",\n" +
                "        \"y_sp\": \"123682.5171\"\n" +
                "    }]";
        JSONArray jsonArray = new JSONArray(jsonString);
        Mockito.when(utilMock.isWithinRadius(anyDouble(),anyDouble(),anyDouble(),anyDouble(),anyDouble())).thenReturn(true);
        Mockito.when(httpProxyMock.getData(any(HttpGet.class))).thenReturn(jsonArray);
        Mockito.when(utilMock.getHttpProxyInstance()).thenReturn(httpProxyMock);

        Map<String, Integer> result = serviceToBeTested.fetchTreeData(searchParam,utilMock);
        assert result.size() == 2;
    }

    @Test
    public void testFetchTreeDataIrregularData() throws URISyntaxException {
        SearchParam searchParam = new SearchParam();
        searchParam.setX_coord(913368);
        searchParam.setY_coord(124270);
        searchParam.setRadius(3200);
        searchParam.setCommon_name("maple");

        Utils utilMock = Mockito.mock(Utils.class);
        HttpProxy httpProxyMock = Mockito.mock(HttpProxy.class);

        String jsonString = "[    {\n" +
                "        \"spc_common\": \"\",\n" +
                "        \"x_sp\": \"914129.1996\",\n" +
                "        \"y_sp\": \"123890.4885\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"spc_common\": \"Norway maple\",\n" +
                "        \"x_sp\": \"914294.322\",\n" +
                "        \"y_sp\": \"123682.5171\"\n" +
                "    }]";
        JSONArray jsonArray = new JSONArray(jsonString);
        Mockito.when(utilMock.isWithinRadius(anyDouble(),anyDouble(),anyDouble(),anyDouble(),anyDouble())).thenReturn(true);
        Mockito.when(httpProxyMock.getData(any(HttpGet.class))).thenReturn(jsonArray);
        Mockito.when(utilMock.getHttpProxyInstance()).thenReturn(httpProxyMock);

        Map<String, Integer> result = serviceToBeTested.fetchTreeData(searchParam,utilMock);
        assert result.size() == 1;
    }

    @Test
    public void testFetchTreeDataOutsideRadius() throws URISyntaxException {
        SearchParam searchParam = new SearchParam();
        searchParam.setX_coord(913368);
        searchParam.setY_coord(124270);
        searchParam.setRadius(3200);
        searchParam.setCommon_name(null);

        Utils utilMock = Mockito.mock(Utils.class);
        HttpProxy httpProxyMock = Mockito.mock(HttpProxy.class);

        String jsonString = "[    {\n" +
                "        \"spc_common\": \"\",\n" +
                "        \"x_sp\": \"914129.1996\",\n" +
                "        \"y_sp\": \"123890.4885\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"spc_common\": \"Norway maple\",\n" +
                "        \"x_sp\": \"914294.322\",\n" +
                "        \"y_sp\": \"123682.5171\"\n" +
                "    }]";
        JSONArray jsonArray = new JSONArray(jsonString);
        Mockito.when(utilMock.isWithinRadius(anyDouble(),anyDouble(),anyDouble(),anyDouble(),anyDouble())).thenReturn(false);
        Mockito.when(httpProxyMock.getData(any(HttpGet.class))).thenReturn(jsonArray);
        Mockito.when(utilMock.getHttpProxyInstance()).thenReturn(httpProxyMock);

        Map<String, Integer> result = serviceToBeTested.fetchTreeData(searchParam,utilMock);
        assert result.size() == 0;
    }
}
