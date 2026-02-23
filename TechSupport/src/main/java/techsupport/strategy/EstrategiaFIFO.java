package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.List;

public class EstrategiaFIFO implements EstrategiaEscalonamento {
    @Override
    public OrdemServico escolherProxima(List<OrdemServico> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
    }
}
