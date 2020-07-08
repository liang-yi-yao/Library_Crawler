package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class YaleLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> YaleLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
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
		
		Elements showdocument = doc.select("div[class=recordForm]").select("div[class=bibliographicData]")
				.select("div[id=divbib]").select("ul").select("li>*");
		for (Element e : showdocument) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
			for (int i = 0; i < Htmldivbib.size(); i++) {
				if (Htmldivbib.get(i).equals("Title:")) {	
					int j = i + 1;
					title = Htmldivbib.get(j);
					//System.out.println("title:"+title);
				}
				if (Htmldivbib.get(i).equals("ISBN:")) {	
					int j = i + 1;
					isbn = Htmldivbib.get(j);
					//System.out.println("isbn:"+isbn);
				}
				if (Htmldivbib.get(i).equals("Published/Created:")) {	
					int j = i + 1;
					publicaTion = Htmldivbib.get(j);
					//System.out.println("Published/Created:"+publicaTion);
				}
				if (Htmldivbib.get(i).equals("Physical Description:")) {	
					int j = i + 1;
					format = Htmldivbib.get(j);
					//System.out.println("Physical Description:"+format);
				}
				if (Htmldivbib.get(i).equals("Local Notes:")) {	
					int j = i + 1;
					localnotes = Htmldivbib.get(j);
					//System.out.println("Local Notes:"+localnotes);
				}
				if (Htmldivbib.get(i).equals("Notes:")) {	
					int j = i + 1;
					notes = Htmldivbib.get(j);
					//System.out.println("Notes:"+notes);
				}
				if (Htmldivbib.get(i).equals("Access and use:")) {	
					int j = i + 1;
					accessanduse = Htmldivbib.get(j);
					//System.out.println("Access and use:"+accessanduse);
				}
				if (Htmldivbib.get(i).equals("Format:")) {	
					int j = i + 1;
					type = Htmldivbib.get(j);
					//System.out.println("Format:"+type);
				}
				if (Htmldivbib.get(i).equals("Series:")) {	
					int j = i + 1;
					series = Htmldivbib.get(j);
					//System.out.println("Series:"+series);
				}
				if (Htmldivbib.get(i).equals("Bibliography")) {	
					int j = i + 1;
					bibliography = Htmldivbib.get(j);
					//System.out.println("Bibliography："+bibliography);
				}
				if (Htmldivbib.get(i).equals("Contents:")) {	
					int j = i + 1;
					Contents = Htmldivbib.get(j);
					//System.out.println("Contents:"+Contents);
				}
				if (Htmldivbib.get(i).equals("Subjects:")) {	
					int j = i + 1;
					subject = Htmldivbib.get(j);
					//System.out.println("Subjects:"+subject);
				}
				if (Htmldivbib.get(i).equals("Author/Creator:")) {	
					int j = i + 1;
					author = Htmldivbib.get(j);
					//System.out.println("Subjects:"+subject);
				}
			
				
			}
			
			try {
				if (!title.isEmpty()) {	
					result.setTitle(title);
					result.setIsBn(isbn);
					result.setPublicaTion(publicaTion);
					result.setFormat(format);
					result.setLocalnotes(localnotes);
					result.setNote(notes);
					result.setAccessanduse(accessanduse);
					result.setType(type);
					result.setSeries(series);
					result.setBibliography(bibliography);
					result.setConTents(Contents);
					result.setSubject(subject);
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
