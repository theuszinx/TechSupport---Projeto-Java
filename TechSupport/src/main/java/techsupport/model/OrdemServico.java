package techsupport.model;

import techsupport.enums.Prioridade;
import techsupport.enums.Complexidade;
import techsupport.enums.StatusOS;

public class OrdemServico {
    private static int nextId = 0;
    private int id;

    private String descricao;
    private Prioridade prioridade;
    private Complexidade complexidade;
    private int tempoEstimado;
    private StatusOS status;
    private int tempoEspera;


    public OrdemServico(String descricao, Prioridade prioridade, Complexidade complexidade, int tempoEstimado) {
        // Pré-definidos
        this.id = ++nextId;
        this.status = StatusOS.PENDENTE;
        this.tempoEspera = 0;


        this.descricao = descricao;
        this.prioridade = prioridade;
        this.complexidade = complexidade;
        this.tempoEstimado = tempoEstimado;
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
    public Complexidade getComplexidade() {
        return complexidade;
    }
    public StatusOS getStatus() {
        return status;
    }
    public int getTempoEstimado() {
        return tempoEstimado;
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

    public String formatarTempoEstimado(int tempo){
        int horas = (int) tempo / 60;
        int minutos = (int) tempo % 60;

        // Garantir formatação 00:00
        return ((horas < 10) ? "0" : "") + horas
                + ":" +
                ((minutos < 10) ? "0" : "") + minutos;
    };

    @Override
    public String toString() {
        return "OS #" + id + " [Prioridade: " + prioridade + "] | [Complexidade: " + complexidade + "] | " +
                "(Tempo estimado ~ " + formatarTempoEstimado(tempoEstimado) + ")" +
                "\n" + descricao + " (Status: " + status + ")" + "\n";
    }
}