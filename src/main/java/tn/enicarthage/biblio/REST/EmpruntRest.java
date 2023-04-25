package tn.enicarthage.biblio.REST;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path="/emprunt")
public interface EmpruntRest {
	
	@PostMapping(path="/generateReport")
	ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);
	
}
