package com.holidu.interview.assignment.util;

import com.holidu.interview.assignment.request.HttpProxy;
import org.apache.http.impl.client.HttpClientBuilder;

public class Utils {
    public boolean isWithinRadius(double x, double y, double targetX, double targetY, double radius) {
        return Math.sqrt(Math.pow((targetX - x), 2) + Math.pow((targetY - y), 2)) <= radius;
    }

    public HttpProxy getHttpProxyInstance(){
        return new HttpProxy(HttpClientBuilder.create().build());
    }

}
