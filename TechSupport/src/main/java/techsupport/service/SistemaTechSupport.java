package techsupport.service;

import techsupport.model.*;
import techsupport.repository.*;
import techsupport.enums.*;
import techsupport.util.ConsoleUtils;
import techsupport.util.GeradorDadosTeste;
import techsupport.strategy.Estrategias;
import techsupport.strategy.GerenciadorEstrategias;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SistemaTechSupport {
    private final TecnicoRepository tecnicoRepo;
    private final OrdemServicoRepository osRepo;
    private final EscalonadorService escalonador;
    private final GerenciadorEstrategias gerenciadorEstrategias; // Necessário para resetar/configurar a fila
    // Histório completo de todas as OS já criadas (inclusive as removidas da fila pelo escalonador)
    private final List<OrdemServico> historicoOS = new ArrayList<>();

    public SistemaTechSupport(TecnicoRepository tecnicoRepo, OrdemServicoRepository osRepo, 
                               EscalonadorService escalonador, GerenciadorEstrategias gerenciadorEstrategias) {
        this.tecnicoRepo = tecnicoRepo;
        this.osRepo = osRepo;
        this.escalonador = escalonador;
        this.gerenciadorEstrategias = gerenciadorEstrategias;
    }

    // Cadastro manual de técnico via console
    public void cadastrarTecnico() {
        System.out.println("=== CADASTRAR TÉCNICO ===");

        // 1. Solicitar nome — não permite nome vazio
        String nome;
        while (true) {
            nome = ConsoleUtils.lerString("Nome do técnico: ").trim();
            if (!nome.isEmpty()) {
                break;
            }
            System.out.println("Erro: o nome não pode ser vazio. Tente novamente.");
        }

        // 2. Solicitar nível via menu numérico com validação completa
        NivelTecnico nivel = null;
        while (nivel == null) {
            System.out.println("Selecione o nível do técnico:");
            System.out.println("1 - JUNIOR");
            System.out.println("2 - PLENO");
            System.out.println("3 - SENIOR");
            try {
                int nivelOp = Integer.parseInt(ConsoleUtils.lerString("Opção: ").trim());
                nivel = switch (nivelOp) {
                    case 1 -> NivelTecnico.JUNIOR;
                    case 2 -> NivelTecnico.PLENO;
                    case 3 -> NivelTecnico.SENIOR;
                    default -> {
                        System.out.println("Erro: opção inválida! Digite 1, 2 ou 3.");
                        yield null;
                    }
                };
            } catch (NumberFormatException e) {
                System.out.println("Erro: entrada inválida! Digite apenas um número (1, 2 ou 3).");
            }
        }

        // 3. Criar e armazenar o técnico (ID incremental automático, status DISPONÍVEL por padrão)
        Tecnico tecnico = new Tecnico(nome, nivel);
        tecnicoRepo.adicionar(tecnico);

        System.out.println("Técnico cadastrado com sucesso!");
    }

    // Cadastro manual de OS via console
    public void cadastrarOS() {
        System.out.println("=== CADASTRAR ORDEM DE SERVIÇO ===");

        // 1. Solicitar descrição — não permite valor vazio
        String desc;
        while (true) {
            desc = ConsoleUtils.lerString("Descrição da OS: ").trim();
            if (!desc.isEmpty()) {
                break;
            }
            System.out.println("Erro: a descrição não pode ser vazia. Tente novamente.");
        }

        // 2. Solicitar prioridade via menu numérico com validação completa
        Prioridade prioridade = null;
        while (prioridade == null) {
            System.out.println("Selecione a prioridade:");
            System.out.println("1 - BAIXA");
            System.out.println("2 - MÉDIA");
            System.out.println("3 - ALTA");
            System.out.println("4 - CRÍTICA");
            try {
                int prioOp = Integer.parseInt(ConsoleUtils.lerString("Opção: ").trim());
                prioridade = switch (prioOp) {
                    case 1 -> Prioridade.BAIXA;
                    case 2 -> Prioridade.MEDIA;
                    case 3 -> Prioridade.ALTA;
                    case 4 -> Prioridade.CRITICA;
                    default -> {
                        System.out.println("Erro: opção inválida! Digite 1, 2, 3 ou 4.");
                        yield null;
                    }
                };
            } catch (NumberFormatException e) {
                System.out.println("Erro: entrada inválida! Digite apenas um número (1 a 4).");
            }
        }

        // 3. Solicitar complexidade via menu numérico com validação completa
        Complexidade complexidade = null;
        while (complexidade == null) {
            System.out.println("Selecione a complexidade:");
            System.out.println("1 - BAIXA");
            System.out.println("2 - MÉDIA");
            System.out.println("3 - ALTA");
            try {
                int compOp = Integer.parseInt(ConsoleUtils.lerString("Opção: ").trim());
                complexidade = switch (compOp) {
                    case 1 -> Complexidade.BAIXA;
                    case 2 -> Complexidade.MEDIA;
                    case 3 -> Complexidade.ALTA;
                    default -> {
                        System.out.println("Erro: opção inválida! Digite 1, 2 ou 3.");
                        yield null;
                    }
                };
            } catch (NumberFormatException e) {
                System.out.println("Erro: entrada inválida! Digite apenas um número (1, 2 ou 3).");
            }
        }

        // 4. Solicitar tempo estimado — deve ser um número positivo
        int tempo = 0;
        while (tempo <= 0) {
            try {
                tempo = Integer.parseInt(ConsoleUtils.lerString("Tempo estimado (min): ").trim());
                if (tempo <= 0) {
                    System.out.println("Erro: o tempo estimado deve ser maior que zero. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: entrada inválida! Digite um número inteiro positivo.");
            }
        }

        // 5. Criar e armazenar a OS (ID incremental automático, status PENDENTE por padrão)
        OrdemServico os = new OrdemServico(desc, prioridade, complexidade, tempo);
        osRepo.adicionar(os);
        historicoOS.add(os); // Registra no histórico completo

        System.out.println("OS cadastrada e enviada para a fila com sucesso!");
    }

    // Apresenta menu de estratégia ao usuário e executa o escalonamento
    public void escalonarOS() {
        System.out.println("\n=== ESTRATÉGIA DE ESCALONAMENTO ===");
        System.out.println("1 - FIFO (ordem de chegada)");
        System.out.println("2 - Maior prioridade");
        System.out.println("3 - Menor tempo estimado (SJF)");

        int opcao;
        while (true) {
            opcao = ConsoleUtils.lerInt("Escolha a estratégia (1, 2 ou 3): ");
            if (opcao >= 1 && opcao <= 3) break;
            System.out.println("Opção inválida! Digite 1, 2 ou 3.");
        }

        Comparator<OrdemServico> comparator = switch (opcao) {
            case 1 -> {
                System.out.println("[ESTRATÉGIA] FIFO (ordem de chegada) selecionado.");
                yield Comparator.comparingInt(OrdemServico::getId);
            }
            case 2 -> {
                System.out.println("[ESTRATÉGIA] Maior prioridade selecionada.");
                yield Estrategias.PRIORIDADE;
            }
            default -> {
                System.out.println("[ESTRATÉGIA] Menor tempo estimado (SJF) selecionado.");
                yield Estrategias.MENOR_TEMPO;
            }
        };

        escalonador.alocarProximaOS(comparator);
    }

    // Exibe todos os dados atuais do sistema
    public void listarDados() {
        System.out.println("\n--- TÉCNICOS ---");
        tecnicoRepo.listar().forEach(System.out::println);
        
        System.out.println("\n--- FILA DE ATENDIMENTO (PENDENTES) ---");
        java.util.List<OrdemServico> ordens = osRepo.listar();
        if(ordens.isEmpty()) {
            System.out.println("Fila vazia.");
        } else {
            ordens.forEach(System.out::println);
        }
    }

    // Gera relatório consolidado com Stream API
    public void gerarRelatorios() {
        System.out.println("=== RELATÓRIOS DO SISTEMA ===");

        List<Tecnico> tecnicos = tecnicoRepo.listar();
        // Usa o histórico completo para incluir OS em atendimento e concluídas
        List<OrdemServico> ordens = new ArrayList<>(historicoOS);
        if (ordens.isEmpty()) ordens.addAll(osRepo.listar()); // fallback para compatibilidade

        // Guarda para cenário sem dados
        if (tecnicos.isEmpty() && ordens.isEmpty()) {
            System.out.println("Nenhum dado disponível para gerar relatórios.");
            return;
        }

        // ── Ordens de Serviço ──
        System.out.println("\nOrdens de Serviço:");
        System.out.println("  Total: " + ordens.size());

        // Contagem por status via filter + count
        long pendentes     = ordens.stream().filter(o -> o.getStatus() == StatusOS.PENDENTE).count();
        long emAtendimento = ordens.stream().filter(o -> o.getStatus() == StatusOS.EM_ATENDIMENTO).count();
        long concluidas    = ordens.stream().filter(o -> o.getStatus() == StatusOS.CONCLUIDA).count();
        System.out.println("  Pendentes: "      + pendentes);
        System.out.println("  Em atendimento: " + emAtendimento);
        System.out.println("  Concluídas: "     + concluidas);

        // Agrupamento por prioridade via groupingBy + counting
        Map<Prioridade, Long> porPrioridade = ordens.stream()
                .collect(Collectors.groupingBy(OrdemServico::getPrioridade, Collectors.counting()));
        System.out.println("\n  Por prioridade:");
        System.out.println("    Baixa: "   + porPrioridade.getOrDefault(Prioridade.BAIXA,   0L));
        System.out.println("    Média: "   + porPrioridade.getOrDefault(Prioridade.MEDIA,   0L));
        System.out.println("    Alta: "    + porPrioridade.getOrDefault(Prioridade.ALTA,    0L));
        System.out.println("    Crítica: " + porPrioridade.getOrDefault(Prioridade.CRITICA, 0L));

        // ── Técnicos ──
        System.out.println("\nTécnicos:");
        System.out.println("  Total: " + tecnicos.size());

        // Disponíveis / Ocupados via filter + count
        long disponiveis = tecnicos.stream().filter(Tecnico::estaDisponivel).count();
        long ocupados    = tecnicos.stream().filter(t -> !t.estaDisponivel()).count();
        System.out.println("  Disponíveis: " + disponiveis);
        System.out.println("  Ocupados: "    + ocupados);

        // Agrupamento por nível via groupingBy + counting
        Map<NivelTecnico, Long> porNivel = tecnicos.stream()
                .collect(Collectors.groupingBy(Tecnico::getNivel, Collectors.counting()));
        System.out.println("\n  Por nível:");
        System.out.println("    Junior: " + porNivel.getOrDefault(NivelTecnico.JUNIOR, 0L));
        System.out.println("    Pleno: "  + porNivel.getOrDefault(NivelTecnico.PLENO,  0L));
        System.out.println("    Senior: " + porNivel.getOrDefault(NivelTecnico.SENIOR, 0L));
    }

    // Simula conclusão das OS em atendimento e libera técnicos ocupados
    public void simularPassagemTempo() {
        System.out.println("=== SIMULAÇÃO DE TEMPO ===");

        List<Tecnico> tecnicos = tecnicoRepo.listar();
        List<OrdemServico> todasOS = new ArrayList<>(historicoOS);

        // Guarda: sistema sem dados
        if (tecnicos.isEmpty() && todasOS.isEmpty()) {
            System.out.println("Nenhum dado disponível para simulação.");
            return;
        }

        // 1. Concluir OS em atendimento
        long osConcluidas = todasOS.stream()
                .filter(o -> o.getStatus() == StatusOS.EM_ATENDIMENTO)
                .peek(o -> o.setStatus(StatusOS.CONCLUIDA))
                .count();

        if (osConcluidas == 0) {
            System.out.println("Nenhuma OS em atendimento para concluir.");
        }

        // 2. Liberar técnicos ocupados
        long tecnicosLiberados = tecnicos.stream()
                .filter(t -> !t.estaDisponivel())
                .peek(Tecnico::liberar)
                .count();

        if (tecnicosLiberados == 0) {
            System.out.println("Nenhum técnico ocupado no momento.");
        }

        // 3. Exibir resumo (apenas se houve alguma alteração)
        if (osConcluidas > 0 || tecnicosLiberados > 0) {
            System.out.println();
            if (osConcluidas    > 0) System.out.println("OS concluídas: "      + osConcluidas);
            if (tecnicosLiberados > 0) System.out.println("Técnicos liberados: " + tecnicosLiberados);
            System.out.println("\nSimulação realizada com sucesso!");
        }
    }

    // Usa a classe utilitária para preencher o sistema rapidamente
    public void gerarRelatorio() {
        List<OrdemServico> novas = GeradorDadosTeste.gerarOrdens(gerenciadorEstrategias, 10);
        historicoOS.addAll(novas); // Registra no histórico completo

        // Mock de geração de técnicos (Direto no repo)
        for(int i=0; i<5; i++) {
            tecnicoRepo.adicionar(new Tecnico("Técnico Teste " + (i+1), NivelTecnico.nivelRandom()));
        }
        System.out.println("Dados de teste gerados com sucesso!");
    }
}