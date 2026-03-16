package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GerenciadorEstrategia {
    private final Queue<OrdemServico> fila;

    // Construtor vazio = Será uma Queue normal (FIFO)
    public GerenciadorEstrategia() {
        this.fila = new LinkedList<>();
    }

    // Construtor com comparator = Será uma PriorityQueue
    public GerenciadorEstrategia(Comparator<OrdemServico> estrategia) {
        this.fila = new PriorityQueue<>(estrategia);
    }

    public void adicionarOrdem(OrdemServico ordem) {
        if (ordem != null) {
            this.fila.offer(ordem);
        }
    }

    public OrdemServico proximoAtendimento() {
        return this.fila.poll();
    }

    public boolean possuiOrdens() {
        return !this.fila.isEmpty();
    }

    public int tamanho() {
        return this.fila.size();
    }
}