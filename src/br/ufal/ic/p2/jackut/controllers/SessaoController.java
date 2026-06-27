package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Classe responsável por gerenciar as sessões dos usuários logados no sistema.
 * Mapeia identificadores únicos (UUIDs) de sessão para os logins correspondentes,
 * garantindo que apenas usuários autenticados possam realizar ações.
 */
public class SessaoController {

    private final Map<String, String> sessoes;

    /**
     * Construtor do SessaoController.
     * Inicializa a estrutura de armazenamento das sessões ativas.
     */
    public SessaoController() {
        this.sessoes = new HashMap<>();
    }

    /**
     * Cria uma nova sessão de acesso para um usuário recém-autenticado.
     *
     * @param login O login do usuário que acabou de fazer login.
     * @return Uma string contendo um UUID único gerado para esta sessão.
     */
    public String criarSessao(String login) {
        String idSessao = UUID.randomUUID().toString();
        this.sessoes.put(idSessao, login);
        return idSessao;
    }

    /**
     * Recupera o login do usuário a partir do identificador da sua sessão ativa.
     *
     * @param idSessao O identificador da sessão.
     * @return O login do usuário correspondente.
     * @throws UsuarioNaoCadastradoException Se o identificador for inválido, nulo ou não existir.
     */
    public String getLoginDaSessao(String idSessao) {
        if (idSessao == null || idSessao.isEmpty() || !this.sessoes.containsKey(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }
        return this.sessoes.get(idSessao);
    }

    /**
     * Remove uma sessão ativa, efetivando o logout (ou remoção) do usuário no sistema.
     *
     * @param idSessao O identificador da sessão a ser encerrada.
     */
    public void removerSessao(String idSessao) {
        this.sessoes.remove(idSessao);
    }

    /**
     * Remove todas as sessões ativas de uma só vez, zerando os logins do sistema.
     */
    public void zerarSessoes() {
        this.sessoes.clear();
    }
}