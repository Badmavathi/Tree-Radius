package com.holidu.interview.assignment;


import com.holidu.interview.assignment.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTest {
    Utils utilsObj = new Utils();

    @Test
    public void testIsWithinRadiusTrue(){
        assert utilsObj.isWithinRadius(913368.0, 124270.0, 914129.1996, 123890.4885, 3200.0);
    }

    @Test
    public void testIsWithinRadiusFalse(){
        assert !utilsObj.isWithinRadius(124270.0, 124970.0, 914129.1996, 123890.4885, 3200.0);
    }

    @Test
    public void testGetHttpProxyInstance(){
        assert utilsObj.getHttpProxyInstance() != null;
    }
}
