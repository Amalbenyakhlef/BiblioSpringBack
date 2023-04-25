package tn.enicarthage.biblio.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQuery(name ="Utilisateur.findByEmailId", query="select u from Utilisateur u where u.email=:email")
@NamedQuery(name="Utilisateur.getAllUser", query ="select new tn.enicarthage.biblio.utils.UserApprouvAdmin(u.id,u.nom,u.email,u.contactNumber,u.status) from Utilisateur u where u.role='user'")
@NamedQuery(name="Utilisateur.updateStatus", query="update Utilisateur u set u.status=:status where u.id=:id")
@NamedQuery(name="Utilisateur.getAllAdmin", query ="select u.email from Utilisateur u where u.role='admin'")


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "T_Utilisateur")
public  class Utilisateur implements Serializable {

	private static final long serialVersionUID= 1L;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Integer id;

@Column(name = "nom")
private String nom;
@Column(name = "contactNumber")
private String contactNumber ;
@Column(name = "email")
private String email ;
@Column(name = "password")
private String password ;
@Column(name = "status")
private String status ;
@Column(name = "role")
private String role ;

/*
@ManyToMany(fetch =FetchType.EAGER , cascade =CascadeType.ALL)
@JoinTable(name ="user_roles", joinColumns =@JoinColumn(name ="user_id", referencedColumnName="id"),
	inverseJoinColumns = @JoinColumn(name ="role_id", referencedColumnName="id"))
private List<Role> roles = new ArrayList<>();*/

@OneToMany(cascade=CascadeType.ALL)
private List<Livre> Livres_empruntes;




// dans le role on peut l'utiliser pour identifier le professeur , l'etudiant et meme l'admin



}
