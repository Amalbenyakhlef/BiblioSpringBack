package tn.enicarthage.biblio.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.enicarthage.biblio.Entities.Utilisateur;
import tn.enicarthage.biblio.repositories.UserRepository;
import java.util.Objects;


@Service
@Slf4j
public class BiblioUsersDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	
	private tn.enicarthage.biblio.Entities.Utilisateur userDetails;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside loadUserByUsername {}",username);
		userDetails = userRepo.findByEmailId(username);
		if (!Objects.isNull(userDetails)) {
			return new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User not found");
		}
	}
	
	public tn.enicarthage.biblio.Entities.Utilisateur getUserDetails(){
		return userDetails;
	}

	

}
