package tn.enicarthage.biblio.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import tn.enicarthage.biblio.Entities.Devoir;

public interface DevoirInterface {

	
	
	ResponseEntity<String> ajouterDevoir(Map<String,String> requestMap);

	ResponseEntity<List<Devoir>> AfficherTousLesDevoirs(String filterValue);
	
	ResponseEntity<String> updateDevoir(Map<String,String> requestMap);
	

	ResponseEntity<List<Devoir>> rechercheParSpecialite(String specialite);

	ResponseEntity<List<Devoir>> rechercheParMatiere(String matiere);


	Devoir rechercherParId(Integer id);

	ResponseEntity<String> supprimerParId(Integer id);

	
	//not yet response
	//ResponseEntity<String> supprimerParId(Integer id);

}
