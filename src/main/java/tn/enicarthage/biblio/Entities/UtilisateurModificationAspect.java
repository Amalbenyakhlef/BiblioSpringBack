package tn.enicarthage.biblio.Entities;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.itextpdf.text.log.LoggerFactory;

import tn.enicarthage.biblio.services.UserService;
import tn.enicarthage.biblio.services.UserServiceImp;

@Aspect
@Component
public class UtilisateurModificationAspect {
private static final Logger logger = (Logger) LoggerFactory.getLogger(UtilisateurModificationAspect.class);
@Before("execution (* tn.enicarthage.bibliotheque.Services.UserServiceImp.Utilisateur ChangeInfo(..)) && args(utilisateur)")
 public void beforeUpdateInformationsPersonnelles(JoinPoint joinPoint, Utilisateur utilisateur) {
        logger.info("Avant la mise à jour des informations personnelles de l'utilisateur : " + utilisateur.getId());
		UserServiceImp userService;
        Utilisateur utilisateurAvant = UserService.findById(utilisateur.getId());
		 if (!utilisateur.getNom().equals(utilisateurAvant.getNom())) {
            logger.info("Champ 'nom' modifié. Ancienne valeur : " + utilisateurAvant.getNom() + ", Nouvelle valeur : " + utilisateur.getNom());
        }
		 if (!utilisateur.getContactNumber().equals(utilisateurAvant.getContactNumber())) {
            logger.info("Champ 'Contact Number' modifié. Ancienne valeur : " + utilisateurAvant.getContactNumber() + ", Nouvelle valeur : " + utilisateur.getContactNumber());
        }
		 if (!utilisateur.getEmail().equals(utilisateurAvant.getEmail())) {
            logger.info("Champ 'Email' modifié. Ancienne valeur : " + utilisateurAvant.getEmail() + ", Nouvelle valeur : " + utilisateur.getEmail());
        }
		 if (!utilisateur.getPassword().equals(utilisateurAvant.getPassword())) {
            logger.info("Champ 'Password' modifié. Ancienne valeur : " + utilisateurAvant.getPassword() + ", Nouvelle valeur : " + utilisateur.getPassword());
        }
		}
		
}