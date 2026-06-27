package br.ufal.ic.p2.jackut.entities;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.SenhaInvalidaException;
import br.ufal.ic.p2.jackut.exceptions.SemMensagensException;
import br.ufal.ic.p2.jackut.exceptions.SemRecadosException;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário na rede de relacionamentos Jackut.
 * Armazena as credenciais de acesso, os dados do perfil e gerencia todas as
 * interações e relacionamentos (amigos, fãs, ídolos, paqueras, inimigos, comunidades e mensagens).
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Classe auxiliar interna que representa uma mensagem ou recado na caixa de entrada do usuário.
     * Armazena quem enviou a mensagem para permitir a exclusão de rastros.
     */
    public static class MensagemCaixa implements Serializable {
        private static final long serialVersionUID = 1L;
        public final String remetente;
        public final String texto;

        /**
         * Construtor da MensagemCaixa.
         *
         * @param remetente O login do usuário que enviou a mensagem ou recado.
         * @param texto O conteúdo da mensagem em si.
         */
        public MensagemCaixa(String remetente, String texto) {
            this.remetente = remetente;
            this.texto = texto;
        }
    }

    private final String login;
    private final String senha;
    private final Map<String, String> perfil;

    private final List<String> amigos;
    private final Set<String> convitesEnviados;
    private final Queue<MensagemCaixa> recados;
    private final Set<String> minhasComunidades;

    private final Set<String> fas;
    private final Set<String> idolos;
    private final Set<String> paqueras;
    private final Set<String> inimigos;
    private final Queue<MensagemCaixa> mensagens;

    /**
     * Construtor principal do Usuário.
     *
     * @param login O identificador único do usuário no sistema.
     * @param senha A senha de acesso do usuário.
     * @param nome  O nome de exibição do usuário no perfil.
     * @throws LoginInvalidoException Se o login fornecido for nulo ou vazio.
     * @throws SenhaInvalidaException Se a senha fornecida for nula ou vazia.
     */
    public Usuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty())
            throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty())
            throw new SenhaInvalidaException();

        this.login = login;
        this.senha = senha;
        this.perfil = new HashMap<>();
        this.perfil.put("nome", nome);

        this.amigos = new ArrayList<>();
        this.convitesEnviados = new LinkedHashSet<>();
        this.recados = new LinkedList<>();
        this.minhasComunidades = new LinkedHashSet<>();

        this.fas = new LinkedHashSet<>();
        this.idolos = new LinkedHashSet<>();
        this.paqueras = new LinkedHashSet<>();
        this.inimigos = new LinkedHashSet<>();
        this.mensagens = new LinkedList<>();
    }

    /**
     * Recupera o login do usuário.
     *
     * @return O login do usuário.
     */
    public String getLogin() { return login; }

    /**
     * Verifica se a senha fornecida corresponde à senha do usuário.
     *
     * @param senha A senha a ser validada.
     * @return true se a senha estiver correta, false caso contrário.
     */
    public boolean verificarSenha(String senha) { return this.senha.equals(senha); }

    /**
     * Recupera o valor de um atributo específico do perfil do usuário.
     *
     * @param atributo O nome do atributo desejado.
     * @return O valor associado ao atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo não existir no perfil.
     */
    public String getAtributo(String atributo) {
        if (!this.perfil.containsKey(atributo))
            throw new AtributoNaoPreenchidoException();
        return this.perfil.get(atributo);
    }

    /**
     * Define ou atualiza um atributo no perfil do usuário.
     *
     * @param atributo O nome do atributo a ser modificado.
     * @param valor O novo valor do atributo.
     */
    public void setAtributo(String atributo, String valor) { this.perfil.put(atributo, valor); }

    /**
     * Verifica se outro usuário está na lista de amigos.
     *
     * @param login O login do usuário a ser verificado.
     * @return true se for amigo, false caso contrário.
     */
    public boolean ehAmigo(String login) { return this.amigos.contains(login); }

    /**
     * Verifica se já existe um convite de amizade pendente enviado para o usuário informado.
     *
     * @param login O login do usuário destino.
     * @return true se o convite já foi enviado.
     */
    public boolean temConvitePara(String login) { return this.convitesEnviados.contains(login); }

    /**
     * Registra o envio de um convite de amizade.
     *
     * @param login O login do usuário que receberá o convite.
     */
    public void enviarConvite(String login) { this.convitesEnviados.add(login); }

    /**
     * Efetiva a amizade após a aceitação de um convite.
     *
     * @param login O login do novo amigo.
     */
    public void confirmarAmizade(String login) {
        this.convitesEnviados.remove(login);
        if (!this.amigos.contains(login)) this.amigos.add(login);
    }

    /**
     * Retorna a lista de amigos do usuário.
     *
     * @return Uma lista imutável contendo os logins dos amigos.
     */
    public List<String> getAmigos() { return Collections.unmodifiableList(this.amigos); }

    /**
     * Adiciona o nome de uma comunidade à lista de comunidades que o usuário participa.
     *
     * @param nome O nome da comunidade.
     */
    public void adicionarComunidade(String nome) { this.minhasComunidades.add(nome); }

    /**
     * Remove o nome de uma comunidade da lista de comunidades do usuário.
     *
     * @param nome O nome da comunidade.
     */
    public void removerComunidade(String nome) { this.minhasComunidades.remove(nome); }

    /**
     * Retorna as comunidades nas quais o usuário está inscrito.
     *
     * @return Um conjunto imutável com os nomes das comunidades.
     */
    public Set<String> getMinhasComunidades() { return Collections.unmodifiableSet(this.minhasComunidades); }

    /**
     * Adiciona um usuário à lista de ídolos.
     *
     * @param idoloLogin O login do ídolo.
     */
    public void adicionarIdolo(String idoloLogin) { this.idolos.add(idoloLogin); }

    /**
     * Adiciona um usuário à lista de fãs.
     *
     * @param faLogin O login do fã.
     */
    public void adicionarFa(String faLogin) { this.fas.add(faLogin); }

    /**
     * Verifica se este usuário é fã de um determinado ídolo.
     *
     * @param idoloLogin O login do ídolo a ser verificado.
     * @return true se o usuário for fã do ídolo informado.
     */
    public boolean ehFa(String idoloLogin) { return this.idolos.contains(idoloLogin); }

    /**
     * Retorna a lista de fãs do usuário.
     *
     * @return Um conjunto imutável contendo os logins dos fãs.
     */
    public Set<String> getFas() { return Collections.unmodifiableSet(this.fas); }

    /**
     * Adiciona um usuário à lista de paqueras.
     *
     * @param paqueraLogin O login da paquera.
     */
    public void adicionarPaquera(String paqueraLogin) { this.paqueras.add(paqueraLogin); }

    /**
     * Verifica se um usuário está na lista de paqueras.
     *
     * @param paqueraLogin O login a ser verificado.
     * @return true se for paquera.
     */
    public boolean ehPaquera(String paqueraLogin) { return this.paqueras.contains(paqueraLogin); }

    /**
     * Retorna a lista de paqueras do usuário.
     *
     * @return Um conjunto imutável com os logins das paqueras.
     */
    public Set<String> getPaqueras() { return Collections.unmodifiableSet(this.paqueras); }

    /**
     * Adiciona um usuário à lista de inimigos.
     *
     * @param inimigoLogin O login do inimigo.
     */
    public void adicionarInimigo(String inimigoLogin) { this.inimigos.add(inimigoLogin); }

    /**
     * Verifica se um determinado usuário é inimigo.
     *
     * @param inimigoLogin O login a ser verificado.
     * @return true se for inimigo.
     */
    public boolean ehInimigo(String inimigoLogin) { return this.inimigos.contains(inimigoLogin); }

    /**
     * Recebe um recado privado de outro usuário.
     *
     * @param remetente O login do remetente do recado.
     * @param recado O conteúdo do recado.
     */
    public void receberRecado(String remetente, String recado) { this.recados.add(new MensagemCaixa(remetente, recado)); }

    /**
     * Lê e remove o próximo recado da fila.
     *
     * @return O conteúdo do recado.
     * @throws SemRecadosException Se a caixa de recados estiver vazia.
     */
    public String lerProximoRecado() {
        if (this.recados.isEmpty()) throw new SemRecadosException();
        return this.recados.poll().texto;
    }

    /**
     * Recebe uma mensagem proveniente de uma comunidade.
     *
     * @param remetente O login do usuário que postou a mensagem na comunidade.
     * @param mensagem O conteúdo da mensagem.
     */
    public void receberMensagem(String remetente, String mensagem) { this.mensagens.add(new MensagemCaixa(remetente, mensagem)); }

    /**
     * Lê e remove a próxima mensagem de comunidade da fila.
     *
     * @return O conteúdo da mensagem.
     * @throws SemMensagensException Se a caixa de mensagens estiver vazia.
     */
    public String lerProximaMensagem() {
        if (this.mensagens.isEmpty()) throw new SemMensagensException();
        return this.mensagens.poll().texto;
    }

    /**
     * Limpa todas as referências (mensagens, recados e relacionamentos) que envolvam
     * um usuário que está sendo removido do sistema.
     *
     * @param loginRemovido O login do usuário que foi deletado.
     */
    public void removerRastrosDeRelacionamento(String loginRemovido) {
        this.amigos.remove(loginRemovido);
        this.convitesEnviados.remove(loginRemovido);
        this.fas.remove(loginRemovido);
        this.idolos.remove(loginRemovido);
        this.paqueras.remove(loginRemovido);
        this.inimigos.remove(loginRemovido);

        this.recados.removeIf(m -> m.remetente.equals(loginRemovido));
        this.mensagens.removeIf(m -> m.remetente.equals(loginRemovido));
    }
}