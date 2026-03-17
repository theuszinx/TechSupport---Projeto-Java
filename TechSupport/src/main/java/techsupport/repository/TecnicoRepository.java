package techsupport.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import techsupport.model.Tecnico;

// Gerencia o armazenamento e busca de técnicos em memória
public class TecnicoRepository {
    private final List<Tecnico> listaTecnicos = new ArrayList<>();

    // Adiciona um novo técnico à lista do sistema
    public void adicionar(Tecnico tecnico) {
        if (tecnico != null) {
            listaTecnicos.add(tecnico);
        }
    }

    // Retorna todos os técnicos cadastrados
    public List<Tecnico> listar() {
        return new ArrayList<>(listaTecnicos);
    }

    // Localiza o primeiro técnico que não está ocupado em um atendimento
    public Optional<Tecnico> buscarDisponivel() {
        return listaTecnicos.stream()
                .filter(Tecnico::estaDisponivel)
                .findFirst();
    }

    // Retorna TODOS os técnicos disponíveis para que o escalonador possa iterar por todos
    public List<Tecnico> buscarDisponiveis() {
        return listaTecnicos.stream()
                .filter(Tecnico::estaDisponivel)
                .collect(Collectors.toList());
    }
}
