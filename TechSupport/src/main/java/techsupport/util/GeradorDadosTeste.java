package techsupport.util;

import techsupport.enums.NivelTecnico;
import techsupport.enums.Prioridade;
import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.service.SistemaTechSupport;

public class GeradorDadosTeste {
    public static void gerar(SistemaTechSupport sistema) {
        // 5 Técnicos
        sistema.cadastrarTecnico(new Tecnico("João Silva", NivelTecnico.JUNIOR));
        sistema.cadastrarTecnico(new Tecnico("Maria Souza", NivelTecnico.PLENO));
        sistema.cadastrarTecnico(new Tecnico("Carlos Oliveira", NivelTecnico.SENIOR));
        sistema.cadastrarTecnico(new Tecnico("Ana Pereira", NivelTecnico.JUNIOR));
        sistema.cadastrarTecnico(new Tecnico("Ricardo Santos", NivelTecnico.PLENO));

        // 10 Ordens de Serviço
        sistema.cadastrarOS(new OrdemServico("Problema no Windows", Prioridade.MEDIA, 30, 1));
        sistema.cadastrarOS(new OrdemServico("Internet lenta", Prioridade.BAIXA, 20, 1));
        sistema.cadastrarOS(new OrdemServico("Servidor fora do ar", Prioridade.CRITICA, 120, 3));
        sistema.cadastrarOS(new OrdemServico("Instalar Impressora", Prioridade.BAIXA, 15, 1));
        sistema.cadastrarOS(new OrdemServico("Trocar HD", Prioridade.MEDIA, 60, 2));
        sistema.cadastrarOS(new OrdemServico("Configurar Roteador", Prioridade.MEDIA, 40, 2));
        sistema.cadastrarOS(new OrdemServico("Recuperar Dados", Prioridade.ALTA, 180, 3));
        sistema.cadastrarOS(new OrdemServico("Email não funciona", Prioridade.MEDIA, 25, 1));
        sistema.cadastrarOS(new OrdemServico("Vírus no PC", Prioridade.ALTA, 90, 2));
        sistema.cadastrarOS(new OrdemServico("Atualizar Sistema Operacional", Prioridade.MEDIA, 50, 2));
        
        System.out.println("Dados de teste gerados com sucesso!");
    }
}
