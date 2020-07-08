package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.cj.util.StringUtils;
import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class MitLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> MitLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
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
		String type=null;
		String source=null;
		String dbase=null;
		
		 title = doc.select("div[class=wrap-outer-content layout-band]").select("div[class=discovery-full-record-basic-info]")
				.select("h2[class=record-title]").text();
		 title = title.replace("Title:","");
			//System.out.println("=================="+title);
		 subject =  doc.select("div[class=wrap-outer-content layout-band]").select("div[class=gridband layout-3q1q wrap-full-record]")
					.select("div[class=discovery-full-record-more-info]").select("ul[class=list-subjects]").select("li").text();
		 //System.out.println("subject ==============================="+subject);
		Elements showdocument = doc.select("div[class=wrap-outer-content layout-band]").select("div[class=gridband layout-3q1q wrap-full-record]")
				.select("div[class=discovery-full-record-more-info]").select("ul>*");
		for (Element e : showdocument) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		for (int i = 0; i < Htmldivbib.size(); i++) {
			if(Htmldivbib.get(i).indexOf(":")>0) {
			Htmldivbib.get(i).substring(0,Htmldivbib.get(i).indexOf(":"));
				
			if (Htmldivbib.get(i).substring(0,Htmldivbib.get(i).indexOf(":")).equals("Document type")) {	
				type = Htmldivbib.get(i).substring(Htmldivbib.get(i).indexOf(":")+1,Htmldivbib.get(i).length());
				//System.out.println("==========================type:"+type);
			}
			
			if (Htmldivbib.get(i).substring(0,Htmldivbib.get(i).indexOf(":")).equals("Source")) {	
				source= Htmldivbib.get(i).substring(Htmldivbib.get(i).indexOf(":")+1,Htmldivbib.get(i).length());
				//System.out.println("==========================source:"+source);
			}
			
			if (Htmldivbib.get(i).substring(0,Htmldivbib.get(i).indexOf(":")).equals("Publication info")) {	
				publicaTion= Htmldivbib.get(i).substring(Htmldivbib.get(i).indexOf(":")+1,Htmldivbib.get(i).length());
				//System.out.println("==========================publicaTion:"+publicaTion);
			}
			
			if (Htmldivbib.get(i).substring(0,Htmldivbib.get(i).indexOf(":")).equals("Language")) {	
				language= Htmldivbib.get(i).substring(Htmldivbib.get(i).indexOf(":")+1,Htmldivbib.get(i).length());
				//System.out.println("==========================language:"+language);
			}
			
			if (Htmldivbib.get(i).substring(0,Htmldivbib.get(i).indexOf(":")).equals("Database")) {	
				dbase= Htmldivbib.get(i).substring(Htmldivbib.get(i).indexOf(":")+1,Htmldivbib.get(i).length());
				//System.out.println("==========================database:"+database);
			}
		
			}
			
			
		}
		try {
			if (!title.isEmpty()) {	
				result.setTitle(title);
				result.setSubject(subject);
				result.setType(type);
				result.setSource(source);
				result.setPublicaTion(publicaTion);
				result.setLanguage(language);
				result.setDbase(dbase);
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
