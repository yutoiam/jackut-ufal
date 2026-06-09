package br.ufal.ic.p2.jackut;

import br.edu.ufcg.ccc.jackut.controller.*;
import br.edu.ufcg.ccc.jackut.exception.*;
import br.ufal.ic.p2.jackut.controller.*;
import br.ufal.ic.p2.jackut.exception.JackutException;

import java.io.IOException;

/**
 * Fachada do sistema Jackut.
 *
 * <p>Esta classe atua como o único ponto de comunicação entre a biblioteca de testes
 * EasyAccept e a lógica de negócio do sistema. Ela não possui lógica de domínio,
 * delegando todas as chamadas aos controladores responsáveis.</p>
 */
public class Facade {

    private final PersistenciaController persistencia;

    /**
     * Construtor padrão da Facade.
     * Inicializa o controlador de persistência e recarrega os dados do sistema
     * previamente salvos em disco.
     */
    public Facade() {
        this.persistencia = new PersistenciaController();
        this.persistencia.carregarSistema();
    }

    /**
     * Apaga todos os dados mantidos no sistema.
     */
    public void zerarSistema() {
        persistencia.zerarSistema();
    }

    /**
     * Grava o cadastro em arquivo e encerra o programa.
     *
     * @throws IOException caso ocorra um erro ao salvar os dados em disco
     */
    public void encerrarSistema() throws IOException {
        persistencia.encerrarSistema();
    }

    /**
     * Cria um usuário com os dados da conta fornecidos.
     *
     * @param login identificador único do usuário
     * @param senha senha de acesso do usuário
     * @param nome nome de exibição do usuário
     * @throws JackutException caso os dados sejam inválidos ou o login já exista
     */
    public void criarUsuario(String login, String senha, String nome) throws JackutException {
        UsuarioController.getInstance().criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessão para um usuário com o login e a senha fornecidos.
     *
     * @param login identificador único do usuário
     * @param senha senha de acesso do usuário
     * @return identificador (ID) da sessão aberta
     * @throws JackutException caso o login ou senha sejam inválidos
     */
    public String abrirSessao(String login, String senha) throws JackutException {
        return SessaoController.getInstance().abrirSessao(login, senha);
    }

    /**
     * Retorna o valor de um atributo armazenado no perfil do usuário.
     *
     * @param login identificador único do usuário
     * @param atributo nome do atributo a ser consultado
     * @return valor do atributo correspondente
     * @throws JackutException caso o usuário não exista ou o atributo não esteja preenchido
     */
    public String getAtributoUsuario(String login, String atributo) throws JackutException {
        return UsuarioController.getInstance().getAtributoUsuario(login, atributo);
    }

    /**
     * Modifica o valor de um atributo do perfil de um usuário.
     *
     * @param id identificador da sessão válida do usuário
     * @param atributo nome do atributo a ser modificado ou criado
     * @param valor novo valor a ser atribuído
     * @throws JackutException caso a sessão seja inválida ou ocorra erro na edição
     */
    public void editarPerfil(String id, String atributo, String valor) throws JackutException {
        String login = SessaoController.getInstance().resolverLoginDaSessao(id);
        UsuarioController.getInstance().editarPerfil(login, atributo, valor);
    }

    /**
     * Adiciona um amigo ao usuário com a sessão especificada.
     *
     * @param id identificador da sessão válida do usuário que está enviando o convite
     * @param amigo login do usuário a ser adicionado como amigo
     * @throws JackutException caso a sessão seja inválida, o usuário não exista, ou a regra de amizade seja violada
     */
    public void adicionarAmigo(String id, String amigo) throws JackutException {
        AmizadeController.getInstance().adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usuários possuem vínculo de amizade confirmado.
     *
     * @param login identificador do primeiro usuário
     * @param amigo identificador do segundo usuário
     * @return "true" se os usuários são amigos, "false" caso contrário
     * @throws JackutException caso algum dos usuários não esteja cadastrado
     */
    public String ehAmigo(String login, String amigo) throws JackutException {
        return AmizadeController.getInstance().ehAmigo(login, amigo);
    }

    /**
     * Retorna a lista formatada de amigos de um usuário específico.
     *
     * @param login identificador único do usuário
     * @return string formatada contendo os logins dos amigos
     * @throws JackutException caso o usuário não esteja cadastrado
     */
    public String getAmigos(String login) throws JackutException {
        return AmizadeController.getInstance().getAmigos(login);
    }

    /**
     * Envia um recado para o destinatário especificado.
     *
     * @param id identificador da sessão válida do usuário remetente
     * @param destinatario login do usuário que receberá o recado
     * @param recado texto contendo a mensagem a ser enviada
     * @throws JackutException caso a sessão ou destinatário sejam inválidos
     */
    public void enviarRecado(String id, String destinatario, String recado) throws JackutException {
        RecadoController.getInstance().enviarRecado(id, destinatario, recado);
    }

    /**
     * Retorna o primeiro recado da fila de recados do usuário.
     *
     * @param id identificador da sessão válida do usuário
     * @return texto do recado mais antigo não lido
     * @throws JackutException caso a sessão seja inválida ou não existam recados
     */
    public String lerRecado(String id) throws JackutException {
        return RecadoController.getInstance().lerRecado(id);
    }
}