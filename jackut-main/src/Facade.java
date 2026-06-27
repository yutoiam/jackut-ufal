import controllers.ComunidadeController;
import controllers.PersistenciaController;
import controllers.SessaoController;
import controllers.UsuarioController;
import entities.Comunidade;
import entities.Usuario;

import java.util.Map;

/**
 * Ponto de entrada (Fachada) do sistema Jackut.
 * Centraliza a comunicação entre a interface (ou suíte de testes do EasyAccept)
 * e as lógicas de negócio dos controladores. Esta classe aplica o padrão arquitetural Facade,
 * mascarando a complexidade interna dos subsistemas.
 */
public class Facade {

    private final SessaoController sessaoController;
    private final UsuarioController usuarioController;
    private final ComunidadeController comunidadeController;
    private final PersistenciaController persistenciaController;

    private final Map<String, Usuario> mapaUsuarios;
    private final Map<String, Comunidade> mapaComunidades;

    /**
     * Construtor da Facade.
     * Responsável por inicializar a persistência, resgatar os dados salvos
     * e injetar as dependências nos controladores.
     */
    public Facade() {
        this.persistenciaController = new PersistenciaController();

        this.mapaUsuarios = persistenciaController.carregarUsuarios();
        this.mapaComunidades = persistenciaController.carregarComunidades();

        this.usuarioController = new UsuarioController(this.mapaUsuarios);
        this.comunidadeController = new ComunidadeController(this.mapaComunidades, this.usuarioController);
        this.sessaoController = new SessaoController();
    }

    /**
     * Remove todos os dados da memória e do disco, reiniciando o sistema completamente.
     */
    public void zerarSistema() {
        this.mapaUsuarios.clear();
        this.mapaComunidades.clear();
        this.sessaoController.zerarSessoes();
        this.persistenciaController.limparDados();
    }

    /**
     * Persiste os dados na memória para os arquivos físicos de disco de forma segura.
     */
    public void encerrarSistema() {
        this.persistenciaController.salvarDados(this.mapaUsuarios, this.mapaComunidades);
    }

    /**
     * Delega a criação de um novo usuário para o sistema.
     *
     * @param login Login do novo usuário.
     * @param senha Senha de acesso.
     * @param nome Nome de exibição.
     */
    public void criarUsuario(String login, String senha, String nome) {
        usuarioController.criarUsuario(login, senha, nome);
    }

    /**
     * Inicia uma sessão de usuário validando suas credenciais.
     *
     * @param login Login fornecido na tentativa de autenticação.
     * @param senha Senha fornecida na tentativa de autenticação.
     * @return O ID único (UUID) correspondente à sessão gerada.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            throw new exceptions.LoginOuSenhaInvalidosException();
        }
        try {
            Usuario usuario = usuarioController.buscarUsuario(login);
            if (!usuario.verificarSenha(senha)) {
                throw new exceptions.LoginOuSenhaInvalidosException();
            }
            return sessaoController.criarSessao(login);
        } catch (exceptions.UsuarioNaoCadastradoException e) {
            throw new exceptions.LoginOuSenhaInvalidosException();
        }
    }

    /**
     * Remove todos os dados referentes a um usuário autenticado.
     *
     * @param id O identificador da sessão ativa.
     */
    public void removerUsuario(String id) {
        String login = sessaoController.getLoginDaSessao(id);
        comunidadeController.removerRastrosDeUsuario(login);
        usuarioController.removerUsuario(login);
        sessaoController.removerSessao(id);
    }

    /**
     * Retorna o valor de um atributo específico do perfil.
     *
     * @param login O login do usuário a ser consultado.
     * @param atributo O nome do atributo (ex: "nome").
     * @return O valor armazenado para o atributo requisitado.
     */
    public String getAtributoUsuario(String login, String atributo) {
        return usuarioController.buscarUsuario(login).getAtributo(atributo);
    }

    /**
     * Modifica o valor de um atributo no perfil do usuário logado.
     *
     * @param id O identificador da sessão ativa.
     * @param atributo O nome do atributo a ser modificado.
     * @param valor O novo valor para o atributo.
     */
    public void editarPerfil(String id, String atributo, String valor) {
        String login = sessaoController.getLoginDaSessao(id);
        usuarioController.buscarUsuario(login).setAtributo(atributo, valor);
    }

    /**
     * Adiciona ou envia convite de amizade para outro usuário do sistema.
     *
     * @param id O identificador da sessão ativa.
     * @param amigo O login do usuário que se deseja ter como amigo.
     */
    public void adicionarAmigo(String id, String amigo) {
        String login = sessaoController.getLoginDaSessao(id);
        usuarioController.adicionarAmigo(login, amigo);
    }

    /**
     * Verifica se os dois usuários informados possuem vínculo de amizade.
     *
     * @param login O login do usuário base.
     * @param amigo O login do alvo da amizade.
     * @return true se forem amigos, false caso contrário.
     */
    public boolean ehAmigo(String login, String amigo) {
        try { return usuarioController.buscarUsuario(login).ehAmigo(amigo); } catch (Exception e) { return false; }
    }

    /**
     * Retorna todos os amigos de um determinado usuário, no formato de string de conjunto.
     *
     * @param login O login do usuário pesquisado.
     * @return Uma string contendo os logins formatados, ex: "{joao,maria}".
     */
    public String getAmigos(String login) {
        return "{" + String.join(",", usuarioController.buscarUsuario(login).getAmigos()) + "}";
    }

    /**
     * Envia um recado privado.
     *
     * @param id O identificador da sessão do remetente.
     * @param destinatario O login de quem vai receber.
     * @param recado O texto a ser enviado.
     */
    public void enviarRecado(String id, String destinatario, String recado) {
        String remetente = sessaoController.getLoginDaSessao(id);
        usuarioController.enviarRecado(remetente, destinatario, recado);
    }

    /**
     * Retira e lê o próximo recado privado da caixa de entrada do usuário logado.
     *
     * @param id O identificador da sessão ativa.
     * @return O conteúdo em texto do recado lido.
     */
    public String lerRecado(String id) {
        String login = sessaoController.getLoginDaSessao(id);
        return usuarioController.buscarUsuario(login).lerProximoRecado();
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param id O identificador da sessão do usuário criador (futuro dono).
     * @param nome O nome da comunidade.
     * @param descricao O objetivo da comunidade.
     */
    public void criarComunidade(String id, String nome, String descricao) {
        String dono = sessaoController.getLoginDaSessao(id);
        comunidadeController.criarComunidade(dono, nome, descricao);
    }

    /**
     * Retorna a descrição de uma comunidade específica.
     *
     * @param nome O nome da comunidade.
     * @return A descrição desta.
     */
    public String getDescricaoComunidade(String nome) {
        return comunidadeController.getDescricaoComunidade(nome);
    }

    /**
     * Retorna o dono de uma comunidade específica.
     *
     * @param nome O nome da comunidade.
     * @return O login do usuário dono.
     */
    public String getDonoComunidade(String nome) {
        return comunidadeController.getDonoComunidade(nome);
    }

    /**
     * Retorna os membros cadastrados na comunidade especificada.
     *
     * @param nome O nome da comunidade.
     * @return A listagem de logins dos membros em formato de texto "{...}".
     */
    public String getMembrosComunidade(String nome) {
        return comunidadeController.getMembrosComunidade(nome);
    }

    /**
     * Permite ao usuário logado ingressar em uma comunidade já existente.
     *
     * @param id O identificador da sessão ativa.
     * @param nome O nome da comunidade que o usuário deseja entrar.
     */
    public void adicionarComunidade(String id, String nome) {
        String login = sessaoController.getLoginDaSessao(id);
        comunidadeController.adicionarComunidade(login, nome);
    }

    /**
     * Busca as comunidades em que o usuário informado atua como membro.
     *
     * @param login O login do usuário consultado.
     * @return Uma string contendo a lista formatada de comunidades do usuário.
     */
    public String getComunidades(String login) {
        return comunidadeController.getComunidades(login);
    }

    /**
     * Envia uma mensagem global para os membros de uma comunidade.
     *
     * @param id O identificador da sessão do remetente.
     * @param comunidade O nome da comunidade alvo.
     * @param mensagem O conteúdo a ser transmitido.
     */
    public void enviarMensagem(String id, String comunidade, String mensagem) {
        String loginRemetente = sessaoController.getLoginDaSessao(id);
        usuarioController.buscarUsuario(loginRemetente);
        comunidadeController.enviarMensagem(loginRemetente, comunidade, mensagem);
    }

    /**
     * Retira e lê a próxima mensagem vinda de uma comunidade da caixa do usuário.
     *
     * @param id O identificador da sessão ativa.
     * @return O conteúdo da mensagem de comunidade.
     */
    public String lerMensagem(String id) {
        String login = sessaoController.getLoginDaSessao(id);
        return usuarioController.buscarUsuario(login).lerProximaMensagem();
    }

    /**
     * Estabelece relação onde o usuário da sessão declara fã-clube por outro usuário (ídolo).
     *
     * @param id O identificador da sessão ativa (fã).
     * @param idolo O login do alvo de idolatria.
     */
    public void adicionarIdolo(String id, String idolo) {
        String fa = sessaoController.getLoginDaSessao(id);
        usuarioController.adicionarIdolo(fa, idolo);
    }

    /**
     * Verifica se o usuário é fã da pessoa informada.
     *
     * @param login O login do suposto fã.
     * @param idolo O login do suposto ídolo.
     * @return true se a relação for verdadeira.
     */
    public boolean ehFa(String login, String idolo) {
        try { return usuarioController.buscarUsuario(login).ehFa(idolo); } catch (Exception e) { return false; }
    }

    /**
     * Retorna a lista de fãs de um usuário.
     *
     * @param login O login do usuário base.
     * @return String formatada "{fã1, fã2...}".
     */
    public String getFas(String login) {
        return "{" + String.join(",", usuarioController.buscarUsuario(login).getFas()) + "}";
    }

    /**
     * Adiciona paquera para um usuário e dispara notificação em caso de match mútuo.
     *
     * @param id O identificador da sessão ativa.
     * @param paquera O login da pessoa paquerada.
     */
    public void adicionarPaquera(String id, String paquera) {
        String login = sessaoController.getLoginDaSessao(id);
        usuarioController.adicionarPaquera(login, paquera);
    }

    /**
     * Checa o status de paquera entre os usuários.
     *
     * @param id O identificador da sessão ativa.
     * @param paquera O login da paquera alvo.
     * @return true se o usuário está paquerando.
     */
    public boolean ehPaquera(String id, String paquera) {
        try {
            String login = sessaoController.getLoginDaSessao(id);
            return usuarioController.buscarUsuario(login).ehPaquera(paquera);
        } catch (Exception e) { return false; }
    }

    /**
     * Consulta as pessoas cadastradas como paquera na lista do usuário da sessão atual.
     *
     * @param id O identificador da sessão.
     * @return String com os nomes das paqueras "{...}".
     */
    public String getPaqueras(String id) {
        String login = sessaoController.getLoginDaSessao(id);
        return "{" + String.join(",", usuarioController.buscarUsuario(login).getPaqueras()) + "}";
    }

    /**
     * Efetua o bloqueio de outro perfil.
     *
     * @param id O identificador da sessão ativa.
     * @param inimigo O login de quem sofrerá bloqueio das interações.
     */
    public void adicionarInimigo(String id, String inimigo) {
        String login = sessaoController.getLoginDaSessao(id);
        usuarioController.adicionarInimigo(login, inimigo);
    }
}