package br.ufal.ic.p2.jackut.model;

import java.io.Serializable;

/**
 * Representa uma mensagem enviada entre usuários no Jackut.
 *
 * <p>Encapsula o texto do recado para garantir o uso adequado do domínio
 * orientado a objetos em vez de tipos primitivos isolados.</p>
 */
public class Recado implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String texto;

    /**
     * Instancia um novo Recado.
     *
     * @param texto conteúdo textual da mensagem
     */
    public Recado(String texto) {
        this.texto = texto;
    }

    /**
     * Recupera o texto armazenado neste recado.
     *
     * @return string contendo a mensagem
     */
    public String getTexto() {
        return texto;
    }
}