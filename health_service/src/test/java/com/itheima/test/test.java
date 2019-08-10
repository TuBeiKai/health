package com.itheima.test;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class test {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderDate","2019-06-01");
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date orderDate = simpleDateFormat.parse(map.get("orderDate"));
            System.out.println(orderDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
