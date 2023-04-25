package tn.enicarthage.biblio.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="T_emprunt")
public class emprunt implements Serializable {

	
	private static final long serialVersionUID= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name="uid")
	private String uid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="total Emprunt")
	private String total;
	
	@Column(name="livreDetails", columnDefinition="json")
	private String livreDetails;
	
	@Column(name="createdby")
	private String createdby;
}
