package techsupport.exception;

public class TecnicoIndisponivelException extends RuntimeException {
    public TecnicoIndisponivelException(Throwable causa){
        super("Esse técnico não está disponível", causa);
    }
}