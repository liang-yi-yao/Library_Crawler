package com.ruc.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;


import com.ruc.entity.CrawlHttpConf;
import com.ruc.entity.CrawlMeta;

public class HttpUtils {
	
	public static boolean Authorizationtoken=true;
	
	public static String Authorizations="";
	public static int statecode=0;
	public   Response requestJson(CrawlMeta crawlMeta, CrawlHttpConf httpConf) throws Exception {
		
		return (Response) doConnection(crawlMeta, httpConf);
		
	}

	public static  HttpResponse request(CrawlMeta crawlMeta, CrawlHttpConf httpConf) throws Exception {
		//System.out.println("---------------------------"+crawlMeta);
		switch (httpConf.Method(crawlMeta)) {
		

		case GET:
			//System.out.println("---------------------------"+crawlMeta);
			return doGet(crawlMeta.getUrl(), httpConf);
		case POST:

			return doPost(crawlMeta.getUrl(), httpConf);
	
		default:
			return null;
		}
	}

	private static HttpResponse doPost(String url, CrawlHttpConf httpConf) throws Exception {

		// HttpClient httpClient = HttpClient.createDefault();
		SSLContextBuilder builder = new SSLContextBuilder();
		// 全部信任，不做身份鉴定
		builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
		HttpClient httpClient = HttpClientBuilder.create().setSslcontext(builder.build()).build();
		// .setConnectTimeout(5000)
		HttpPost httpPost = new HttpPost(url);
		// 建立一个NameValuePair数组，用于存储域传送的参数
		List<NameValuePair> params = new ArrayList<>();
		for (Map.Entry<String, Object> param : httpConf.getRequestParams().entrySet()) {
			params.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));

		}
		httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		// 设置头请求
		for (Map.Entry<String, String> head : httpConf.getRequestHeaders().entrySet()) {
			httpPost.addHeader(head.getKey(), head.getValue());
		}
		// HttpGet httpGet = new
		// HttpGet("http://127.0.0.1:9999/testHttpClientTimeOutController/socket_timeout.do");

		// 执行网络请求
		return httpClient.execute(httpPost);

	}

	private static HttpResponse doGet(String url, CrawlHttpConf httpConf) throws Exception {
		// 客户端重试自定义设置（HttpRequestRetryHandler），相当于改写DefaultHttpRequestRetryHandler
		
		  HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
		  
		  
			    public boolean retryRequest(
			            IOException exception,
			            int executionCount,
			            HttpContext context) {
			        if (executionCount > 2) {
			            // Do not retry if over max retry count
			        	System.out.println("============================================="+"Do not retry if over max retry count:"+url+"=============================================");
			            return false;
			        }else {
			        if (exception instanceof InterruptedIOException) {
			            // Timeout
			        	System.out.println("============================================="+"Timeout:"+url+"=============================================");
			            return true;
			        }
			        if (exception instanceof UnknownHostException) {
			        	System.out.println("============================================="+"Unknown host:"+url+"=============================================");
			            // Unknown host
			            return true;
			        }
			        if (exception instanceof ConnectTimeoutException) {
			        	System.out.println("============================================="+"Connection refused:"+url+"=============================================");
			            // Connection refused
			            return true;
			        }
			        if (exception instanceof SSLException) {
			            // SSL handshake exception
			        	System.out.println("============================================="+"SSL handshake exception:"+url+"=============================================");
			            return true;
			        }
			       
			        }
			        HttpClientContext clientContext = HttpClientContext.adapt(context);
			        HttpRequest request = (HttpRequest) clientContext.getRequest();
			       // String token = ((HttpRequest) request).getHeader("token");
			       // Enumeration headerNames = ((Object) request).getHeaderNames();
			        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			        if (idempotent) {
			            // Retry if the request is considered idempotent
			            return true;
			        }
			        return false;
			    }
		  
		  
		  
		  
		  /*@Override public boolean retryRequest(IOException exception, int
		  executionCount, HttpContext context) { if (executionCount > 3){
		  
		  return false; } if (exception instanceof InterruptedIOException || exception
		  instanceof NoHttpResponseException) { // Timeout or 服务端断开连接 return true; } //
		  Unknown host if (exception instanceof UnknownHostException) { return false; }
		  // SSL handshake exception if (exception instanceof SSLException) { return
		  false; }
		  
		  final HttpClientContext clientContext = HttpClientContext.adapt(context);
		  final org.apache.http.HttpRequest request = clientContext.getRequest();
		  boolean idempotent = !(request instanceof HttpEntityEnclosingRequest); if
		  (idempotent) { // Retry if the request is considered idempotent return true;
		  } return false; }*/
		  
		  };
		 
		SSLContextBuilder builder = new SSLContextBuilder();
		// 全部信任，不做身份鉴定
		builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
		// 客户端的建立（httpClient）
		HttpClient httpClient = HttpClientBuilder.create().setSslcontext(builder.build()).setRetryHandler(myRetryHandler).build();
		//HttpClient httpClient = HttpClientBuilder.create().setSslcontext(builder.build()).build();

		// 设置头参数
		StringBuilder param = new StringBuilder(url).append("?");

		for (Map.Entry<String, Object> entry : httpConf.getRequestParams().entrySet()) {
			param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		// 过滤掉最后一个无效字符，以及获取对象httpGet实例化
		HttpGet httpGet = new HttpGet(param.substring(0, param.length() - 1));
		// 设置httpGet对象参数
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
				.setConnectionRequestTimeout(10000)
				.build();
		// 把设置的参数返回httpGet
		httpGet.setConfig(requestConfig);

		// 设置请求头
		for (Map.Entry<String, String> head : httpConf.getRequestHeaders().entrySet()) {
			httpGet.addHeader(head.getKey(), head.getValue());

		}
		/*
		 * try {
		 * 
		 * // long start = 0;
		 * 
		 * // start = System.currentTimeMillis(); // httpClient.execute(httpGet); //
		 * HttpResponse response = httpClient.execute(httpGet); // long end =
		 * System.currentTimeMillis();
		 * //System.out.println("与网页握手交互花费时间为===================" + (end-start)/1000 +
		 * "秒"); } catch (SocketTimeoutException exception) {
		 * System.out.println("与网页握手超时=============================================");
		 * exception.printStackTrace(); }
		 */

		// 执行网络请求
		//System.out.println("response前前前前前前前前前前前=============================================");
		long start = 0;
		start = System.currentTimeMillis();
		//System.out.println("网址+++++++++++++++++++++"+url);
		HttpResponse response= httpClient.execute(httpGet);
		//System.out.println("response后后后后后后后后后后后后后=============================================");
		long end = System.currentTimeMillis();
		//System.out.println("获取网页花费时间为" + (end-start)/1000 + "秒");
		return response;

	}
	
	
	
	
	public String CheckAuthorization(CrawlMeta crawlMeta) {
	
		synchronized(Authorizations) {
		if (Authorizationtoken) {
			
			HttpunitTest Token=new HttpunitTest();
			Authorizations="Bearer "+Token.Token(crawlMeta);
			if(Authorizations !="") {
				Authorizationtoken=false;
			}	
		}
		
		
		}
		if(statecode == 403 && Authorizationtoken==false) {
			Authorizationtoken=true;
		}
		//HttpunitTest Token=new HttpunitTest();
		String Authorization="";	
		switch(crawlMeta.getReg()) {
		case "https://searchworks.stanford.edu/view/.*":{
			Authorization=null;
			break;
		}
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":{
			Authorization=Authorizations;
			break;
		}
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":{
			Authorization=Authorizations;
			break;
		}
		case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":{
			Authorization=null;
			break;
		}
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":{
			Authorization=Authorizations;
			break;
		}
		case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":{
			Authorization=Authorizations;
			break;
		}
		case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":{
			Authorization=Authorizations;
			break;
		}
		}
		
		return Authorization;
		
	}	
	
	

	private  Response doConnection(CrawlMeta crawlMeta, CrawlHttpConf httpConf) throws Exception {
		//CheckAuthorization(crawlMeta.getReg());
		Thread.sleep(1000);
		Response res = Jsoup.connect(crawlMeta.getUrl())
				.header("Accept", "application/json, text/plain, */*")
				.header("Accept-Encoding", "gzip, deflate")
				//.header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
				.header("Content-Type", "application/json")
				//.header("Cookie", "JSESSIONID=0078E2D48E765F0829B65D75B18859ED; UqZBpD3n3iPLdHQq9xqtuXrTXegc+YkfcdXJus-Z-FzIAB7F=v1poo+gw__LmF; TBMCookie_3555100366678004564=2825280015884150809RBi6m6X1jzLIQ3dYqNpsq2zEC8=; ___utmvm=###########; ___utmvc=navigator%3Dtrue,navigator.vendor%3D,navigator.appName%3DNetscape,navigator.plugins.length%3D%3D0%3Dfalse,navigator.platform%3DWin32,navigator.webdriver%3Dfalse,plugin_ext%3Ddll,ActiveXObject%3Dfalse,webkitURL%3Dtrue,_phantom%3Dfalse,callPhantom%3Dfalse,chrome%3Dfalse,yandex%3Dfalse,opera%3Dfalse,opr%3Dfalse,safari%3Dfalse,awesomium%3Dfalse,puffinDevice%3Dfalse,__nightmare%3Dfalse,domAutomation%3Dfalse,domAutomationController%3Dfalse,_Selenium_IDE_Recorder%3Dfalse,document.__webdriver_script_fn%3Dfalse,document.%24cdc_asdjflasutopfhvcZLmcfl_%3Dfalse,process.version%3Dfalse,navigator.cpuClass%3Dfalse,navigator.oscpu%3Dtrue,navigator.connection%3Dfalse,navigator.language%3D%3D'C'%3Dfalse,window.outerWidth%3D%3D0%3Dfalse,window.outerHeight%3D%3D0%3Dfalse,window.WebGLRenderingContext%3Dtrue,document.documentMode%3Dundefined,eval.toString().length%3D37,digest=")
				//.header("Origin", "http://idiscover.lib.cam.ac.uk")
				//.header("Host", "idiscover.lib.cam.ac.uk")
				.header("Content-Type", "application/json;charset=utf-8")
				//.header("Referer", "http://idiscover.lib.cam.ac.uk/primo-explore/fulldisplay?docid=44CAM_ALMA21393424180003606&vid=44CAM_PROD&search_scope=SCOP_BOOKS&tab=cam_lib_coll&lang=en_US&context=L")	
				//.header("Content-Type", "application/json;charset=UTF-8")	
				//.header("Content-Type", "application/json;charset=UTF-8")	
				//.header("Content-Type", "application/json;charset=UTF-8")	
				.header("Authorization",CheckAuthorization(crawlMeta))
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")
				.timeout(10000).ignoreContentType(true).execute();//.get();
			//System.out.println("response后后后后后后后后后后后后后=============================================");
		//String body = res.body();
		 //System.out.println(res+"TTTTTTTTTTTTTTTTTTTTTTTTTT");
         int p=res.statusCode();
        //System.out.println(p+"TTTTTTTTTTTTTTTTTTTTTTTTTT");
		// 执行网络请求
		return res;

	}
	
	
	

	/*
	 * HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
	 * 
	 * public boolean retryRequest( IOException exception, int executionCount,
	 * HttpContext context)
	 * 
	 * { System.out.println("我进入到这里=============================================");
	 * if (executionCount >= 5) { // Do not retry if over max retry count return
	 * false; } if (exception instanceof InterruptedIOException) { // Timeout return
	 * false; } if (exception instanceof UnknownHostException) { // Unknown host
	 * return false; } if (exception instanceof ConnectTimeoutException) { //
	 * Connection refused return false; } if (exception instanceof SSLException) {
	 * // SSL handshake exception return false; } HttpClientContext clientContext =
	 * HttpClientContext.adapt(context); HttpRequest request = (HttpRequest)
	 * clientContext.getRequest(); boolean idempotent = !(request instanceof
	 * HttpEntityEnclosingRequest); if (idempotent) { // Retry if the request is
	 * considered idempotent return true; } return false; }
	 * 
	 * }; CloseableHttpClient httpclient = HttpClients.custom()
	 * .setRetryHandler(myRetryHandler) .build();
	 */

}
