package Mysql;

import java.beans.Customizer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

public class Mysqlfinishurl {

	 //根据自己的数据库地址修改
    static DataSource ds = MyJDBC.getDataSource("jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8");
    static QueryRunner qr = new QueryRunner(ds);
   
//	public  boolean executeCompare(CrawlMeta crawlMeta){
////        /*
////         * 定义一个Object数组，行列
////         * 3表示列数，根据自己的数据定义这里面的数字
////         * params[i][0]等是对数组赋值，这里用到集合的get方法
////         * 
////         */
////        Object[][] params = new Object[librarydata.size()][4];
////        for ( int i=0; i<params.length; i++ ){
////            params[i][0] = librarydata.get(i).getAuthor();
////            params[i][1] = librarydata.get(i).getSubject();
////            params[i][2] = librarydata.get(i).getTitle();
////            params[i][3] = librarydata.get(i).getUrl();
////           // System.out.println("检验数据！");
////            //System.out.println(params[i][0]);
////        }
////        qr.batch("insert into user (author, subject,title,url)"
////                + "values (?,?,?,?)", params);
////        System.out.println("执行数据库完毕！"+"成功插入数据："+librarydata.size()+"条");
//    	try {
//    		
//    	String sql =SqlSelcet(crawlMeta.getUrl()) ;//查询语句
//    	System.out.println("================================："+sql);
//    	   //！qr.update(sql);
//    	   //qr.conn.commit();//事务提交
//    	  // conn.setAutoCommit(true);// 更改jdbc事务的默认提交方式 
//    	 //Class type = Customizer.class;
//    	// CrawlMeta crawlMeta= new CrawlMeta();
//    	 List<String> list=new ArrayList<String>();
//    	 ResultSetHandler<List<resultCatch>> h = new BeanListHandler<resultCatch>(resultCatch.class);
//    	 List<resultCatch> obj=qr.query(sql,  h);
//    	 for (resultCatch car:obj) {
//    		 
//    		// System.out.println(car.getUrl());
//    		 System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+crawlMeta.getUrl());
//    		
//    		 if(car.getUrl().equals(crawlMeta.getUrl()))
//    		 {
//    			 System.out.println("============================数据库已存在此URL"+ car.getUrl()); 
//    			// System.out.println("============================数据库已存在此URL"); 
//    		 return false;
//    		 }
//         }
//    	 
//    	 
//    	
//    	  //创建取结果的列表，之所以使用列表，不用数组，因为现在还不知道结果有多少，不能确定数组长度，所有先用list接收，然后转为数组
////    	   while (qr.next()) {//如果有数据，取第一列添加如list
////    	    list.add(rs.getString(1));
////    	   }
////    	   if(list != null && list.size()>0){//如果list中存入了数据，转化为数组
////    	                               String[] arr=new String[list.size()];//创建一个和list长度一样的数组
////    	    for(int i=0;i<list.size();i++){
////    	     arr[i]=list.get(i);//数组赋值了。
////    	    }
////    	                               //输出数组
////    	                              for(int i=0;i<arr.length;i++){
////    	                                   System.out.println("数据库的URL=============="+arr[i]);
////    	                                 }
////    	   }
//    	   
//    	  } catch (Exception e) {
//    	   // TODO Auto-generated catch block   
//    	   e.printStackTrace();
//    	  }
//    	return true;
//    }
	
	
	public String SqlSelcet(String url) {
		//String sql=null;
		String SqlLibrary=null;
		String regurl=Match(url);
		switch (regurl) {
		case "https://searchworks.stanford.edu/view/.*":
			 SqlLibrary="select * from stanfordlibrary ";
			break; // 可选
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":
			 SqlLibrary="select * from cambridgelibrary ";
			break; // 可选
		// 你可以有任意数量的case语句
		
		}

		return SqlLibrary;
	}
	
	
	
	public String SqlSelcetMaxid(String url) {
		//String sql=null;
		String SqlMaxcid=null;
		String regurl=Match(url);
		
		switch (regurl) {
		case "https://searchworks.stanford.edu/view/.*":
			SqlMaxcid="select recordnum from stanfordlibrary where id=(select Max(id) from stanfordlibrary)";
			//根据正则匹配，选择返回sql语句
			break; // 可选
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":
			SqlMaxcid="select recordnum from cambridgelibrary where id=(select Max(id) from cambridgelibrary)";
			break; // 可选
		// 你可以有任意数量的case语句
		case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":
			SqlMaxcid="select recordnum from Californialibrary where id=(select Max(id) from Californialibrary)";
			break; // 可选
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":
			SqlMaxcid="select recordnum from hkulibrary where id=(select Max(id) from hkulibrary)";
			break; // 可选
			
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":
			SqlMaxcid="select recordnum from cuhklibrary where id=(select Max(id) from cuhklibrary)";
			break; // 可选
			
		case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":
			SqlMaxcid="select recordnum from oxfordlibrary where id=(select Max(id) from oxfordlibrary)";
			break; // 可选
			
		case "https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*":
			SqlMaxcid="select recordnum from tokyolibrary where id=(select Max(id) from tokyolibrary)";
			break; // 可选
		case "https://clio.columbia.edu/catalog/.*":
			SqlMaxcid="select recordnum from cliolibrary where id=(select Max(id) from cliolibrary)";
			break; // 可选
		case "https://lib.mit.edu/record/cat00916a/mit.00.*":
			SqlMaxcid="select recordnum from mitlibrary where id=(select Max(id) from mitlibrary)";
			break; // 可选
		case "https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*":
			SqlMaxcid="select recordnum from yalelibrary where id=(select Max(id) from yalelibrary)";
			break; // 可选
		case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":
			SqlMaxcid="select recordnum from harvardlibrary where id=(select Max(id) from harvardlibrary)";
			break; // 可选
		case "http://pi.lib.uchicago.edu/1001/cat/bib/.*":
			SqlMaxcid="select recordnum from chicagolibrary where id=(select Max(id) from chicagolibrary)";
			break; // 可选
		case "http://catalogue.libraries.london.ac.uk/record=b.*":
			SqlMaxcid="select recordnum from londonlibrary where id=(select Max(id) from londonlibrary)";
			break; // 可选
		case "http://morris.law.yale.edu/record=b.*":
			SqlMaxcid="select recordnum from yalelawlibrary where id=(select Max(id) from yalelawlibrary)";
			break; // 可选
		case "https://newcatalog.library.cornell.edu/catalog/.*":
			SqlMaxcid="select recordnum from cornelllibrary where id=(select Max(id) from cornelllibrary)";
			break; // 可选
		}

		return SqlMaxcid;
	}
	
	
	public String  Match(String url) {
		String regurl=null;
		//System.out.println(url+"============================================");
		if(url.matches("https://searchworks.stanford.edu/view/.*")) {
			regurl="https://searchworks.stanford.edu/view/.*";
			
		}
		if(url.matches("http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*")) {
			regurl="http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*";
		}
		if(url.matches("https://catalog.library.ucla.edu/vwebv/holdingsInfo\\?bibId=.*")) {
			regurl="https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*";
		}
		//System.out.println("reg=========================="+url);
		if(url.matches("https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*")) {
			regurl="https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*";
		}
	
		
		if(url.matches("https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*")) {
			regurl="https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*";
		}
		if(url.matches("http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*")) {
			regurl="http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*";
		}
		
		if(url.matches("https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/\\?bibid=200.*")) {
			regurl="https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*";
		}
		if(url.matches("https://clio.columbia.edu/catalog/.*")) {
			regurl="https://clio.columbia.edu/catalog/.*";
		}
		if(url.matches("https://lib.mit.edu/record/cat00916a/mit.00.*")) {
			regurl="https://lib.mit.edu/record/cat00916a/mit.00.*";
		}
		if(url.matches("https://orbis.library.yale.edu/vwebv/holdingsInfo\\?bibId=.*")) {
			regurl="https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*";
		}
		if(url.matches("https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*")) {
			regurl="https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*";
		}
		if(url.matches("http://pi.lib.uchicago.edu/1001/cat/bib/.*")) {
			regurl="http://pi.lib.uchicago.edu/1001/cat/bib/.*";
		}
		if(url.matches("http://catalogue.libraries.london.ac.uk/record=b.*")) {
			regurl="http://catalogue.libraries.london.ac.uk/record=b.*";
		}
		if(url.matches("http://morris.law.yale.edu/record=b.*")) {
			regurl="http://morris.law.yale.edu/record=b.*";
		}
		if(url.matches("https://newcatalog.library.cornell.edu/catalog/.*")) {
			regurl="https://newcatalog.library.cornell.edu/catalog/.*";
		}
		
		return regurl;
	}
	
	public String Reg(String reg) {
		String library=null;
		
		switch(reg) {
		
		
		case "https://searchworks.stanford.edu/view/.*":{
			library="Stanford";
			break;
		}
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":{
			library="Cambridge";
			break;
		}
		case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":{
			library="California";
			break;
		}
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":{
			library="HKU";
			break;
		}
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":{
			library="CUHK";
			break;
		}
		case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":{
			library="Oxford";
			break;
		}
		case "https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*":{
			library="Tokyo";
			break;
		}
		case "https://clio.columbia.edu/catalog/.*":{
			library="Clio";
			break;
		}
		case "https://lib.mit.edu/record/cat00916a/mit.00.*":{
			library="Mit";
			break;
		}
		case "https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*":{
			library="Yale";
			break;
		}
		case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":{
			library="Harvard";
			break;
		}
		case "http://pi.lib.uchicago.edu/1001/cat/bib/.*":{
			library="Chicago";
			break;
		}
		case "http://catalogue.libraries.london.ac.uk/record=b.*":{
			library="London";
			break;
		}
		case "http://morris.law.yale.edu/record=b.*":{
			library="Yalelaw";
			break;
		}
		case "https://newcatalog.library.cornell.edu/catalog/.*":{
			library="Cornell";
			break;
		}
		}
		
		return library;
		
	}
	
	public int  RecordNum(String url){
		List<String> list=new ArrayList<String>();
		int i=1;
		
		try {
			
			String sql =SqlSelcetMaxid(url)  ;//查询语句
			//System.out.println("============================"+sql);
		  
		  	 ResultSetHandler<List<resultCatch>> h = new BeanListHandler<resultCatch>(resultCatch.class);
		  	 List<resultCatch> obj=qr.query(sql,  h);
		  	 for (resultCatch car:obj) {
		  		i= car.getRecordnum();
		  		// System.out.println(car.getUrl());
		  		 //System.out.println(crawlMeta.getUrl());
		  		//System.out.println("============================"+i);
		  		
		       }
		}catch (Exception e) {
		  	   // TODO Auto-generated catch block   
		  	   e.printStackTrace();
		  	  }
		 return i;
	}
	
	
	
	
	
	
	
	
	public List Extracturl(String url){
//      /*
//       * 定义一个Object数组，行列
//       * 3表示列数，根据自己的数据定义这里面的数字
//       * params[i][0]等是对数组赋值，这里用到集合的get方法
//       * 
//       */
//      Object[][] params = new Object[librarydata.size()][4];
//      for ( int i=0; i<params.length; i++ ){
//          params[i][0] = librarydata.get(i).getAuthor();
//          params[i][1] = librarydata.get(i).getSubject();
//          params[i][2] = librarydata.get(i).getTitle();
//          params[i][3] = librarydata.get(i).getUrl();
//         // System.out.println("检验数据！");
//          //System.out.println(params[i][0]);
//      }
//      qr.batch("insert into user (author, subject,title,url)"
//              + "values (?,?,?,?)", params);
//      System.out.println("执行数据库完毕！"+"成功插入数据："+librarydata.size()+"条");
		 List<String> list=new ArrayList<String>();
  	try {
  		
  	String sql = SqlSelcet(url);//查询语句

  	
  	   //！qr.update(sql);
  	   //qr.conn.commit();//事务提交
  	  // conn.setAutoCommit(true);// 更改jdbc事务的默认提交方式 
  	 //Class type = Customizer.class;
  	// CrawlMeta crawlMeta= new CrawlMeta();
  
  	 ResultSetHandler<List<resultCatch>> h = new BeanListHandler<resultCatch>(resultCatch.class);
  	 List<resultCatch> obj=qr.query(sql,  h);
  	 for (resultCatch car:obj) {
  		
  		 list.add(car.getUrl());
  		// System.out.println(car.getUrl());
  		 //System.out.println(crawlMeta.getUrl());
  		
  		
       }
  	 
 
  	
  	  //创建取结果的列表，之所以使用列表，不用数组，因为现在还不知道结果有多少，不能确定数组长度，所有先用list接收，然后转为数组
//  	   while (qr.next()) {//如果有数据，取第一列添加如list
//  	    list.add(rs.getString(1));
//  	   }
//  	   if(list != null && list.size()>0){//如果list中存入了数据，转化为数组
//  	                               String[] arr=new String[list.size()];//创建一个和list长度一样的数组
//  	    for(int i=0;i<list.size();i++){
//  	     arr[i]=list.get(i);//数组赋值了。
//  	    }
//  	                               //输出数组
//  	                              for(int i=0;i<arr.length;i++){
//  	                                   System.out.println("数据库的URL=============="+arr[i]);
//  	                                 }
//  	   }
  	   
  	  } catch (Exception e) {
  	   // TODO Auto-generated catch block   
  	   e.printStackTrace();
  	  }
	 return list;
	}


	
	
	
	
    
}
