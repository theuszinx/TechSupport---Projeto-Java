package techsupport.enums;

// Optei em transformar o atributo "complexidade" em um enum só pra deixar tudo padronizado
public enum Complexidade {
    BAIXA(1),
    MEDIA(2),
    ALTA(3);

    final int valorComplexidade;

    Complexidade(int valorComplexidade){
        this.valorComplexidade = valorComplexidade;
    }

    public int getValorComplexidade() {
        return valorComplexidade;
    }
}
