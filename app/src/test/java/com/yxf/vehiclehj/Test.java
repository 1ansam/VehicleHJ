package com.yxf.vehiclehj;

/**
 * author:yxf
 * time:2023/2/23
 */

public class Test {
    @org.junit.Test
    public void test(){
        boolean a = true;
        boolean b = true;
        System.out.println(System.currentTimeMillis());
        System.out.println(a | b);
        System.out.println(System.currentTimeMillis());
        System.out.println(a || b);
        System.out.println(System.currentTimeMillis());
    }
}
