package techsupport.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import techsupport.enums.*;
import techsupport.model.*;

import java.util.List;
/*
Deve gerar automaticamente:
5 técnicos
10 ordens de serviço
 */
public class GeradorDadosTeste {
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

    // Não é muito profissional porque cada "new" cria mais um objeto na memória Heap, mas é mais simples
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

    private static final Random RANDOM = new Random();

    public List<Tecnico> gerarTecnicos(int quantidade){
        List<Tecnico> listaTecnicos = new ArrayList<>();

        for(int i = 0; i < quantidade; i++){
            String nome = gerarNomeTecnico();
            NivelTecnico nivel = NivelTecnico.nivelRandom();

            listaTecnicos.add(new Tecnico(nome, nivel));
        }

        return listaTecnicos;

    }

    public List<OrdemServico> gerarOrdemsServico(int quantidade){
        List<OrdemServico> listaOrdens = new ArrayList<>();
        for(int i = 0; i < quantidade; i++){
            listaOrdens.add(ORDENS.get(RANDOM.nextInt(ORDENS.size())));
        }

        return listaOrdens;
    }

    private String gerarNomeTecnico(){
        String nome = NOMES.get(RANDOM.nextInt(NOMES.size()));
        String sobrenome = SOBRENOMES.get(RANDOM.nextInt(SOBRENOMES.size()));

        return nome + " " + sobrenome;
    }
}