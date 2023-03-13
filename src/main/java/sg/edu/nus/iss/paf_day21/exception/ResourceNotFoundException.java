package sg.edu.nus.iss.paf_day21.exception;


public class ResourceNotFoundException extends RuntimeException{
    
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

}
