package techsupport.ui;

import techsupport.enums.NivelTecnico;
import techsupport.enums.Prioridade;
import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.service.SistemaTechSupport;
import techsupport.strategy.EstrategiaFIFO;
import techsupport.strategy.EstrategiaMenorTempo;
import techsupport.strategy.EstrategiaPrioridade;
import techsupport.util.ConsoleUtils;
import techsupport.util.GeradorDadosTeste;

public class MenuPrincipal {
    private SistemaTechSupport sistema;

    public MenuPrincipal() {
        this.sistema = new SistemaTechSupport();
    }

    public void executar() {
        int opcao;
        do {
            ConsoleUtils.limparTela();
            System.out.println("=== TECH SUPPORT ===");
            System.out.println("1 - Cadastrar técnico");
            System.out.println("2 - Cadastrar OS");
            System.out.println("3 - Listar dados");
            System.out.println("4 - Executar escalonamento");
            System.out.println("5 - Gerar dados de teste");
            System.out.println("6 - Alterar estratégia de escalonamento");
            System.out.println("0 - Sair");
            opcao = ConsoleUtils.lerInt("Escolha: ");

            switch (opcao) {
                case 1: cadastrarTecnico(); break;
                case 2: cadastrarOS(); break;
                case 3: sistema.listarDados(); ConsoleUtils.pausar(); break;
                case 4: sistema.alocarOS(); ConsoleUtils.pausar(); break;
                case 5: GeradorDadosTeste.gerar(sistema); ConsoleUtils.pausar(); break;
                case 6: alterarEstrategia(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!"); ConsoleUtils.pausar();
            }
        } while (opcao != 0);
    }

    private void cadastrarTecnico() {
        String nome = ConsoleUtils.lerString("Nome do técnico: ");
        System.out.println("Nível: 1-JUNIOR, 2-PLENO, 3-SENIOR");
        int nivelOp = ConsoleUtils.lerInt("Escolha: ");
        NivelTecnico nivel = NivelTecnico.values()[Math.max(0, Math.min(2, nivelOp - 1))];
        sistema.cadastrarTecnico(new Tecnico(nome, nivel));
        System.out.println("Técnico cadastrado com sucesso!");
        ConsoleUtils.pausar();
    }

    private void cadastrarOS() {
        String desc = ConsoleUtils.lerString("Descrição da OS: ");
        System.out.println("Prioridade: 1-BAIXA, 2-MEDIA, 3-ALTA, 4-CRITICA");
        int prioOp = ConsoleUtils.lerInt("Escolha: ");
        Prioridade prioridade = Prioridade.values()[Math.max(0, Math.min(3, prioOp - 1))];
        int tempo = ConsoleUtils.lerInt("Tempo estimado (min): ");
        int complexidade = ConsoleUtils.lerInt("Complexidade (1-BAIXA, 2-MEDIA, 3-ALTA): ");
        sistema.cadastrarOS(new OrdemServico(desc, prioridade, tempo, complexidade));
        System.out.println("OS cadastrada com sucesso!");
        ConsoleUtils.pausar();
    }

    private void alterarEstrategia() {
        System.out.println("Estratégia: 1-FIFO, 2-Prioridade, 3-Menor Tempo");
        int estOp = ConsoleUtils.lerInt("Escolha: ");
        switch (estOp) {
            case 1: sistema.getEscalonadorService().setEstrategia(new EstrategiaFIFO()); break;
            case 2: sistema.getEscalonadorService().setEstrategia(new EstrategiaPrioridade()); break;
            case 3: sistema.getEscalonadorService().setEstrategia(new EstrategiaMenorTempo()); break;
            default: System.out.println("Opção inválida!");
        }
        System.out.println("Estratégia alterada.");
        ConsoleUtils.pausar();
    }
}
