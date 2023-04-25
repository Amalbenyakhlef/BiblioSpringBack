package tn.enicarthage.biblio.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.enicarthage.biblio.Entities.CategorieEnum;
import tn.enicarthage.biblio.Entities.Devoir;
import tn.enicarthage.biblio.Entities.Livre;
import tn.enicarthage.biblio.Entities.Utilisateur;
import tn.enicarthage.biblio.jwt.JwtFilter;
import tn.enicarthage.biblio.repositories.LivreRepository;
import tn.enicarthage.biblio.repositories.UserRepository;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;

@Slf4j
@Service
public class LivreService implements LivreInterface {
	@Autowired
	LivreRepository livreRepository;
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
	@Override
	public ResponseEntity<String> ajouterLivre(Map<String, String> requestMap) {
		try {
			
				if (validateLivreMap(requestMap,false)) {
					livreRepository.save(getLivreFromMap(requestMap,false));
					return Biblio.getResponseEntity("Livre Added Successfully", HttpStatus.OK);

				}
				
				return Biblio.getResponseEntity(BiblioConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

			

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	//to extract livre format from requestMap
	private Livre getLivreFromMap(Map<String, String> requestMap, boolean isAdd) {
		
		Livre livre =new Livre();
		if (isAdd) {
			livre.setId_Livre(Integer.parseInt(requestMap.get("id_Livre")));
		}
		//CategorieEnum category = CategorieEnum.valueOf(requestMap.get("categorie"));

		livre.setAuteur(requestMap.get("auteur"));
		livre.setCategorie(requestMap.get("categorie"));
		livre.setDisponnible(Boolean.getBoolean(requestMap.get("disponnible")));
		livre.setNb_exemplaire(Integer.parseInt(requestMap.get("nb_exemplaire")));
		livre.setTitre(requestMap.get("titre"));
		livre.setNb_emprunts(Integer.parseInt(requestMap.get("nb_emprunts")));
		
		
		return livre;
	}

	//to validate the request with the format in my DB
    private boolean validateLivreMap(Map<String, String> requestMap, boolean validateId) {
    	if (requestMap.containsKey("titre")) {
    		if (requestMap.containsKey("id_Livre") && validateId) {
    			return true;
    		}else if (!validateId) {
    			return true;
    		}
    	}
    	return false;
	}

    @Override 
    public ResponseEntity<List<Livre>> AfficherLivreParCategorie (String categorie){
    	//String category =categorie.toString();
    	//CharSequence or use categorie by enum
    	try {
    		if (Strings.isNotEmpty(categorie)) {
    			log.info("Inside livre par categorie Enum");
    			return new ResponseEntity<List<Livre>>(livreRepository.findByCategorie(categorie),HttpStatus.OK);
    		}
    		return new ResponseEntity<List<Livre>>(livreRepository.findAll(),HttpStatus.OK);

    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return new ResponseEntity<List<Livre>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    	
    	
    	/*List<Livre> livres= new ArrayList<>();
    	for(Livre livre : livreRepository.findAll()) {
    		if (livre.getCategorie() == categorie){
    			livres.add(livre);
    		}
    	}
    	return livres;*/
    }


    
    @Override
	public ResponseEntity<List<Livre>> AfficherTousLesLivres(String filterValue) {
    	try {
    		if (Strings.isNotEmpty(filterValue)) {
    			log.info("Inside Livre All");
    			return new ResponseEntity<List<Livre>>(livreRepository.getAllLivre(),HttpStatus.OK);
    		}
    		return new ResponseEntity<>((List<Livre>)livreRepository.findAll(),HttpStatus.OK);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return new ResponseEntity<List<Livre>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @Override 
    public ResponseEntity<List<Livre>> rechercheLivreParTitre (String titre){
    	
    	try {
    		if (Strings.isNotEmpty(titre)) {
    			log.info("Inside livre par titre");
    			return new ResponseEntity<List<Livre>>(livreRepository.findByTitre(titre),HttpStatus.OK);
    		}
    		return new ResponseEntity<List<Livre>>(livreRepository.findAll(),HttpStatus.OK);

    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return new ResponseEntity<List<Livre>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    	
    	
    	
    	/*List<Livre> livres= new ArrayList<>();
    	for(Livre livre : livreRepository.findAll()) {
    		if (livre.getTitre().toLowerCase().contains(titre.toLowerCase())){
    			livres.add(livre);
    		}
    	}
    	return livres;*/
    }
    

	@Override
	public ResponseEntity<List<Livre>> rechercheLivreParAuteur(String auteur) {
		try {
    		if (Strings.isNotEmpty(auteur)) {
    			log.info("Inside livre par auteur");
    			return new ResponseEntity<List<Livre>>(livreRepository.findByAuteur(auteur),HttpStatus.OK);
    		}
    		return new ResponseEntity<List<Livre>>(livreRepository.findAll(),HttpStatus.OK);

    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return new ResponseEntity<List<Livre>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
	@Override 
    public ResponseEntity<List<Livre>> AfficherLivresDispo(){
    	List<Livre> livresDispo = new ArrayList<Livre>();
    	for (Livre livre : livreRepository.findAll()) {
    		if (livre.isDisponnible()== true && livre.getUser()!= null  ) {
    			livresDispo.add(livre);
    		}
    	}
		return new ResponseEntity<List<Livre>>(livresDispo,HttpStatus.OK);
    	
    }
	
	  
    @Override
    public ResponseEntity<String> supprimerParId (Integer id) {
    	try {
			
				Optional check= livreRepository.findById(id);
				if (!check.isEmpty()) {
					livreRepository.deleteById(id);
					return Biblio.getResponseEntity("Livre deleted successfully", HttpStatus.OK);

				}
				return Biblio.getResponseEntity("Livre id doesn't existe.", HttpStatus.OK);

			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
	@Override 
    public Livre chercherParId( Integer id) {
    	 Optional<Livre> livreOptional = livreRepository.findById(id);
         if (livreOptional.isPresent()) {
             return livreOptional.get();
         } else {
             System.out.println ("Le livre avec l'ID " + id + " est introuvable.");
         }
		return null;
     }
   
  
    
    
    
    public boolean testNbExemplaire(Livre livre) {
    	if (livre.getNb_exemplaire()!=0)
    			return true;
    	else return false;
    }
    public void modifierLivre (Integer id , Livre livreModifie) {
    	for(Livre livre :livreRepository.findAll()) {
    		if (livre.getId_Livre() == id) {
    	livre.setTitre(livreModifie.getTitre());
    	livre.setAuteur(livreModifie.getAuteur());
    	livre.setCategorie(livreModifie.getCategorie());
    	livre.setNb_exemplaire(livreModifie.getNb_exemplaire());
    	livre.setDisponnible(livreModifie.isDisponnible());
    	livreRepository.save(livre);
    		}
    	}
    
    }
    
    
    @Override 
    public List<Livre> AfficherLivresEmpruntes(){
    	List<Livre> livresEmpruntes = new ArrayList<Livre>();
    	for (Livre livre : livreRepository.findAll()) {
    		if ((livre.isDisponnible()== false && livre.getUser()!= null )) {
    			livresEmpruntes.add(livre);
    		}
    	}
		return livresEmpruntes;
    	
    }
    @Override
    public void retournerLivre(Livre livre) {
    	Utilisateur reserveParUser =livre.getUser();
    	
    	for( int i=0 ;i< reserveParUser.getLivres_empruntes().size();i++) {
    		if (reserveParUser.getLivres_empruntes().get(i).getId_Livre() == livre.getId_Livre()) {
    			reserveParUser.getLivres_empruntes().remove(i);
    			break;
    		}
    	}
    	userRepo.save(reserveParUser);
    	livre.setDisponnible(true);
    	livreRepository.save(livre);
    	
    	
    }
    
   

	@Override
	public ResponseEntity<String> effectuerEmprunt(Integer livreId, Utilisateur utilisateur) {
		Livre livre =new Livre();
		Optional<Livre> check = livreRepository.findById(livreId);
        if(check != null) {
        	livre.IncrementerNb_emprunts();
        	livre.setUser(utilisateur);
        	livre.setDisponnible(false);
        	livreRepository.save(livre);
        }
        	return new ResponseEntity<String>("EveryThing is okay", HttpStatus.BAD_REQUEST);

	}


	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			if (jwtFilter.isAdmin()) {
				Optional check= livreRepository.findById(Integer.parseInt(requestMap.get("id_Livre")));
				if (!check.isEmpty()) {
					livreRepository.updateLivreDispo(Boolean.parseBoolean(requestMap.get("disponnible")), Integer.parseInt(requestMap.get("id_Livre")));
					return Biblio.getResponseEntity("Livre status Updated successfully", HttpStatus.OK);

				}
				return Biblio.getResponseEntity("Livre id doesn't existe.", HttpStatus.OK);

			}else {
				return Biblio.getResponseEntity(BiblioConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}



	




	
	
    
    
}