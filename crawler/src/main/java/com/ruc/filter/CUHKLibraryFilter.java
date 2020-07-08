package com.ruc.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

public class CUHKLibraryFilter {
	@Getter
	@Setter
		resultCatch result = new resultCatch();
		List<resultCatch> list = new ArrayList<>();
		List<String> jsonobjection = new ArrayList<String>();

	public List<resultCatch> CUHKLibraryFilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		String body = crawlResult.getJson();
		String isbn=null;
		String isbnt=null;
		String title=null;
		String creator=null;
		String subject=null;
		String format=null;
		String language=null;
		String type=null;
		String contributor=null;
		String publisher=null;
		String note=null;
		String author=null;
		String PublicationDate=null;
		String PublicaTion=null;
		String lds04=null;
		String lds05=null;
		JSONObject jsonObject1 = JSONObject.fromObject(body);
		
	
		
			JSONObject jsonObject = JSONObject.fromObject(body);
			 JSONObject jsonOne = jsonObject.getJSONObject("pnx");
			 JSONObject jsonTwo= jsonOne.getJSONObject("display");
			 
			 String key=null;
				Iterator<String> iter = jsonTwo.keys();
				while(iter.hasNext())
				{
					jsonobjection.add(iter.next());
				//System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyy"+key);
			 
				}
			 
			 
				for (int i = 0; i < jsonobjection.size(); i++) {
					if(jsonobjection.get(i).equals("title")) {
						title=jsonOne.getJSONObject("display").getJSONArray("title").get(0).toString();
						//System.out.println("============================"+title);
					}
					if(jsonobjection.get(i).equals("creator"))
						author=jsonOne.getJSONObject("display").getJSONArray("creator").get(0).toString();
					if(jsonobjection.get(i).equals("creationdate"))
						 PublicationDate=jsonOne.getJSONObject("display").getJSONArray("creationdate").get(0).toString();
					if(jsonobjection.get(i).equals("subject"))
						subject=jsonOne.getJSONObject("display").getJSONArray("subject").get(0).toString();
					if(jsonobjection.get(i).equals("format"))
						 format=jsonOne.getJSONObject("display").getJSONArray("format").get(0).toString();
					if(jsonobjection.get(i).equals("format"))
						format=jsonOne.getJSONObject("display").getJSONArray("format").get(0).toString();
					if(jsonobjection.get(i).equals("language"))
						 language=jsonOne.getJSONObject("display").getJSONArray("language").get(0).toString();
					if(jsonobjection.get(i).equals("type"))
						 type=jsonOne.getJSONObject("display").getJSONArray("type").get(0).toString();
					if(jsonobjection.get(i).equals("contributor"))
						contributor=jsonOne.getJSONObject("display").getJSONArray("contributor").get(0).toString();
					if(jsonobjection.get(i).equals("lds04"))
						 lds04=jsonOne.getJSONObject("display").getJSONArray("lds04").get(0).toString();
					if(jsonobjection.get(i).equals("lds05"))
						lds05=jsonOne.getJSONObject("display").getJSONArray("lds05").get(0).toString();
					if(jsonobjection.get(i).equals("publisher"))
						publisher=jsonOne.getJSONObject("display").getJSONArray("publisher").get(0).toString();
					if(jsonobjection.get(i).equals("identifier")) {
					 	isbnt=jsonOne.getJSONObject("display").getJSONArray("identifier").get(0).toString();
					 	if(isbnt.isEmpty()) {
					     isbn= isbnt.replaceAll("[^0-9\\u4E00-\\u9FA5]", "");
					 	}
					}
				}
					
				 note=lds04+lds05;				
		
		 if (title !=null) {

				//System.out.println("插入网址==============");
				result.setAuthor(author);
				result.setSubject(subject);
				result.setTitle(title);
				// System.out.println("===============================测试4"+crawlMeta.getUrl());
				result.setUrl(crawlMeta.getUrl());
				result.setPublicationDate(PublicationDate);
				result.setContributor(contributor);
				result.setPublicaTion(PublicaTion);
				result.setFormat(format);
				result.setLanguage(language);
				result.setType(type);
				//result.setIsBn(IsBn);
				result.setRecordnum(crawlMeta.getRecordnum());
				result.setHtml(body);
				result.setNote(note);
				result.setPublicaTion(publisher);
				result.setIsBn(isbn);
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
			//System.out.println("================================"+list);
		return list;
	}

}
