package com.ruc.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class YalelawLibraryFilter {
	List<String> Htmldivbib = new ArrayList<String>();
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();

	public List<resultCatch> YalelawLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
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
		
		
		Elements showdocument = doc.select("table").select("tbody").select("tr").select("td");
		for (Element e : showdocument) {					
			Htmldivbib.add(e.text());
			//System.out.println("text============"+e.text());
		}
		for (int i = 0; i < Htmldivbib.size(); i++) {
			if (Htmldivbib.get(i).equals("Title")) {	
				int j = i + 1;
				title = Htmldivbib.get(j);
				System.out.println("title:"+title);
			}
			
			if (Htmldivbib.get(i).equals("Publication/Production Information")) {	
				int j = i + 1;
				publicaTion = Htmldivbib.get(j);
				System.out.println("Publication/Production Information:"+publicaTion);
			}
			
			if (Htmldivbib.get(i).equals("Frequency")) {	
				int j = i + 1;
				frequencyte = Htmldivbib.get(j);
				System.out.println("Frequencyte:"+frequencyte);
			}
			
			if (Htmldivbib.get(i).equals("Notes")) {	
				int j = i + 1;
				notes = Htmldivbib.get(j);
				System.out.println("Notes:"+notes);
			}
			
			if (Htmldivbib.get(i).equals("Continues")) {	
				int j = i + 1;
				continues = Htmldivbib.get(j);
				System.out.println("Continues:"+continues);
			}
			
			if (Htmldivbib.get(i).equals("Subjects")) {	
				int j = i + 1;
				subject = Htmldivbib.get(j);
				System.out.println("Subjects:"+subject);
			}
			
			if (Htmldivbib.get(i).equals("Genre/Form")) {	
				int j = i + 1;
				type = Htmldivbib.get(j);
				System.out.println("Genre/Form:"+type);
			}
			
			if (Htmldivbib.get(i).equals("Other author/creator")) {	
				int j = i + 1;
				otherauthor = Htmldivbib.get(j);
				System.out.println("Other author/creator:"+otherauthor);
			}
			
			if (Htmldivbib.get(i).equals("Authorized form of  title")) {	
				int j = i + 1;
				authorizedtitle = Htmldivbib.get(j);
				System.out.println("Authorized form of  title:"+authorizedtitle);
			}
			
			if (Htmldivbib.get(i).equals("ISBN/ISSN")) {	
				int j = i + 1;
				isbn = Htmldivbib.get(j);
				System.out.println("ISBN/ISSN:"+isbn);
			}
			
			
			
		}
		try {
			if (!title.isEmpty()) {	
				result.setTitle(title);
				result.setPublicaTion(publicaTion);
				result.setFrequencyte(frequencyte);
				result.setNote(notes);
				result.setContinues(continues);
				result.setSubject(subject);
				result.setType(type);
				result.setOtherauthor(otherauthor);
				result.setAuthorizedtitle(authorizedtitle);
				result.setIsBn(isbn);
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
