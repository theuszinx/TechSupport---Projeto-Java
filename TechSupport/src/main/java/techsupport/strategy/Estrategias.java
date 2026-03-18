package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.Comparator;

public final class Estrategias {
    private Estrategias() {}

    public static final Comparator<OrdemServico> PRIORIDADE = ((Comparator<OrdemServico>) (ordem1, ordem2) -> {
        // Ordenação por valor de prioridade (descendente)
        return -1 * Integer.compare(
            ordem1.getPrioridade().getValorPrioritario(),
            ordem2.getPrioridade().getValorPrioritario()
        );
    }).thenComparing(ordem -> ordem.getTempoEstimado()); // Em caso de empate, usa SJF (Menor Tempo)

    // ESTRATÉGIA 2: Menor tempo (Ordem crescente)
    public static Comparator<OrdemServico> MENOR_TEMPO = (ordem1, ordem2) -> {
        return Integer.compare(
                ordem1.getTempoEstimado(),
                ordem2.getTempoEstimado()
        );
    };
}