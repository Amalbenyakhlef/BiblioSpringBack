package tn.enicarthage.biblio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import tn.enicarthage.biblio.Entities.CategorieEnum;
import tn.enicarthage.biblio.Entities.Devoir;
import tn.enicarthage.biblio.Entities.Livre;
@Repository
public interface LivreRepository extends JpaRepository<Livre,Integer>{
	//List<Livre> findAll();

	List<Livre> findByCategorie (String categorie);
	List<Livre> findByTitre (String titre);
	List<Livre> findByAuteur (String auteur);
	List<Livre> getAllLivre();
	@Query("SELECT l FROM Livre l ORDER BY l.nb_emprunts DESC")
    List<Livre> findLivresLesPlusEmpruntes();
	
	@Modifying
	@Transactional
	Integer updateLivreDispo(@Param("disponnible") Boolean disponnible,@Param("id_Livre") Integer id_Livre);
}
