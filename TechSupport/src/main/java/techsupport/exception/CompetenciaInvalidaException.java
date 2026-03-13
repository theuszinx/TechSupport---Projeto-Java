package techsupport.exception;

public class CompetenciaInvalidaException extends RuntimeException {
    public CompetenciaInvalidaException(Throwable causa) {
        super("Competência inválida", causa);
    }
}