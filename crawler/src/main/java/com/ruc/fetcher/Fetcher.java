package com.ruc.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.rules.Timeout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ruc.conf.ConfigWrapper;
import com.ruc.entity.CrawlHttpConf;
import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;
import com.ruc.filter.ResultFilter;
import com.ruc.job.DefaultAbstractCrawlJob;
import com.ruc.pool.ObjectFactory;
import com.ruc.pool.SimplePool;
import com.ruc.utils.HttpUtils;
import com.ruc.utils.HttpunitTest;

//import BaseCrawlerTest.QueueCrawlerTest.QueueCrawlerJob;
import Mysql.Mysqlfinishurl;

@Slf4j
public class Fetcher {

	private int maxDepth;

	public FetchQueue fetchQueue;
	
	

	private Executor executor;

	@Setter
	private ThreadConf threadConf;

	public FetchQueue addFeed(CrawlMeta feed) {

		fetchQueue.addSeed(feed);
		
		return fetchQueue;
	}


	
	public boolean addurl(List url, CrawlMeta crawlMeta) {
			
		if (fetchQueue.addSqlurl(url, crawlMeta)) {
			
			return true;
		}
		return false;
	}

	public <T extends DefaultAbstractCrawlJob> Fetcher(Class<T> jobClz) {
		this(0, jobClz);
	}

	public <T extends DefaultAbstractCrawlJob> Fetcher(int maxDepth, Class<T> jobClz) {
		this(maxDepth, () -> {
			try {
				return jobClz.newInstance();
			} catch (Exception e) {
				log.error("create job error! e:{}", e);
				return null;
			}
		});
	}

	public <T extends DefaultAbstractCrawlJob> Fetcher(int maxDepth, ObjectFactory<T> jobFactory) {
		this.maxDepth = maxDepth;
		fetchQueue = FetchQueue.DEFAULT_INSTANCE;
		threadConf = ThreadConf.DEFAULT_CONF;
		initExecutor();
		SimplePool simplePool = new SimplePool<>(ConfigWrapper.getInstance().getConfig().getFetchQueueSize(),
				jobFactory);
		SimplePool.initInstance(simplePool);
	}

	
	public void newstart() throws Exception{
		
		
		//String url="https://searchworks.stanford.edu/view/";// 可选  斯坦福大学(静态网页)
		//String url="http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21";//剑桥大学(动态网页)
		//String url="https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=";// 可选  加州大学洛杉矶分校（静态网页）
		//String url="https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21";//香港大学（动态网页）
		String url="https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51";//香港中文大学（动态网页）
		//String url="http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph0";// 可选  牛津大学(动态网页)
		//String url="https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200";//东京大学（静态网页）
		//String url="https://clio.columbia.edu/catalog/"; // 可选  哥伦比亚大学（静态网页）
		//String url="https://lib.mit.edu/record/cat00916a/mit.00";// 可选  麻省理工大学（静态网页）
		//String url="https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=";//可选 耶鲁大学(静态网页)
		//String url="https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21";//哈佛大学（动态网页）
		//String url="http://pi.lib.uchicago.edu/1001/cat/bib/";// 可选 芝加哥大学（静态网页）
		//String url="http://catalogue.libraries.london.ac.uk/record=b";// 可选 伦敦大学（静态网页）
		//String url="http://morris.law.yale.edu/record=b";// 可选 耶鲁大学法学院（静态网页）
		//String url="https://newcatalog.library.cornell.edu/catalog/";// 可选 康奈尔大学法学院(静态网页）
		int i = new Mysqlfinishurl().RecordNum(url);
		String url1=null;
		Mysqlfinishurl mysqlfinish= new Mysqlfinishurl();
		//List extracturl= mysqlfinish.Extracturl(url);//数据库数据对比的
		String reg=mysqlfinish.Match(url);
		
		for(;i<=2000000000;i++){		
			//url1 = url + i+"0003606?vid=44CAM_PROD&lang=en_US&search_scope=SCOP_BOOKS";
			//url1 = url + i;
			switch (reg) {
			case "https://searchworks.stanford.edu/view/.*":
				url1 = url + i;
				break; // 可选  斯坦福大学
			case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":
				url1 = url + i+"0003606?vid=44CAM_PROD&lang=en_US&search_scope=SCOP_BOOKS";
				break; // 可选   剑桥大学(按json取数据)
			case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":
				url1 = url + i;
				break; // 可选  加州大学洛杉矶分校
			// 你可以有任意数量的case语句
			case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"0003414?vid=HKU&lang=en_US&search_scope=Books&adaptor=Local%20Search%20Engine&isFrbr=true";
				break; // 可选  香港大学(按json取数据)
				
			case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"0003407?vid=CUHK&lang=en_US&search_scope=Books&adaptor=Local%20Search%20Engine";
				break; // 可选  香港中文大学(按json取数据)
			
			case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"?vid=SOLO&lang=en_US&search_scope=LSCOP_OX";
				break; // 可选  牛津大学(按json取数据)
				
			case "https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"&lang=1";
				break; // 可选  东京大学
				
			case "https://clio.columbia.edu/catalog/.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i;
				break; // 可选  哥伦比亚大学
				
			case "https://lib.mit.edu/record/cat00916a/mit.00.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i;
				break; // 可选  麻省理工大学
				
			case "https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i;
				break; // 可选 耶鲁大学
				
			case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"0003941?vid=HVD2&lang=en_US&search_scope=01HVD_COURSE&adaptor=Local%20Search%20Engine";
				break; // 可选 哈佛大学(按json取数据)
				
			case "http://pi.lib.uchicago.edu/1001/cat/bib/.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i;
				break; // 可选 芝加哥大学
				
			case "http://catalogue.libraries.london.ac.uk/record=b.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"~S24";
				break; // 可选 伦敦大学
				
			case "http://morris.law.yale.edu/record=b.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i+"~S24";
				break; // 可选 耶鲁大学法学院

			case "https://newcatalog.library.cornell.edu/catalog/.*":
				//String url2 = url + i+"&vid=HKU&search_scope=My Institution&tab=default_tab&lang=en_US";
				//url1=url2.replaceAll(" ", "%20");
				url1 =url+i;
				break; // 可选 康奈尔大学法学院
				
			}
			
			
			
			
			CrawlMeta crawlMeta= new CrawlMeta();
			crawlMeta.setUrl(url1);
			crawlMeta.setRecordnum(i);
			crawlMeta.setReg(reg);
			//System.out.println("网址+++++++++++++++++++++"+crawlMeta.getUrl());
			//System.out.println("网址+++++++++++++++++++++"+crawlMeta.getUrl());
//		if(addurl(extracturl,crawlMeta))//这里是匹配数据库是否已经存在URL
//		{   
//			//i++;
//			
//			continue ;
//			
//		}
			//i++;
		
			//crawlMeta.addPositiveRegex("https://searchworks.stanford.edu/view/\\d+$");
			fetchQueue.isOver = false;
			//fetchQueue.timeout = false;
			 
			addFeed(crawlMeta);		
			//System.out.println("===============================iiiii");
			//page job =new page(crawlMeta);
		
			
			
			page job = new page();
			 //TimeoutThread tTime = new TimeoutThread(30000,new TimeoutException("短信发送超时"),smsRequestService,feePrepay,contentType);			
			executor.execute(job);
		}	
		
	}
	
public class page implements Runnable{
		
	CrawlMeta crawlMeta=null;
	
	 Mysqlfinishurl mysqlfinish= new Mysqlfinishurl();
	private CrawlHttpConf httpConf = new CrawlHttpConf();
	public  void run(){
		
			ThreadPoolExecutor tpe = ((ThreadPoolExecutor) executor);

			
			//System.out.println();

			int queueSize = tpe.getQueue().size();
			//System.out.println("当前排队线程数：" + queueSize);

			int activeCount = tpe.getActiveCount();
			//System.out.println("当前活动线程数：" + activeCount);

			long completedTaskCount = tpe.getCompletedTaskCount();
			System.out.println("执行完成线程数：" + completedTaskCount);

			//long taskCount = tpe.getTaskCount();
			//System.out.println("总线程数：" + taskCount);
			//Thread.sleep(3000);
		crawlMeta = fetchQueue.pollSeed();
		//CrawlHttpConf httpConf = new CrawlHttpConf();
		 int depth = 0;
		 String library=mysqlfinish.Reg(crawlMeta.getReg());
		//System.out.println("++++++++++++++++++++++++++++++++"+library);
		 switch (library) {
		 case "Stanford":
			 try {
				dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			 break;
		 case "Cambridge":
			 try {
				dojson( crawlMeta,httpConf,depth) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "California":
			 try {
				 dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "HKU":
			 try {
				 dojson(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
			 
		 case "CUHK":
			 try {
				 dojson(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
			 
		 case "Oxford":
			 try {
				 dojson(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
			 
		 case "Tokyo":
			 try {
				dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			 break;
			 //Response responsejson;
			 //responsejson = HttpUtils.requestJson(crawlMeta,httpConf);
			 
		 case "Clio":
			 try {
				dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			 break;
			 //Response responsejson;
			 //responsejson = HttpUtils.requestJson(crawlMeta,httpConf);

		 case "Mit":
			 try {
				dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			 break;
			 //Response responsejson;
			 //responsejson = HttpUtils.requestJson(crawlMeta,httpConf);
			 
		 case "Yale":
			 try {
				dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			 break;
			 //Response responsejson;
			 //responsejson = HttpUtils.requestJson(crawlMeta,httpConf);
		 case "Harvard":
			 try {
				 dojson(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "Chicago":
			 try {
				 dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "London":
			 try {
				 dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "Yalelaw":
			 try {
				 dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 case "Cornell":
			 try {
				 dohtml(crawlMeta,httpConf, depth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 break;
		 }
  }

	//public abstract void doFetchPage()throws Exception;
}


public void dojson(CrawlMeta crawlMeta,CrawlHttpConf httpConf, int depth) throws Exception{
	CrawlResult crawlResult = new CrawlResult();
	Response response;
	HttpUtils httputils=new HttpUtils();
	response = httputils.requestJson(crawlMeta,httpConf);
	
	String jsonBody = response.body();//已经获取到了json,下面考虑提取具体的文本问题
	//System.out.println("++++++++++++++++++++++++++++++++"+jsonBody);	
	 //crawlResult = doParse(res, crawlMeta);
	//String body = jsonBody.getJson();	
	JSONObject jsonObject1 = JSONObject.fromObject(jsonBody);
	String key=null;
	Iterator<String> iter = jsonObject1.keys();
	key = iter.next();
	//System.out.println("++++++++++++++++++++++++++++++++"+key);	
	if(key.equals("beaconO22")) {		
		return;
	}	
	else
	{
     crawlResult.setJson(jsonBody);
     crawlResult.setUrl(crawlMeta.getUrl());
    // crawlResult.setResult(map);
     //crawlResult.setStatus(CrawlResult.SUCCESS);
     ResultFilter.filter(crawlMeta, crawlResult, fetchQueue, depth);
	
	}
}


public void dohtml(CrawlMeta crawlMeta,CrawlHttpConf httpConf, int depth) throws Exception{
	
	try {
		
	
	//System.out.println("++++++++++++++++++++++++++++++++"+crawlMeta);	
		
	HttpResponse response;
	
		
			//Thread.sleep(ConfigWrapper.getInstance().getConfig().getEmptyQueueWaitTime());
			
					
			       // long start = 0;
			       
			           // start = System.currentTimeMillis();
			           // httpClient.execute(httpGet);
			          //  long end = System.currentTimeMillis();
				        //System.out.println("与网页握手交互花费时间为===================" + (end-start)/1000 + "秒");
	
			response = HttpUtils.request(crawlMeta,httpConf);
			
			//System.out.println("网址+++++++++++++++++++++"+response); 	  
	        
	
	String res = EntityUtils.toString(response.getEntity(), httpConf.getCode());
	//System.out.println("网址内容+++++++++++++++++++++"+res); 	
			
	CrawlResult	crawlResult = new CrawlResult();
	//System.out.println("代码+++++++++++++++++++++"+response.getStatusLine().getStatusCode());
	if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){		
		
		
		
			
		//crawlResult.setStatus(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
		//crawlResult.setUrl(crawlMeta.getUrl());
		System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
		return;
			
	}
	else{
    crawlResult = doParse(res, crawlMeta);
	ResultFilter.filter(crawlMeta, crawlResult, fetchQueue, depth);
	
	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
}
				
	 
	 private CrawlResult doParse(String html,CrawlMeta crawlMeta) throws FailingHttpStatusCodeException, MalformedURLException, IOException
		{

	        Document doc = Jsoup.parse(html,crawlMeta.getUrl());
		 

			//System.out.println("内容==============================+++++++++++++++++++++"+doc);
			// System.out.println("=============================="+doc);	
		        Map<String, List<String>> map = new HashMap<>(crawlMeta.getSelectorRules().size());
		        for (String rule: crawlMeta.getSelectorRules()) {
		            List<String> list = new ArrayList<>();
		            for (Element element: doc.select(rule)) {
		                list.add(element.text());
		            }

		            map.put(rule, list);
		        }


		        CrawlResult crawlResult = new CrawlResult();
		        crawlResult.setHtmlDoc(doc);
		        crawlResult.setUrl(crawlMeta.getUrl());
		        crawlResult.setResult(map);
		        crawlResult.setStatus(CrawlResult.SUCCESS);
				return crawlResult;
		}
	/**
	 * 初始化线程池
	 */
	private void initExecutor() {
		
		executor = new ThreadPoolExecutor(threadConf.getCoreNum(), threadConf.getMaxNum(), threadConf.getAliveTime(),
				threadConf.getTimeUnit(), new LinkedBlockingQueue<>(threadConf.getQueueSize()),
				new CustomThreadFactory(threadConf.getThreadName()), new ThreadPoolExecutor.CallerRunsPolicy());

	}

	/**
	 * 线程的创建工厂
	 * 
	 * @author 大哥
	 * @date 2020年3月17日
	 */

	public static class CustomThreadFactory implements ThreadFactory {

		private String name;

		private AtomicInteger count = new AtomicInteger(0);

		public CustomThreadFactory(String name) {
			this.name = name;
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, name + "-" + count.addAndGet(1));
		}

	}

	/**
	 * 线程池配置类
	 */
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	public static class ThreadConf {
		/*
		 * private int coreNum = 6; private int maxNum = 10; private int queueSize = 10;
		 * private int aliveTime = 1; private TimeUnit timeUnit = TimeUnit.MINUTES;
		 * private String threadName = "crawl-fetch"; public final static ThreadConf
		 * DEFAULT_CONF = new ThreadConf();
		 * 
		 */
		// 参数初始化
		private int CPU_COUNT = Runtime.getRuntime().availableProcessors();
		// 核心线程数量大小
		private int coreNum = Math.max(4, Math.min(CPU_COUNT - 1, 8));
		// 线程池最大容纳线程数
		private int maxNum = CPU_COUNT * 2 + 5;
		// 线程空闲后的存活时长
		private int aliveTime = 20;

		private int queueSize = 20;

		private TimeUnit timeUnit = TimeUnit.MINUTES;

		private String threadName = "crawl-fetch";

		public final static ThreadConf DEFAULT_CONF = new ThreadConf();
	}
	
	
}
