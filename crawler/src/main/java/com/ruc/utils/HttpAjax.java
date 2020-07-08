package com.ruc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.util.Elements;

import org.apache.bcel.classfile.Constant;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.gargoylesoftware.htmlunit.javascript.host.Document;

import Mysql.resultCatch;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;


public class HttpAjax {
	
	public static void main(String[] args) throws Exception{
		List<String> jsonobjection = new ArrayList<String>();
		resultCatch result = new resultCatch();
		List<resultCatch> list = new ArrayList<>();
		//链接url，并获取Document对象
//		org.jsoup.nodes.Document document=Jsoup.connect("http://list.suning.com/0-243505-0-0-0-0-0-0-0-0-18062.html").get();
//		//根据HTML页面可以知道，商品价格信息存在class为"res-info"的div中，获取这个div
//		//根据对ajax请求进行分析可以知道，ajax传入的决定返回价格的数据存在于"price"这个div中的"datasku"这个属性中（分析就省略了，无非就是找规律啦！）
//		org.jsoup.select.Elements elements=document.getElementsByClass("res-info");
//		        for(int i=0;i<elements.size();i++){ 
//		        //然后获得datasku的属性值就行了，这里就只打印出来        System.out.println(elements.get(i).getElementsByClass("price").get(0).attr("datasku"));
//		        }
//			 org.jsoup.nodes.Document  document1=Jsoup.connect("http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/v1/rta/44CAM_PROD?")
//				                .ignoreContentType(true)
//				                .data("query", "Java")
//				                .userAgent("Mozilla")
//				               // .cookie("auth", "token")
//				                .cookie("auth", "token")
//				                .timeout(30000)
//				                .get();
//				      
//			 //String body = Jsoup.connect("http://ds.suning.cn/ds/generalForTile/000000000133537397-9173-2-0000000000-1--ds000000000.jsonp").ignoreContentType(true).execute().body(); 
//			 							
//			 System.out.println(document1);
								
		//http://idiscover.lib.cam.ac.uk/primo-explore/fulldisplay?docid=44CAM_ALMA21393424180003606&vid=44CAM_PROD&search_scope=SCOP_BOOKS&tab=cam_lib_coll&lang=en_US&context=L
			                          //http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21393424180003606?vid=44CAM_PROD&lang=en_US&search_scope=SCOP_BOOKS	
		Response res = Jsoup.connect("http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph020880347?vid=SOLO&lang=en_US&search_scope=LSCOP_OX")
									 // http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph001362506?vid=SOLO&lang=en_US&search_scope=LSCOP_ALL&adaptor=Local%20Search%20Engine&isFrbr=true
				//.header("Accept", "application/json, text/plain, */*")
				//.header("Accept-Encoding", "gzip, deflate")
				//.header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
				//.header("Content-Type", "application/json")
				//.header("Cookie", "JSESSIONID=E0FE6A209A789426C8546A15AFBD026A; UqZBpD3n3iPLdHQq9xqtuXrTXegc+YkfcdXJus-Z-FzIAB7F=v1poo+gw__LmF; JSESSIONID=6AAAEED9685B3FF01DEE1EB0EF7C7FC6; TBMCookie_3555100366678004564=8894400015885921909ZTYEte75W5x8Ibjk3+OFN8Gsps=; ___utmvm=###########")
				//.header("Origin", "http://idiscover.lib.cam.ac.uk")
				//.header("Host", "idiscover.lib.cam.ac.uk")
				//.header("Content-Type", "application/json")
				//.header("Referer", "http://idiscover.lib.cam.ac.uk/primo-explore/fulldisplay?docid=44CAM_ALMA21393424200003606&vid=44CAM_PROD&search_scope=SCOP_BOOKS&tab=cam_lib_coll&lang=en_US&context=L")				
				//                  http://idiscover.lib.cam.ac.uk/primo-explore/fulldisplay?docid=44CAM_ALMA21393424180003606&vid=44CAM_PROD&search_scope=SCOP_BOOKS&tab=cam_lib_coll&lang=en_US&context=L
				//.header("Authorization", "Bearer eyJraWQiOiJwcmltb0V4cGxvcmVQcml2YXRlS2V5LU9YIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiJQcmltbyIsImp0aSI6IiIsImV4cCI6MTU5Mzc2NjQzNSwiaWF0IjoxNTkzNjgwMDM1LCJ1c2VyIjoiYW5vbnltb3VzLTA3MDJfMDg1MzU1IiwidXNlck5hbWUiOm51bGwsInVzZXJHcm91cCI6IkdVRVNUIiwiYm9yR3JvdXBJZCI6bnVsbCwidWJpZCI6bnVsbCwiaW5zdGl0dXRpb24iOiJPWCIsInZpZXdJbnN0aXR1dGlvbkNvZGUiOiJPWCIsImlwIjoiMTIwLjg0LjExLjQ0IiwicGRzUmVtb3RlSW5zdCI6bnVsbCwib25DYW1wdXMiOiJmYWxzZSIsImxhbmd1YWdlIjoiZW5fVVMiLCJhdXRoZW50aWNhdGlvblByb2ZpbGUiOiIiLCJ2aWV3SWQiOiJTT0xPIiwiaWxzQXBpSWQiOm51bGwsInNhbWxTZXNzaW9uSW5kZXgiOiIiLCJqd3RBbHRlcm5hdGl2ZUJlYWNvbkluc3RpdHV0aW9uQ29kZSI6Ik9YIn0")
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
				.timeout(10000).ignoreContentType(true).execute();//.get();
		
	//	String Jsion={"browseShelfItems":[{"recordId":"44CAM_ALMA21449909440003606","date":"2010","callNumber":"WAT S [mon]","author":"Carlos Mondragón; Museo Nacional de Antropología (Mexico); ","title":"Moana : culturas de las islas del Pacífico \/ [coordinación general, Carlos Mondragón].","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9786074840728\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21391817120003606","date":"1996","callNumber":"WATERS","author":"Fiona Waters; ","title":"The poetry book : poems for children \/ chosen by Fiona Waters ; illustrated by Caroline Crossland.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=185881183X\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21400313930003606","date":"2001","callNumber":"WATERS","author":"Fiona Waters; ","title":"Poems from many cultures \/ compiled by Fiona Waters.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0237521040\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21403620060003606","date":"1996","callNumber":"WATERS","author":"Fiona Waters; ","title":"Glitter when you jump : the seven ages of woman : poems \/ chosen by Fiona Waters ; illustrated by Amanda Harvey.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0330341049\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21405816060003606","date":"2017","callNumber":"WATSON","author":"Kate Watson (Young adult writer), author.; ","title":"Seeking Mansfield \/ Kate Watson.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781635830026\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21510047470003606","date":"1990","callNumber":"WATSON","author":"Richard Jesse Watson author.; ","title":"Tom Thumb \/ retold and illustrated by Richard Jesse Watson.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0744540070\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21584097110003606","date":"2017","callNumber":"WATSON","author":"Renée Watson author.; ","title":"This side of home \/ Renée Watson.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781619639300\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21462657040003606","date":"1997","callNumber":"Watt archive, Small Collections box 25, Room 17","author":"Lakshmi Mazumdar; ","title":"A dream came true \/ edited by Lakshmi Mazumdar.","thumbnails":{"url":["no_cover"]}},{"recordId":"44CAM_ALMA21462659820003606","date":"1938","callNumber":"Watt archive, Small collections box 25, Room 17","author":"Narendrakumāra. Jaina; ","title":"How scouting : or a short history of scout-movement \/ Narendra Kumar Jain.","thumbnails":{"url":["no_cover"]}},{"recordId":"44CAM_ALMA21429293350003606","date":"2000","callNumber":"WAV Cha","author":"Robert. Chametzky; ","title":"Phrase structure : from GB to minimalism \/ Robert A. Chametzky.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0631201580\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67632266","date":"1964","callNumber":"WAW Bac","author":"Emmon W. Bach 1929- author.; ","title":"An introduction to transformational grammars \/ Emmon Bach, The University of Texas.","thumbnails":{"url":["no_cover"]}},{"recordId":"dedupmrg67493592","date":"1957","callNumber":"WAW Cho\/1","author":"Noam. Chomsky; ","title":"Syntactic structures \/ by Noam Chomsky.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9027933855\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21510105710003606","date":"1973","callNumber":"WAW Gri","author":"John Grinder author.; ","title":"Guide to transformational grammar : history, theory, practice \/ John T. Grinder, University of California, Santa Cruz, Suzette Haden Elgin California State University at San Diego.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0030801265\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg68139121","date":"1970","callNumber":"WAW Jac","author":"Roderick A. Jacobs; ","title":"English transformational grammar \/ Roderick A. Jacobs, Peter S. Rosenbaum ; with an epilogue by Paul M. Postal.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0602216087\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg69153057","date":"1966","callNumber":"WAW Kou","author":"Andreas. Koutsoudas; ","title":"Writing transformational grammars : an introduction.","thumbnails":{"url":["no_cover"]}},{"recordId":"dedupmrg69047969","date":"1999","callNumber":"WAW Ouh","author":"Jamal Ouhalla 1961-; ","title":"Introducing transformational grammar : from principles and parameters to minimalism \/ Jamal Ouhalla.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780340740361\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21518683890003606","date":"1999","callNumber":"WAW Q Yam","author":"Mutsumi Yamamoto 1942-; ","title":"Animacy and reference : a cognitive approach to corpus linguistics \/ Mutsumi Yamamoto.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9027230498\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg66983304","date":"1996","callNumber":"WAW Rad","author":"Andrew. Radford; ","title":"Transformational grammar : a first course \/ Andrew Radford.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0521347505\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg69381509","date":"1988","callNumber":"WAW Rad","author":"Andrew. Radford; ","title":"Transformational grammar : a first course \/ Andrew Radford.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0521347505\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67692856","date":"2002","callNumber":"WAX O McC","author":"John J. McCarthy 1953-; ","title":"A thematic guide to optimality theory \/ John J. McCarthy.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780521791946\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21393424180003606","date":"2001","callNumber":"WB (2)","author":"Joseph O'Connor 1948 September 12-; ","title":"Way of NLP \/ Joseph O'Connor and Ian McDermott.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=0007110200\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21437407260003606","date":"1947","callNumber":"WB (2)","author":"Alfred Sherwood Romer 1894-1973.; ","title":"Review of the Labyrinthodontia \/ by Alfred Sherwood Romer.","thumbnails":{"url":["no_cover"]}},{"recordId":"44CAM_ALMA21436741970003606","date":"2004","callNumber":"WB (3)","author":"Robert M. Sapolsky author.; ","title":"Why zebras don't get ulcers \/ Robert M. Sapolsky.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780805073690\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg68991049","date":"1997","callNumber":"WB (4)","author":"James W. Pennebaker; ","title":"Opening up : the healing power of expressing emotions \/ James W. Pennebaker.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781572302389\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67577691","date":"2009","callNumber":"WB (5, 5B)","author":"Richard H. Thaler 1945- author; ","title":"Nudge : improving decisions about health, wealth and happiness \/ Richard H. Thaler and Cass R. Sunstein.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780141040011\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67666546","date":"2005","callNumber":"WB (6)","author":"Kate. Fox; ","title":"Watching the English : the hidden rules of English behaviour \/ Kate Fox.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780340818862\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21504767130003606","date":"2011","callNumber":"WB (7)","author":"Steve Peters (Sports psychiatrist), author.; ","title":"The chimp paradox : how our impulses and emotions can determine success and happiness and how we can control them \/ by Steve Peters.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780091935580\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21416617470003606","date":"2009","callNumber":"WB (8)","author":"Paul McKenna 1963-; ","title":"I can make you sleep \/ Paul McKenna ; edited by Hugh Willbourn.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780593055380\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21432837290003606","date":"2010","callNumber":"WB (9)","author":"Colin A. Espie author.; ","title":"Overcoming insomnia and sleep problems : a self-help guide using cognitive behavioral techniques \/ Colin A. Espie.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=1845290704\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67239321","date":"2007","callNumber":"WB (10)","author":"Gillian. Riley; ","title":"How to stop smoking and stay stopped for good \/ Gillian Riley.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780091917036\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67603575","date":"2011","callNumber":"WB (11)","author":"Julia Buckroyd author.; ","title":"Understanding your eating : how to eat and not worry about it \/ Julia Buckroyd.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780335241972\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21519617560003606","date":"2003","callNumber":"WB (12)","author":"Dorothy. Rowe; ","title":"Depression : the way out of your prison \/ Dorothy Rowe.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781583912867\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg67303327","date":"2011","callNumber":"WB (13)","author":"Roger Baker 1946-; ","title":"Understanding panic attacks and overcoming fear \/ Roger Baker.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780745955452\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21419791010003606","date":"2005","callNumber":"WB (14)","author":"Mary. Burgess; ","title":"Overcoming chronic fatigue : a self-help guide using cognitive behavioral techniques \/ Mary Burgess with Trudie Chalder.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781849011327\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21519235830003606","date":"2009","callNumber":"WB (15)","author":"William Davies 1950-; ","title":"Overcoming anger and irritability : a self-help guide using cognitive behavioral techniques \/ William Davies.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781849011310\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21516108850003606","date":"1999","callNumber":"WB (16)","author":"Gillian Butler 1942- author.; ","title":"Overcoming social anxiety and shyness : a self-help guide using cognitive behavioral techniques \/ Gillian Butler.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781849010009\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21435360800003606","date":"2008","callNumber":"WB (17)","author":"Tony. Attwood; ","title":"The complete guide to Asperger's syndrome \/ Tony Attwood.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9781843106692\/SC.JPG&client=cambridgeh"]}},{"recordId":"dedupmrg66413799","date":"2002","callNumber":"WB (18)","author":"Susan Forward author.; ","title":"Toxic parents : overcoming their hurtful legacy and reclaiming your life \/ Susan Forward with Craig Buck.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780553381405\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21500908340003606","date":"1995","callNumber":"WB (19)","author":"Dennis. Greenberger; ","title":"Mind over mood : change how you feel by changing the way you think \/ Dennis Greenberger, Christine A. Padesky ; foreword by Aaron T. Beck.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780898621280\/SC.JPG&client=cambridgeh"]}},{"recordId":"44CAM_ALMA21392136300003606","date":"2010","callNumber":"WB (20)","author":"John. Franklin; ","title":"How to be a better cyclist : the essential guide.","thumbnails":{"url":["https:\/\/proxy-eu.hosted.exlibrisgroup.com\/exl_rewrite\/syndetics.com\/index.aspx?isbn=9780956223920\/SC.JPG&client=cambridgeh"]}}],"nextTerm":"wb (000000000021)","backTerm":"war po bak","currTerm":"144CAMwb+%28000000000002%29","browseField":"browse_callnumber.1.V2","beaconO22":"758"};
		//String headersOne  = res.header("Authorization");
		 
	
		//System.out.println("++++++++++++++++++++++++++++++++"+headersOne);
		String body = res.body();
		//System.out.println("======================="+body);
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
						//System.out.println("======================="+title);
					//System.out.println(title+"oooooooooooooooooooooooooooooooooo");
					}
					if(jsonobjection.get(i).equals("creator"))
						author=jsonOne.getJSONObject("display").getJSONArray("creator").get(0).toString();
					//System.out.println("======================="+author);
					if(jsonobjection.get(i).equals("creationdate"))
						 PublicationDate=jsonOne.getJSONObject("display").getJSONArray("creationdate").get(0).toString();
					//System.out.println("======================="+PublicationDate);
					if(jsonobjection.get(i).equals("subject"))
						subject=jsonOne.getJSONObject("display").getJSONArray("subject").get(0).toString();
					//System.out.println("======================="+subject);
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
				 if (title !=null) {

						//System.out.println("插入网址==============");
						result.setAuthor(author);
						result.setSubject(subject);
						result.setTitle(title);
						//System.out.println("===============================测试4"+result.getTitle());
						//result.setUrl(crawlMeta.getUrl());
						result.setPublicationDate(PublicationDate);
						result.setContributor(contributor);
						result.setPublicaTion(PublicaTion);
						result.setFormat(format);
						result.setLanguage(language);
						result.setType(type);
						//result.setIsBn(IsBn);
						//result.setRecordnum(crawlMeta.getRecordnum());
						result.setHtml(body);
						result.setNote(note);
						//System.out.println("这里是作者的名称html---------------------------------"+result.getHtml());
						// System.out.println("这里是作者的名称result---------------------------------");
						// System.out.println( title);
					     
						// System.out.println(list.size());
						//int p = 0;
					} 
				 PrintStream ps = new PrintStream("C:\\Users\\Administrator\\Desktop\\书目系统\\打印.txt");
				  System.setOut(ps);
				 System.out.println("result===================================="+result);
					//list.add(result);
					//System.out.println("list===================================="+list);
	}

}
