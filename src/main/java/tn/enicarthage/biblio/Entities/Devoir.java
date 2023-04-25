package tn.enicarthage.biblio.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQuery(name="Devoir.getAllDevoir", query="select c from Devoir c")
@NamedQuery(name="Devoir.RechercheParMatiere", query="select d from Devoir d where d.matiere=:matiere")
@NamedQuery(name="Devoir.RechercheParSpecilite" ,query="select d from Devoir d where d.specialite=:specialite")
//@NamedQuery(name="Devoir.supprimerDevoir", query="delete d from Devoir d where d.id_devoir=:id")

@Entity 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="T_Devoir")
public class Devoir implements Serializable{
	
	private static final long serialVersionUID= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_devoir")
	private Integer id_devoir;
	
	@Column(name = "session")
	private String session;
	@Column(name = "matiere")
	private String matiere;
	@Column(name = "specialite")
	private String specialite;
	@Column(name = "type")
	private String type;
	@Column(name = "date_dev")
	private String date_dev;
	@Column(name = "dispo_correc")
	private boolean dispo_correc;

}
