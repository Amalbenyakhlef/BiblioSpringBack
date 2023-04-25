package tn.enicarthage.biblio.REST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.biblio.Entities.Devoir;
import tn.enicarthage.biblio.services.DevoirService;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;
@CrossOrigin(origins = "http://localhost:4200", maxAge=3600)
@RestController
@SuppressWarnings("rawtypes")
public class DevoirRestImpl implements DevoirRest {

	@Autowired
	DevoirService devoirService;
	
	
	@Override
	public ResponseEntity<String> addDevoir(Map<String, String> requestMap) {
		try {
			return devoirService.ajouterDevoir(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<List<Devoir>> getAllDevoir(String filterValue) {
		try {
			return devoirService.AfficherTousLesDevoirs(filterValue);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<String> updateDevoir(Map<String, String> requestMap) {
		try {
			return devoirService.updateDevoir(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<List<Devoir>> RechercheParMatiere(String matiere) {
		try {
			return devoirService.rechercheParMatiere(matiere);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<List<Devoir>> RechercheParSpecilite(String specialite) {
		try {
			return devoirService.rechercheParSpecialite(specialite);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<String> supprimerDevoir(Integer Id) {
		try {
			return devoirService.supprimerParId(Id);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	} 
 

}
