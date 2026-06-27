package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta ler um recado privado, mas a fila de recados está vazia.
 */
public class SemRecadosException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão indicando ausência de recados.
     */
    public SemRecadosException() {
        super("N\u00E3o h\u00E1 recados.");
    }
}