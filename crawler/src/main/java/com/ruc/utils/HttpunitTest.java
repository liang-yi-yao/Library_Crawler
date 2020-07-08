package com.ruc.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ruc.entity.CrawlMeta;


public class HttpunitTest {
	
	
	public String Token(CrawlMeta crawlMeta) {
		List<String> group = new ArrayList<String>();
	    System.setProperty("webdriver.chrome.driver","C:\\Users\\Administrator\\Desktop\\chrom\\chromedriver.exe");
	    //开启webDriver进程
	    String token="";
	    ChromeOptions chromeOptions = new ChromeOptions();
	    boolean corr=true;
//      设置为 headless 模式 （必须）
	    chromeOptions.addArguments("-headless");
	    
	    WebDriver webDriver = new ChromeDriver();
	   // webDriver.manage().window().maximize();
	    //webDriver.manage().timeouts().pageLoadTimeout(7,TimeUnit.SECONDS);
	  
	    //new StopLoadPage(webDriver,10).start();
	    //webDriver.manage().timeouts().setScriptTimeout(10,TimeUnit.SECONDS);
	    try {
			//Thread.sleep(10000);
	    	//webDriver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
	    	
	    	//webDriver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
	    	 Thread.sleep(10000);
	    	//System.out.println("wwwwwwwwwww");
			webDriver.get(Url(crawlMeta)); 
			//System.out.println("sssssssssssss");
			
			//wait.until(webDriver.findElement(By.id("b")));
			//webDriver.manage().timeouts().setScriptTimeout(10,TimeUnit.SECONDS);
	        Thread.sleep(20000);
	        Document   doc = Jsoup.parse(webDriver.getPageSource());
	        Elements linkElements=doc.select("span[class=urlToXmlPnx]");
	        for (Element e : linkElements) {
	        	System.out.println(e.attr("title"));
	        	 Pattern pattern =Pattern.compile("eyJra[a-zA-Z0-9\\.\\_\\-]+");
	        	 String pp=e.attr("title");                            
	        	 Matcher matcher=pattern.matcher(pp);
	        	 String result="";
	        	 int i=0;
	        	 while (matcher.find()) { 
	        		// System.out.println("");
	                 result = matcher.group();//只取第一组  
	                 //System.out.println("ttttttttttt"+result);
	                 //result=result.replace("[", "").replace("]", "");
	                 group.add(result);
	                 System.out.println("============================="+result);
	               //  System.out.println(result);
	                 i++;
	             }
	        	 
	        }
	        //String element1 = webDriver.findElement(By.cssSelector(".urlToXmlPnx")).toString(); 
	        //System.out.println(doc);
	        System.out.println(group);
	       
	        //webDriver.quit();
	        //webDriver.close();
	    }
	    catch(Exception e){
	       // ((JavascriptExecutor) webDriver).executeScript("window.stop();");
	    }
	    finally {
		   if(corr) {
			   System.out.println("================================================================="+"网络延迟 请继续待或重启服务"+"===============================================================");
		   }
		   //System.out.println("tttttttttttttttt");
		   webDriver.quit();
	   }
	       return  token=group.get(0);
	    
	   
	    }
	
	
	public String Url(CrawlMeta crawlMeta) {
		
		String url="";
		switch(crawlMeta.getReg()) {
		
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":{
			url="https://julac.hosted.exlibrisgroup.com/permalink/f/1iv15ah/CUHK_IZ21952295490003407";
			break;
		}
		
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":{
			url="http://find.lib.hku.hk/record=HKU_IZ21410953820003414";
			break;
		}
		case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":{
			url="http://solo.bodleian.ox.ac.uk/primo-explore/fulldisplay?docid=oxfaleph017199230&context=L&vid=SOLO&lang=en_US&search_scope=LSCOP_ALL&adaptor=Local%20Search%20Engine&tab=local&query=any,contains,NLP&offset=0";
			break;
		}
		case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":{
			url="http://id.lib.harvard.edu/alma/990001007750203941/catalog";
			break;
		}
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":{
			url="http://idiscover.lib.cam.ac.uk/permalink/f/vhqh8h/44CAM_ALMA21505786180003606";
			break;
		}
		}
		return url;
	}
	    
	}
	


