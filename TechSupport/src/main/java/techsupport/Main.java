package techsupport;

import techsupport.enums.Prioridade;
import techsupport.model.OrdemServico;

class Main{
    public static void main(String[] args){
        OrdemServico ordem = new OrdemServico("Commitar para o github", Prioridade.BAIXA, 20, 2);

        System.out.println(ordem.toString());
    }
}