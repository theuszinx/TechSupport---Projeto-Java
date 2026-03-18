package techsupport.exception;

/**
 * Classe base para exceções CHECKED do sistema.
 * Exige tratamento obrigatório (try-catch ou throws).
 */
public abstract class TechSupportException extends Exception {
    public TechSupportException(String mensagem) {
        super(mensagem);
    }

    public TechSupportException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
