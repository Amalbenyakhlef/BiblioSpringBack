package tn.enicarthage.biblio.REST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.biblio.Entities.CategorieEnum;
import tn.enicarthage.biblio.Entities.Livre;
import tn.enicarthage.biblio.Entities.Utilisateur;
import tn.enicarthage.biblio.services.LivreService;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@SuppressWarnings("rawtypes")
public class LivreRestImpl implements LivreRest {

	@Autowired
	LivreService livreService;
	
	@Override
	public ResponseEntity<String> ajouterLivre(Map<String, String> requestMap) {
		try {
			return livreService.ajouterLivre(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Livre>> afficherLivreParCategorie(String categorie) {
		try {
			return  livreService.AfficherLivreParCategorie(categorie);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
    	return new ResponseEntity<List<Livre>>(new ArrayList<>(), HttpStatus.OK);
 
	}
	
	@Override
	public ResponseEntity<List<Livre>> getAllLivre(String filterValue) {
		try {
			return livreService.AfficherTousLesLivres(filterValue);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Livre>> afficherLivreParTitre(String titre) {
		try {
			return livreService.rechercheLivreParTitre(titre);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<List<Livre>> afficherLivreParAuteur(String auteur) {
		try {
			return livreService.rechercheLivreParAuteur(auteur);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@Override
	public ResponseEntity<List<Livre>> getAllLivreDispo() {
		try {
			return livreService.AfficherLivresDispo();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	
	
	@Override
	public ResponseEntity<String> effectuerEmprunt(Integer livreId, Utilisateur utilisateur) {
		 livreService.effectuerEmprunt(livreId, utilisateur);
		   return ResponseEntity.ok("Emprunt effectué avec succès !");
	}

	@Override
	public ResponseEntity<String> deleteLivre(Integer id) {
		try {
			return livreService.supprimerParId(id);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateDispo(Map<String, String> requestMap) {
		try {
			return livreService.updateStatus(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	

	

	

	

	
}
