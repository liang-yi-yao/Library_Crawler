package com.ruc.fetcher;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.ruc.entity.CrawlMeta;

import Mysql.Mysqlfinishurl;

/**
 * 代爬的网页队列
 * @author 大哥
 * @date 2020年3月16日
 */

@Slf4j
public class FetchQueue
{

	public static FetchQueue DEFAULT_INSTANCE = newInstance("default");
	
	/**
	 * 表示爬取队列的标识
	 */
	private String tag;
	
	/**
	 * 待爬取的网页队列
	 */
	@Getter
	@Setter
	private Queue<CrawlMeta> toFetchQueue = new LinkedBlockingQueue<>();
	@Getter
	@Setter
	private static Queue<CrawlMeta> crawlMetaAll = new ArrayBlockingQueue<>(2000);
	
	/**
	 * jobcount映射表，key为jobcount#id,value为对应的jobCount
	 */
	private Map<Integer,JobCount> jobCountMap = new ConcurrentHashMap<>();
	
	/**
	 * 爬取是否完成的标识
	 */
	public volatile boolean isOver = false;
	public volatile boolean timeout = false;
	public volatile int fetchQueueCount = 0;
	
	/**
	 * 所有爬取过的URL集合，用于去重
	 */
	public Set<String> urls = ConcurrentHashMap.newKeySet();
	

    public int size() {
        return toFetchQueue.size();
    }
	
    public Queue<CrawlMeta> addSeedto(CrawlMeta crawlMeta) {
    	System.out.println("我已经到这里"+crawlMeta);
    	synchronized(this) {
    	toFetchQueue.add(crawlMeta);
        return toFetchQueue;
    	}
    }
    
    public Queue<CrawlMeta> addSeedcrawl(CrawlMeta crawlMeta) {
    	System.out.println("我已经到这里"+crawlMeta);
    	crawlMetaAll.add(crawlMeta);
        return crawlMetaAll;
    }
    
	private FetchQueue(String tag){
		this.tag = tag;
	}
	
	public static FetchQueue newInstance(String tag){
		return new FetchQueue(tag);
	}
	
	public  boolean addSqlurl(List url,CrawlMeta crawlMeta)
	{
		for(int i=0;i<url.size();i++)
		{
			
			//System.out.println("这是现在URL");
			//System.out.println(url.get(i));
			if(url.get(i).equals(crawlMeta.getUrl()))
			{ 
				System.out.println("============================数据库已存在此URL"+ crawlMeta.getUrl());
			//System.out.println(url.get(i));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 当没有爬取过时，才丢入队列；主要是避免重复爬取的问题
	 * @throws SQLException 
	 */
	public boolean addSeed(CrawlMeta crawlMeta){
		//System.out.println("---------------------------"+crawlMeta.getUrl());
		//boolean compare= new Mysqlfinishurl().executeCompare(crawlMeta);
		if(urls.contains(crawlMeta.getUrl())){
			//System.out.println("============================数据库已存在此URL"+ crawlMeta.getUrl());
			return false;
		}
		synchronized(this){
			if(urls.contains(crawlMeta.getUrl())){
				//System.out.println("============================数据库已存在此URL"+ crawlMeta.getUrl()); 
				return false;
			}
			
//			if(!compare){
//				return false;
//			}
			
			//System.out.println("===============================测试1"+crawlMeta.getUrl());
			urls.add(crawlMeta.getUrl());
			toFetchQueue.add(crawlMeta);
			
			
			return true;
		}
	}
	
	
	
	
	
	
	public CrawlMeta pollSeed(){
		//System.out.println("===============================测试11111"+toFetchQueue);
		return toFetchQueue.poll();
	}
	
	public CrawlMeta pollSeedcrawl(){
		//System.out.println("===============================测试"+toFetchQueue);
		return crawlMetaAll.poll();
	}
	
	public void finishJob(CrawlMeta crawlMeta, int count, int maxDepth){
		if(finishOneJob(crawlMeta, count, maxDepth)){
			isOver = true;
			//System.out.println("========finish crawl!===========");
		}
	}
	
	private boolean finishOneJob(CrawlMeta crawlMeta,int count,int maxDepth){
		JobCount jobCount = new JobCount(crawlMeta.getJobId(),
				crawlMeta.getParentJobId(),
				crawlMeta.getCurrentDepth(),
				count,0);
		jobCountMap.put(crawlMeta.getJobId(),jobCount);
		
		if(crawlMeta.getCurrentDepth() == 0){
			return count == 0;
		}
		
		//if((count == 0 || crawlMeta.getCurrentDepth() == maxDepth)&&jobCountMap.get(crawlMeta.getParentJobId())!=null){
		if((count == 0 || crawlMeta.getCurrentDepth() == maxDepth)){
			//System.out.println("这里是作者的名称--------------------------------------");
			//finishOneJob(jobCountMap.get(crawlMeta.getJobId()));
			//System.out.println("========================================"+jobCountMap.get(crawlMeta.getParentJobId()));
			return finishOneJob(jobCountMap.get(crawlMeta.getParentJobId()));
			
		}
		
		return false;
		
	}

	private boolean finishOneJob(JobCount jobCount){
		//CrawlMeta crawlMeta;
		//System.out.println("========================================"+crawlMeta.getJobId());
		
		if(jobCount.finishJob()){//队列中完成的URL与与爬取的URL数目相等时，返回true,否则false
			
			if(jobCount.getCurrentDepth() == 0){
				return true;
			}
			return finishOneJob(jobCountMap.get(jobCount.getParentId()));
		}
		
		return false;
	}

	
}
