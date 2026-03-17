package techsupport.enums;

public enum Prioridade {
    BAIXA(1),
    MEDIA(2),
    ALTA(3),
    CRITICA(4);

    final int valorPrioritario;

    Prioridade(int valorPrioritario){
        this.valorPrioritario = valorPrioritario;
    };

    public int getValorPrioritario() {
        return valorPrioritario;
    }
}