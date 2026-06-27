package br.ufal.ic.p2.jackut.model;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário cadastrado na rede Jackut.
 *
 * <p>Centraliza as informações de conta, perfil, amizades e recados do usuário,
 * garantindo o encapsulamento de suas coleções internas.</p>
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String login;
    private String senha;
    private final Map<String, String> perfil;
    private final LinkedHashSet<String> amigos;
    private final Set<String> convitesEnviados;
    private final Queue<Recado> caixaDeRecados;

    /**
     * Instancia um novo Usuário no sistema.
     *
     * @param login identificador único do usuário
     * @param senha senha de acesso à conta
     * @param nome nome de exibição (armazenado no perfil)
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.perfil = new LinkedHashMap<>();
        this.amigos = new LinkedHashSet<>();
        this.convitesEnviados = new HashSet<>();
        this.caixaDeRecados = new LinkedList<>();

        this.perfil.put("nome", nome);
    }

    /**
     * Recupera o login do usuário.
     *
     * @return login único do usuário
     */
    public String getLogin() { return login; }

    /**
     * Verifica se a senha informada corresponde à senha do usuário.
     *
     * @param senhaFornecida senha submetida para verificação
     * @return true se a senha confere, false caso contrário
     */
    public boolean verificarSenha(String senhaFornecida) {
        return this.senha.equals(senhaFornecida);
    }

    /**
     * Recupera o valor de um atributo específico do perfil.
     *
     * @param atributo chave do atributo desejado
     * @return valor associado ao atributo, ou null caso não exista
     */
    public String getAtributoPerfil(String atributo) {
        return perfil.get(atributo);
    }

    /**
     * Modifica ou adiciona um atributo no perfil do usuário.
     *
     * @param atributo chave do atributo
     * @param valor valor a ser atribuído
     */
    public void setAtributoPerfil(String atributo, String valor) {
        perfil.put(atributo, valor);
    }

    /**
     * Verifica se este usuário é amigo confirmado do usuário informado.
     *
     * @param loginAmigo login do usuário a ser verificado
     * @return true se existir a amizade, false caso contrário
     */
    public boolean ehAmigo(String loginAmigo) {
        return amigos.contains(loginAmigo);
    }

    /**
     * Verifica se este usuário possui um convite de amizade pendente enviado ao login informado.
     *
     * @param loginAlvo login do destinatário do convite
     * @return true se o convite já foi enviado, false caso contrário
     */
    public boolean jaEnviouConvitePara(String loginAlvo) {
        return convitesEnviados.contains(loginAlvo);
    }

    /**
     * Registra o envio de um convite de amizade a outro usuário.
     *
     * @param loginAlvo login do destinatário do convite
     */
    public void registrarConviteEnviado(String loginAlvo) {
        convitesEnviados.add(loginAlvo);
    }

    /**
     * Efetiva a amizade com outro usuário, adicionando-o à lista de amigos e removendo o convite pendente.
     *
     * @param loginAmigo login do novo amigo confirmado
     */
    public void confirmarAmizade(String loginAmigo) {
        convitesEnviados.remove(loginAmigo);
        amigos.add(loginAmigo);
    }

    /**
     * Recupera a lista de amigos confirmados do usuário.
     * O retorno é uma cópia imutável para proteger o encapsulamento interno.
     *
     * @return lista imutável contendo os logins dos amigos
     */
    public List<String> getAmigos() {
        return Collections.unmodifiableList(new ArrayList<>(amigos));
    }

    /**
     * Adiciona um novo recado à fila de caixa de entrada do usuário.
     *
     * @param recado objeto contendo a mensagem recebida
     */
    public void receberRecado(Recado recado) {
        caixaDeRecados.add(recado);
    }

    /**
     * Remove e retorna o recado mais antigo da fila.
     *
     * @return texto do recado lido, ou null caso a fila esteja vazia
     */
    public String lerProximoRecado() {
        Recado recado = caixaDeRecados.poll();
        return recado != null ? recado.getTexto() : null;
    }

    /**
     * Verifica se a caixa de entrada possui recados não lidos.
     *
     * @return true se existirem recados, false caso contrário
     */
    public boolean possuiRecados() {
        return !caixaDeRecados.isEmpty();
    }
}