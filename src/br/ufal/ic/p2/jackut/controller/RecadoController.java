package br.ufal.ic.p2.jackut.controller;

import br.ufal.ic.p2.jackut.exception.*;
import br.ufal.ic.p2.jackut.exception.NaoHaRecadosException;
import br.ufal.ic.p2.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.exception.UsuarioNaoPodeEnviarRecadoParaSiMesmoException;
import br.ufal.ic.p2.jackut.model.Recado;
import br.ufal.ic.p2.jackut.model.Usuario;

/**
 * Controlador responsável por gerenciar o fluxo de recados entre os usuários.
 *
 * <p>Esta classe implementa o padrão Singleton e orquestra o envio e a leitura
 * de mensagens, garantindo que as regras de negócio de comunicação sejam respeitadas.</p>
 *
 * @author Yuri Raphael Mota De Araujo Barbosa
 */
public class RecadoController {
    private static RecadoController instancia;

    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private RecadoController() {}

    /**
     * Retorna a instância única do controlador de recados.
     *
     * @return a instância única de {@link RecadoController}
     */
    public static RecadoController getInstance() {
        if (instancia == null) instancia = new RecadoController();
        return instancia;
    }

    /**
     * Envia um recado de um usuário remetente para um destinatário.
     *
     * @param idSessao identificador da sessão válida do usuário que está enviando a mensagem
     * @param destinatario login do usuário que receberá a mensagem
     * @param texto conteúdo textual do recado a ser enviado
     * @throws UsuarioNaoCadastradoException caso a sessão seja inválida ou o destinatário não exista
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException caso o remetente tente enviar um recado para ele próprio
     */
    public void enviarRecado(String idSessao, String destinatario, String texto)
            throws UsuarioNaoCadastradoException, UsuarioNaoPodeEnviarRecadoParaSiMesmoException {

        String loginRemetente = SessaoController.getInstance().resolverLoginDaSessao(idSessao);
        if (loginRemetente.equals(destinatario)) {
            throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        }

        Usuario usuarioDestinatario = UsuarioController.getInstance().buscarUsuario(destinatario);
        usuarioDestinatario.receberRecado(new Recado(texto));
    }

    /**
     * Lê e remove o primeiro recado da caixa de entrada do usuário.
     *
     * @param idSessao identificador da sessão válida do usuário que está lendo a mensagem
     * @return o texto do recado mais antigo não lido
     * @throws UsuarioNaoCadastradoException caso o ID da sessão não corresponda a um usuário logado
     * @throws NaoHaRecadosException caso a caixa de entrada do usuário esteja vazia
     */
    public String lerRecado(String idSessao)
            throws UsuarioNaoCadastradoException, NaoHaRecadosException {

        String login = SessaoController.getInstance().resolverLoginDaSessao(idSessao);
        Usuario usuario = UsuarioController.getInstance().buscarUsuario(login);

        if (!usuario.possuiRecados()) {
            throw new NaoHaRecadosException();
        }
        return usuario.lerProximoRecado();
    }
}