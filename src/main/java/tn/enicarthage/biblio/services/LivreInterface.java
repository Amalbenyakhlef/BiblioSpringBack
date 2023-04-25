package tn.enicarthage.biblio.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import tn.enicarthage.biblio.Entities.CategorieEnum;
import tn.enicarthage.biblio.Entities.Livre;
import tn.enicarthage.biblio.Entities.Utilisateur;

public interface LivreInterface{

	//done
	ResponseEntity<String> ajouterLivre(Map<String, String> requestMap);

	ResponseEntity<String> updateStatus(Map<String, String> requestMap);
	
	ResponseEntity<List<Livre>> AfficherLivreParCategorie(String categorie);
	
	ResponseEntity<List<Livre>> AfficherTousLesLivres(String filterValue);
	
	ResponseEntity<List<Livre>> rechercheLivreParTitre(String titre);

	ResponseEntity<List<Livre>> rechercheLivreParAuteur(String auteur);

	ResponseEntity<List<Livre>> AfficherLivresDispo();
	
	ResponseEntity<String> supprimerParId(Integer id);
	
	//List<Livre> AfficherLivresEmpruntes();
	
	
	//not yet
	Livre chercherParId(Integer livreId);
	


	
	
	void modifierLivre (Integer id , Livre livreModifie);
	

	List<Livre> AfficherLivresEmpruntes();

	void retournerLivre(Livre livre);

	ResponseEntity<String> effectuerEmprunt(Integer livreId, Utilisateur utilisateur);

	
	

	

}
