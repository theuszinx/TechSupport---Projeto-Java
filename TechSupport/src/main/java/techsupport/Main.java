package techsupport;

import techsupport.strategy.*;
import techsupport.model.*;

import techsupport.util.GeradorDadosTeste;

import java.util.ArrayList;
import java.util.List;

class Main{
    public static void main(String[] args){
        EstrategiaEscalonamento fila = new MultiEstrategias(
                List.of(
                        EstrategiaPrioridade.comparator,
                        EstrategiaMenorTempo.comparator
                )
        );
        try{
            GeradorDadosTeste.gerarOrdens(fila, 20);

        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("ERRO: A quantidade ultrapassou o número de exemplos. Qtd Máxima: " + GeradorDadosTeste.getORDENS().size());

        }

        while(fila.possuiOrdens()){
            System.out.println(fila.proximaOrdem());
        }

        List<Tecnico> listaTecnicos = new ArrayList<>();
        GeradorDadosTeste.gerarTecnicos(listaTecnicos, 10);
        listaTecnicos.forEach(System.out::println);

    }
}