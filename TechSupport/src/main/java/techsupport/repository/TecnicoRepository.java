package techsupport.repository;

import techsupport.model.Tecnico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TecnicoRepository {
    private List<Tecnico> tecnicos = new ArrayList<>();

    public void adicionar(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }

    public List<Tecnico> listar() {
        return new ArrayList<>(tecnicos);
    }

    public List<Tecnico> buscarDisponiveis() {
        return tecnicos.stream()
                .filter(Tecnico::estaDisponivel)
                .collect(Collectors.toList());
    }

    public Optional<Tecnico> buscarPorId(int id) {
        return tecnicos.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }
}
