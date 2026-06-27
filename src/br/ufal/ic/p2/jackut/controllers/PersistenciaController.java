package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.entities.Comunidade;
import br.ufal.ic.p2.jackut.entities.Usuario;
import br.ufal.ic.p2.jackut.exceptions.JackutException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por realizar a persistência em disco dos dados do sistema.
 * Utiliza o mecanismo de Serialização do Java para gravar e ler os mapas
 * de usuários e comunidades em arquivos binários (.dat).
 */
public class PersistenciaController {

    private static final String ARQUIVO_USUARIOS = "data/usuarios.dat";
    private static final String ARQUIVO_COMUNIDADES = "data/comunidades.dat";

    /**
     * Lê o arquivo de dados dos usuários e os carrega para a memória.
     *
     * @return Um Map contendo os usuários salvos ou um Map vazio se o arquivo não existir.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Usuario> carregarUsuarios() {
        return (Map<String, Usuario>) carregarDados(ARQUIVO_USUARIOS);
    }

    /**
     * Lê o arquivo de dados das comunidades e as carrega para a memória.
     *
     * @return Um Map contendo as comunidades salvas ou um Map vazio se o arquivo não existir.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Comunidade> carregarComunidades() {
        return (Map<String, Comunidade>) carregarDados(ARQUIVO_COMUNIDADES);
    }

    /**
     * Grava os mapas de dados atuais da memória nos respectivos arquivos de disco.
     *
     * @param usuarios O mapa de usuários atualizado.
     * @param comunidades O mapa de comunidades atualizado.
     */
    public void salvarDados(Map<String, Usuario> usuarios, Map<String, Comunidade> comunidades) {
        new File("data").mkdirs();
        salvarArquivo(ARQUIVO_USUARIOS, usuarios);
        salvarArquivo(ARQUIVO_COMUNIDADES, comunidades);
    }

    /**
     * Exclui os arquivos físicos de dados, limpando permanentemente o estado do sistema.
     */
    public void limparDados() {
        new File(ARQUIVO_USUARIOS).delete();
        new File(ARQUIVO_COMUNIDADES).delete();
    }

    /**
     * Método genérico e interno para leitura de objetos serializados no sistema de arquivos.
     *
     * @param caminhoArquivo O caminho do arquivo a ser lido.
     * @return O objeto desserializado lido do disco.
     * @throws JackutException Se houver erro de I/O ou incompatibilidade de classes.
     */
    private Object carregarDados(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) return new HashMap<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return in.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException e) {
            throw new JackutException("Falha de leitura no sistema de arquivos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new JackutException("Classe de dados incompatível ou não encontrada: " + e.getMessage());
        }
    }

    /**
     * Método genérico e interno para escrita de objetos serializados no sistema de arquivos.
     *
     * @param caminho O caminho do arquivo onde o dado será salvo.
     * @param dados O objeto a ser serializado.
     * @throws JackutException Se não for possível escrever no destino especificado.
     */
    private void salvarArquivo(String caminho, Object dados) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(dados);
        } catch (FileNotFoundException e) {
            throw new JackutException("Caminho não encontrado para salvar dados.");
        } catch (IOException e) {
            throw new JackutException("Erro ao escrever no arquivo de dados.");
        }
    }
}