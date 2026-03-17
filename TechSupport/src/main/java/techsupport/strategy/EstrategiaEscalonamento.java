package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.*;

public class EstrategiaEscalonamento {
    private final Queue<OrdemServico> fila;

    // Construtor vazio = Será uma Queue normal (LinkedList, FIFO)
    public EstrategiaEscalonamento() {
        this.fila = new LinkedList<>();
    }

    // Construtor com comparator = Será uma PriorityQueue
    public EstrategiaEscalonamento(Comparator<OrdemServico> estrategia) {
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
}