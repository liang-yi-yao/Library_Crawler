package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class ChicagoLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> ChicagoLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		
		// TODO Auto-generated method stub
Document doc = crawlResult.getHtmlDoc();
		
		//System.out.println("=================title"+pagetitle);
		Elements table = doc.select("div[class=record sourceSolr row]").select("div[class=col-sm-9 info-col]")
				.select("table[class=table table-striped]").select("tbody").select("tr>*");
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
		String imprint=null;
		String type=null;
		String subtitle=null;
		String summary=null;
		String otherform=null;
		
		title=doc.select("div[class=record sourceSolr row]").select("div[class=col-sm-9 info-col]")
				.select("table[class=table table-striped]").select("tbody").select("tr[class=title]").text();
		System.out.println("title:"+title);
		for (Element e : table) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		
		for (int i = 0; i < Htmldivbib.size(); i++) {
			if (Htmldivbib.get(i).equals("Author / Creator")) {	
				int j = i + 1;
				author = Htmldivbib.get(j);
				System.out.println("author:"+author);
			}
			
			if (Htmldivbib.get(i).equals("Imprint")) {	
				int j = i + 1;
				imprint = Htmldivbib.get(j);
				System.out.println("Imprint:"+imprint);
			}
			
		
			if (Htmldivbib.get(i).equals("Description")) {	
				int j = i + 1;
				format = Htmldivbib.get(j);
				System.out.println("Description:"+format);
			}
			
			if (Htmldivbib.get(i).equals("Language")) {	
				int j = i + 1;
				language = Htmldivbib.get(j);
				System.out.println("Language:"+language);
			}
			
			if (Htmldivbib.get(i).equals("Series")) {	
				int j = i + 1;
				series = Htmldivbib.get(j);
				System.out.println("Series:"+series);
			}
			
			if (Htmldivbib.get(i).equals("Subject")) {	
				int j = i + 1;
				subject = Htmldivbib.get(j);
				System.out.println("Subject:"+subject);
			}
			
			if (Htmldivbib.get(i).equals("Format")) {	
				int j = i + 1;
				type = Htmldivbib.get(j);
				System.out.println("Format:"+type);
			}
			
			if (Htmldivbib.get(i).equals("Varying Form of Title")) {	
				int j = i + 1;
				subtitle = Htmldivbib.get(j);
				System.out.println("Varying Form of Title:"+subtitle);
			}
			
			if (Htmldivbib.get(i).equals("ISBN")) {	
				int j = i + 1;
				isbn = Htmldivbib.get(j);
				System.out.println("ISBN:"+isbn);
			}
			
			if (Htmldivbib.get(i).equals("Notes")) {	
				int j = i + 1;
				notes = Htmldivbib.get(j);
				System.out.println("Notes:"+notes);
			}
			
			if (Htmldivbib.get(i).equals("Summary")) {	
				int j = i + 1;
				summary = Htmldivbib.get(j);
				System.out.println("Summary:"+summary);
			}
			
			if (Htmldivbib.get(i).equals("Other form")) {	
				int j = i + 1;
				otherform = Htmldivbib.get(j);
				System.out.println("Other form:"+otherform);
			}
		}
		
		try {
			if (!title.isEmpty()) {	
				
				result.setTitle(title);
				result.setSubtitle(subtitle);
				result.setAuthor(author);
				result.setImprint(imprint);
				result.setFormat(format);
				result.setLanguage(language);
				result.setSeries(series);
				result.setSubject(subject);
				result.setAccessanduse(type);
				result.setSubtitle(subtitle);
				result.setIsBn(isbn);
				result.setNote(notes);
				result.setSumMary(summary);
				result.setOtherform(otherform);
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
