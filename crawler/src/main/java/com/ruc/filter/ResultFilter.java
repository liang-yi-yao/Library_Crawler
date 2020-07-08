package com.ruc.filter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;
import com.ruc.fetcher.FetchQueue;
import com.ruc.fetcher.JobCount;

import Mysql.MysqlControl;
import Mysql.Mysqlfinishurl;
import Mysql.resultCatch;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class ResultFilter {
	
	public  static void filter(CrawlMeta crawlMeta, CrawlResult crawlResult, FetchQueue fetchQueue, int maxDepth) {
		
	
		int count = 0;
		
		//List<resultCatch> list = new ArrayList<>();
		long start = System.currentTimeMillis();
		try {
			int currentDepth = crawlMeta.getCurrentDepth();
			if (currentDepth == maxDepth) {

				//StanfordLibraryFilter data=new StanfordLibraryFilter();
				
				
				SelectRilter SelectRilter = new SelectRilter();
				List<resultCatch> list=SelectRilter.Selectrilter(crawlMeta, crawlResult);
				 
				//resultCatch result= data.getResult();
				
				try {
					 MysqlControl control=new MysqlControl();
					 control.executeInsert(list,crawlMeta);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (currentDepth > maxDepth) {
				return;
			}
			// 当前的网址中可以继续爬的链接数
//			Elements elements = crawlResult.getHtmlDoc().select("a[href]");
//			String src;
//			for (Element element : elements) {
//				// 确保将相对地址转为绝对地址
//				src = element.attr("abs:href");
//				if (!matchRegex(crawlMeta, src)) {
//					continue;
//				}
//				/*
//				 * CrawlMeta meta = new CrawlMeta(JobCount.genId(),crawlMeta.getRecordnum(),
//				 * crawlMeta.getJobId(), currentDepth + 1, src, crawlMeta.getSelectorRules(),
//				 * crawlMeta.getPositiveRegex(), crawlMeta.getNegativeRegex());
//				 * 
//				 * if (fetchQueue.addSeed(meta)) { if (log.isDebugEnabled()) { //
//				 * System.out.println("===============================测试3"+crawlMeta.getUrl());
//				 * log.debug("put into queue! parentUrl:{} url:{} depth:{}", crawlMeta.getUrl(),
//				 * src, currentDepth + 1); } count++;
//				 * 
//				 * }
//				 */
//			}

		} finally { // 上一层爬完计数+1

			fetchQueue.finishJob(crawlMeta, count, maxDepth);
			long end = System.currentTimeMillis();

			if (log.isDebugEnabled()) {
				log.debug("url {} subUrl counts:{},filter result cost: {}ms, currentDepth:{} \n\n", crawlMeta.getUrl(),
						count, end - start, crawlMeta.getCurrentDepth());
			}

		}
	}

	private static boolean matchRegex(CrawlMeta crawlMeta, String url) {
		Matcher matcher;
		for (Pattern pattern : crawlMeta.getPositiveRegex()) {
			matcher = pattern.matcher(url);
			if (matcher.find()) {
				return true;
			}
		}

		for (Pattern pattern : crawlMeta.getNegativeRegex()) {
			matcher = pattern.matcher(url);
			if (matcher.find()) {
				return false;
			}
		}

		return crawlMeta.getPositiveRegex().size() == 0;
	}

}
