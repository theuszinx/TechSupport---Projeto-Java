package techsupport.repository;

import techsupport.model.OrdemServico;
import techsupport.enums.StatusOS;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemServicoRepository {
    private List<OrdemServico> ordensServico = new ArrayList<>();

    public void adicionar(OrdemServico os) {
        ordensServico.add(os);
    }

    public List<OrdemServico> listar() {
        return new ArrayList<>(ordensServico);
    }

    public List<OrdemServico> listarPendentes() {
        return ordensServico.stream()
                .filter(os -> os.getStatus() == StatusOS.PENDENTE)
                .collect(Collectors.toList());
    }

    public void remover(OrdemServico os) {
        ordensServico.remove(os);
    }
}
