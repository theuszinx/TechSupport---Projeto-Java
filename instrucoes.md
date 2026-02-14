# Projeto: TechSupport – Sistema de Fila Inteligente de Atendimento

## 📌 Visão Geral

O sistema TechSupport simula um ambiente de suporte técnico onde ordens de serviço (OS) são distribuídas automaticamente para técnicos, respeitando:

* Competência do técnico
* Prioridade da tarefa
* Disponibilidade
* Estratégia de escalonamento

Utiliza:

* Orientação a Objetos
* Collections (Queue, PriorityQueue)
* Strategy Pattern
* Streams e Lambdas
* Tratamento de Exceções
* Interface de Console

---

# 📁 Estrutura de Pastas do Projeto

```
TechSupport/
│
├── README.md
├── pom.xml (se usar Maven)
│
├── src/
│   └── main/
│       └── java/
│           └── techsupport/
│
│               ├── Main.java
│
│               ├── model/
│               │   ├── Tecnico.java
│               │   ├── OrdemServico.java
│               │
│               ├── enums/
│               │   ├── NivelTecnico.java
│               │   ├── Prioridade.java
│               │   ├── StatusOS.java
│               │
│               ├── service/
│               │   ├── SistemaTechSupport.java
│               │   ├── EscalonadorService.java
│               │
│               ├── strategy/
│               │   ├── EstrategiaEscalonamento.java
│               │   ├── EstrategiaFIFO.java
│               │   ├── EstrategiaPrioridade.java
│               │   ├── EstrategiaMenorTempo.java
│               │
│               ├── repository/
│               │   ├── TecnicoRepository.java
│               │   ├── OrdemServicoRepository.java
│               │
│               ├── exception/
│               │   ├── TechSupportException.java
│               │   ├── TecnicoIndisponivelException.java
│               │   ├── CompetenciaInvalidaException.java
│               │
│               ├── util/
│               │   ├── GeradorDadosTeste.java
│               │   ├── ConsoleUtils.java
│               │
│               └── ui/
│                   └── MenuPrincipal.java
```

---

# 📦 Descrição de Cada Pasta

---

## main/

Classe principal que inicia o sistema.

### Main.java

Responsável por iniciar o menu:

```java
public class Main {
    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.executar();
    }
}
```

---

## model/

Contém as entidades principais.

### Tecnico.java

Representa um técnico.

Atributos:

```
id
nome
nivel (JUNIOR, PLENO, SENIOR)
disponivel
```

Métodos:

```
estaDisponivel()
ocupar()
liberar()
```

---

### OrdemServico.java

Representa uma ordem de serviço.

Atributos:

```
id
descricao
prioridade
tempoEstimado
complexidade
status
tempoEspera
```

---

## enums/

Enums para regras do sistema.

---

### NivelTecnico.java

```
JUNIOR
PLENO
SENIOR
```

---

### Prioridade.java

```
BAIXA
MEDIA
ALTA
CRITICA
```

---

### StatusOS.java

```
PENDENTE
EM_ATENDIMENTO
CONCLUIDA
```

---

## repository/

Responsável por armazenar dados em memória.

---

### TecnicoRepository.java

Armazena técnicos.

Estrutura:

```java
List<Tecnico>
```

Métodos:

```
adicionar()
listar()
buscarDisponivel()
```

---

### OrdemServicoRepository.java

Armazena ordens de serviço.

Estrutura:

```java
Queue<OrdemServico>
```

ou

```java
PriorityQueue<OrdemServico>
```

Métodos:

```
adicionar()
listar()
buscarProxima()
```

---

## strategy/

Define como a fila será organizada.

Usa Strategy Pattern.

---

### EstrategiaEscalonamento.java

Interface:

```java
public interface EstrategiaEscalonamento {
    OrdemServico escolherProxima(List<OrdemServico> lista);
}
```

---

### EstrategiaFIFO.java

Primeiro que chega, primeiro que sai.

---

### EstrategiaPrioridade.java

Maior prioridade primeiro.

Usa Comparator.

---

### EstrategiaMenorTempo.java

Menor tempo estimado primeiro.

(SJF)

---

## service/

Contém a lógica do sistema.

---

### SistemaTechSupport.java

Controla tudo:

Responsável por:

```
cadastrarTecnico()
cadastrarOS()
alocarOS()
listarDados()
gerarRelatorio()
```

---

### EscalonadorService.java

Responsável por:

```
Escolher próxima OS
Alocar técnico correto
Aplicar estratégia
```

---

## exception/

Exceções personalizadas.

---

### TechSupportException.java

Exceção base.

---

### TecnicoIndisponivelException.java

Quando técnico está ocupado.

---

### CompetenciaInvalidaException.java

Quando técnico não pode resolver OS.

(Regra obrigatória do trabalho)

---

## ui/

Interface com o usuário.

---

### MenuPrincipal.java

Exibe:

```
1 - Cadastrar técnico
2 - Cadastrar OS
3 - Listar dados
4 - Executar escalonamento
5 - Gerar dados de teste
0 - Sair
```

Responsável por interação com usuário.

---

## util/

Classes auxiliares.

---

### GeradorDadosTeste.java

Cria dados automaticamente:

```
5 técnicos
10 ordens de serviço
```

Para facilitar apresentação.

---

### ConsoleUtils.java

Métodos auxiliares:

```
limparTela()
pausar()
lerInt()
lerString()
```

---

# 📊 Estrutura de Classes (Resumo UML Simplificado)

```
Tecnico
OrdemServico

SistemaTechSupport
EscalonadorService

EstrategiaEscalonamento (interface)
 ├ EstrategiaFIFO
 ├ EstrategiaPrioridade
 └ EstrategiaMenorTempo

TecnicoRepository
OrdemServicoRepository

MenuPrincipal
```

---

# 📌 Estruturas de Dados Utilizadas

Obrigatórias pelo trabalho:

```
List
Queue
PriorityQueue
Comparator
Stream API
```

---

# 📌 Fluxo do Sistema

```
Main
 ↓
MenuPrincipal
 ↓
SistemaTechSupport
 ↓
EscalonadorService
 ↓
Repository
 ↓
Model
```

---

# 📌 Exemplo de Execução

```
=== TECH SUPPORT ===

1 Cadastrar Técnico
2 Cadastrar Ordem Serviço
3 Listar
4 Executar Escalonamento
5 Gerar Dados Teste
0 Sair

Escolha:
```

---

# 📌 Recursos Obrigatórios do Trabalho (Atendidos)

✔ Orientação a Objetos
✔ Herança
✔ Polimorfismo
✔ Strategy Pattern
✔ Queue / PriorityQueue
✔ Comparator
✔ Exceptions personalizadas
✔ Streams e Lambdas
✔ Interface de Console
✔ Organização em pacotes

---

# 📌 Resultado Final Esperado

Sistema completo com:

* Arquitetura limpa
* Fácil manutenção
* Seguindo boas práticas
* Pronto para apresentação

---

# 📌 Próximo Passo

Implementar as classes nesta ordem:

1. enums
2. model
3. exception
4. repository
5. strategy
6. service
7. ui
8. main

---
