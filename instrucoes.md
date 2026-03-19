# TechSupport – Sistema Inteligente de Escalonamento de OS

## 📌 Resumo do Projeto

O **TechSupport** é um sistema de bancada de suporte técnico voltado para a distribuição e resolução inteligente de Ordens de Serviço (OS). Desenvolvido inteiramente em Java puro (sem frameworks pesados), o projeto demonstra o domínio e a aplicação prática de conceitos avançados de Programação Orientada a Objetos (POO), Estruturas de Dados e Programação Funcional.

O sistema recebe demandas de suporte (OS) com diferentes graus de prioridade e complexidade e as adiciona a uma fila de atendimento dinâmica. Técnicos com capacidades variadas (Junior, Pleno, Sênior) são encarregados de atender essas OS mediante políticas criteriosas de escalonamento que o administrador escolhe no momento da alocação — garantindo uma entrega eficiente e uma governança rigorosa.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17** ou superior | usamos o 21
- **Maven** instalado na máquina (ou execução nativa via IDE)

### Opção 1: Execução Automática via IDE (Recomendado)
*Compatível com IntelliJ IDEA, Eclipse ou VS Code.*
1. Abra ou importe a pasta `TechSupport` como um projeto Maven.
2. Certifique-se de que o **SDK / Language Level** do projeto está configurado para o **Java 21**. Se tiver mudado a configuração estrutural de pacotes, faça o *Rebuild* no menu principal.
3. Navegue até o arquivo `src/main/java/techsupport/Main.java`.
4. Execute (`Run`) o método `main()` desta classe. 
5. Interaja com o menu pelo terminal nativo da IDE.

### Opção 2: Execução via Terminal / Prompt de Comando
1. Abra o seu terminal de preferência e navegue até a pasta raiz do projeto (`TechSupport/`, onde o arquivo `pom.xml` está alocado).
2. Para compilar todos os arquivos Java, use o Maven:
   ```bash
   mvn clean compile
   ```
3. Após a compilação obter sucesso, mande o Maven engatilhar a classe Main usando a instrução de execução de propriedades:
   ```bash
   mvn exec:java -Dexec.mainClass="techsupport.Main"
   ```

---

## 🛠️ Tecnologias e Conceitos Utilizados

O sistema foi rigorosamente desenhado para atender aos requisitos essenciais de código limpo e aplicar soluções eficientes do mundo corporativo:

1. **Modelagem de Classes, Herança e Polimorfismo:**
   - O domínio técnico obedece a uma hierarquia sólida onde `Tecnico` atua como classe abstrata com filhas concretas (`TecnicoJunior`, `TecnicoPleno` e `TecnicoSenior`).
   - O método polimórfico `podeAtender(Complexidade)` dita a capacidade individual sem a necessidade de lógicas condicionais acopladas, validando a restrição de alocação de forma inteligente.

2. **Padrões de Projeto (Design Patterns):**
   - **Strategy:** Usado para alternar os algoritmos de escalonamento em tempo de execução via `Comparator` sem mutar o controlador base (`FIFO`, `Prioridade + SJF`, `Menor Tempo (SJF)`).
   - **Facade e Separation of Concerns:** A classe `SistemaTechSupport` age como uma fachada coordenadora, delegando suas operações massivas sobre instâncias limpas (`CadastroService` e `RelatorioService`). 
   - **Repository:** Abstração de armazenamento em escopo de memória persistente e estrutural (`TecnicoRepository` e `OrdemServicoRepository`).

3. **Estruturas de Dados Dinâmicas (Collections Framework):**
   - Gestão fina de fila baseada na submissão progressiva com o uso das estruturas `LinkedList` (Queue convencional) e as simulações estratégicas usando processamentos temporários orientados sobre o `PriorityQueue`.

4. **Programação Funcional (Streams API) e Lambdas:**
   - Emprego contínuo da **Stream API** para agregações estatísticas nos relatórios analíticos do sistema (`.groupingBy()`, `.counting()`).
   - Uso de encadeamentos paralelos via `.filter()` cruzados operando em fluxos na varredura da simulação do tempo e de transições de pendências lógicas de instâncias.

5. **Tratamento Robusto de Erros (Exceções):**
   - Estruturação própria de falhas da Aplicação. Foram criadas amarras fortes sobre Exceções *Checked* (ex: `CapacidadeFilaExcedidaException`) para interromper estouramentos de pilhas na origem da falha com recuperação controlada por painéis GUI do Console. Em paralelo, temos também defesas arquiteturais usando hierarquização *Unchecked* base (`CompetenciaInvalidaException`) na verificação falha das regras contratuais.

---

## ⚙️ Funcionalidades e Tela do Sistema

O software controla toda sua usabilidade e fluxo por meio da listagem do *Menu Principal (ConsoleUI)*, viabilizando:

* **[1] Cadastrar Técnico & [2] Cadastrar OS:** Realiza entradas manuais de cadastramentos orgânicos. A UI repassa tratativas rigorosas sobre erro de tipagem de dados em respostas a menus internos (impedindo o travamento crônico com dados sujos e alfanuméricos equivocados em entradas numéricas).
* **[3] Listar Dados:** Visão em real time das Filas de `Pendência`, `Ocupações dos Analistas` e das entidades de sucesso alocadas em histórico no `Sistema de Concluídas`. 
* **[4] Escalonador Base / Escalonador Tático:** Avalia o primeiro recurso (OS) a ser executado no painel da Fila e persegue na varredura os técnicos presentes em conformidade com o algoritmo matemático adotado de escolha pontual (*FIFO ou SLA Prioritário Acumulativo*).
* **[5] Gerar Dados em Massa Automático:** Permite uma validação visual ultra rápida simulando as cargas de ambiente e comportamentos estatísticos mediante criação de um cenário extenso padronizado com preenchimento Randomizado para simulação em Bancada.
* **[6] Relatório Dinâmico Gerencial:** Demonstra os processamentos aglutinados dos grupos e gargalos sob os recursos atuais da Operação (uso pesado do `Collectors Framework`). 
* **[7] Start de Transição Temporal (Simulador de Termino):** Efetua o "Tick" fictício do avanço universal liberando ao mercado de atendimento os analistas retidos alocando o status `CONCLUIDA` nas referências rastreadas e finalizando o trabalho por Completo.

---

## 👨‍💻 Desenvolvedores (Equipe)

* **Matheus Mendes de Sousa**
* **Filipe Lucas de Souza oliveira**
* **Luiz Carlos de Sousa Lins Neto**
