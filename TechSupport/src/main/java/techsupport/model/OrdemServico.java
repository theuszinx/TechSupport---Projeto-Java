package techsupport.model;

import techsupport.enums.Prioridade;
import techsupport.enums.StatusOS;

public class OrdemServico {
    private static int contadorId = 1;
    private int id;

    private String descricao;
    private Prioridade prioridade;
    private int tempoEstimado;
    private int complexidade;
    private StatusOS status;
    private int tempoEspera;


    public OrdemServico(String descricao, Prioridade prioridade, int tempoEstimado, int complexidade) {
        // Pré-definidos
        this.id = contadorId++; 
        this.status = StatusOS.PENDENTE;
        this.tempoEspera = 0;


        this.descricao = descricao;
        this.prioridade = prioridade;
        this.tempoEstimado = tempoEstimado;
        this.complexidade = complexidade;
    }


    // Getters
    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
    public Prioridade getPrioridade() {
        return prioridade;
    }
    public int getTempoEstimado() {
        return tempoEstimado;
    }
    public int getComplexidade() {
        return complexidade;
    }
    public StatusOS getStatus() {
        return status;
    }
    public int getTempoEspera() {
        return tempoEspera;
    }


    // Setters
    public void setStatus(StatusOS status) {
        this.status = status;
    }
    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }


    @Override
    public String toString() {
        return "OS #" + id + " [" + prioridade + "] - " + descricao + " (Status: " + status + ")";
    }
}