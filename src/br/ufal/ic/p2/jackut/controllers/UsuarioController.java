package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.entities.Usuario;
import br.ufal.ic.p2.jackut.exceptions.*;

import java.util.Map;

/**
 * Classe responsável por gerenciar as regras de negócio relacionadas aos usuários.
 * Controla a criação, busca, remoção e todas as interações interpessoais,
 * como amizades, inimizades, paqueras, fãs e recados privados.
 */
public class UsuarioController {

    private final Map<String, Usuario> usuarios;

    /**
     * Construtor do UsuarioController.
     * Recebe a estrutura de dados por injeção de dependência para evitar acoplamento e estado global.
     *
     * @param usuarios O mapa de usuários ativos no sistema.
     */
    public UsuarioController(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param login O login do novo usuário.
     * @param senha A senha do novo usuário.
     * @param nome O nome de exibição do usuário.
     * @throws ContaJaExisteException Se já houver um usuário com o mesmo login.
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (this.usuarios.containsKey(login)) throw new ContaJaExisteException();
        this.usuarios.put(login, new Usuario(login, senha, nome));
    }

    /**
     * Busca um usuário cadastrado pelo seu login.
     *
     * @param login O login do usuário desejado.
     * @return O objeto Usuario correspondente.
     * @throws UsuarioNaoCadastradoException Se o login não existir no sistema.
     */
    public Usuario buscarUsuario(String login) {
        Usuario usuario = this.usuarios.get(login);
        if (usuario == null) throw new UsuarioNaoCadastradoException();
        return usuario;
    }

    /**
     * Remove um usuário do sistema e limpa todos os rastros dele nos perfis de terceiros.
     *
     * @param login O login do usuário a ser removido.
     * @throws UsuarioNaoCadastradoException Se o usuário não existir no sistema.
     */
    public void removerUsuario(String login) {
        if (!this.usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();

        this.usuarios.remove(login);

        for (Usuario u : this.usuarios.values()) {
            u.removerRastrosDeRelacionamento(login);
        }
    }

    /**
     * Envia ou confirma um convite de amizade entre dois usuários.
     *
     * @param loginRemetente O login de quem enviou o convite.
     * @param loginDestino O login de quem vai receber o convite.
     * @throws AutoAmizadeException Se o usuário tentar adicionar a si mesmo.
     * @throws UsuarioJaAmigoException Se os usuários já forem amigos.
     * @throws ConvitePendenteException Se já houver um convite enviado aguardando resposta.
     */
    public void adicionarAmigo(String loginRemetente, String loginDestino) {
        Usuario remetente = buscarUsuario(loginRemetente);
        Usuario destino = buscarUsuario(loginDestino);

        validarBloqueioInimizade(destino, remetente);

        if (remetente.getLogin().equals(loginDestino)) throw new AutoAmizadeException();
        if (remetente.ehAmigo(loginDestino)) throw new UsuarioJaAmigoException();
        if (remetente.temConvitePara(loginDestino)) throw new ConvitePendenteException();

        if (destino.temConvitePara(remetente.getLogin())) {
            remetente.confirmarAmizade(loginDestino);
            destino.confirmarAmizade(remetente.getLogin());
        } else {
            remetente.enviarConvite(loginDestino);
        }
    }

    /**
     * Envia um recado privado de um usuário para outro.
     *
     * @param loginRemetente O login de quem envia o recado.
     * @param loginDestino O login de quem recebe o recado.
     * @param recado O conteúdo da mensagem.
     * @throws AutoRecadoException Se o usuário tentar mandar recado para si próprio.
     */
    public void enviarRecado(String loginRemetente, String loginDestino, String recado) {
        Usuario remetente = buscarUsuario(loginRemetente);
        Usuario destino = buscarUsuario(loginDestino);

        validarBloqueioInimizade(destino, remetente);

        if (remetente.getLogin().equals(loginDestino)) throw new AutoRecadoException();
        destino.receberRecado(loginRemetente, recado);
    }

    /**
     * Estabelece uma relação unilateral de fã e ídolo.
     *
     * @param loginFa O login do usuário que será o fã.
     * @param loginIdolo O login do usuário que será o ídolo.
     * @throws JackutException Se houver auto-idolatria ou se o vínculo já existir.
     */
    public void adicionarIdolo(String loginFa, String loginIdolo) {
        Usuario fa = buscarUsuario(loginFa);
        Usuario idolo = buscarUsuario(loginIdolo);

        validarBloqueioInimizade(idolo, fa);

        if (fa.getLogin().equals(loginIdolo)) throw new JackutException("Usuário não pode ser fã de si mesmo.");
        if (fa.ehFa(loginIdolo)) throw new JackutException("Usuário já está adicionado como ídolo.");

        fa.adicionarIdolo(loginIdolo);
        idolo.adicionarFa(loginFa);
    }

    /**
     * Adiciona um usuário na lista de paqueras. Se o sentimento for mútuo, gera um recado automático.
     *
     * @param login O login do usuário que adicionou a paquera.
     * @param loginPaquera O login do alvo da paquera.
     * @throws JackutException Se houver tentativa de adicionar a si mesmo ou a paquera já existir.
     */
    public void adicionarPaquera(String login, String loginPaquera) {
        Usuario usuario = buscarUsuario(login);
        Usuario paquera = buscarUsuario(loginPaquera);

        validarBloqueioInimizade(paquera, usuario);

        if (usuario.getLogin().equals(loginPaquera)) throw new JackutException("Usuário não pode ser paquera de si mesmo.");
        if (usuario.ehPaquera(loginPaquera)) throw new JackutException("Usuário já está adicionado como paquera.");

        usuario.adicionarPaquera(loginPaquera);

        if (paquera.ehPaquera(login)) {
            usuario.receberRecado("Jackut", paquera.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
            paquera.receberRecado("Jackut", usuario.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
        }
    }

    /**
     * Bloqueia outro usuário, definindo-o como inimigo e impedindo interações futuras.
     *
     * @param login O login do usuário que efetuou o bloqueio.
     * @param loginInimigo O login do inimigo.
     * @throws JackutException Se houver auto-inimizade ou se o vínculo já existir.
     */
    public void adicionarInimigo(String login, String loginInimigo) {
        Usuario usuario = buscarUsuario(login);
        Usuario inimigo = buscarUsuario(loginInimigo);

        if (usuario.getLogin().equals(loginInimigo)) throw new JackutException("Usuário não pode ser inimigo de si mesmo.");
        if (usuario.ehInimigo(loginInimigo)) throw new JackutException("Usuário já está adicionado como inimigo.");

        usuario.adicionarInimigo(loginInimigo);
    }

    /**
     * Método auxiliar para validar se a ação requerida está bloqueada devido a uma inimizade prévia.
     *
     * @param destino O usuário alvo da interação (que possivelmente declarou inimizade).
     * @param remetente O usuário que está tentando efetuar a interação.
     * @throws JackutException Se o remetente estiver na lista de inimigos do destino.
     */
    private void validarBloqueioInimizade(Usuario destino, Usuario remetente) {
        if (destino.ehInimigo(remetente.getLogin())) {
            throw new JackutException("Função inválida: " + destino.getAtributo("nome") + " é seu inimigo.");
        }
    }

    /**
     * Remove o registro de uma comunidade da lista pessoal de todos os usuários.
     * Utilizado quando uma comunidade é encerrada pelo sistema.
     *
     * @param nomeComunidade O nome da comunidade a ser removida.
     */
    public void removerComunidadeDeTodos(String nomeComunidade) {
        for (Usuario u : this.usuarios.values()) {
            u.removerComunidade(nomeComunidade);
        }
    }
}