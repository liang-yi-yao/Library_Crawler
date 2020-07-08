package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class LondonLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> LondonLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
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
		String localnotes=null;
		String accessanduse=null;
		String type=null;
		String bibliography=null;
		String imprint=null;
		String copyrightdate=null;
		String addauthor=null;
		
		
		Elements showdocument = doc.select("table").select("tbody").select("tr").select("td");
				
		for (Element e : showdocument) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		for (int i = 0; i < Htmldivbib.size(); i++) {
			if (Htmldivbib.get(i).equals("Title")) {	
				int j = i + 1;
				title = Htmldivbib.get(j);
				//System.out.println("title:"+title);
			}
			
			if (Htmldivbib.get(i).equals("Imprint")) {	
				int j = i + 1;
				imprint = Htmldivbib.get(j);
				//System.out.println("Imprint:"+imprint);
			}
			
			if (Htmldivbib.get(i).equals("Copyright date")) {	
				int j = i + 1;
				copyrightdate = Htmldivbib.get(j);
				//System.out.println("Copyright date:"+copyrightdate);
			}
			
			
			if (Htmldivbib.get(i).equals("Descript.")) {	
				int j = i + 1;
				format = Htmldivbib.get(j);
				//System.out.println("Descript.:"+format);
			}
			
			if (Htmldivbib.get(i).equals("Edition")) {	
				int j = i + 1;
				edition = Htmldivbib.get(j);
				//System.out.println("Edition:"+edition);
			}
			
			if (Htmldivbib.get(i).equals("Bibliog.")) {	
				int j = i + 1;
				bibliography = Htmldivbib.get(j);
				//System.out.println("Bibliog.:"+bibliography);
			}
			
			if (Htmldivbib.get(i).equals("Contents")) {	
				int j = i + 1;
				Contents = Htmldivbib.get(j);
				//System.out.println("Contents:"+Contents);
			}
			
			if (Htmldivbib.get(i).equals("Subject")) {	
				int j = i + 1;
				subject = Htmldivbib.get(j);
				//System.out.println("Subject:"+subject);
			}
			
			if (Htmldivbib.get(i).equals("Add Author")) {	
				int j = i + 1;
				addauthor = Htmldivbib.get(j);
				//System.out.println("Add Author:"+addauthor);
			}
			
			if (Htmldivbib.get(i).equals("ISBN")) {	
				int j = i + 1;
				isbn = Htmldivbib.get(j);
				//System.out.println("ISBN:"+isbn);
			}
			
			if (Htmldivbib.get(i).equals("Author")) {	
				int j = i + 1;
				author = Htmldivbib.get(j);
				//System.out.println("ISBN:"+isbn);
			}	
		}
		try {
			if (!title.isEmpty()) {	
				result.setTitle(title);
				result.setImprint(imprint);
				result.setCopyrightdate(copyrightdate);
				result.setFormat(format);
				result.setEdiTion(edition);
				result.setBibliography(bibliography);
				result.setConTents(Contents);
				result.setSubject(subject);
				result.setAddauthor(addauthor);
				result.setIsBn(isbn);
				result.setAuthor(author);
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
