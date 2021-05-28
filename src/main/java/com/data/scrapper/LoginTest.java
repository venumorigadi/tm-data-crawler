package com.data.scrapper;

import com.data.scrapper.utils.Constants;
import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class LoginTest {

    private final static String URL = " https://www.oreilly.com/member/auth/login/";
    public static void main(String[] args) throws IOException {
        var map = new HashMap<String, String>();
        map.put("email", "v.morigadi@gmail.com");
        map.put("password", "Oreilly@2021");

        Document doc = Jsoup.connect(URL)
                .userAgent(Constants.USER_AGENT)
                .header("Content-Type", "application/json")
                //.data("email", "v.morigadi@gmail.com")
                //.data("password", "Oreilly@2021")
                //.data(map)
                //.data("data", new Gson().toJson(map))
                .method(Connection.Method.POST)
                .post();

        System.out.println(doc.text());

    }

}
