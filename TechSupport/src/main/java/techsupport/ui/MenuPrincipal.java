package techsupport.ui;

import techsupport.service.SistemaTechSupport;
import techsupport.util.ConsoleUtils;

public class MenuPrincipal {
    private final SistemaTechSupport sistema;

    public MenuPrincipal(SistemaTechSupport sistema) {
        this.sistema = sistema;
    }

    // Loop principal da aplicação
    public void executar() {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = ConsoleUtils.lerInt("Escolha uma opção: ");
            processarOpcao(opcao);
        }
        System.out.println("Encerrando o sistema...");
    }

    private void exibirMenu() {
        ConsoleUtils.limparTela();
        System.out.println("=== TECH SUPPORT ===");
        System.out.println("1 - Cadastrar técnico");
        System.out.println("2 - Cadastrar OS");
        System.out.println("3 - Listar dados");
        System.out.println("4 - Executar escalonamento");
        System.out.println("5 - Gerar dados de teste");
        System.out.println("6 - Exibir relatórios");
        System.out.println("7 - Simular passagem de tempo");
        System.out.println("0 - Sair");
        System.out.println("====================");
    }

    // Direciona a escolha do usuário para a funcionalidade correta
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> sistema.cadastrarTecnico();
            case 2 -> sistema.cadastrarOS();
            case 3 -> sistema.listarDados();
            case 4 -> sistema.escalonarOS();
            case 5 -> sistema.gerarRelatorio();
            case 6 -> sistema.gerarRelatorios();
            case 7 -> sistema.simularPassagemTempo();
            case 0 -> {}
            default -> System.out.println("Opção inválida!");
        }
        if (opcao != 0) ConsoleUtils.pausar();
    }
}