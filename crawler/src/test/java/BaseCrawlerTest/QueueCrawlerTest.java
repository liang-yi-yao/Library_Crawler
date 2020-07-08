package BaseCrawlerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;
import com.ruc.fetcher.FetchQueue;
import com.ruc.fetcher.Fetcher;
import com.ruc.fetcher.JobCount;
import com.ruc.job.DefaultAbstractCrawlJob;

import Mysql.Mysqlfinishurl;

public class QueueCrawlerTest
{
	public static class QueueCrawlerJob extends DefaultAbstractCrawlJob{

		public void beforeRun(){
			//设置返回的网页编码
			super.setResponseCode("gbk");
		}
		@Override
		protected void visit(CrawlResult crawlResult)
		{
			//System.out.println(Thread.currentThread().getName() + "____" + getCrawlMeta().getCurrentDepth() + "____" + crawlResult.getUrl());
			
		}
		
	}
	
	
	/*public static void main(String[] args) throws Exception{
		Fetcher fetcher = new Fetcher(1);
		String url = "http://chengyu.t086.com/gushi/1.htm";
		CrawlMeta crawlMeta = new CrawlMeta();
		crawlMeta.setUrl(url);
		crawlMeta.addPositiveRegex("http://chengyu.t086.com/gushi/[0-9]+\\.htm$");
		fetcher.addFeed(crawlMeta);
		fetcher.start(QueueCrawlerJob.class);
	}*/
	
	
//	@Test
//	public void testCrawl() throws Exception{
//		//Fetcher fetcher = null;
//		//CrawlMeta crawlMeta = null;
//		
//		 int i = new Mysqlfinishurl().RecordNum();
//		//AtomicInteger atomicInteger = new AtomicInteger(13475204);
//		List<String> urlall=new ArrayList<String>();
//		//String url = "https://catalog.lib.uchicago.edu/vufind/Search/Results?filter%5B%5D=format%3A%22E-Resource%22&filter%5B%5D=format%3A%22Book%22&type=AllFields&page=";
//		CrawlMeta crawlMeta= new CrawlMeta();
//		//String url1;
//		Fetcher fetcher = new Fetcher(0,QueueCrawlerJob.class); 
//		String url="https://searchworks.stanford.edu/view/";
//		String url1=null;
//		List extracturl= new Mysqlfinishurl().Extracturl();
//		while(i<=200000000){
//		
//			 url1 = url + i;
//			 //System.out.println("第"+url1 +"页在数据库已存在");
//			crawlMeta.setUrl(url1);
//			crawlMeta.setRecordnum(i);;
//		if(fetcher.addurl(extracturl,crawlMeta))
//		{   //atomicInteger.addAndGet(1);
//			i++;
//			//atomicInteger.addAndGet(1);
//			continue ;
//		}
//		
//		
//		
//			
//			
//			//String url = "https://searchworks.stanford.edu/?f[format_main_ssim][]=Book&page=";
//			//String url2 = "https://searchworks.stanford.edu/?f%5Bformat_main_ssim%5D%5B%5D=Book&page=2";
//			//Fetcher fetcher = null;
//			//CrawlMeta crawlMeta = null;
//			//JobCount jobcount=null;
//			//FetchQueue fetchqueue=null;
//			//Fetcher fetcher = new Fetcher();
//			
//			//String url1 = url + i;
//			//String url1 = url ;
//			//System.out.println("++++++++++++++++++++++++++第"+ i +"+++++++++++++++++++++++++++++++++++");
//			//System.out.println("++++++++++++++++++++++++++开始获取"+url1 +"网址数据+++++++++++++++++++++++++++++++++++");
//			i++;
//			
//			//crawlMeta.setUrl(url1);
//			//crawlMeta.setUrl(url2);
//			crawlMeta.addPositiveRegex("https://searchworks.stanford.edu/view/\\d+$");
//			//crawlMeta.addPositiveRegex("https://searchworks.stanford.edu/?f%5Bformat_main_ssim%5D%5B%5D=Book");
//			//crawlMeta.addPositiveRegex("https://catalog.lib.uchicago.edu/vufind/Record/(.*){0,5}\\d+$");
//			fetcher.fetchQueue.isOver = false;
//			fetcher.fetchQueue.timeout = false;
//			fetcher.addFeed(crawlMeta);
//			
//			fetcher.start();
//			
//		}	
//	
//	}
	
	/*@Test
    public void testCrawel() throws Exception {
        Fetcher fetcher = new Fetcher(2, QueueCrawlerJob.class);

        String url = "http://chengyu.911cha.com/zishu_4.html";
        CrawlMeta crawlMeta = new CrawlMeta();
        crawlMeta.setUrl(url);
        crawlMeta.addPositiveRegex("http://chengyu.911cha.com/zishu_4_p[0-9]+\\.html$");

        fetcher.addFeed(crawlMeta);


        fetcher.start();
    }*/
	@Test
    public void testCrawel() throws Exception {
        Fetcher fetcher = new Fetcher(0, QueueCrawlerJob.class);
        fetcher.newstart();

       
       
    }
	
}
