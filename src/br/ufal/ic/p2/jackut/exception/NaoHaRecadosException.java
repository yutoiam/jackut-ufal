package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando se tenta ler um recado da caixa de entrada
 * de um usuário que não possui recados pendentes.
 */
public class NaoHaRecadosException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public NaoHaRecadosException() {
        super("Não há recados.");
    }
}
