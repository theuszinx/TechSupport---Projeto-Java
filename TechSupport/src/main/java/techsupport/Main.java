package techsupport;

import techsupport.enums.NivelTecnico;
import techsupport.enums.Prioridade;

import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;

class Main{
    public static void main(String[] args){
        OrdemServico ordem = new OrdemServico("Commitar para o github", Prioridade.BAIXA, 20, 2);
        Tecnico tecnico = new Tecnico("Bananildo", NivelTecnico.JUNIOR);

        
        System.out.println(ordem.toString());


        System.out.println(tecnico.toString());
        tecnico.ocupar();
        System.out.println(tecnico.estaDisponivel());
        tecnico.liberar();
        System.out.println(tecnico.estaDisponivel());
    }
}