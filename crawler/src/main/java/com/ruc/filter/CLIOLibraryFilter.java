package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class CLIOLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> CLIOLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		// TODO Auto-generated method stub
		Document doc = crawlResult.getHtmlDoc();
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
		String summary=null;
		String standardno=null;
		
		
		Elements showdocument = doc.select("div[class=show-document]").select("div[class=row]")
				.select("div[class=info col-sm-6 col-xs-6]").select("div > *");
		for (Element e : showdocument) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		for (int i = 0; i < Htmldivbib.size(); i++) {
			if (Htmldivbib.get(i).equals("Author")) {	
				int j = i + 1;
				author = Htmldivbib.get(j);
				//System.out.println("author:"+author);
			}
			if (Htmldivbib.get(i).equals("Title")) {	
				int j = i + 1;
				title = Htmldivbib.get(j);
				//System.out.println("title:"+title);
			}
			if (Htmldivbib.get(i).equals("Published")) {	
				int j = i + 1;
				publicaTion = Htmldivbib.get(j);
				//System.out.println("publicaTion:"+publicaTion);
			}
			if (Htmldivbib.get(i).equals("Subjects")) {	
				int j = i + 1;
				subject = Htmldivbib.get(j);
				//System.out.println("subject:"+subject);
			}
			if (Htmldivbib.get(i).equals("Summary")) {	
				int j = i + 1;
				summary = Htmldivbib.get(j);
				//System.out.println("summary:"+summary);
			}
			if (Htmldivbib.get(i).equals("Contents")) {	
				int j = i + 1;
				Contents = Htmldivbib.get(j);
				//System.out.println("Contents:"+Contents);
			}
			if (Htmldivbib.get(i).equals("Language")) {	
				int j = i + 1;
				language = Htmldivbib.get(j);
				//System.out.println("language:"+language);
			}
			if (Htmldivbib.get(i).equals("ISBN")) {	
				int j = i + 1;
				isbn = Htmldivbib.get(j);
				//System.out.println("isbn:"+isbn);
			}
			if (Htmldivbib.get(i).equals("Standard No.")) {	
				int j = i + 1;
				standardno = Htmldivbib.get(j);
				//System.out.println("standardno:"+isbn);
			}
			if (Htmldivbib.get(i).equals("Format")) {	
				int j = i + 1;
				format = Htmldivbib.get(j);
				//System.out.println("format:"+format);
			}
			if (Htmldivbib.get(i).equals("Series")) {	
				int j = i + 1;
				series = Htmldivbib.get(j);
				//System.out.println("series:"+series);
			}
			if (Htmldivbib.get(i).equals("Notes")) {	
				int j = i + 1;
				notes = Htmldivbib.get(j);
				//System.out.println("Notes:"+notes);
			}
		}
		
		try {
			if (!title.isEmpty()) {	
				result.setAuthor(author);
				result.setTitle(title);
				result.setPublicaTion(publicaTion);
				result.setSubject(subject);
				result.setSumMary(summary);
				result.setConTents(Contents);
				result.setLanguage(language);
				result.setIsBn(isbn);
				result.setStandardno(standardno);
				result.setFormat(format);
				result.setSeries(series);
				result.setNote(notes);
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
