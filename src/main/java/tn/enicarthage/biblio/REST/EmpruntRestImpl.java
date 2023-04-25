package tn.enicarthage.biblio.REST;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.biblio.services.EmpruntService;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmpruntRestImpl implements EmpruntRest{

	@Override
	public ResponseEntity<String> generateReport(Map<String,Object> requestMap) {
		try {
			return EmpruntService.generateReport(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
