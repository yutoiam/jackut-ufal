package br.ufal.ic.p2.jackut.controller;

import br.edu.ufcg.ccc.jackut.exception.*;
import br.ufal.ic.p2.jackut.exception.*;
import br.ufal.ic.p2.jackut.model.Usuario;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controlador responsável por gerenciar as operações de usuários.
 *
 * <p>Esta classe implementa o padrão Singleton para centralizar a criação,
 * busca e edição dos perfis dos usuários cadastrados no sistema.</p>
 *
 * @author Yuri Raphael Mota De Araujo Barbosa
 */
public class UsuarioController {
    private static UsuarioController instancia;
    private Map<String, Usuario> usuarios;

    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private UsuarioController() {
        this.usuarios = new LinkedHashMap<>();
    }

    /**
     * Retorna a instância única do controlador de usuários.
     *
     * @return a instância única de {@link UsuarioController}
     */
    public static UsuarioController getInstance() {
        if (instancia == null) {
            instancia = new UsuarioController();
        }
        return instancia;
    }

    /**
     * Substitui o mapa atual de usuários.
     * Utilizado principalmente para a restauração do estado do sistema.
     *
     * @param usuarios novo mapa contendo os usuários recuperados
     */
    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Recupera o mapa atual contendo todos os usuários cadastrados.
     *
     * @return mapa onde a chave é o login e o valor é o objeto {@link Usuario}
     */
    public Map<String, Usuario> getUsuarios() {
        return this.usuarios;
    }

    /**
     * Limpa todos os dados de usuários armazenados na memória.
     */
    public void limparDados() {
        this.usuarios = new LinkedHashMap<>();
    }

    /**
     * Cria um novo usuário e o armazena no sistema.
     *
     * @param login identificador único do usuário
     * @param senha senha de acesso da conta
     * @param nome nome de exibição do usuário
     * @throws LoginInvalidoException caso o login seja nulo ou vazio
     * @throws SenhaInvalidaException caso a senha seja nula ou vazia
     * @throws ContaJaExisteException caso o login já esteja em uso
     */
    public void criarUsuario(String login, String senha, String nome)
            throws LoginInvalidoException, SenhaInvalidaException, ContaJaExisteException {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
        if (usuarios.containsKey(login)) throw new ContaJaExisteException();

        usuarios.put(login, new Usuario(login, senha, nome == null ? "" : nome));
    }

    /**
     * Busca um usuário cadastrado no sistema a partir do seu login.
     *
     * @param login identificador único do usuário procurado
     * @return o objeto {@link Usuario} correspondente
     * @throws UsuarioNaoCadastradoException caso não exista usuário com o login informado
     */
    public Usuario buscarUsuario(String login) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) throw new UsuarioNaoCadastradoException();
        return usuario;
    }

    /**
     * Retorna o valor de um atributo específico do perfil de um usuário.
     *
     * @param login identificador único do usuário
     * @param atributo chave do atributo que se deseja consultar
     * @return o valor do atributo armazenado
     * @throws UsuarioNaoCadastradoException caso o usuário não esteja cadastrado
     * @throws AtributoNaoPreenchidoException caso o atributo procurado não tenha valor atribuído
     */
    public String getAtributoUsuario(String login, String atributo)
            throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        Usuario usuario = buscarUsuario(login);
        String valor = usuario.getAtributoPerfil(atributo);
        if (valor == null) throw new AtributoNaoPreenchidoException();
        return valor;
    }

    /**
     * Modifica ou cria um atributo no perfil do usuário.
     *
     * @param login identificador único do usuário dono do perfil
     * @param atributo chave do atributo a ser alterado
     * @param valor novo valor a ser salvo
     * @throws UsuarioNaoCadastradoException caso o usuário não esteja cadastrado
     */
    public void editarPerfil(String login, String atributo, String valor)
            throws UsuarioNaoCadastradoException {
        Usuario usuario = buscarUsuario(login);
        usuario.setAtributoPerfil(atributo, valor);
    }
}