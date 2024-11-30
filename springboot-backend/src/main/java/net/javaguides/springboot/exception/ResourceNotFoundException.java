package net.javaguides.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // Cette  indique que lorsque cette exception est levée, le serveur renverra une réponse HTTP avec le code d'état 404 (Not Found)
/**
 * Cette classe représente une exception personnalisée utilisée pour signaler qu'une ressource demandée n'a pas été trouvée
 * Elle hérite de RuntimeException , permettant ainsi de gérer les erreurs liées aux ressources de manière centralisée
 */
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L; // identifier de manière unique la version de la classe lors de la sérialisation et de la désérialisation
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}

/**
 * REMARQUE :
 * Cette exception est typiquement utilisée dans ls contrôlurs REST pour gérer des scénarios où
 * une ressource spécifique n'existe pas dans la BD
 */
