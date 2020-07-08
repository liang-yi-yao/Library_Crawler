package com.ruc.filter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.MysqlControl;
import Mysql.resultCatch;
import lombok.Getter;
import lombok.Setter;

public class StanfordLibraryFilter {
	
	@Getter
	@Setter
		resultCatch result = new resultCatch();
		List<resultCatch> list = new ArrayList<>();
		List<String> Middle = new ArrayList<String>();
		List<String> Middle1 = new ArrayList<String>();
		List<String> Middle2 = new ArrayList<String>();
		
		public List<resultCatch> StanfordLibraryfilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		Document doc = crawlResult.getHtmlDoc();
		Elements element3 = doc.select("div[id=bibliography-info]").select("div[class=section-body]")
				.select("dl > *");
		boolean isRow = true;
		String Publicationdate = "Publication date";
		String Responsibility = "Responsibility";
		String Publication = "Publication";
		String Edition = "Edition";
		String Summary = "Summary";
		String Contents = "Contents";
		String Isbn = "ISBN";
		// String Title="Title";
		boolean isRow1 = true;
		
		for (Element e : element3) {					
			Middle.add(e.text());
			// System.out.println("ISBN:"+isbn);
		}
		 
//    			if(! isRow1 && !p.equals(Publicationdate))
//    				{
//   					Element publicationdate=e.select("dd").first();
//   					if(publicationdate!=null) {
//    					}
//   			
//   			}
		Elements element5 = doc.select("div[class=row]").select("div[class=col-sm-10]").select("dl > *");

		for (Element e : element5) {
			Middle1.add(e.text());
			// System.out.println("我已经到这里了");
			// System.out.println("Responsibility:============================"+Middle1);
		}

		Elements element6 = doc.select("div[class=record-sections col-md-8]").select("div[id=contents-summary]")
				.select("div[class=section-body]").select("dl > *");


		for (Element e : element6) {
			Middle2.add(e.text());
			//System.out.println("我已经到这里了+++++++++++"+e);
			// System.out.println("Responsibility:============================"+Middle2);
		}
//	for(Element e : element6){
//   		Middle3.add(e.text());
//   		//System.out.println("我已经到这里了");
//       	//System.out.println("Responsibility:============================"+Middle2);
//   	}

		String PublicationDate = null;
		String IsBn = null;
		for (int i = 0; i < Middle.size(); i++) {
			String publicationdate = Middle.get(i);
			String isbn = Middle.get(i);
			if (publicationdate.equals(Publicationdate)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("Publicationdate:"+Middle.get(j));
				PublicationDate = Middle.get(j);
			}
			if (isbn.equals(Isbn)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("ISBN:"+Middle.get(j));
				IsBn = Middle.get(j);
			}
		}
		String ResponsiBility = null;
		String PublicaTion = null;
		String EdiTion = null;
		String SumMary = null;
		String ConTents = null;
		for (int i = 0; i < Middle1.size(); i++) {
			String responsibility = Middle1.get(i);
			String publication = Middle1.get(i);
			String edition = Middle1.get(i);
			if (responsibility.equals(Responsibility)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("ResponsiBility:"+Middle1.get(j));
				ResponsiBility = Middle1.get(j);
			}
			if (publication.equals(Publication)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("ResponsiBility:"+Middle1.get(j));
				PublicaTion = Middle1.get(j);
			}
			if (edition.equals(Edition)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("ResponsiBility:"+Middle1.get(j));
				EdiTion = Middle1.get(j);
			}
		}

		for (int i = 0; i < Middle2.size(); i++) {
			String summary = Middle2.get(i);
			String contents = Middle2.get(i);

			if (summary.equals(Summary)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("Publicationdate:"+Middle2.get(j));
				SumMary = Middle2.get(j);
			}
			if (contents.equals(Contents)) {
				// System.out.println("我已经到这里了");
				int j = i + 1;
				// System.out.println("ConTents:"+Middle2.get(j));
				ConTents = Middle2.get(j);
			}
		}
		// String TiTle=null;
//    	for(int i=0;i<Middle3.size();i++)
//		{
//			String title=Middle2.get(i);
//			
//			
//			if(title.equals(Title))
//			{
//				//System.out.println("我已经到这里了");
//				int j=i+1;
//			//System.out.println("Publicationdate:"+Middle2.get(j));
//				TiTle=Middle3.get(j);
//			}
//		}
		Elements element1 = doc.select("div[id=contributors]").select("div[class=section-body]").select("dd")
				.select("a");
		// Elements element2 =
		// doc.select("div[id=document]").select("h1").select("span[itemprop=name]");
		Element element4 = doc.select("div[id=subjects]").select("div[class=section-body]").select("dl")
				.first();
		String author = element1.text();
		String title = doc.select("div[id=document]").select("h1").select("span[itemprop=name]").text();
		Elements element7 = doc.select("div[class=dialog]");
		// System.out.println("我已经到这里了"+title);
		// String title;
//    	if(element2==null)
//    	{
//    		title = "";
//    	}
//    	else
//    	{
//    		title = element2.select("dd").text();
//    	}
		String subject;
		if (element4 == null) {
			subject = "";
		} else {
			subject = element4.select("dd").text();
		}
		//System.out.println("这里的标题是：===========" + title);
		if (!title.isEmpty()) {

			//System.out.println("插入网址==============");
			result.setAuthor(author);
			result.setSubject(subject);
			result.setTitle(title);
			// System.out.println("===============================测试4"+crawlMeta.getUrl());
			result.setUrl(crawlMeta.getUrl());
			result.setPublicationDate(PublicationDate);
			result.setResponsiBility(ResponsiBility);
			result.setPublicaTion(PublicaTion);
			result.setEdiTion(EdiTion);
			result.setSumMary(SumMary);
			result.setConTents(ConTents);
			result.setIsBn(IsBn);
			result.setRecordnum(crawlMeta.getRecordnum());
			result.setHtml(doc.toString());
			//System.out.println("这里是作者的名称html---------------------------------"+result.getHtml());
			// System.out.println("这里是作者的名称result---------------------------------");
			// System.out.println( title);
		     
			// System.out.println(list.size());
			int p = 0;
		} else {
			//System.out.println("不插入网址==============");
			return null;
		}
		list.add(result);
		return  list;
	

		/*
		 * System.out.println("Author:" +author); System.out.println("Title:" +title);
		 * System.out.println("Subject:" +subject);
		 */
	}
	
}
