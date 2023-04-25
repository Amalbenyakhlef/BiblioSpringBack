package tn.enicarthage.biblio.REST;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tn.enicarthage.biblio.utils.UserApprouvAdmin;

@RequestMapping(path="/user")
public interface UserRest_ {
	
	@PostMapping(path="/signup")
	public ResponseEntity<String> signUp(@RequestBody(required =true) Map<String,String> requestMap);
	
	
	@PostMapping(path="/login")
	public ResponseEntity<String> login(@RequestBody(required =true) Map<String,String> requestMap);
	
	@GetMapping(path="/get")
	public ResponseEntity<List<UserApprouvAdmin>> getAllUser();

	@PostMapping(path="/update")
	public ResponseEntity<String> update(@RequestBody(required =true) Map<String,String> requestMap);


	@GetMapping(path="/checkToken")
	public ResponseEntity<String> checkToken();


	
}
