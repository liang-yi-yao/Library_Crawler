package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class TokyoLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> TokyoLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		// TODO Auto-generated method stub
		Document doc = crawlResult.getHtmlDoc();
		
		//System.out.println("=================title"+pagetitle);
		Elements table = doc.select("table[id=detail_tbl]").select("tbody").select("tr > *");
		String title = null;
		String ResourceType =  null;
		String author =  null;
		String publicaTion =  null;
		String format= null;
		String series= null;
		String Contents= null;
		String subject= null;
		String localsubjcet= null;
		String IncludedIn= null;
		String isbn= null;
		String genre= null;
		String LCcontrolnumber= null;
		String RecordID= null;
		String notes= null;
		String Otherformat= null;
		String pubYear=null;
		String language=null;
		String ncid=null;
		String classification=null;
		String othertitles=null;
		String edition=null;
		title = doc.select("div[class=pageTitle]").select("h2[class=bb_ttl]").text();
		for (Element e : table) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
			
		for (int i = 0; i < Htmldivbib.size(); i++) {
			
			if (Htmldivbib.get(i).equals("Publisher")) {	
				int j = i + 1;
				publicaTion = Htmldivbib.get(j);
				//System.out.println("publicaTion:"+publicaTion);
			}
			if (Htmldivbib.get(i).equals("Year")) {	
				int j = i + 1;
				pubYear = Htmldivbib.get(j);
				//System.out.println("pubYear:"+pubYear);
			}
			if (Htmldivbib.get(i).equals("Size")) {	
				int j = i + 1;
				format = Htmldivbib.get(j);
				//System.out.println("format:"+format);
			}
			
			if (Htmldivbib.get(i).equals("Authors")) {	
				int j = i + 1;
				author = Htmldivbib.get(j);
				//System.out.println("Authors:"+author);
			}
			
			if (Htmldivbib.get(i).equals("Language")) {	
				int j = i + 1;
				language = Htmldivbib.get(j);
				//System.out.println("Language:"+language);
			}
		
			if (Htmldivbib.get(i).equals("Notes")) {	
				int j = i + 1;
				notes = Htmldivbib.get(j);
				//System.out.println("Notes:"+notes);
			}
			if (Htmldivbib.get(i).equals("Classification")) {	
				int j = i + 1;
				classification = Htmldivbib.get(j);
				//System.out.println("Classification:"+classification);
			}
			if (Htmldivbib.get(i).equals("Other titles")) {	
				int j = i + 1;
				othertitles = Htmldivbib.get(j);
				//System.out.println("Other titles:"+othertitles);
			}
			if (Htmldivbib.get(i).equals("Edition")) {	
				int j = i + 1;
				edition = Htmldivbib.get(j);
				//System.out.println("edition:"+edition);
			}
			if (Htmldivbib.get(i).equals("ISBN")) {	
				int j = i + 1;
				isbn = Htmldivbib.get(j);
				//System.out.println("isbn:"+edition);
			}
			if (Htmldivbib.get(i).equals("Subjects")) {	
				int j = i + 1;
				subject = Htmldivbib.get(j);
				//System.out.println("subject:"+subject);
			}
		}
		try {
			if (!title.isEmpty()) {	
				result.setPublicaTion(publicaTion);
				result.setPublicationDate(pubYear);
				result.setFormat(format);
				result.setAuthor(author);
				result.setLanguage(language);
				result.setNote(notes);
				result.setClassification(classification);
				result.setOthertitles(othertitles);
				result.setEdiTion(edition);
				result.setIsBn(isbn);
				result.setSubject(subject);
				result.setTitle(title);
				result.setUrl(crawlMeta.getUrl());
				result.setHtml(doc.toString());
				result.setRecordnum(crawlMeta.getRecordnum());
				list.add(result);
				//System.out.println("========================"+list);
				return list;
				
			} else {
				//System.out.println("不插入网址==============");
				return null;
			}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
		
	}
	
	
}
