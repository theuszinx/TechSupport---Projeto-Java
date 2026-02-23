package techsupport.model;
import techsupport.enums.NivelTecnico;


public class Tecnico {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private NivelTecnico nivel;
    private boolean disponivel;

    public Tecnico(String nome, NivelTecnico nivel) {
        this.id = contadorId++;
        this.disponivel = true;
        this.nome = nome;
        this.nivel = nivel;


    }

    public boolean estaDisponivel(){
        return this.disponivel;
    }

    public boolean ocupar(){
        if(!this.disponivel){
            return false; // Já estava ocupado, então a operação falhou
        }
        
        this.disponivel = false;
        return true; 
    }

    public boolean liberar(){
        if(this.disponivel){
            return false; // Já estava liberado; falhou
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
        String disponibilidadeAtual;
        if(estaDisponivel()){
            disponibilidadeAtual = "DISPONÍVEL";
        }else{
            disponibilidadeAtual = "OCUPADO";
        }

        return "Técnico #"  + id + " [" + nivel + "] " + " - " + nome +  " (" + disponibilidadeAtual + ")";
    }
    


}