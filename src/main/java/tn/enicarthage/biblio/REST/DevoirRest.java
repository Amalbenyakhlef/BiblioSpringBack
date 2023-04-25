package tn.enicarthage.biblio.REST;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tn.enicarthage.biblio.Entities.Devoir;
@RequestMapping(path="/devoir")
public interface DevoirRest {

	@PostMapping(path="/ajouter")
	ResponseEntity<String> addDevoir(@RequestBody(required= true) Map<String,String> requestMap);


	
	@GetMapping(path="/getAll")
	ResponseEntity<List<Devoir>> getAllDevoir(@RequestParam(required= false) String filterValue);
	
	
	@PostMapping(path="/update")
	ResponseEntity<String> updateDevoir(@RequestBody(required= true) Map<String,String> requestMap);
	
	
	
	@GetMapping(path="/matiere/{matiere}")
	ResponseEntity<List<Devoir>> RechercheParMatiere(@PathVariable String matiere);
	
	@GetMapping("/specialite/{specialite}")
	ResponseEntity<List<Devoir>> RechercheParSpecilite(@PathVariable String specialite);
	
	

	@DeleteMapping(path="/delete/{id}")
	ResponseEntity<String> supprimerDevoir(@PathVariable Integer Id);
}
