package techsupport.repository;

import java.util.Queue;
import techsupport.model.OrdemServico;

// Armazenar ordens de serviço
public class OrdemServicoRepository {
    // Queue<OrdemServico> ou PriorityQueue<OrdemServico>
    private Queue<OrdemServico> listaOrdemsServico;

    public OrdemServicoRepository(){}

    public void adicionar(){};
    public void listar(){};
    public void buscarProxima(){};
}