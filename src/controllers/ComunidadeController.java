package controllers;

import entities.Comunidade;
import entities.Usuario;
import exceptions.JackutException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe responsável por gerenciar as regras de negócio das comunidades.
 * Controla a criação, adesão de novos membros e envio de mensagens em grupo.
 */
public class ComunidadeController {

    private final Map<String, Comunidade> comunidades;
    private final UsuarioController usuarioController;

    /**
     * Construtor do ComunidadeController.
     * Recebe as dependências necessárias via injeção.
     *
     * @param comunidades Mapa contendo todas as comunidades do sistema.
     * @param usuarioController Controlador de usuários para verificação e notificação de membros.
     */
    public ComunidadeController(Map<String, Comunidade> comunidades, UsuarioController usuarioController) {
        this.comunidades = comunidades;
        this.usuarioController = usuarioController;
    }

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param loginDono O login do criador da comunidade (que será o dono).
     * @param nome O nome único da comunidade.
     * @param descricao A descrição dos objetivos da comunidade.
     * @throws JackutException Se já existir uma comunidade com este mesmo nome.
     */
    public void criarComunidade(String loginDono, String nome, String descricao) {
        if (this.comunidades.containsKey(nome)) {
            throw new JackutException("Comunidade com esse nome já existe.");
        }
        Comunidade nova = new Comunidade(nome, descricao, loginDono);
        this.comunidades.put(nome, nova);
        this.usuarioController.buscarUsuario(loginDono).adicionarComunidade(nome);
    }

    /**
     * Retorna a descrição de uma comunidade específica.
     *
     * @param nome O nome da comunidade.
     * @return A string contendo a descrição.
     */
    public String getDescricaoComunidade(String nome) {
        return buscarComunidade(nome).getDescricao();
    }

    /**
     * Retorna o login do dono (criador) da comunidade.
     *
     * @param nome O nome da comunidade.
     * @return O login do usuário proprietário.
     */
    public String getDonoComunidade(String nome) {
        return buscarComunidade(nome).getDonoLogin();
    }

    /**
     * Formata e retorna os membros cadastrados na comunidade.
     *
     * @param nome O nome da comunidade.
     * @return String formatada com os membros no padrão "{membro1,membro2}".
     */
    public String getMembrosComunidade(String nome) {
        Set<String> membros = buscarComunidade(nome).getMembros();
        return "{" + String.join(",", membros) + "}";
    }

    /**
     * Adiciona um usuário a uma comunidade existente.
     *
     * @param loginUsuario O login do usuário a ser adicionado.
     * @param nome O nome da comunidade destino.
     * @throws JackutException Se a comunidade não existir ou se o usuário já for membro.
     */
    public void adicionarComunidade(String loginUsuario, String nome) {
        Comunidade comunidade = buscarComunidade(nome);
        if (comunidade.ehMembro(loginUsuario)) {
            throw new JackutException("Usuario já faz parte dessa comunidade.");
        }
        comunidade.adicionarMembro(loginUsuario);
        this.usuarioController.buscarUsuario(loginUsuario).adicionarComunidade(nome);
    }

    /**
     * Retorna as comunidades em que o usuário está inscrito.
     *
     * @param loginUsuario O login do usuário desejado.
     * @return String formatada com as comunidades no padrão "{comunidade1,comunidade2}".
     */
    public String getComunidades(String loginUsuario) {
        Usuario u = usuarioController.buscarUsuario(loginUsuario);
        return "{" + String.join(",", u.getMinhasComunidades()) + "}";
    }

    /**
     * Envia uma mensagem para todos os membros de uma comunidade.
     *
     * @param loginRemetente O login de quem enviou a mensagem.
     * @param nomeComunidade O nome da comunidade que receberá a mensagem.
     * @param mensagem O conteúdo a ser enviado.
     */
    public void enviarMensagem(String loginRemetente, String nomeComunidade, String mensagem) {
        Comunidade comunidade = buscarComunidade(nomeComunidade);
        for (String membroLogin : comunidade.getMembros()) {
            try {
                Usuario membro = usuarioController.buscarUsuario(membroLogin);
                membro.receberMensagem(loginRemetente, mensagem);
            } catch (Exception ignored) { }
        }
    }

    /**
     * Remove todos os vínculos do usuário com comunidades do sistema.
     * Se ele for dono de comunidades, estas serão deletadas. Remove ele como membro das outras.
     *
     * @param login O login do usuário sendo encerrado no sistema.
     */
    public void removerRastrosDeUsuario(String login) {
        List<String> comunidadesDeletadas = new ArrayList<>();
        this.comunidades.entrySet().removeIf(entry -> {
            if (entry.getValue().getDonoLogin().equals(login)) {
                comunidadesDeletadas.add(entry.getKey());
                return true;
            }
            return false;
        });

        for (Comunidade com : this.comunidades.values()) {
            com.getMembros().remove(login);
        }

        for (String com : comunidadesDeletadas) {
            this.usuarioController.removerComunidadeDeTodos(com);
        }
    }

    /**
     * Método auxiliar de busca de comunidade com validação de existência.
     *
     * @param nome O nome da comunidade desejada.
     * @return A instância da comunidade buscada.
     * @throws JackutException Se não houver comunidade com o nome especificado.
     */
    private Comunidade buscarComunidade(String nome) {
        Comunidade comunidade = this.comunidades.get(nome);
        if (comunidade == null) {
            throw new JackutException("Comunidade não existe.");
        }
        return comunidade;
    }
}