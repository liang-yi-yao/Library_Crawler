package Mysql;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import lombok.Getter;
import lombok.Setter;

public class MysqlControl {
	String Table=null;//返回插入sql语句
	String sql=null;
	String values=null;
	resultCatch result=new resultCatch();
	Object[][] params;
	public static int countNum=1;

    //根据自己的数据库地址修改
    static DataSource ds = MyJDBC.getDataSource("jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8");
    static QueryRunner qr = new QueryRunner(ds);
    //第一类方法
    public static void executeUpdate(String sql){
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //第二类数据库操作方法
    public  void executeInsert(List<resultCatch> librarydata,CrawlMeta crawlMeta) throws SQLException {
        /*
         * 定义一个Object数组，行列
         * 3表示列数，根据自己的数据定义这里面的数字
         * params[i][0]等是对数组赋值，这里用到集合的get方法
         * 
         */
    	
//        Object[][] params = new Object[librarydata.size()][12];
//        for ( int i=0; i<params.length; i++ ){
//            params[i][0] = librarydata.get(i).getAuthor();
//            params[i][1] = librarydata.get(i).getSubject();
//            params[i][2] = librarydata.get(i).getTitle();
//           // System.out.println("正在2测试"+librarydata.get(i).getUrl());
//            params[i][3] = librarydata.get(i).getUrl();
//            params[i][4] = librarydata.get(i).getPublicationDate();
//            params[i][5] = librarydata.get(i).getResponsiBility();
//            params[i][6] = librarydata.get(i).getPublicaTion();
//            params[i][7] = librarydata.get(i).getEdiTion();
//            params[i][8] = librarydata.get(i).getSumMary();
//            params[i][9] = librarydata.get(i).getConTents();
//            params[i][10] = librarydata.get(i).getIsBn();
//            params[i][11] = librarydata.get(i).getRecordnum();
//            //System.out.println("================================="+params[i][11]);
//          //  System.out.println("执行数据库完毕！"+"成功插入数据："+librarydata.get(i).getUrl());
//           // System.out.println("检验数据！");
//            //System.out.println(params[i][0]);
//        }
       
    	
    	//System.out.println("==============================="+librarydata);
    	//Object[][] params = new Object[librarydata.size()][result.getFiledName(result)];
      
       SqlInsert(crawlMeta, librarydata);
        //System.out.println("insert into "+ Table+ " (author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum)"
          //     + "values (?,?,?,?,?,?,?,?,?,?,?,?)");
       //System.out.println("========================="+params);
       if(params!=null) {
    	   
    	  // System.out.println("========================="+"insert into "+ Table+ sql + values);
        qr.batch("insert into "+ Table+ sql + values, params);
       }
       else
    	   return;
        //qr.batch("insert into user (title)"
        //        + "values (?)", params);
        System.out.println("执行数据库完毕！"+"成功插入数据："+countNum+"条");
        countNum++;
    }
    

    public void SqlInsert(CrawlMeta crawlMeta,List<resultCatch> librarydata) {
    	//String Table=null;//返回插入sql语句
    	//String sql=null;
    	//String values=null;
    	//Mysqlfinishurl match=new Mysqlfinishurl();
    	String regurl= crawlMeta.getReg();
    	switch (regurl) {
		case "https://searchworks.stanford.edu/view/.*":
			{Table="stanfordlibrary";
			sql=" (Html,author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum)";
			values="values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			params = new Object[librarydata.size()][13];
			
	        for ( int i=0; i<params.length; i++ ){

	        	params[i][0] = librarydata.get(i).getHtml();
	        	//System.out.println("PPPPPPPPPPPPPP"+params[i][0]);
	            params[i][1] = librarydata.get(i).getAuthor();
	            params[i][2] = librarydata.get(i).getSubject();
	            params[i][3] = librarydata.get(i).getTitle();
	            params[i][4] = librarydata.get(i).getUrl();
	            params[i][5] = librarydata.get(i).getPublicationDate();
	            params[i][6] = librarydata.get(i).getResponsiBility();
	            params[i][7] = librarydata.get(i).getPublicaTion();
	            params[i][8] = librarydata.get(i).getEdiTion();
	            params[i][9] = librarydata.get(i).getSumMary();
	            params[i][10] = librarydata.get(i).getConTents();
	            params[i][11] = librarydata.get(i).getIsBn();
	            params[i][12] = librarydata.get(i).getRecordnum();
	        }
			//根据正则匹配，选择返回sql语句
			break; 
			}// 可选
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":
			{Table="cambridgelibrary";
			sql=" (note,contributor,type,language,format,Html,author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum)";
			values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			params = new Object[librarydata.size()][18];
	    	// System.out.println("==============================="+librarydata);
	    	//params = new Object[librarydata.size()][result.getFiledName(result)];
	        for ( int i=0; i<params.length; i++ ){
	        	params[i][0] = librarydata.get(i).getNote();
	        	params[i][1] = librarydata.get(i).getContributor();
	        	params[i][2] = librarydata.get(i).getType();
	        	params[i][3] = librarydata.get(i).getLanguage();	
	        	params[i][4] = librarydata.get(i).getFormat();	
	        	params[i][5] = librarydata.get(i).getHtml();
	            params[i][6] = librarydata.get(i).getAuthor();
	            params[i][7] = librarydata.get(i).getSubject();
	            params[i][8] = librarydata.get(i).getTitle();
	            params[i][9] = librarydata.get(i).getUrl();
	            params[i][10] = librarydata.get(i).getPublicationDate();
	            params[i][11] = librarydata.get(i).getResponsiBility();
	            params[i][12] = librarydata.get(i).getPublicaTion();
	            params[i][13] = librarydata.get(i).getEdiTion();
	            params[i][14] = librarydata.get(i).getSumMary();
	            params[i][15] = librarydata.get(i).getConTents();
	            params[i][16] = librarydata.get(i).getIsBn();
	            params[i][17] = librarydata.get(i).getRecordnum();
	        }
			break; // 可选
			}
		// 你可以有任意数量的case语句
			
		case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":
			try {	
		{Table="Californialibrary";
		sql=" (note,contributor,type,language,format,Html,author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum,resourcetype,series,localsubjcet,includedin,genre,lccontroinumber,recordid,otherformat)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		params = new Object[librarydata.size()][26];
    	// System.out.println("==============================="+librarydata);
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
		
//		result.setAuthor(author);1
//		result.setResourceType(ResourceType);1
//		result.setTitle(title);1
//		result.setPublicaTion(publicaTion);1
//		result.setFormat(format);1
//		result.setSeries(series);1
//		result.setConTents(Contents);1
//		result.setSubject(subject);1
//		result.setLocalsubjcet(localsubjcet);1
//		result.setIncludedIn(IncludedIn);1
//		result.setIsBn(isbn);1
//		result.setGenre(genre);1
//		result.setLCcontrolnumber(LCcontrolnumber);1
//		result.setRecordID(RecordID);1
//		result.setNote(notes);1
//		result.setOtherformat(Otherformat);1
        for ( int i=0; i<params.length; i++ ){
//        	(note,contributor,type,language,format,Html,author, subject,title,url,pubDate,appendix,publisher,
//        			edition,description,contents,ISBN,recordnum,resourcetype,series,localsubjcet,includedin,genre,lccontroinumber,
//        			recordid,otherformat)
        	params[i][0] = librarydata.get(i).getNote();
        	params[i][1] = librarydata.get(i).getContributor();
        	params[i][2] = librarydata.get(i).getType();
        	params[i][3] = librarydata.get(i).getLanguage();	
        	params[i][4] = librarydata.get(i).getFormat();	
        	params[i][5] = librarydata.get(i).getHtml();
            params[i][6] = librarydata.get(i).getAuthor();
            params[i][7] = librarydata.get(i).getSubject();
            params[i][8] = librarydata.get(i).getTitle();
            params[i][9] = librarydata.get(i).getUrl();
            params[i][10] = librarydata.get(i).getPublicationDate();
            params[i][11] = librarydata.get(i).getResponsiBility();
            params[i][12] = librarydata.get(i).getPublicaTion();
            params[i][13] = librarydata.get(i).getEdiTion();
            params[i][14] = librarydata.get(i).getSumMary();
            params[i][15] = librarydata.get(i).getConTents();
            params[i][16] = librarydata.get(i).getIsBn();
            params[i][17] = librarydata.get(i).getRecordnum();
            params[i][18] = librarydata.get(i).getResourceType();
            params[i][19] = librarydata.get(i).getSeries();
            params[i][20] = librarydata.get(i).getLocalsubjcet();
            params[i][21] = librarydata.get(i).getIncludedIn();
            params[i][22] = librarydata.get(i).getGenre();
            params[i][23] = librarydata.get(i).getLCcontrolnumber();
            params[i][24] = librarydata.get(i).getRecordID();
            params[i][25] = librarydata.get(i).getOtherformat();
            
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
		
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":
		{Table="hkulibrary";
		sql=" (note,contributor,type,language,format,Html,author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		params = new Object[librarydata.size()][18];
    	// System.out.println("==============================="+librarydata);
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getNote();
        	params[i][1] = librarydata.get(i).getContributor();
        	params[i][2] = librarydata.get(i).getType();
        	params[i][3] = librarydata.get(i).getLanguage();	
        	params[i][4] = librarydata.get(i).getFormat();	
        	params[i][5] = librarydata.get(i).getHtml();
        	//System.out.println("==========================="+params[i][5]);
            params[i][6] = librarydata.get(i).getAuthor();
            params[i][7] = librarydata.get(i).getSubject();
            params[i][8] = librarydata.get(i).getTitle();
            params[i][9] = librarydata.get(i).getUrl();
            params[i][10] = librarydata.get(i).getPublicationDate();
            params[i][11] = librarydata.get(i).getResponsiBility();
            params[i][12] = librarydata.get(i).getPublicaTion();
            params[i][13] = librarydata.get(i).getEdiTion();
            params[i][14] = librarydata.get(i).getSumMary();
            params[i][15] = librarydata.get(i).getConTents();
            params[i][16] = librarydata.get(i).getIsBn();
            params[i][17] = librarydata.get(i).getRecordnum();
        }
		break; // 可选
		}
		
		
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":
		{Table="cuhklibrary";
		sql=" (note,contributor,type,language,format,Html,author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		params = new Object[librarydata.size()][18];
    	// System.out.println("==============================="+librarydata);
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getNote();
        	params[i][1] = librarydata.get(i).getContributor();
        	params[i][2] = librarydata.get(i).getType();
        	params[i][3] = librarydata.get(i).getLanguage();	
        	params[i][4] = librarydata.get(i).getFormat();	
        	params[i][5] = librarydata.get(i).getHtml();
        	//System.out.println("==========================="+params[i][5]);
            params[i][6] = librarydata.get(i).getAuthor();
            params[i][7] = librarydata.get(i).getSubject();
            params[i][8] = librarydata.get(i).getTitle();
            params[i][9] = librarydata.get(i).getUrl();
            params[i][10] = librarydata.get(i).getPublicationDate();
            params[i][11] = librarydata.get(i).getResponsiBility();
            params[i][12] = librarydata.get(i).getPublicaTion();
            params[i][13] = librarydata.get(i).getEdiTion();
            params[i][14] = librarydata.get(i).getSumMary();
            params[i][15] = librarydata.get(i).getConTents();
            params[i][16] = librarydata.get(i).getIsBn();
            params[i][17] = librarydata.get(i).getRecordnum();
        }
		break; // 可选
		}
		
		
		case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":
		{Table="oxfordlibrary";
		sql=" (note,contributor,type,language,format,Html,author, subject,title,url,pubDate,appendix,publisher,edition,description,contents,ISBN,recordnum)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		params = new Object[librarydata.size()][18];
    	// System.out.println("==============================="+librarydata);
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getNote();
        	params[i][1] = librarydata.get(i).getContributor();
        	params[i][2] = librarydata.get(i).getType();
        	params[i][3] = librarydata.get(i).getLanguage();	
        	params[i][4] = librarydata.get(i).getFormat();	
        	params[i][5] = librarydata.get(i).getHtml();
        	//System.out.println("==========================="+params[i][5]);
            params[i][6] = librarydata.get(i).getAuthor();
            params[i][7] = librarydata.get(i).getSubject();
            params[i][8] = librarydata.get(i).getTitle();
            params[i][9] = librarydata.get(i).getUrl();
            params[i][10] = librarydata.get(i).getPublicationDate();
            params[i][11] = librarydata.get(i).getResponsiBility();
            params[i][12] = librarydata.get(i).getPublicaTion();
            params[i][13] = librarydata.get(i).getEdiTion();
            params[i][14] = librarydata.get(i).getSumMary();
            params[i][15] = librarydata.get(i).getConTents();
            params[i][16] = librarydata.get(i).getIsBn();
            params[i][17] = librarydata.get(i).getRecordnum();
        }
		break; // 可选
		}
		
		
		case "https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*":
			try{
		{Table="tokyolibrary";
		sql=" (Html,url,recordnum,publisher,pubYear,format,author,language,note,classification,subTitle,edition,ISBN,subject,title)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][15];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getPublicaTion();	
        	params[i][4] = librarydata.get(i).getPublicationDate();
        	params[i][5] = librarydata.get(i).getFormat();
            params[i][6] = librarydata.get(i).getAuthor();
            params[i][7] = librarydata.get(i).getLanguage();
            params[i][8] = librarydata.get(i).getNote();
            params[i][9] = librarydata.get(i).getClassification();
            params[i][10] = librarydata.get(i).getOthertitles();
            params[i][11] = librarydata.get(i).getEdiTion();
            params[i][12] = librarydata.get(i).getIsBn();
            params[i][13] = librarydata.get(i).getSubject();
            params[i][14] = librarydata.get(i).getTitle();
           
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
	// 你可以有任意数量的case语句
		
		case "https://clio.columbia.edu/catalog/.*":
			try{
		{Table="cliolibrary";
		sql=" (Html,url,recordnum,author,title,publisher,subject,summary,contents,language,ISBN,standardno,format,series,note)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][15];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getAuthor();
        	params[i][4] = librarydata.get(i).getTitle();
        	params[i][5] = librarydata.get(i).getPublicaTion();
            params[i][6] = librarydata.get(i).getSubject();
            params[i][7] = librarydata.get(i).getSumMary();
            params[i][8] = librarydata.get(i).getConTents();
            params[i][9] = librarydata.get(i).getLanguage();
            params[i][10] = librarydata.get(i).getIsBn();
            params[i][11] = librarydata.get(i).getStandardno();
            params[i][12] = librarydata.get(i).getFormat();
            params[i][13] = librarydata.get(i).getSeries();
            params[i][14] = librarydata.get(i).getNote();
           
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}	
			
			
			
		case "https://lib.mit.edu/record/cat00916a/mit.00.*":
			try{
		{Table="mitlibrary";
		sql="(Html,url,recordnum,title,subject,type,source,publisher,language,dbase)";
		values="values (?,?,?,?,?,?,?,?,?,?)";
		
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][10];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();
        	params[i][4] = librarydata.get(i).getSubject();
        	params[i][5] = librarydata.get(i).getType();
            params[i][6] = librarydata.get(i).getSource();
            params[i][7] = librarydata.get(i).getPublicaTion();
            params[i][8] = librarydata.get(i).getLanguage();
            params[i][9] = librarydata.get(i).getDbase();
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}	
			
		
		case "https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*":
			try{
		{Table="yalelibrary";
		sql="(Html,url,recordnum,title,ISBN,publisher,format,localnotes,note,accessanduse,type,series,bibliography,contents,subject,author)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][16];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();
        	params[i][4] = librarydata.get(i).getIsBn();
        	params[i][5] = librarydata.get(i).getPublicaTion();
            params[i][6] = librarydata.get(i).getFormat();
            params[i][7] = librarydata.get(i).getLocalnotes();
            params[i][8] = librarydata.get(i).getNote();
            params[i][9] = librarydata.get(i).getAccessanduse();
            params[i][10] = librarydata.get(i).getType();
        	params[i][11] = librarydata.get(i).getSeries();
            params[i][12] = librarydata.get(i).getBibliography();
            params[i][13] = librarydata.get(i).getConTents();
            params[i][14] = librarydata.get(i).getSubject();
            params[i][15] = librarydata.get(i).getAuthor();
            
        
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
			
			
		case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":
		{Table="harvardlibrary";
		sql=" (Html,url,recordnum,title,author,pubDate,subject,format,language,type,contributor,publisher,ISBN,edition,summary,contents,note)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		params = new Object[librarydata.size()][17];
    	// System.out.println("==============================="+librarydata);
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();	
        	params[i][4] = librarydata.get(i).getAuthor();	
        	params[i][5] = librarydata.get(i).getPublicationDate();
        	//System.out.println("==========================="+params[i][5]);
            params[i][6] = librarydata.get(i).getSubject();
            params[i][7] = librarydata.get(i).getFormat();
            params[i][8] = librarydata.get(i).getLanguage();
            params[i][9] = librarydata.get(i).getType();
            params[i][10] = librarydata.get(i).getContributor();
            params[i][11] = librarydata.get(i).getPublicaTion();
            params[i][12] = librarydata.get(i).getIsBn();
            params[i][13] = librarydata.get(i).getEdiTion();
            params[i][14] = librarydata.get(i).getSumMary();
            params[i][15] = librarydata.get(i).getConTents();
            params[i][16] = librarydata.get(i).getNote();
           
        }
		break; // 可选
		}
		
		
		case "http://pi.lib.uchicago.edu/1001/cat/bib/.*":
			try{
		{Table="chicagolibrary";
		sql="(Html,url,recordnum,title,author,imprint,format,language,series,subject,type,subTitle,ISBN,note,summary,otherform)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][16];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();
        	params[i][4] = librarydata.get(i).getAuthor();
        	params[i][5] = librarydata.get(i).getImprint();
            params[i][6] = librarydata.get(i).getFormat();
            params[i][7] = librarydata.get(i).getLanguage();
            params[i][8] = librarydata.get(i).getSeries();
            params[i][9] = librarydata.get(i).getSubject();
            params[i][10] = librarydata.get(i).getType();
        	params[i][11] = librarydata.get(i).getSubtitle();
            params[i][12] = librarydata.get(i).getIsBn();
            params[i][13] = librarydata.get(i).getNote();
            params[i][14] = librarydata.get(i).getSumMary();
            params[i][15] = librarydata.get(i).getOtherform();
        
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
		
			
		case "http://catalogue.libraries.london.ac.uk/record=b.*":
			try{
		{Table="londonlibrary";
		sql="(Html,url,recordnum,title,imprint,copyrightdate,format,edition,bibliography,contents,subject,addauthor,ISBN,author)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][14];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();
        	params[i][4] = librarydata.get(i).getImprint();
        	params[i][5] = librarydata.get(i).getCopyrightdate();
            params[i][6] = librarydata.get(i).getFormat();
            params[i][7] = librarydata.get(i).getEdiTion();
            params[i][8] = librarydata.get(i).getBibliography();
            params[i][9] = librarydata.get(i).getConTents();
            params[i][10] = librarydata.get(i).getSubject();
        	params[i][11] = librarydata.get(i).getAddauthor();
            params[i][12] = librarydata.get(i).getIsBn();
            params[i][13] = librarydata.get(i).getAuthor();
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
			
			
		
		
			
		case "http://morris.law.yale.edu/record=b.*":
			try{
		{Table="yalelawlibrary";
		sql="(Html,url,recordnum,title,publisher,frequencyte,note,continues,subject,type,otherauthor,authorizedtitle,ISBN)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][13];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();
        	params[i][4] = librarydata.get(i).getPublicaTion();
        	params[i][5] = librarydata.get(i).getFrequencyte();
            params[i][6] = librarydata.get(i).getNote();
            params[i][7] = librarydata.get(i).getContinues();
            params[i][8] = librarydata.get(i).getSubject();
            params[i][9] = librarydata.get(i).getType();
            params[i][10] = librarydata.get(i).getOtherauthor();
        	params[i][11] = librarydata.get(i).getAuthorizedtitle();
            params[i][12] = librarydata.get(i).getIsBn();
        
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
			
			
		case "https://newcatalog.library.cornell.edu/catalog/.*":
			try{
		{Table="cornelllibrary";
		sql="(Html,url,recordnum,title,subTitle,author,format,language,publisher,access,ISBN,othertitle,relatedwork,note)";
		values="values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//System.out.println("==============================="+librarydata);
		params = new Object[librarydata.size()][14];
    	
    	//params = new Object[librarydata.size()][result.getFiledName(result)];
        for ( int i=0; i<params.length; i++ ){
        	params[i][0] = librarydata.get(i).getHtml();
        	params[i][1] = librarydata.get(i).getUrl();
        	params[i][2] = librarydata.get(i).getRecordnum();
        	params[i][3] = librarydata.get(i).getTitle();
        	params[i][4] = librarydata.get(i).getSubtitle();
        	params[i][5] = librarydata.get(i).getAuthor();
            params[i][6] = librarydata.get(i).getFormat();
            params[i][7] = librarydata.get(i).getLanguage();
            params[i][8] = librarydata.get(i).getPublicaTion();
            params[i][9] = librarydata.get(i).getAccess();
            params[i][10] = librarydata.get(i).getIsBn();
        	params[i][11] = librarydata.get(i).getOthertitles();
            params[i][12] = librarydata.get(i).getRelatedwork();
            params[i][13] = librarydata.get(i).getNote();
        
        }
		break; // 可选
		}
    	}
			catch (Exception e) {
				System.out.println("============================================="+"网页网页无数据，已略过:"+crawlMeta.getUrl()+"=============================================");
				return ;
			}
			
			
			
		}
    	//return Table;
    }
}
