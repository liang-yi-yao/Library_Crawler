package com.ruc.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




public class CambridgeLibraryFilter {
	
@Getter
@Setter
	resultCatch result = new resultCatch();
	List<resultCatch> list = new ArrayList<>();
	List<String> jsonobjection = new ArrayList<String>();
	public List<resultCatch> CambridgeLibraryfilter(CrawlMeta crawlMeta, CrawlResult crawlResult) {
		String body = crawlResult.getJson();
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
					//System.out.println(title+"oooooooooooooooooooooooooooooooooo");
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
				}
					
				 note=lds04+lds05;
				
//			 title=jsonOne.getJSONObject("display").getString("title");
//			// System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyy"+title);
//			 //List<Data>  listdata=JSONObject.parseArray(data, Data.class);
//			 author=jsonOne.getJSONObject("display").getString("creator");
//			 PublicationDate=jsonOne.getJSONObject("display").getString("creationdate");
//			 subject=jsonOne.getJSONObject("display").getString("subject");
//			 format=jsonOne.getJSONObject("display").getString("format");
//			 language=jsonOne.getJSONObject("display").getString("language");
//			 type=jsonOne.getJSONObject("display").getString("type");
//			 contributor=jsonOne.getJSONObject("display").getString("contributor");
//			 PublicaTion=jsonOne.getJSONObject("display").getString("publisher");
//			 if(jsonOne.getJSONObject("display").getString("lds04")!=null)
//			 lds04=jsonOne.getJSONObject("display").getString("lds04");
//			 else
//				 lds04=null;	 
//			// if(jsonOne.getJSONObject("display").getString("lds05")!=null)
//			 lds05=jsonOne.getJSONObject("display").getString("lds05");
//			System.out.println(lds05+"oooooooooooooooooooooooooooooo");
//			 node=lds04+lds05;
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
//			 JSONArray titleArray = jsonTwo.getJSONArray("title");
//			  title=titleArray.get(0).toString(); 
//			 JSONArray creatorArray = jsonTwo.getJSONArray("creator");
//			  author=creatorArray.get(0).toString();
			 
//			 JSONArray creationdateArray = jsonTwo.getJSONArray("creationdate");
//			 PublicationDate=creationdateArray.get(0).toString();
			 
//			 JSONArray subjectArray = jsonTwo.getJSONArray("subject");
//			  subject=subjectArray.get(0).toString();
			 
//			 JSONArray formatArray = jsonTwo.getJSONArray("format");
//			  format=formatArray.get(0).toString();
			 
//			 JSONArray languageArray = jsonTwo.getJSONArray("language");
//			  language=languageArray.get(0).toString();
			 
//			 JSONArray typeArray = jsonTwo.getJSONArray("type");
//			  type=typeArray.get(0).toString();
			 
//			 JSONArray contributorArray = jsonTwo.getJSONArray("contributor");
//			  contributor=contributorArray.get(0).toString();
			 
//			 JSONArray publisherArray = jsonTwo.getJSONArray("publisher");
//			  PublicaTion=publisherArray.get(0).toString();
			 
//			 JSONArray lds04Array = jsonTwo.getJSONArray("lds04");
//			  lds04=lds04Array.get(0).toString();
//			  lds05=lds04Array.get(1).toString();
//			  node=lds04+lds05;
//			 //System.out.println(title);
			
		
		
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
			
		return list;
		
	}
}
