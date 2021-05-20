package com.xWash.entity;

import org.junit.Before;
import org.junit.Test;

public class QueryResultTest {
    QueryResult rs;
    @Before
    public void init(){
        rs = new QueryResult();
        rs.setAll("name",MStatus.UNKNOWN,2,"a test");
    }
    @Test
    public void toStringTest(){
        System.out.println("Json");
        System.out.println(rs.toJson());
        System.out.println("String");
        System.out.println(rs.toString());
    }
}
