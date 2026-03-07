package techsupport.exception;

public class TechSupportException extends RuntimeException {
    public TechSupportException(Throwable causa) {
        super("Ocorreu um erro", causa);
    }
}