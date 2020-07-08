package com.ruc.filter;

import java.util.List;

import com.ruc.entity.CrawlMeta;
import com.ruc.entity.CrawlResult;

import Mysql.resultCatch;

public class SelectRilter {
	
	
	public List<resultCatch> Selectrilter(CrawlMeta crawlMeta, CrawlResult  crawlResult) {
		
		String regurl=crawlMeta.getReg();
		List<resultCatch> list=null;
		switch (regurl) {
		case "https://searchworks.stanford.edu/view/.*":
			 StanfordLibraryFilter stanforddata=new StanfordLibraryFilter();
			 list = stanforddata.StanfordLibraryfilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
		case "http://idiscover.lib.cam.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/44CAM_ALMA21.*":
			CambridgeLibraryFilter cambridgedata=new CambridgeLibraryFilter();
			
			 list = cambridgedata.CambridgeLibraryfilter(crawlMeta, crawlResult);
			// System.out.println("================================================="+list);
			 //list = null;
			break; 
		case "https://catalog.library.ucla.edu/vwebv/holdingsInfo?bibId=.*":
			 CaliforniaLibraryFilter californiadata=new CaliforniaLibraryFilter();
			 list = californiadata.CaliforniaLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
		// 你可以有任意数量的case语句
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/HKU_IZ21.*":
			 HKULibraryFilter hkudata=new  HKULibraryFilter();
			 list = hkudata.HKULibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
		// 你可以有任意数量的case语句
		case "https://julac.hosted.exlibrisgroup.com/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/CUHK_IZ51.*":
			 CUHKLibraryFilter cuhkdata=new  CUHKLibraryFilter();
			 list = cuhkdata.CUHKLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
		// 你可以有任意数量的case语句
			
		case "http://solo.bodleian.ox.ac.uk/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/oxfaleph.*":
			 OxfordLibraryFilter oxforddata=new  OxfordLibraryFilter();
			 list = oxforddata.OxfordLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
		// 你可以有任意数量的case语句
			
		case "https://opac.dl.itc.u-tokyo.ac.jp/opac/opac_details/?bibid=200.*":
			 TokyoLibraryFilter tokyodata=new  TokyoLibraryFilter();
			 list = tokyodata.TokyoLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "https://clio.columbia.edu/catalog/.*":
			 CLIOLibraryFilter cliodata=new  CLIOLibraryFilter();
			 list = cliodata.CLIOLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "https://lib.mit.edu/record/cat00916a/mit.00.*":
			MitLibraryFilter mitdata=new  MitLibraryFilter();
			 list = mitdata.MitLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "https://orbis.library.yale.edu/vwebv/holdingsInfo?bibId=.*":
			YaleLibraryFilter yaledata=new  YaleLibraryFilter();
			 list = yaledata.YaleLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "https://hollis.harvard.edu/primo_library/libweb/webservices/rest/primo-explore/v1/pnxs/L/01HVD_ALMA21.*":
			HarvardLibraryFilter harvarddata=new  HarvardLibraryFilter();
			 list = harvarddata.HarvardLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "http://pi.lib.uchicago.edu/1001/cat/bib/.*":
			ChicagoLibraryFilter chicagodata=new  ChicagoLibraryFilter();
			 list = chicagodata.ChicagoLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "http://catalogue.libraries.london.ac.uk/record=b.*":
			LondonLibraryFilter londondata=new  LondonLibraryFilter();
			 list = londondata.LondonLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "http://morris.law.yale.edu/record=b.*":
			YalelawLibraryFilter yalelawdata=new  YalelawLibraryFilter();
			 list = yalelawdata.YalelawLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
			
		case "https://newcatalog.library.cornell.edu/catalog/.*":
			CornellLibraryFilter Cornelldata=new  CornellLibraryFilter();
			 list = Cornelldata.CornellLibraryFilter(crawlMeta, crawlResult);
			
			//根据正则匹配，选择返回sql语句
			break; 
		}
		return list;
	}

}
