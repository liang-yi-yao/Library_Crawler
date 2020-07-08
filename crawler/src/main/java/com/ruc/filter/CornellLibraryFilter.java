package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class CornellLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();
	
	public List<resultCatch> CornellLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
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
		String frequencyte=null;
		String continues=null;
		String otherauthor=null;
		String authorizedtitle=null;
		String access=null;
		String subtitle=null;
		String relatedwork=null;
		String othertitle=null;
		
		title=doc.select("div[id=document]").select("div[class=document-header]").select("h2")
				.text();
		subtitle=doc.select("div[id=document]").select("div[class=document-header]").select("h3")
				.text();
		//System.out.println("subtitle:"+subtitle);
		
		Elements showdocument = doc.select("div[id=document]").select("div[class=row]").select("dl>*");
		for (Element e : showdocument) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		for (int i = 0; i < Htmldivbib.size(); i++) {
			
			if (Htmldivbib.get(i).equals("Author, etc.:")) {	
				int j = i + 1;
				author = Htmldivbib.get(j);
				//System.out.println("author:"+author);
			}
			
			if (Htmldivbib.get(i).equals("Format:")) {	
				int j = i + 1;
				format = Htmldivbib.get(j);
				//System.out.println("format:"+format);
			}
			
			if (Htmldivbib.get(i).equals("Language:")) {	
				int j = i + 1;
				language= Htmldivbib.get(j);
				//System.out.println("Language:"+language);
			}
			
			if (Htmldivbib.get(i).equals("Published:")) {	
				int j = i + 1;
				publicaTion= Htmldivbib.get(j);
				//System.out.println("Published:"+publicaTion);
			}
			
			if (Htmldivbib.get(i).equals("Description:")) {	
				int j = i + 1;
				access= Htmldivbib.get(j);
				//System.out.println("Description:"+access);
			}
			
			if (Htmldivbib.get(i).equals("ISBN:")) {	
				int j = i + 1;
				isbn= Htmldivbib.get(j);
				//System.out.println("ISBN:"+isbn);
			}
			
			if (Htmldivbib.get(i).equals("Other title:")) {	
				int j = i + 1;
				othertitle= Htmldivbib.get(j);
				//System.out.println("Other title:"+othertitle);
			}
			
			if (Htmldivbib.get(i).equals("Related Work:")) {	
				int j = i + 1;
				relatedwork= Htmldivbib.get(j);
				//System.out.println("Related Work:"+relatedwork);
			}
			
			if (Htmldivbib.get(i).equals("Notes:")) {	
				int j = i + 1;
				notes= Htmldivbib.get(j);
				//System.out.println("Notes:"+notes);
			}
			
		}
		try {
			if (!title.isEmpty()) {	
				result.setTitle(title);
				result.setSubtitle(subtitle);
				result.setAuthor(author);
				result.setFormat(format);
				result.setLanguage(language);
				result.setPublicaTion(publicaTion);
				result.setAccess(access);
				result.setIsBn(isbn);
				result.setOthertitles(othertitle);
				result.setRelatedwork(relatedwork);
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
