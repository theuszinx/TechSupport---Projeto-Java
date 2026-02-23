package techsupport;

import techsupport.enums.NivelTecnico;
import techsupport.enums.Prioridade;
import techsupport.exception.CompetenciaInvalidaException;
import techsupport.exception.TecnicoIndisponivelException;
import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.service.SistemaTechSupport;
import techsupport.strategy.EstrategiaFIFO;
import techsupport.strategy.EstrategiaMenorTempo;
import techsupport.strategy.EstrategiaPrioridade;
import techsupport.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO TECH SUPPORT ===");
        
        SistemaTechSupport sistema = new SistemaTechSupport();
        
        // 1. Demonstração de Criação de Modelos
        System.out.println("\n1. Criando Técnicos e Ordens de Serviço...");
        Tecnico junior = new Tecnico("João Junior", NivelTecnico.JUNIOR);
        Tecnico senior = new Tecnico("Carlos Senior", NivelTecnico.SENIOR);
        
        OrdemServico osSimples = new OrdemServico("Trocar mouse", Prioridade.BAIXA, 10, 1);
        OrdemServico osComplexa = new OrdemServico("Configurar Cluster", Prioridade.CRITICA, 120, 3);
        
        System.out.println(junior);
        System.out.println(senior);
        System.out.println(osSimples);
        System.out.println(osComplexa);

        // 2. Demonstração de Exceções e Regras de Negócio (Competência)
        System.out.println("\n2. Testando Regras de Competência...");
        try {
            System.out.println("Tentando alocar OS Complexa (Nível 3) para Técnico Junior (Nível 1)...");
            sistema.getEscalonadorService().alocarTecnico(osComplexa, junior);
        } catch (CompetenciaInvalidaException e) {
            System.err.println("Erro esperado: " + e.getMessage());
        } catch (TecnicoIndisponivelException e) {
            System.err.println(e.getMessage());
        }

        try {
            System.out.println("Tentando alocar OS Complexa (Nível 3) para Técnico Senior (Nível 3)...");
            sistema.getEscalonadorService().alocarTecnico(osComplexa, senior);
            System.out.println("Sucesso! " + osComplexa);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // 3. Demonstração de Estratégias de Escalonamento
        System.out.println("\n3. Demonstrando Estratégias de Escalonamento...");
        sistema.cadastrarOS(new OrdemServico("OS A", Prioridade.BAIXA, 100, 1));
        sistema.cadastrarOS(new OrdemServico("OS B (Alta Prio)", Prioridade.ALTA, 50, 1));
        sistema.cadastrarOS(new OrdemServico("OS C (Rápida)", Prioridade.MEDIA, 10, 1));

        var pendentes = sistema.getOsRepository().listarPendentes();
        
        sistema.getEscalonadorService().setEstrategia(new EstrategiaFIFO());
        System.out.println("Próxima OS (FIFO): " + sistema.getEscalonadorService().escolherProximaOS(pendentes).getDescricao());

        sistema.getEscalonadorService().setEstrategia(new EstrategiaPrioridade());
        System.out.println("Próxima OS (Prioridade): " + sistema.getEscalonadorService().escolherProximaOS(pendentes).getDescricao());

        sistema.getEscalonadorService().setEstrategia(new EstrategiaMenorTempo());
        System.out.println("Próxima OS (Menor Tempo): " + sistema.getEscalonadorService().escolherProximaOS(pendentes).getDescricao());

        System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===\n");
        System.out.println("Iniciando Menu Principal em 3 segundos...");
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        MenuPrincipal menu = new MenuPrincipal();
        menu.executar();
    }
}
