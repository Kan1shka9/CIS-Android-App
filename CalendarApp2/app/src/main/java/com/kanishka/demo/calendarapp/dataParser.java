package com.kanishka.demo.calendarapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class dataParser {

    private InputStream dataInputStream;

    dataParser(InputStream inputStream) {
        dataInputStream = inputStream;
    }

    private ArrayList<String> parseData() {
        ArrayList<String> tempData = new ArrayList<>();
        String str = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
            while ((str = reader.readLine()) != null) {
                if (str.contains("*")) {
                    continue;
                } else if (str.contains("AD")) {
                    str = str.replace("AD>>:", "");
                    tempData.add(str);
                }
                System.out.println(str);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return tempData;
    }
    ArrayList<String> getData() {
        return parseData();
    }
}