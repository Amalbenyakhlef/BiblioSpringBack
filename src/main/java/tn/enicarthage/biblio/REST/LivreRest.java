package tn.enicarthage.biblio.REST;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tn.enicarthage.biblio.Entities.CategorieEnum;
import tn.enicarthage.biblio.Entities.Livre;
import tn.enicarthage.biblio.Entities.Utilisateur;
@RequestMapping(path="/livre")
public interface LivreRest {
	
	@PostMapping(path="/ajouter")
	ResponseEntity<String> ajouterLivre(@RequestBody Map<String,String> requestMap);


	@GetMapping(path="/get")
	ResponseEntity<List<Livre>> getAllLivre(@RequestParam(required= false) String filterValue);
	
	
	@PostMapping(path="/updateDispo")
	ResponseEntity<String> updateDispo(@RequestBody Map<String,String> requestMap);

	@GetMapping(path="/categorie/{categorie}")
	ResponseEntity<List<Livre>> afficherLivreParCategorie(@PathVariable String categorie );
	
	@GetMapping(path="/titre/{titre}")
	ResponseEntity<List<Livre>> afficherLivreParTitre(@PathVariable String titre );
	
	@GetMapping(path="/auteur/{auteur}")
	ResponseEntity<List<Livre>> afficherLivreParAuteur(@PathVariable String auteur );
	
	@PostMapping(path="/delete/{id}")
	ResponseEntity<String> deleteLivre(@PathVariable Integer id);
	
	@GetMapping(path="/getDispo")
	ResponseEntity<List<Livre>> getAllLivreDispo();
	
	@PostMapping(path="/emprunterLivre/{livreId}")
	public ResponseEntity<String> effectuerEmprunt(@PathVariable Integer livreId, @RequestBody Utilisateur utilisateur) ;
	
	
	
	
	
	
}
