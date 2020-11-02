package com.holidu.interview.assignment;

import com.holidu.interview.assignment.request.HttpProxy;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpProxyTest {

    @MockBean
    CloseableHttpClient httpClient;
    @MockBean
    CloseableHttpResponse httpResponse;

    @Test
    public void testGetDataThrowsRuntimeException()  throws  Exception{
        HttpProxy testInstance = new HttpProxy(httpClient);
        StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 4,0 ), 500, "SERVER ERROR");
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);

        assertThrows(RuntimeException.class, ()->{
            testInstance.getData(new HttpGet());
        });
    }

    @Test
    public void testGetDataReturnsEmptyEntity()  throws  Exception{
        HttpProxy testInstance = new HttpProxy(httpClient);
        StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 4,0 ), 200, "OK");
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getEntity()).thenReturn(null);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);

        assert testInstance.getData(new HttpGet()).length() == 0;
    }

    @Test
    public void testGetDataThrowsIOException()  throws  Exception{
        IOException ioException = Mockito.mock(IOException.class);
        HttpProxy testInstance = new HttpProxy(httpClient);
        StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 4,0 ), 200, "OK");
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getEntity()).thenReturn(null);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenThrow(ioException);

        assert testInstance.getData(new HttpGet()).length() == 0;
        verify(ioException, times(1)).printStackTrace();
    }

    @Test
    public void testGetDataSuccess()  throws  Exception{
        IOException ioException = Mockito.mock(IOException.class);
        HttpProxy testInstance = new HttpProxy(httpClient);
        StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 4,0 ), 200, "OK");
        HttpEntity entity = mock(BasicHttpEntity.class);
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
        InputStream stream = new ByteArrayInputStream(jsonString.getBytes());
        Mockito.when(entity.getContent()).thenReturn(stream);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getEntity()).thenReturn(entity);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);

        assert testInstance.getData(new HttpGet()).length() == 2;
        verify(ioException, times(0)).printStackTrace();
    }

}
