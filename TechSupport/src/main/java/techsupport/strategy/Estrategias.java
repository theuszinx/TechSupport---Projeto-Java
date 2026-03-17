package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.Comparator;
import java.util.List;

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


    // ESTRATÉGIA 3: Menor complexidade (Ordem crescente)
    public static Comparator<OrdemServico> MENOR_COMPLEXIDADE = (ordem1, ordem2) -> {
        return Integer.compare(
                ordem1.getComplexidade().getValorComplexidade(),
                ordem2.getComplexidade().getValorComplexidade()
        );
    };


    // ABORDAGEM MISTA (Múltiplas estratégias)
    public static Comparator<OrdemServico> abordagemMista(List<Comparator<OrdemServico>> listaEstrategias){
        return listaEstrategias.stream()
                .reduce(Comparator::thenComparing)
                .orElseThrow(
                        () -> new IllegalArgumentException("A lista de estratégias de ordenação não pode estar vazia.")
                );

        /*
        .reduce() cria uma comparação em cadeia.
        EX:
            List.of(Estrategias.PRIORIDADE, Estrategias.MENOR_TEMPO)
            vai gerar um comparator encadeado que ordenará baseado em prioridade,
            e para ordens de mesma prioridade (desempate), ordenará baseado em menor tempo

         */
    }
}