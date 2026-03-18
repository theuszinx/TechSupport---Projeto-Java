package techsupport.exception;

/**
 * Exceção CHECKED lançada quando a fila de OS atinge seu limite máximo.
 */
public class CapacidadeFilaExcedidaException extends TechSupportException {
    public CapacidadeFilaExcedidaException(int limite) {
        super("Limite da fila excedido! O sistema suporta no máximo " + limite + " ordens pendentes.");
    }
}
