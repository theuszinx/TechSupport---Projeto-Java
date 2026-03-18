package techsupport.model;

import techsupport.enums.Complexidade;
import techsupport.enums.NivelTecnico;

/**
 * Classe base abstrata para todos os técnicos do sistema.
 * Cada subclasse define sua própria regra de competência via podeAtender().
 */
public abstract class Tecnico {
    private static int contadorId = 0;
    private final int id;
    private String nome;
    private NivelTecnico nivel;
    private boolean disponivel;

    public Tecnico(String nome, NivelTecnico nivel) {
        this.id = ++contadorId;
        this.disponivel = true;
        this.nome = nome;
        this.nivel = nivel;
    }

    /**
     * cada subclasse define quais complexidades pode atender.
     */
    public abstract boolean podeAtender(Complexidade complexidade);

    public boolean estaDisponivel() {
        return this.disponivel;
    }

    public boolean ocupar() {
        if (!this.disponivel) {
            return false;
        }
        this.disponivel = false;
        return true;
    }

    public boolean liberar() {
        if (this.disponivel) {
            return false;
        }
        this.disponivel = true;
        return true;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public NivelTecnico getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        String disponibilidadeAtual = estaDisponivel() ? "DISPONÍVEL" : "OCUPADO";
        return "Técnico #" + id + " [" + nivel + "] - " + nome + " (" + disponibilidadeAtual + ")";
    }
}