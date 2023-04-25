package tn.enicarthage.biblio.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import tn.enicarthage.biblio.utils.UserApprouvAdmin;

public interface UserService {
	
	ResponseEntity<String> signUp(Map<String,String> requestMap);
	ResponseEntity<String> login(Map<String,String> requestMap);
	ResponseEntity<List<UserApprouvAdmin>> getAllUser();
	ResponseEntity<String> update(Map<String,String> requestMap);
	ResponseEntity<String> checkToken();
}
