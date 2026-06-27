package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção base do sistema Jackut.
 * Todas as exceções de negócio herdam desta classe, garantindo um tipo comum
 * para captura na br.ufal.ic.p2.jackut.Facade e no EasyAccept. Por ser unchecked (RuntimeException),
 * não obriga o uso de try/catch em todos os métodos chamadores.
 */
public class JackutException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção base com uma mensagem de erro específica.
     *
     * @param mensagem A mensagem detalhando o erro de regra de negócio que ocorreu.
     */
    public JackutException(String mensagem) {
        super(mensagem);
    }
}