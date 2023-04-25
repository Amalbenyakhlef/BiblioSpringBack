package tn.enicarthage.biblio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.enicarthage.biblio.Entities.Devoir;
@Repository
public interface DevoirRepository extends JpaRepository <Devoir,Integer>{
	 List<Devoir> findBySpecialite (String specialite);
	 List<Devoir> findByMatiere (String matiere);

	 List<Devoir> getAllDevoir();
	}