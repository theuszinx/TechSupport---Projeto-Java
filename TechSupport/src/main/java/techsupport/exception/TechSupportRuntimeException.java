package techsupport.exception;

/**
 * Classe base para exceções UNCHECKED (Runtime) do sistema.
 * Representa erros de regra de negócio ou estados inválidos.
 */
public abstract class TechSupportRuntimeException extends RuntimeException {
    public TechSupportRuntimeException(String mensagem) {
        super(mensagem);
    }

    public TechSupportRuntimeException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
