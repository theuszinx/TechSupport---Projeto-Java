package techsupport;

import techsupport.service.*;
import techsupport.ui.MenuPrincipal;
import techsupport.repository.*;
import techsupport.strategy.*;


public class Main {
    public static void main(String[] args) {
        // 1. Inicializa Repositórios e Estratégias
        // A fila usa FIFO como padrão; a estratégia é definida em tempo de execução pelo usuário (Opção 4)
        GerenciadorEstrategias estrategias = new GerenciadorEstrategias();
        OrdemServicoRepository osRepo = new OrdemServicoRepository(estrategias);
        TecnicoRepository tecnicoRepo = new TecnicoRepository();

        // 2. Inicializa Serviços com as dependências
        EscalonadorService escalonador = new EscalonadorService(tecnicoRepo, osRepo);
        SistemaTechSupport sistema = new SistemaTechSupport(tecnicoRepo, osRepo, escalonador, estrategias);
        
        // 3. Inicializa a interface com o serviço central
        MenuPrincipal menu = new MenuPrincipal(sistema);
        
        // 4. Inicia o fluxo principal
        menu.executar();
    }
}