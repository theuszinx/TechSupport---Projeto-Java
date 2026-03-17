package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.*;

public class GerenciadorEstrategias {
    private final Queue<OrdemServico> fila;

    // Construtor vazio = Será uma Queue normal (FIFO)
    public GerenciadorEstrategias() {
        this.fila = new LinkedList<>();
    }

    // Construtor com comparator = Será uma PriorityQueue
    public GerenciadorEstrategias(Comparator<OrdemServico> estrategia) {
        this.fila = new PriorityQueue<>(estrategia);
    }

    public void addOrdem(OrdemServico ordem) {
        // Prevenir ordem vazia
        if (ordem != null) {
            this.fila.offer(ordem);
        }
    }

    public OrdemServico proximaOrdem() {
        return this.fila.poll();
    }

    public boolean possuiOrdens() {
        return !this.fila.isEmpty();
    }

    public int tamanho() {
        return this.fila.size();
    }

    // Retorna uma cópia da fila para exibição ou processamento externo
    public List<OrdemServico> getFila() {
        return new ArrayList<>(this.fila);
    }

    /**
     * Retorna a lista de ordens ordenada pelo comparator fornecido,
     * sem remover elementos da fila original.
     */
    public List<OrdemServico> peekOrdenado(Comparator<OrdemServico> comparator) {
        List<OrdemServico> copia = new ArrayList<>(this.fila);
        copia.sort(comparator);
        return copia;
    }

    /**
     * Remove um item específico da fila.
     *
     * @return true se o item foi encontrado e removido
     */
    public boolean remover(OrdemServico ordem) {
        return this.fila.remove(ordem);
    }
}