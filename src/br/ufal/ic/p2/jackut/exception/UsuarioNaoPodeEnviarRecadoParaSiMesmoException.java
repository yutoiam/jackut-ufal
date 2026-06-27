package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando um usuário tenta enviar um recado para si mesmo.
 */
public class UsuarioNaoPodeEnviarRecadoParaSiMesmoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public UsuarioNaoPodeEnviarRecadoParaSiMesmoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
