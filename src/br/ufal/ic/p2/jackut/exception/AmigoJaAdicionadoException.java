package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando se tenta adicionar como amigo um usuário
 * que já é amigo confirmado.
 */
public class AmigoJaAdicionadoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public AmigoJaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
