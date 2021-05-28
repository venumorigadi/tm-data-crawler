package com.data.scrapper;

import com.data.scrapper.utils.Constants;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class LoginTest1 {

    public static void main(String[] args) throws IOException {
        // # Constants used in this example
        final String LOGIN_FORM_URL = "https://www.oreilly.com/member/login/";
        final String LOGIN_ACTION_URL = "https://www.oreilly.com/member/auth/login/";
        final String USERNAME = "v.morigadi@gmail.com";
        final String PASSWORD = "Oreilly@2021";

// # Go to login page and grab cookies sent by server
        Connection.Response loginForm = Jsoup.connect(LOGIN_FORM_URL)
                .method(Connection.Method.GET)
                .userAgent(Constants.USER_AGENT)
                .execute();
        Document loginDoc = loginForm.parse(); // this is the document containing response html
        HashMap<String, String> cookies = new HashMap<>(loginForm.cookies()); // save the cookies to be passed on to next request
        System.out.println("Cookie: "+ cookies);
        System.out.println("\n\n");
        //System.out.println("login Doc: "+loginDoc.html());

// # Prepare login credentials
       /* String authToken = loginDoc.select("#login > form > div:nth-child(1) > input[type=\"hidden\"]:nth-child(2)")
                .first()
                .attr("value");*/

        HashMap<String, String> formData = new HashMap<>();
        //formData.put("commit", "Sign in");
        //formData.put("utf8", "e2 9c 93");
        formData.put("email", USERNAME);
        formData.put("password", PASSWORD);
        //formData.put("authenticity_token", authToken);

// # Now send the form for login
        Connection.Response homePage = Jsoup.connect(LOGIN_ACTION_URL)
                .cookies(cookies)
                .data(formData)
                .header("Content-type", "application/json")
                .header("accept","*/*")
                .method(Connection.Method.POST)
                .userAgent(Constants.USER_AGENT)
                .execute();

        System.out.println(homePage.parse().html());

    }

}
