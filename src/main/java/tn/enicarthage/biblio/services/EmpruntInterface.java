package tn.enicarthage.biblio.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface EmpruntInterface {
	
	
	
	ResponseEntity<String> generateReport(Map<String,Object> requestMap);
}
