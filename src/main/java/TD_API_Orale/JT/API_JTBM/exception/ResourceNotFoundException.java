package TD_API_Orale.JT.API_JTBM.exception;

// exception simple quand, ne trouve pas une ressource (auteur, livre, etc.)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
