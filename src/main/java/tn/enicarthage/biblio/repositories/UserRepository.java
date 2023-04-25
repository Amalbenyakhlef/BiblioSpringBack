package tn.enicarthage.biblio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import tn.enicarthage.biblio.Entities.Utilisateur;
import tn.enicarthage.biblio.utils.UserApprouvAdmin;
public interface UserRepository extends JpaRepository<Utilisateur,Integer>{

	Utilisateur findByEmailId(@Param("email") String email); 
	
	List<tn.enicarthage.biblio.utils.UserApprouvAdmin> getAllUser();
	
	List<String> getAllAdmin ();
	
	//not yet
	List<Utilisateur> findByRole(String role);
	
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
	
}
