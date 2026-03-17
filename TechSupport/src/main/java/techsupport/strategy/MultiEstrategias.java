package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.Comparator;
import java.util.List;

public class MultiEstrategias extends EstrategiaEscalonamento {

    // List.of(comparators de estratégias)
    public MultiEstrategias(List<Comparator<OrdemServico>> listaEstrategias){
        super(fundirEstrategias(listaEstrategias));
    }

    /*
    Fundir vários comparators em um só (A ordem importa)
    PRIORIDADE.thenComparing(MENOR_TEMPO);
    !=
    MENOR_TEMPO.thenComparing(PRIORIDADE);
     */
    public static Comparator<OrdemServico> fundirEstrategias(List<Comparator<OrdemServico>> listaEstrategias){
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
