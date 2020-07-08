package Mysql;

import org.jsoup.nodes.Document;

import com.google.protobuf.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class resultCatch {
	
	@Getter
	@Setter
	private String author;
	
	@Getter
	@Setter
	private String ResourceType;
	
	@Getter
	@Setter
	private String subject;
	
	@Getter
	@Setter
	private String title;
	
	@Setter
	@Getter
	private String url;
	
	@Setter
	@Getter
	private String PublicationDate;
	
	@Setter
	@Getter
	private String PublicaTion;
	
	@Setter
	@Getter
	private String ResponsiBility;
	
	@Setter
	@Getter
	private String EdiTion;
	
	@Setter
	@Getter
	private String SumMary;
	
	@Setter
	@Getter
	private String ConTents;
	
	@Setter
	@Getter
	private String IsBn;
	
	@Setter
	@Getter
	private int recordnum;

	//public int getFiledName;
	
	@Setter
	@Getter
	private String Html;
	
	
	@Setter
	@Getter
	private  String format;
	
	@Setter
	@Getter
	private  String series;
	
	@Setter
	@Getter
	private  String language;
	
	
	@Setter
	@Getter
	private  String contributor;
	
	@Setter
	@Getter
	private  String type;
	

	
	@Setter
	@Getter
	private  String localsubjcet;
	
	@Setter
	@Getter
	private  String IncludedIn;
	
	@Setter
	@Getter
	private  String genre;
	
	@Setter
	@Getter
	private  String LCcontrolnumber;
	
	@Setter
	@Getter
	private  String RecordID;
	
	@Setter
	@Getter
	private  String note;
	
	@Setter
	@Getter
	private  String Otherformat;
	
	@Setter
	@Getter
	private  String classification;
	
	@Setter
	@Getter
	private  String othertitles;
	
	@Setter
	@Getter
	private  String standardno;
	
	@Setter
	@Getter
	private  String source;
	
	@Setter
	@Getter
	private  String dbase;
	
	@Setter
	@Getter
	private  String localnotes;
	
	@Setter
	@Getter
	private  String accessanduse;
	
	@Setter
	@Getter
	private  String bibliography;
	

	@Setter
	@Getter
	private  String imprint;
	
	@Setter
	@Getter
	private  String subtitle;
	
	

	@Setter
	@Getter
	private  String otherform;
	
	@Setter
	@Getter
	private  String copyrightdate;
	
	@Setter
	@Getter
	private  String addauthor;
	
	@Setter
	@Getter
	private  String frequencyte;
	
	@Setter
	@Getter
	private  String continues;
	
	@Setter
	@Getter
	private  String authorizedtitle;
	
	@Setter
	@Getter
	private  String otherauthor;
	
	@Setter
	@Getter
	private  String access;
	
	@Setter
	@Getter
	private  String relatedwork;

 public resultCatch() {
		
	}
	
	public resultCatch(String author) {
		this.author = author;
	}
	

	
	public resultCatch(String author,String subject,String title,String url,
			String PublicationDate,String ResponsiBility,String PublicaTion,String EdiTion,
			String SumMary,String ConTents,String IsBn,int recordnum,String Html, String format,String series,String language,
			String type,String contributor,String ResourceType,String localsubjcet,String IncludedIn,
			String genre,String LCcontrolnumber,String RecordID,String note,String Otherformat,String classification,
			String othertitles,String standardno,String source,String dbase,String localnotes,String accessanduse,
			String bibliography,String imprint, String subtitle,String otherform,String copyrightdate,
			String addauthor,String frequencyte,String continues, String otherauthor,String authorizedtitle,
			String access,String relatedwork){
		this.IncludedIn = IncludedIn;
		this.Otherformat = Otherformat;
		this.note = note;
		this.RecordID = RecordID;
		this.LCcontrolnumber = LCcontrolnumber;
		this.genre = genre;
		this.author = author;
		this.subject = subject;
		this.title = title;
		this.url = url;
		this.PublicationDate = PublicationDate;
		this.ResponsiBility = ResponsiBility;
		this.PublicaTion = PublicaTion;
		this.EdiTion = EdiTion;
		this.SumMary = SumMary;
		this.ConTents = ConTents;
		this.IsBn = IsBn;
		this.recordnum = recordnum;
		this.Html = Html;
		this.format = format;
		this.series = series;
		this.language = language;
		this.type = type;
		this.contributor = contributor;
		
		this.ResourceType = ResourceType;
		this.localsubjcet = localsubjcet;
		this.classification = classification;
		this.othertitles = othertitles;
		this.standardno = standardno;
		this.source = source;
		this.dbase = dbase;
		this.localnotes = localnotes;
		this.accessanduse = accessanduse;
		this.bibliography = bibliography;
		this.imprint = imprint;
		this.subtitle = subtitle;
		this.otherform = otherform;
		this.copyrightdate = copyrightdate;
		this.addauthor = addauthor;
		this.frequencyte = frequencyte;
		this.continues = continues;
		this.otherauthor = otherauthor;
		this.authorizedtitle = authorizedtitle;
		this.access = access;
		this.relatedwork = relatedwork;
	}
	 public  int getFiledName(Object o){  
	        java.lang.reflect.Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	        int i=0;
	        for(;i<fields.length;i++){  
	          //  System.out.println(fields[i].getType());  
	            fieldNames[i]=fields[i].getName();  
	        }  
	        return i;  
	    }  
	
//	 public static void main (String[] args) {
//		 getFiledName(new resultCatch());
//	 }
}
