package com.ruc.filter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class CaliforniaLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();
	public List<resultCatch> CaliforniaLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		// TODO Auto-generated method stub
		Document doc = crawlResult.getHtmlDoc();
		//System.out.println("网址============"+crawlMeta.getUrl());
		Elements divbib = doc.select("div[id=divbib]").select("ul[title=Bibliographic Record Display]")
				.select("li > *");
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
		for (Element e : divbib) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		for (int i = 0; i < Htmldivbib.size(); i++) {
			
			if (Htmldivbib.get(i).equals("Author/Name:")) {	
				int j = i + 1;
				author = Htmldivbib.get(j);
				//System.out.println("author:"+author);
			}
			if (Htmldivbib.get(i).equals("Resource Type:")) {	
				int j = i + 1;
				ResourceType = Htmldivbib.get(j);
				//System.out.println("ResourceType:"+ResourceType);
			}
			if (Htmldivbib.get(i).equals("Title:")) {			
				int j = i + 1;
				title = Htmldivbib.get(j);
				//System.out.println("title:"+title);
			}
			if (Htmldivbib.get(i).equals("Published/Distributed:")) {		
				int j = i + 1;
				publicaTion = Htmldivbib.get(j);
				//System.out.println("Publicationdate:"+publicaTion);
			}
			if (Htmldivbib.get(i).equals("Physical Description:")) {
				int j = i + 1;
				format = Htmldivbib.get(j);
				//System.out.println("format:"+format);
			}
			if (Htmldivbib.get(i).equals("Series:")) {
				int j = i + 1;
				series = Htmldivbib.get(j);
				//System.out.println("Series:"+series);
			}
			if (Htmldivbib.get(i).equals("Contents:")) {		
				int j = i + 1;
				Contents = Htmldivbib.get(j);
				//System.out.println("contents:"+Contents);
			}
			if (Htmldivbib.get(i).equals("Subject(s):")) {		
				int j = i + 1;
				subject = Htmldivbib.get(j);
				//System.out.println("subjcet:"+subject);
			}
			if (Htmldivbib.get(i).equals("Local Subject(s):")) {		
				int j = i + 1;
				localsubjcet = Htmldivbib.get(j);
				//System.out.println("localsubjcet:"+localsubjcet);
			}
			if (Htmldivbib.get(i).equals("Included In:")) {		
				int j = i + 1;
				IncludedIn = Htmldivbib.get(j);
				//System.out.println("localsubjcet:"+IncludedIn);
			}
			if (Htmldivbib.get(i).equals("ISBN:")) {		
				int j = i + 1;
				isbn = Htmldivbib.get(j);
				//System.out.println("isbn:"+isbn);
			}
			if (Htmldivbib.get(i).equals("Genre/form:")) {		
				int j = i + 1;
				genre = Htmldivbib.get(j);
				//System.out.println("genre:"+genre);
			}
			if (Htmldivbib.get(i).equals("LC control number:")) {	
				int j = i + 1;
				LCcontrolnumber = Htmldivbib.get(j);
				//System.out.println("LCcontrolnumber:"+LCcontrolnumber);
			}
			if (Htmldivbib.get(i).equals("Record ID:")) {	
				int j = i + 1;
				RecordID= Htmldivbib.get(j);
				//System.out.println("RecordID:"+RecordID);
			}
			if (Htmldivbib.get(i).equals("Notes:")) {
				int j = i + 1;
				notes= Htmldivbib.get(j);
				//System.out.println("notes:"+notes);
			}
			if (Htmldivbib.get(i).equals("Other format:")) {
				int j = i + 1;
				Otherformat= Htmldivbib.get(j);
				//System.out.println("Otherformat:"+Otherformat);
			}
		}
		try {
		if (!title.isEmpty()) {	
			result.setAuthor(author);
			result.setResourceType(ResourceType);
			result.setTitle(title);
			result.setPublicaTion(publicaTion);
			result.setFormat(format);
			result.setSeries(series);
			result.setConTents(Contents);
			result.setSubject(subject);
			result.setLocalsubjcet(localsubjcet);
			result.setIncludedIn(IncludedIn);
			result.setIsBn(isbn);
			result.setGenre(genre);
			result.setLCcontrolnumber(LCcontrolnumber);
			result.setRecordID(RecordID);
			result.setNote(notes);
			result.setOtherformat(Otherformat);
			result.setRecordnum(crawlMeta.getRecordnum());
			result.setUrl(crawlMeta.getUrl());
			result.setHtml(doc.toString());
			list.add(result);
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
		//return null;
	}

}
