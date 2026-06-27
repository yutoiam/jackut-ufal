package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando se tenta adicionar como amigo um usuário
 * para o qual já existe um convite pendente aguardando aceitação.
 */
public class AmigoJaAdicionadoAguardandoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public AmigoJaAdicionadoAguardandoException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
