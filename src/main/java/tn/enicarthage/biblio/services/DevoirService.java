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
import tn.enicarthage.biblio.Entities.Devoir;
import tn.enicarthage.biblio.jwt.JwtFilter;
import tn.enicarthage.biblio.repositories.DevoirRepository;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;

@Slf4j
@Service
public  class DevoirService implements DevoirInterface {
	@Autowired
	DevoirRepository devoirRepository ;

	@Autowired
	JwtFilter jwtFilter;


/*
@Override 
public void ajouterDevoir (Devoir devoir) {
 devoirRepository.save(devoir);	
}*/

@Override
public ResponseEntity<String> ajouterDevoir(Map<String, String> requestMap) {
	try {
		
			if (validateDevoirMap(requestMap,false)) {
				devoirRepository.save(getDevoirFromMap(requestMap,false));
				//the response that everything is okay
				return Biblio.getResponseEntity("Devoir Added Successfully", HttpStatus.OK);
			}
			
		
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
}



//to extact the model from the map request
private Devoir getDevoirFromMap(Map<String, String> requestMap , Boolean isAdd) {
	Devoir devoir =new Devoir();
	if (isAdd) {
		devoir.setId_devoir(Integer.parseInt(requestMap.get("id_devoir")));
	}
	devoir.setDate_dev(requestMap.get("date_dev"));
	devoir.setDispo_correc(Boolean.parseBoolean(requestMap.get("dispo_correc")));
	devoir.setMatiere(requestMap.get("matiere"));
	devoir.setSession(requestMap.get("session"));
	devoir.setSpecialite(requestMap.get("specialite"));
	devoir.setType(requestMap.get("type"));
	
	
	return devoir;

}

//to validate the requestMap
private boolean validateDevoirMap(Map<String,String> requestMap,boolean validateId) {
	if (requestMap.containsKey("session")) {
		if (requestMap.containsKey("id_devoir") && validateId) {
			return true;
		}else if (!validateId) {
			return true;
		}
	}
	return false;
	
}

@Override
public ResponseEntity<List<Devoir>> AfficherTousLesDevoirs(String filterValue) {
	try {
		if (Strings.isNotEmpty(filterValue)) {
			log.info("Inside devoir");
			return new ResponseEntity<List<Devoir>>(devoirRepository.getAllDevoir(),HttpStatus.OK);
		}
		return new ResponseEntity<>((List<Devoir>)devoirRepository.findAll(),HttpStatus.OK);
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	return new ResponseEntity<List<Devoir>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
}


@Override
public ResponseEntity<String> updateDevoir(Map<String, String> requestMap) {
	try {
		
			if(validateDevoirMap(requestMap,true)) {
				Optional check= devoirRepository.findById(Integer.parseInt(requestMap.get("id_devoir")));
				if(!check.isEmpty()) {
					devoirRepository.save(getDevoirFromMap(requestMap,true));
					return Biblio.getResponseEntity("Devoir Updated Successfully",HttpStatus.OK);
				}else {
					return Biblio.getResponseEntity("Devoir id doesn't existe",HttpStatus.OK);
				}
			}
			return Biblio.getResponseEntity(BiblioConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
		

		
		
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

}



@Override 
public Devoir rechercherParId(Integer id) {
	 
	
	Optional<Devoir> livreOptional = devoirRepository.findById(id);
     if (livreOptional.isPresent()) {
         return livreOptional.get();
     } else {
         System.out.println ("Le livre avec l'ID " + id + " est introuvable.");
     }
	return null;
 }



@Override
public ResponseEntity<List<Devoir>> rechercheParSpecialite(String specialite){
	try {
		if (Strings.isNotEmpty(specialite)) {
			log.info("Inside devoir par Specialite");
			return new ResponseEntity<List<Devoir>>(devoirRepository.findBySpecialite(specialite),HttpStatus.OK);
		}
		return new ResponseEntity<>((List<Devoir>)devoirRepository.findAll(),HttpStatus.OK);

	}catch(Exception ex) {
		ex.printStackTrace();
	}
	return new ResponseEntity<List<Devoir>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	
	/*List<Devoir> devoirs = new ArrayList<Devoir>();
 for(Devoir devoir : devoirRepository.findAll() ) {
	 if (devoir.getSpecialite().toLowerCase().contains(specialite.toLowerCase())) {
			devoirs.add(devoir);
			}
  }
 return devoirs;*/
}
@Override
public ResponseEntity<List<Devoir>> rechercheParMatiere(String matiere){
	try {
		if (Strings.isNotEmpty(matiere) ) {
			log.info("Inside devoir par matière");
			return new ResponseEntity<List<Devoir>>(devoirRepository.findByMatiere(matiere),HttpStatus.OK);
		}
		return new ResponseEntity<>((List<Devoir>)devoirRepository.findAll(),HttpStatus.OK);

	}catch(Exception ex) {
		ex.printStackTrace();
	}
	return new ResponseEntity<List<Devoir>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	
	/*List<Devoir> devoirs = new ArrayList<Devoir>();
 for(Devoir devoir : devoirRepository.findAll() ) {
	 if (devoir.getMatiere().toLowerCase().contains(matiere.toLowerCase())) {
			devoirs.add(devoir);
			}
  }
 return devoirs;*/
}




@Override 
public ResponseEntity<String> supprimerParId(Integer id) {
	try {
		Optional check= devoirRepository.findById(id);
		if(!check.isEmpty()) {
		    devoirRepository.deleteById(id);
		    return Biblio.getResponseEntity("Devoir d'id "+id+" is deleted successfully", HttpStatus.OK);
		}
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	return Biblio.getResponseEntity(BiblioConstants.INVALID_DATA,HttpStatus.NOT_FOUND);

	
}












}