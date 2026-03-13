package techsupport;

import techsupport.enums.NivelTecnico;
import techsupport.enums.Prioridade;
import techsupport.enums.Complexidade;

import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.ui.MenuPrincipal;
import techsupport.util.GeradorDadosTeste;

import java.awt.*;
import java.util.List;

class Main{
    public static void main(String[] args){
        /*
        OrdemServico ordem = new OrdemServico("Commitar para o github", Prioridade.BAIXA, Complexidade.BAIXA, 2);
        Tecnico tecnico = new Tecnico("Bananildo", NivelTecnico.JUNIOR);

        System.out.println(ordem.toString());


        System.out.println(tecnico.toString());
        tecnico.ocupar();
        System.out.println(tecnico.estaDisponivel());
        tecnico.liberar();
        System.out.println(tecnico.estaDisponivel());

        // Opções do menu principal (Apenas print)
        MenuPrincipal menu = new MenuPrincipal();
        menu.opcoes();
         */

        GeradorDadosTeste geradorDados = new GeradorDadosTeste();
        List<Tecnico> listaTecnicos = geradorDados.gerarTecnicos(5);
        List<OrdemServico> listaOrdens = geradorDados.gerarOrdemsServico(10);

        listaTecnicos.forEach(System.out::println);
        System.out.println();
        listaOrdens.forEach(System.out::println);
    }
}