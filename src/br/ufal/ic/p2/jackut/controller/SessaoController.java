package br.ufal.ic.p2.jackut.controller;

import br.ufal.ic.p2.jackut.exception.*;
import br.ufal.ic.p2.jackut.exception.LoginOuSenhaInvalidosException;
import br.ufal.ic.p2.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.model.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador responsável por gerenciar as sessões de autenticação do sistema.
 *
 * <p>Esta classe implementa o padrão Singleton para controlar o fluxo de login
 * e mapear sessões ativas (IDs) para seus respectivos usuários.</p>
 *
 * @author Yuri Raphael Mota De Araujo Barbosa
 */
public class SessaoController {
    private static SessaoController instancia;
    private Map<String, String> sessoes;

    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private SessaoController() {
        this.sessoes = new HashMap<>();
    }

    /**
     * Retorna a instância única do controlador de sessões.
     *
     * @return a instância única de {@link SessaoController}
     */
    public static SessaoController getInstance() {
        if (instancia == null) {
            instancia = new SessaoController();
        }
        return instancia;
    }

    /**
     * Invalida e limpa todas as sessões ativas do sistema.
     */
    public void limparDados() {
        this.sessoes = new HashMap<>();
    }

    /**
     * Autentica um usuário e abre uma nova sessão no sistema.
     *
     * @param login identificador único do usuário
     * @param senha senha de acesso
     * @return o identificador (ID) único da sessão recém-criada
     * @throws LoginOuSenhaInvalidosException caso as credenciais não confiram ou o usuário não exista
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidosException {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginOuSenhaInvalidosException();
        }

        try {
            Usuario usuario = UsuarioController.getInstance().buscarUsuario(login);
            if (!usuario.verificarSenha(senha)) throw new LoginOuSenhaInvalidosException();
        } catch (UsuarioNaoCadastradoException e) {
            throw new LoginOuSenhaInvalidosException();
        }

        String idSessao = UUID.randomUUID().toString();
        sessoes.put(idSessao, login);
        return idSessao;
    }

    /**
     * Retorna o login do usuário correspondente a um determinado ID de sessão.
     *
     * @param idSessao identificador da sessão ativa
     * @return login do usuário dono da sessão
     * @throws UsuarioNaoCadastradoException caso o ID da sessão não seja válido ou não possua dono logado
     */
    public String resolverLoginDaSessao(String idSessao) throws UsuarioNaoCadastradoException {
        if (idSessao == null || idSessao.trim().isEmpty()) throw new UsuarioNaoCadastradoException();
        String login = sessoes.get(idSessao);
        if (login == null) throw new UsuarioNaoCadastradoException();
        return login;
    }
}