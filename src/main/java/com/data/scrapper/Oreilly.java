package com.data.scrapper;

import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class Oreilly {
    //private static final String URL = "https://www.oreilly.com/member/auth/login/";
    private static final String URL = "https://www.oreilly.com/member/auth/login/";

    public static void main(String[] args) throws IOException {
        var map = new HashMap<String, String>();
        map.put("email", "v.morigadi@gmail.com");
        map.put("password", "Oreilly@2021");
        //Connection connection = Jsoup.connect(URL)
        Document doc = Jsoup.connect(URL)
                //.method(Connection.Method.POST)
                //.header("content-type","application/json")
                .header("Content-Type","application/json")
                .referrer("https://www.oreilly.com/member/login")
                .requestBody(new Gson().toJson(map)).post();
                //.data("data-raw", "{\"email\":\"v.morigadi@gmail.com\",\"password\":\"Oreilly@2021\"}")
                //.data("--data-raw", "{\"email\":\"v.morigadi@gmail.com\",\"password\":\"Oreilly@2021\"}");
        //System.out.println(new Gson().toJson(connection.request()));
        //Document doc = connection.get();
        System.out.println(doc.text());

    }
}
/*
    curl 'https://www.oreilly.com/member/auth/corporate/lookup/' \
            -H 'authority: www.oreilly.com' \
            -H 'sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90"' \
            -H 'sec-ch-ua-mobile: ?0' \
            -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36' \
            -H 'content-type: application/json' \
            -H 'accept: **' \
            -H 'origin: https://www.oreilly.com' \
            -H 'sec-fetch-site: same-origin' \
            -H 'sec-fetch-mode: cors' \
            -H 'sec-fetch-dest: empty' \
            -H 'referer: https://www.oreilly.com/member/login' \
            -H 'accept-language: en-IN,en;q=0.9,hi-IN;q=0.8,hi;q=0.7,te;q=0.6,en-GB;q=0.5,en-US;q=0.4' \
            -H 'cookie: _vwo_uuid_v2=D37DDF85074CE09E2E569641B9A7CC96C|24860fb0b225aefe230787a4d2420007; _gcl_au=1.1.1250759996.1621585673; DriftPlaybook=B; _gid=GA1.2.1696832655.1621585673; _vis_opt_s=1%7C; _vis_opt_test_cookie=1; _mkto_trk=id:107-FMS-070&token:_mch-oreilly.com-1621585673433-60440; _vwo_uuid=D37DDF85074CE09E2E569641B9A7CC96C; _vwo_ds=3%241621585673%3A24.22045521%3A%3A; drift_campaign_refresh=8c6432fe-1cfa-403a-a54e-216337cb772a; kampyle_userid=85a0-7b1e-b7fc-123d-2a4f-b38f-ae6d-7651; cd_user_id=1798e09c1164f-0dfe23a0fbdd96-37607201-13c680-1798e09c117787; drift_aid=1c21be39-14d8-43d5-9098-320a88da6684; driftt_aid=1c21be39-14d8-43d5-9098-320a88da6684; akaalb_OReillyALB=~op=www_oreilly_com_GCP_ALB:dc3_gcp|www_oreilly_com_ALB_90_SEB:dc1_sebastopol|~rv=92~m=dc3_gcp:0|dc1_sebastopol:0|~os=ea59de9527866dfb0dcb25ebf250c4a3~id=caab2f47a5faa643747d4a4d5770656b; _vwo_sn=0%3A4; csrftoken=TFHDrLdxRdaf8Rhrw6Op2oh6IhQPiwWvn56fSM0AoXd7M8xK21jlALMVVGdTu4Js; _gat_UA-112091926-1=1; _gat_UA-112091926-16=1; _ga_4WZYL59WMV=GS1.1.1621585684.1.1.1621585966.60; _uetsid=6fe51e30ba0e11eb8049e10e1ed79a93; _uetvid=6fe533a0ba0e11ebae31b58c8c08dee4; _ga=GA1.2.938732094.1621585673; kampyleUserSession=1621585969192; kampyleUserSessionsCount=5; kampyleSessionPageCounter=1; _ga_092EL089CH=GS1.1.1621585673.1.1.1621585969.58; _dd_s=logs=1&id=1291574c-c535-4ba9-a6c6-0a7b804aec43&created=1621585684436&expire=1621586891167' \
            --data-raw '{"domain":"gmail.com"}' \
            --compressed ;
    curl 'https://browser-http-intake.logs.datadoghq.com/v1/input/pub8399c9005ebe7454be1698e2aee94fba?ddsource=browser&ddtags=sdk_version%3A2.10.0' \
            -H 'authority: browser-http-intake.logs.datadoghq.com' \
            -H 'sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90"' \
            -H 'sec-ch-ua-mobile: ?0' \
            -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36' \
            -H 'content-type: text/plain;charset=UTF-8' \
            -H 'accept: *' \
            -H 'origin: https://www.oreilly.com' \
            -H 'sec-fetch-site: cross-site' \
            -H 'sec-fetch-mode: no-cors' \
            -H 'sec-fetch-dest: empty' \
            -H 'referer: https://www.oreilly.com/' \
            -H 'accept-language: en-IN,en;q=0.9,hi-IN;q=0.8,hi;q=0.7,te;q=0.6,en-GB;q=0.5,en-US;q=0.4' \
            --data-raw '{"service":"groot_frontend_ds-browser","session_id":"1291574c-c535-4ba9-a6c6-0a7b804aec43","date":1621585967764,"view":{"referrer":"https://learning.oreilly.com/","url":"https://www.oreilly.com/member/login"},"message":"XHR error GET https://107-fms-070.mktoresp.com/webevents/visitWebPage?_mchNc=1621585967764&_mchCn=&_mchId=107-FMS-070&_mchTk=_mch-oreilly.com-1621585673433-60440&_mchHo=www.oreilly.com&_mchPo=&_mchRu=%2Fmember%2Flogout%2F&_mchPc=https%3A&_mchVr=159&_mchEcid=&_mchHa=&_mchRe=https%3A%2F%2Flearning.oreilly.com%2F&_mchQp=partial%3Dtrue","status":"error","env":"production","error":{"origin":"network","stack":"Failed to load"},"http":{"method":"GET","status_code":0,"url":"https://107-fms-070.mktoresp.com/webevents/visitWebPage?_mchNc=1621585967764&_mchCn=&_mchId=107-FMS-070&_mchTk=_mch-oreilly.com-1621585673433-60440&_mchHo=www.oreilly.com&_mchPo=&_mchRu=%2Fmember%2Flogout%2F&_mchPc=https%3A&_mchVr=159&_mchEcid=&_mchHa=&_mchRe=https%3A%2F%2Flearning.oreilly.com%2F&_mchQp=partial%3Dtrue"}}' \
            --compressed ;
    curl 'https://www.oreilly.com/member/auth/login/' \
            -H 'authority: www.oreilly.com' \
            -H 'sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90"' \
            -H 'sec-ch-ua-mobile: ?0' \
            -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36' \
            -H 'content-type: application/json' \
            -H 'accept: **' \
            -H 'origin: https://www.oreilly.com' \
            -H 'sec-fetch-site: same-origin' \
            -H 'sec-fetch-mode: cors' \
            -H 'sec-fetch-dest: empty' \
            -H 'referer: https://www.oreilly.com/member/login' \
            -H 'accept-language: en-IN,en;q=0.9,hi-IN;q=0.8,hi;q=0.7,te;q=0.6,en-GB;q=0.5,en-US;q=0.4' \
            -H 'cookie: _vwo_uuid_v2=D37DDF85074CE09E2E569641B9A7CC96C|24860fb0b225aefe230787a4d2420007; _gcl_au=1.1.1250759996.1621585673; DriftPlaybook=B; _gid=GA1.2.1696832655.1621585673; _vis_opt_s=1%7C; _vis_opt_test_cookie=1; _mkto_trk=id:107-FMS-070&token:_mch-oreilly.com-1621585673433-60440; _vwo_uuid=D37DDF85074CE09E2E569641B9A7CC96C; _vwo_ds=3%241621585673%3A24.22045521%3A%3A; drift_campaign_refresh=8c6432fe-1cfa-403a-a54e-216337cb772a; kampyle_userid=85a0-7b1e-b7fc-123d-2a4f-b38f-ae6d-7651; cd_user_id=1798e09c1164f-0dfe23a0fbdd96-37607201-13c680-1798e09c117787; drift_aid=1c21be39-14d8-43d5-9098-320a88da6684; driftt_aid=1c21be39-14d8-43d5-9098-320a88da6684; akaalb_OReillyALB=~op=www_oreilly_com_GCP_ALB:dc3_gcp|www_oreilly_com_ALB_90_SEB:dc1_sebastopol|~rv=92~m=dc3_gcp:0|dc1_sebastopol:0|~os=ea59de9527866dfb0dcb25ebf250c4a3~id=caab2f47a5faa643747d4a4d5770656b; _vwo_sn=0%3A4; csrftoken=TFHDrLdxRdaf8Rhrw6Op2oh6IhQPiwWvn56fSM0AoXd7M8xK21jlALMVVGdTu4Js; _ga_4WZYL59WMV=GS1.1.1621585684.1.1.1621585966.60; _uetsid=6fe51e30ba0e11eb8049e10e1ed79a93; _uetvid=6fe533a0ba0e11ebae31b58c8c08dee4; _ga=GA1.2.938732094.1621585673; kampyleUserSession=1621585969192; kampyleUserSessionsCount=5; kampyleSessionPageCounter=1; _ga_092EL089CH=GS1.1.1621585673.1.1.1621585969.58; _dd_s=logs=1&id=1291574c-c535-4ba9-a6c6-0a7b804aec43&created=1621585684436&expire=1621586943672' \
            --data-raw '{"email":"v.morigadi@gmail.com","password":"Oreilly@202"}' \
            --compressed*/

