package com.holidu.interview.assignment;

import com.holidu.interview.assignment.request.HttpProxy;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        assert testInstance.getData(new HttpGet()) == null;
    }

    @Test
    public void testGetDataThrowsIOException()  throws  Exception{
        IOException ioException = Mockito.mock(IOException.class);
        HttpProxy testInstance = new HttpProxy(httpClient);
        StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 4,0 ), 200, "OK");
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getEntity()).thenReturn(null);
        Mockito.when(httpClient.execute(any(HttpGet.class))).thenThrow(ioException);

        assert testInstance.getData(new HttpGet()) == null;
        verify(ioException, times(1)).printStackTrace();
    }

}
