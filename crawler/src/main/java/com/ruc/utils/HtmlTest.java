package com.ruc.utils;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.jsoup.Connection;
import org.jsoup.Connection.Base;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class HtmlTest {
	public static void main(String[] args) throws Exception{
		String url="http://solo.bodleian.ox.ac.uk/primo-explore/fulldisplay?docid=TN_oupqrefacref-9780199568758-e-0302&context=PC&vid=SOLO&lang=en_US&search_scope=LSCOP_ALL&adaptor=primo_central_multiple_fe&tab=local&query=any,contains,CC&offset=0";
		Connection con = Jsoup.connect(url);
		con.header("Accept", "application/json, text/plain, */*");
		con.header("Accept-Encoding", "gzip, deflate");
		con.header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		con.header("Content-Type", "application/json");
		con.header("Accept", "text/html, application/xhtml+xml, */*"); 
        con.header("Content-Type", "application/json");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
        Response resp=con.ignoreContentType(true).execute();
        //Map<String,String> cookies = resp.
        String headersOne =resp.body();
        System.out.println("所有头文件值："+headersOne);
	}

}
