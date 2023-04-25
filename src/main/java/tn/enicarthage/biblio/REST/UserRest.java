package tn.enicarthage.biblio.REST;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.biblio.services.UserService;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;
import tn.enicarthage.biblio.utils.UserApprouvAdmin;

@RestController
public class UserRest implements UserRest_ {

	@Autowired
	UserService userService;
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		try{
			return userService.signUp(requestMap);
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		try {
			return userService.login(requestMap);
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

		
	}

	@Override
	public ResponseEntity<List<UserApprouvAdmin>> getAllUser() {
		try {
			return userService.getAllUser();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return new ResponseEntity<List<UserApprouvAdmin>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			return userService.update(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> checkToken() {
		try {
			return userService.checkToken();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	


}
