package techsupport.service;

import techsupport.enums.*;
import techsupport.model.*;
import techsupport.repository.*;
import techsupport.util.ConsoleUtils;

import java.util.List;

/**
 *  cadastro de Técnicos e Ordens de Serviço.
 */
public class CadastroService {
    private final TecnicoRepository tecnicoRepo;
    private final OrdemServicoRepository osRepo;
    private final List<OrdemServico> historicoOS; // Referência compartilhada ao histórico

    public CadastroService(TecnicoRepository tecnicoRepo, OrdemServicoRepository osRepo, List<OrdemServico> historicoOS) {
        this.tecnicoRepo = tecnicoRepo;
        this.osRepo = osRepo;
        this.historicoOS = historicoOS;
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

        // 3. Criar a subclasse correta via polimorfismo (ID automático, status DISPONÍVEL)
        Tecnico tecnico = switch (nivel) {
            case JUNIOR -> new TecnicoJunior(nome);
            case PLENO  -> new TecnicoPleno(nome);
            case SENIOR -> new TecnicoSenior(nome);
        };
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
        
        try {
            osRepo.adicionar(os);
            historicoOS.add(os); // Registra no histórico completo
            System.out.println("OS cadastrada e enviada para a fila com sucesso!");
        } catch (techsupport.exception.CapacidadeFilaExcedidaException e) {
            System.err.println("\nERRO DO SISTEMA: " + e.getMessage());
            System.out.println("Não foi possível processar a nova OS no momento.");
        }
    }
}
