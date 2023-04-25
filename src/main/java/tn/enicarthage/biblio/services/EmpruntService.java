package tn.enicarthage.biblio.services;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;
import tn.enicarthage.biblio.Entities.emprunt;
import tn.enicarthage.biblio.jwt.JwtFilter;
import tn.enicarthage.biblio.repositories.empruntRepository;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;

@Slf4j
@Service
public class EmpruntService implements EmpruntInterface{

	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	empruntRepository EmpruntRepo;
	
	@Override
	public ResponseEntity<String> generateReport(Map<String,Object> requestMap) {
		log.info("Inside generateReport");
		try {
			String fileName;
			if(validateRequestMap(requestMap)) {
				if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate") ) {
					fileName= (String) requestMap.get("uid");
				}else {
					fileName=Biblio.getUid();
					requestMap.put("uid", fileName);
					insertEmprunt(requestMap);
				}
				String data="Name: "+requestMap.get("name")+ "\n" +"Email: "+requestMap.get("email");
				
				Document doc= new Document();
				PdfWriter.getInstance(doc,new FileOutputStream(BiblioConstants.STORE_LOCATION+ "\\" +fileName+".pdf"));
				
				doc.open();
				
				Paragraph chunk =new Paragraph("Biblio Management Enicar", getFont("Header"));
				chunk.setAlignment(Element.ALIGN_CENTER);
				doc.add(chunk);
				
				Paragraph para =new Paragraph(data+"\n \n", getFont("Data"));
				doc.add(para);
				
				PdfPTable table =new PdfPTable(5);
				table.setWidthPercentage(100);
				addTableHeader(table);
				
				JSONArray jsonArray =Biblio.getJsonArrayFromString((String) requestMap.get("livreDetails"));
				
				
			}
			return Biblio.getResponseEntity("Required data not found", HttpStatus.BAD_REQUEST);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void addTableHeader(PdfPTable table) {
		Stream.of("Name","categorie","Quantite","Sub total")
			.forEach(columnTitle ->{
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setBorder(2);
				header.setPhrase(new Phrase(columnTitle));
				header.setBackgroundColor(BaseColor.YELLOW);
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(header);
			});
		
	}

	private Font getFont(String type) {
		switch(type) {
		case "Header": Font headerFont= FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
						headerFont.setStyle(Font.BOLD);
							return headerFont;
		case "Data": Font dataFont= FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
					dataFont.setStyle(Font.BOLD);
						return dataFont;
		default: 
			return new Font();
		}
	}

	private void insertEmprunt(Map<String, Object> requestMap) {
		try {
			emprunt emprunt=new emprunt();
			
			emprunt.setUid((String)requestMap.get("uid")) ;
			emprunt.setName((String)requestMap.get("name")) ;
			emprunt.setEmail((String)requestMap.get("email")) ;
			emprunt.setTotal((String)requestMap.get("total")) ;
			emprunt.setLivreDetails((String)requestMap.get("livreDetails")) ;
			emprunt.setCreatedby(jwtFilter.getCurrentUser());
			EmpruntRepo.save(emprunt);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	
		
	}

	//to parse the data form
	private boolean validateRequestMap(Map<String, Object> requestMap) {
		return requestMap.containsKey("name") &&
				requestMap.containsKey("contactNumber") &&
				requestMap.containsKey("email") &&
				requestMap.containsKey("livreDetails") &&
				requestMap.containsKey("total");
				
	}
	
	
	
}
