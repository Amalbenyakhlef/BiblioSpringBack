package tn.enicarthage.biblio.Entities;

import java.io.Serializable;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NamedQuery(name="Livre.getAllLivre", query="select l from Livre l")
@NamedQuery(name="Livre.updateLivreDispo", query="update Livre l set l.disponnible=:disponnible  where l.id_Livre=:id_Livre")

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="T_Livre")
public class Livre implements Serializable{
	private static final long serialVersionUID= 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Livre")
	private Integer id_Livre;
	
	@Column (name="categorie")
	private String categorie;
	
	@Column (name="titre")
	private String titre;
	@Column (name="auteur")
	private String auteur;
	@Column(name="nb_exemplaire")
	private Integer nb_exemplaire;
	@Column(name="nb_emprunts")
	private Integer nb_emprunts;
	@Column(name ="disponnible")
	private boolean disponnible;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reservePar_id",nullable=false)
	private Utilisateur user;
	
	
	public void IncrementerNb_emprunts() {
		nb_emprunts++;
	}
	
	

}
