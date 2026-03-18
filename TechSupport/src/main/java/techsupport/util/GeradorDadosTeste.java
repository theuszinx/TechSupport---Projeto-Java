package techsupport.util;

import java.util.*;

import techsupport.enums.*;
import techsupport.model.*;
import techsupport.strategy.GerenciadorEstrategias;

public final class GeradorDadosTeste {
    private static final Random RANDOM = new Random();

    // Lista de nomes fictícios e ordens fictícias (Gerado por IA)
    private static final List<String> NOMES = List.of(
            "Arthur", "Bernardo", "Carlos", "Daniel", "Eduardo",
            "Fernando", "Gabriel", "Henrique", "Igor", "João",
            "Lucas", "Marcelo", "Nicolas", "Otávio", "Pedro",
            "Rafael", "Samuel", "Tiago", "Victor", "Wesley",
            "Alice", "Beatriz", "Camila", "Daniela", "Eduarda",
            "Fernanda", "Gabriela", "Helena", "Isabela", "Julia"
    );
    private static final List<String> SOBRENOMES = List.of(
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues",
            "Ferreira", "Alves", "Pereira", "Lima", "Gomes",
            "Costa", "Ribeiro", "Martins", "Carvalho", "Almeida",
            "Lopes", "Soares", "Fernandes", "Vieira", "Barbosa",
            "Rocha", "Dias", "Mendes", "Nunes", "Machado",
            "Moreira", "Marques", "Freitas", "Cardoso", "Ramos"
    );
    private static final List<OrdemServico> ORDENS = Arrays.asList(
            // --- Complexidade ALTA ---
            new OrdemServico("Recuperação de banco de dados SQL corrompido", Prioridade.ALTA, Complexidade.ALTA, 180),
            new OrdemServico("Análise de invasão e brecha de segurança", Prioridade.ALTA, Complexidade.ALTA, 240),
            new OrdemServico("Migração crítica de servidores para Cloud", Prioridade.MEDIA, Complexidade.ALTA, 300),
            new OrdemServico("Falha total no core de rede (Switch Principal)", Prioridade.ALTA, Complexidade.ALTA, 120),
            new OrdemServico("Restauração de Backup pós-ataque Ransomware", Prioridade.ALTA, Complexidade.ALTA, 480),

            // --- Complexidade MÉDIA ---
            new OrdemServico("Configuração de regras de Firewall corporativo", Prioridade.ALTA, Complexidade.MEDIA, 90),
            new OrdemServico("Instalação e integração de API de pagamentos", Prioridade.MEDIA, Complexidade.MEDIA, 150),
            new OrdemServico("Lentidão intermitente na rede Wi-Fi", Prioridade.MEDIA, Complexidade.MEDIA, 60),
            new OrdemServico("Configuração de VPN para novos colaboradores", Prioridade.BAIXA, Complexidade.MEDIA, 40),
            new OrdemServico("Atualização de políticas de grupo (GPO) no AD", Prioridade.MEDIA, Complexidade.MEDIA, 80),
            new OrdemServico("Sincronização de e-mails Outlook travada", Prioridade.BAIXA, Complexidade.MEDIA, 45),
            new OrdemServico("Troubleshooting em impressora de etiquetas", Prioridade.MEDIA, Complexidade.MEDIA, 30),

            // --- Complexidade BAIXA ---
            new OrdemServico("Reset de senha e desbloqueio de conta", Prioridade.ALTA, Complexidade.BAIXA, 10),
            new OrdemServico("Instalação de pacote Office em nova máquina", Prioridade.BAIXA, Complexidade.BAIXA, 30),
            new OrdemServico("Limpeza física e troca de pasta térmica", Prioridade.BAIXA, Complexidade.BAIXA, 60),
            new OrdemServico("Substituição de mouse e teclado com defeito", Prioridade.BAIXA, Complexidade.BAIXA, 15),
            new OrdemServico("Mapeamento de unidade de rede", Prioridade.MEDIA, Complexidade.BAIXA, 20),
            new OrdemServico("Orientação de uso de videoconferência", Prioridade.BAIXA, Complexidade.BAIXA, 15),
            new OrdemServico("Troca de toner e atolamento de papel", Prioridade.MEDIA, Complexidade.BAIXA, 20),
            new OrdemServico("Verificação de conexão de cabos de monitor", Prioridade.BAIXA, Complexidade.BAIXA, 5)
    );

    public static List<OrdemServico> getORDENS() {
        return ORDENS;
    }

    public static List<OrdemServico> gerarOrdens(GerenciadorEstrategias gerenciador, int quantidade) throws techsupport.exception.CapacidadeFilaExcedidaException {
        // Prevenir ordens repetidas
        if(quantidade > ORDENS.size()){
            throw new ArrayIndexOutOfBoundsException();
        }

        List<OrdemServico> listaMisturada = new ArrayList<>(ORDENS);
        Collections.shuffle(listaMisturada);

        List<OrdemServico> adicionadas = listaMisturada.subList(0, quantidade);
        for (OrdemServico os : adicionadas) {
            // Recriamos o repositório temporariamente ou chamamos o método que lança a exceção
            // No caso, o repositório é quem tem a lógica de limite agora.
            // Para simplificar, assumimos que o Gerador atende à regra do repositório.
            if (gerenciador.getFila().size() >= 100) {
                 throw new techsupport.exception.CapacidadeFilaExcedidaException(100);
            }
            gerenciador.addOrdem(os);
        }
        return new ArrayList<>(adicionadas);
    }

    public static void gerarTecnicos(List<Tecnico> lista, int quantidade){
        for(int i = 0; i < quantidade; i++){
            String nome = NOMES.get(RANDOM.nextInt(NOMES.size()));
            String sobrenome = SOBRENOMES.get(RANDOM.nextInt(SOBRENOMES.size()));
            String nomeCompleto = nome + " " + sobrenome;

            NivelTecnico nivel = NivelTecnico.nivelRandom();
            Tecnico tecnico = switch (nivel) {
                case JUNIOR -> new TecnicoJunior(nomeCompleto);
                case PLENO  -> new TecnicoPleno(nomeCompleto);
                case SENIOR -> new TecnicoSenior(nomeCompleto);
            };
            lista.add(tecnico);
        }
    }
}