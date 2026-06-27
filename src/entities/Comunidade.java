package entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Representa uma comunidade na rede de relacionamentos Jackut.
 * Comunidades reúnem usuários em torno de um tema comum, possuindo um dono
 * e uma lista de membros participantes.
 */
public class Comunidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nome;
    private final String descricao;
    private final String donoLogin;
    private final Set<String> membros;

    /**
     * Construtor principal da Comunidade.
     * O usuário criador é automaticamente adicionado como membro.
     *
     * @param nome O nome único da comunidade.
     * @param descricao A descrição ou propósito da comunidade.
     * @param donoLogin O login do usuário criador e administrador da comunidade.
     */
    public Comunidade(String nome, String descricao, String donoLogin) {
        this.nome = nome;
        this.descricao = descricao;
        this.donoLogin = donoLogin;
        this.membros = new LinkedHashSet<>();
        this.membros.add(donoLogin);
    }

    /**
     * Recupera o nome da comunidade.
     *
     * @return O nome da comunidade.
     */
    public String getNome() { return nome; }

    /**
     * Recupera a descrição da comunidade.
     *
     * @return A descrição da comunidade.
     */
    public String getDescricao() { return descricao; }

    /**
     * Recupera o login do usuário dono da comunidade.
     *
     * @return O login do proprietário.
     */
    public String getDonoLogin() { return donoLogin; }

    /**
     * Adiciona um novo membro à comunidade.
     *
     * @param login O login do usuário a ser adicionado.
     */
    public void adicionarMembro(String login) {
        this.membros.add(login);
    }

    /**
     * Verifica se um usuário faz parte da comunidade.
     *
     * @param login O login do usuário a ser verificado.
     * @return true se o usuário for membro.
     */
    public boolean ehMembro(String login) {
        return this.membros.contains(login);
    }

    /**
     * Retorna a lista de membros da comunidade.
     *
     * @return Um conjunto imutável contendo os logins dos membros.
     */
    public Set<String> getMembros() {
        return Collections.unmodifiableSet(this.membros);
    }
}