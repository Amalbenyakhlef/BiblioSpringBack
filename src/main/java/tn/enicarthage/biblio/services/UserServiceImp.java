package tn.enicarthage.biblio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.enicarthage.biblio.Entities.Utilisateur;
import tn.enicarthage.biblio.Entities.UtilisateurModificationAspect;
import tn.enicarthage.biblio.jwt.BiblioUsersDetailsService;
import tn.enicarthage.biblio.jwt.JwtFilter;
import tn.enicarthage.biblio.jwt.JwtUtil;
import tn.enicarthage.biblio.repositories.UserRepository;
import tn.enicarthage.biblio.utils.Biblio;
import tn.enicarthage.biblio.utils.BiblioConstants;
import tn.enicarthage.biblio.utils.EmailUtils;
import tn.enicarthage.biblio.utils.UserApprouvAdmin;

@Slf4j
@Service
public class UserServiceImp implements UserService{

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	BiblioUsersDetailsService biblioService;

	@Autowired 
	JwtUtil jwt;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired 
	EmailUtils emailUtil;
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("Inside signup {}",requestMap);
		try {
			if (validateSignUpMap(requestMap)) {
			Utilisateur user= userRepo.findByEmailId(requestMap.get("email"));
			 if (Objects.isNull(user)) {
				//make the changes in the DB
				userRepo.save(getUserFromMap(requestMap));
				return Biblio.getResponseEntity("successfully Registred", HttpStatus.OK);
			}
			else {
				return Biblio.getResponseEntity("Email alreday exists.", HttpStatus.BAD_REQUEST);
			}
		}
		else {
			return Biblio.getResponseEntity(BiblioConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
		}}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	
	private boolean validateSignUpMap(Map<String,String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password"))
		{
			return true;
		}
		return false;
	}
	
	private Utilisateur getUserFromMap(Map<String,String> requestMap) {
		Utilisateur user = new Utilisateur();
	
		user.setNom(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("false");
		user.setRole("User");
		return user;
		
		
	}

	
	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		log.info("Inside login");
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if (auth.isAuthenticated()) {
				if (biblioService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
					return new ResponseEntity<String>("{\"token\":\""+
							jwt.generateToken(biblioService.getUserDetails().getEmail(),
									biblioService.getUserDetails().getRole() )+ "\"}",
							HttpStatus.OK);
				}
				else {
					return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval. "+"\"}",HttpStatus.BAD_REQUEST);
				}
			}
		}
		catch(Exception ex) {
			log.error("{}",ex);
		}
		
		return Biblio.getResponseEntity(BiblioConstants.BAD_CREDENTIAL, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserApprouvAdmin>> getAllUser() {
		try {
			
				return new ResponseEntity<>(userRepo.getAllUser(),HttpStatus.OK);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			
				Optional<Utilisateur> check = userRepo.findById(Integer.parseInt(requestMap.get("id")));
				if (!check.isEmpty()) {
					userRepo.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
					sendMailToAllAdmin(requestMap.get("status"),check.get().getEmail(),userRepo.getAllAdmin());
					return Biblio.getResponseEntity("User status updated successfully",HttpStatus.OK);
				}else {
					Biblio.getResponseEntity("User id dosen't exist",HttpStatus.OK);
				}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Biblio.getResponseEntity(BiblioConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	private void sendMailToAllAdmin(String status, String userEmail, List<String> allAdmin) {
		// TODO Auto-generated method stub
		allAdmin.remove(jwtFilter.getCurrentUser());
		if (status !=null && status.equalsIgnoreCase("true")) {
			emailUtil.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approuved", "USER:- "+userEmail+"\n is approuved by \nADMIN:- "+ jwtFilter.getCurrentUser(), allAdmin);
		}else {
			emailUtil.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "USER:- "+userEmail+"\n is disabled by \nADMIN:- "+ jwtFilter.getCurrentUser(), allAdmin);

		}
	}

	@Override
	public ResponseEntity<String> checkToken() {
		return Biblio.getResponseEntity("true", HttpStatus.OK);
	}

	@Autowired
    private UtilisateurModificationAspect utilisateurModificationAspect;
@Override
	public Utilisateur ChangeInfo(Utilisateur user) {
	utilisateurModificationAspect.beforeUpdateInformationsPersonnelles(user); 
		Utilisateur usr = userRepo.findById(user.getId());
		usr.setNom(user.getNom());
		usr.setContactNumber(user.getContactNumber());
		usr.setEmail(user.getEmail());
		usr.setPassword(user.getPassword());
		return userRepo.save(usr);
	}

}
