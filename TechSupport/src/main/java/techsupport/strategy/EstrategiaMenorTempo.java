package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.Comparator;

public class EstrategiaMenorTempo extends EstrategiaEscalonamento {
    public static Comparator<OrdemServico> comparator = (ordem1, ordem2) -> {
        return Integer.compare(
                ordem1.getTempoEstimado(),
                ordem2.getTempoEstimado()
        );
    };

    public EstrategiaMenorTempo(){
        super(comparator);
    }

}
