package techsupport.strategy;

import java.util.List;
import techsupport.model.OrdemServico;

public interface EstrategiaEscalonamento {
    OrdemServico escolherProxima(List<OrdemServico> lista);
}