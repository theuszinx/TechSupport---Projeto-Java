package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.Comparator;

public final class Estrategias {
    private Estrategias() {}

    // ESTRATÉGIA 1: Maior prioridade (Ordem decrescente)
    public static final Comparator<OrdemServico> PRIORIDADE = (ordem1, ordem2) -> {
        // Multiplica por -1 para reverter a comparação, tornando-a decrescente (Fica mais legível)
        return -1 * Integer.compare(
            ordem1.getPrioridade().getValorPrioritario(),
            ordem2.getPrioridade().getValorPrioritario()
        );
    };

    // ESTRATÉGIA 2: Menor tempo (Ordem crescente)
    public static Comparator<OrdemServico> MENOR_TEMPO = (ordem1, ordem2) -> {
        return Integer.compare(
                ordem1.getTempoEstimado(),
                ordem2.getTempoEstimado()
        );
    };
}