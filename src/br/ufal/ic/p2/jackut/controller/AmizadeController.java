package br.ufal.ic.p2.jackut.controller;

import br.ufal.ic.p2.jackut.exception.*;
import br.ufal.ic.p2.jackut.exception.AmigoJaAdicionadoAguardandoException;
import br.ufal.ic.p2.jackut.exception.AmigoJaAdicionadoException;
import br.ufal.ic.p2.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.exception.UsuarioNaoPodeAdicionarASiMesmoException;
import br.ufal.ic.p2.jackut.model.Usuario;
import java.util.List;

/**
 * Controlador responsável por mediar as relações de amizade entre usuários.
 *
 * <p>Esta classe implementa o padrão Singleton para gerenciar convites,
 * confirmações de amizade e listagem da rede de relacionamentos.</p>
 *
 * @author Yuri Raphael Mota De Araujo Barbosa
 */
public class AmizadeController {
    private static AmizadeController instancia;

    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private AmizadeController() {}

    /**
     * Retorna a instância única do controlador de amizades.
     *
     * @return a instância única de {@link AmizadeController}
     */
    public static AmizadeController getInstance() {
        if (instancia == null) instancia = new AmizadeController();
        return instancia;
    }

    /**
     * Adiciona ou envia um convite de amizade de um usuário para outro.
     *
     * @param idSessao identificador da sessão do usuário que iniciou a ação
     * @param loginAmigo login do usuário alvo do convite ou adição
     * @throws UsuarioNaoCadastradoException caso algum dos usuários não exista
     * @throws UsuarioNaoPodeAdicionarASiMesmoException caso o usuário tente adicionar a si próprio
     * @throws AmigoJaAdicionadoException caso os dois usuários já sejam amigos
     * @throws AmigoJaAdicionadoAguardandoException caso o usuário já tenha enviado o convite anteriormente
     */
    public void adicionarAmigo(String idSessao, String loginAmigo)
            throws UsuarioNaoCadastradoException, UsuarioNaoPodeAdicionarASiMesmoException,
            AmigoJaAdicionadoException, AmigoJaAdicionadoAguardandoException {

        String loginAtual = SessaoController.getInstance().resolverLoginDaSessao(idSessao);
        Usuario usuarioAtual = UsuarioController.getInstance().buscarUsuario(loginAtual);
        Usuario alvo = UsuarioController.getInstance().buscarUsuario(loginAmigo);

        if (loginAtual.equals(loginAmigo)) throw new UsuarioNaoPodeAdicionarASiMesmoException();
        if (usuarioAtual.ehAmigo(loginAmigo)) throw new AmigoJaAdicionadoException();
        if (usuarioAtual.jaEnviouConvitePara(loginAmigo)) throw new AmigoJaAdicionadoAguardandoException();

        if (alvo.jaEnviouConvitePara(loginAtual)) {
            usuarioAtual.confirmarAmizade(loginAmigo);
            alvo.confirmarAmizade(loginAtual);
        } else {
            usuarioAtual.registrarConviteEnviado(loginAmigo);
        }
    }

    /**
     * Verifica se existe uma amizade confirmada entre dois usuários.
     *
     * @param login login do primeiro usuário
     * @param loginAmigo login do segundo usuário
     * @return a string "true" se a amizade existir, "false" caso contrário
     * @throws UsuarioNaoCadastradoException caso algum dos logins informados não corresponda a um usuário válido
     */
    public String ehAmigo(String login, String loginAmigo) throws UsuarioNaoCadastradoException {
        Usuario usuario = UsuarioController.getInstance().buscarUsuario(login);
        UsuarioController.getInstance().buscarUsuario(loginAmigo);
        return String.valueOf(usuario.ehAmigo(loginAmigo));
    }

    /**
     * Retorna uma representação em formato de string da lista de amigos de um usuário.
     *
     * @param login identificador único do usuário
     * @return string no formato {amigo1,amigo2} contendo a lista de amigos
     * @throws UsuarioNaoCadastradoException caso o usuário especificado não exista
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        Usuario usuario = UsuarioController.getInstance().buscarUsuario(login);
        List<String> amigos = usuario.getAmigos();

        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < amigos.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(amigos.get(i));
        }
        sb.append('}');
        return sb.toString();
    }
}