package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.Comparator;
import java.util.List;

public class EstrategiaMenorTempo implements EstrategiaEscalonamento {
    @Override
    public OrdemServico escolherProxima(List<OrdemServico> lista) {
        return lista.stream()
                .min(Comparator.comparingInt(OrdemServico::getTempoEstimado))
                .orElse(null);
    }
}
