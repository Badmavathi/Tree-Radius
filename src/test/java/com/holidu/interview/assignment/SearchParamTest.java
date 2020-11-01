package com.holidu.interview.assignment;

import com.holidu.interview.assignment.model.SearchParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchParamTest {

    @Test
    public void testSearchParamVariables(){
        double x_coord = 56789;
        double y_coord = 12345;
        double radius = 1000;
        String common_name = "maple";

        SearchParam searchParam = new SearchParam();
        searchParam.setX_coord(x_coord);
        searchParam.setY_coord(y_coord);
        searchParam.setRadius(radius);
        searchParam.setCommon_name(common_name);

        assert x_coord == searchParam.getX_coord();
        assert y_coord == searchParam.getY_coord();
        assert radius == searchParam.getRadius();
        assert common_name == searchParam.getCommon_name();
    }

    @Test
    public void testSearchParamToString(){
        double x_coord = 56789;
        double y_coord = 12345;
        double radius = 1000;
        String expected_string = "SearchParam [x_coord=56789.0, y_coord=12345.0, radius=1000.0, common_name=null]";

        SearchParam searchParam = new SearchParam();
        searchParam.setX_coord(x_coord);
        searchParam.setY_coord(y_coord);
        searchParam.setRadius(radius);

        assert expected_string.equals(searchParam.toString());
    }
}
