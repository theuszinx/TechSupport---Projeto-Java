package techsupport.repository;

import techsupport.model.OrdemServico;
import techsupport.strategy.GerenciadorEstrategias;

import java.util.Comparator;

// Gerencia a fila de ordens de serviço integrada com as estratégias de escalonamento
public class OrdemServicoRepository {
    private final GerenciadorEstrategias gerenciador;

    public OrdemServicoRepository(GerenciadorEstrategias gerenciador) {
        this.gerenciador = gerenciador;
    }

    // Insere uma nova OS na fila, onde será ordenada pela estratégia ativa
    public void adicionar(OrdemServico os) {
        gerenciador.addOrdem(os);
    }

    // Remove e retorna a OS prioritária de acordo com as regras de escalonamento
    public OrdemServico buscarProxima() {
        return gerenciador.proximaOrdem();
    }

    // Verifica se existem ordens aguardando atendimento
    public boolean possuiOrdens() {
        return gerenciador.possuiOrdens();
    }

    // Retorna a lista de todas as ordens atualmente na fila
    public java.util.List<OrdemServico> listar() {
        return gerenciador.getFila();
    }

    /**
     * Retorna a próxima OS conforme o comparator fornecido, SEM removê-la da fila.
     * Permite inspecionar a fila antes de confirmar a alocação.
     */
    public OrdemServico peekProxima(Comparator<OrdemServico> comparator) {
        java.util.List<OrdemServico> ordenadas = gerenciador.peekOrdenado(comparator);
        return ordenadas.isEmpty() ? null : ordenadas.get(0);
    }

    /**
     * Remove uma OS específica da fila após alocação bem-sucedida.
     */
    public boolean remover(OrdemServico os) {
        return gerenciador.remover(os);
    }
}