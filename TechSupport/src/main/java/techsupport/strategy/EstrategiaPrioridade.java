package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.Comparator;

public class EstrategiaPrioridade extends EstrategiaEscalonamento {
    public static final Comparator<OrdemServico> comparator = (ordem1, ordem2) -> {
        // Multiplica por -1 para reverter a comparação, tornando-a decrescente (Fica mais legível)
        return -1 * Integer.compare(
                ordem1.getPrioridade().getValorPrioritario(),
                ordem2.getPrioridade().getValorPrioritario()
        );
    };

    public EstrategiaPrioridade(){
        super(comparator);
    }


}
