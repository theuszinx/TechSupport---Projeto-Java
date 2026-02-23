package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.Comparator;
import java.util.List;

public class EstrategiaPrioridade implements EstrategiaEscalonamento {
    @Override
    public OrdemServico escolherProxima(List<OrdemServico> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        
        // Retorna a OS com maior ordinal de prioridade.
        // Se houver empate, pode-se usar o ID (FIFO dentro da prioridade)
        return lista.stream()
                .max(Comparator.comparing(OrdemServico::getPrioridade)
                               .thenComparing(os -> -os.getId())) // Inverte ID para que o menor ID (mais antigo) ganhe no empate
                .orElse(null);
    }
}
