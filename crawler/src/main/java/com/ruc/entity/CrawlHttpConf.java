package com.ruc.entity;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CrawlHttpConf
{
	private static Map<String,String> DEFAULT_HEADERS;
	
	static{
		DEFAULT_HEADERS = new HashMap<>();
		DEFAULT_HEADERS.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		DEFAULT_HEADERS.put("connection", "Keep-Alive");
		DEFAULT_HEADERS.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:75.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//		DEFAULT_HEADERS = new HashMap<>();
//		DEFAULT_HEADERS.put("accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		DEFAULT_HEADERS.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//		DEFAULT_HEADERS.put("Accept-Encoding", "gzip, deflate");
//		DEFAULT_HEADERS.put("Cookie", "");
//		DEFAULT_HEADERS.put("Host", "");
//		DEFAULT_HEADERS.put("refer", "");
//		//DEFAULT_HEADERS.put("Cookie", "");
//		DEFAULT_HEADERS.put("connection", "Keep-Alive");
//		DEFAULT_HEADERS.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:75.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		//DEFAULT_HEADERS.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
	
	}
	
	public enum	HttpMethod{
		GET,
		POST,
		OPTIONS,
		PUT,
		CONNECTION;
	}
	
	@Getter
	@Setter
	private HttpMethod method = null;
	
	public HttpMethod Method(CrawlMeta crawlMeta) {
		//System.out.println("---------------------------"+crawlMeta.getReg());
		//String Table=null;//返回插入sql语句
    	//Mysqlfinishurl match=new Mysqlfinishurl();
    	String regurl= crawlMeta.getReg();
    	switch (regurl) {
		case "https://searchworks.stanford.edu/view/.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":
			method = HttpMethod.CONNECTION;
			break; // 可选
		// 你可以有任意数量的case语句
		case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":
			method = HttpMethod.GET;
			break; // 可选
		case "https://julac.hosted.exlibrisgroup.com/primo-explore/fulldisplay?docid=TN_timaritarticles/.*":
			method = HttpMethod.GET;
			break; // 可选
			
		case "https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "https://clio.columbia.edu/catalog/.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "https://lib.mit.edu/record/cat00916a/mit.00.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "http://pi.lib.uchicago.edu/1001/cat/bib/.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "http://catalogue.libraries.london.ac.uk/record=b.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "http://morris.law.yale.edu/record=b.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
			
		case "https://newcatalog.library.cornell.edu/catalog/.*":
			method = HttpMethod.GET;
			//根据正则匹配，选择返回sql语句
			break; // 可选
		}
    	return method;
    }
	
		
	@Setter
	private Map<String,String> 	requestHeaders;
	
	@Setter
	private Map<String,Object> requestParams;
	
	@Getter
	@Setter
	private String code = "UTF-8";
	
	
	public Map<String,String> getRequestHeaders(){
		return requestHeaders == null ? DEFAULT_HEADERS : requestHeaders;		
	}
	
	public Map<String, Object> getRequestParams() {
        return requestParams == null ? Collections.emptyMap() : requestParams;
    }


}
