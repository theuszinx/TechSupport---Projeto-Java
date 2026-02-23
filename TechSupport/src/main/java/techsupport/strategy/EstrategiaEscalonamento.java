package techsupport.strategy;

import techsupport.model.OrdemServico;
import java.util.List;

public interface EstrategiaEscalonamento {
    OrdemServico escolherProxima(List<OrdemServico> lista);
}
