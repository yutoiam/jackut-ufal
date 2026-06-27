package br.ufal.ic.p2.jackut.controller;

import br.ufal.ic.p2.jackut.model.Usuario;
import java.io.*;
import java.util.Map;

/**
 * Controlador responsável pela persistência de dados do sistema.
 *
 * <p>Isola a lógica de Input/Output (I/O) em disco, serializando e
 * desserializando o estado dos usuários para garantir a continuidade
 * dos dados entre as execuções do programa.</p>
 *
 * @author Yuri Raphael Mota De Araujo Barbosa
 */
public class PersistenciaController {
    /** Caminho e nome do arquivo onde os dados serão salvos. */
    private static final String ARQUIVO_DADOS = "jackut.dat";

    /**
     * Limpa a memória atual dos controladores e apaga o arquivo de dados do disco.
     * Útil para reiniciar o estado do sistema durante as baterias de testes.
     */
    public void zerarSistema() {
        UsuarioController.getInstance().limparDados();
        SessaoController.getInstance().limparDados();
        new File(ARQUIVO_DADOS).delete();
    }

    /**
     * Serializa o estado atual do controlador de usuários e salva em disco.
     *
     * @throws IOException caso ocorra uma falha de escrita no arquivo de dados
     */
    public void encerrarSistema() throws IOException {
        Map<String, Usuario> dados = UsuarioController.getInstance().getUsuarios();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(dados);
        }
    }

    /**
     * Tenta carregar o estado do sistema a partir do disco.
     * Caso o arquivo não exista ou esteja corrompido, inicializa o sistema com dados limpos.
     * As sessões ativas nunca são restauradas.
     */
    @SuppressWarnings("unchecked")
    public void carregarSistema() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Map<String, Usuario> dados = (Map<String, Usuario>) ois.readObject();
                UsuarioController.getInstance().setUsuarios(dados);
            } catch (Exception e) {
                UsuarioController.getInstance().limparDados();
            }
        }
        SessaoController.getInstance().limparDados();
    }
}