package tn.enicarthage.biblio.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApprouvAdmin {

	private Integer id;
	private String nom;
	private String email;
	private String contactNumber;
	private String status;
	
	
	
}
