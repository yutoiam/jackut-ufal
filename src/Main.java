import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal responsável pela inicialização e execução das baterias
 * de testes de aceitação do sistema através do framework EasyAccept.
 */
public class Main {

    private static final String FACADE = "Facade";
    private static final String ARQUIVO_USUARIOS = "jackut-main" + File.separator + "data" + File.separator + "usuarios.dat";
    private static final String ARQUIVO_COMUNIDADES = "jackut-main" + File.separator + "data" + File.separator + "comunidades.dat";

    private static final String[][] TESTES = {
            {"testes/us1_1.txt", "testes/us1_2.txt"},
            {"testes/us2_1.txt", "testes/us2_2.txt"},
            {"testes/us3_1.txt", "testes/us3_2.txt"},
            {"testes/us4_1.txt", "testes/us4_2.txt"},
            {"testes/us5_1.txt", "testes/us5_2.txt"},
            {"testes/us6_1.txt", "testes/us6_2.txt"},
            {"testes/us7_1.txt", "testes/us7_2.txt"},
            {"testes/us8_1.txt", "testes/us8_2.txt"},
            {"testes/us9_1.txt", "testes/us9_2.txt"}
    };

    /**
     * Método principal que dispara a checagem automática dos scripts do sistema.
     *
     * @param args Parâmetros de linha de comando.
     * @throws Exception Se ocorrer alguma falha estrutural no lançamento dos testes.
     */
    public static void main(String[] args) throws Exception {
        File pastaDoCodigo = new File(System.getProperty("user.dir") + File.separator + "jackut-main");

        if (!pastaDoCodigo.exists()) {
            pastaDoCodigo = new File(System.getProperty("user.dir"));
        }

        for (String[] par : TESTES) {
            new File(pastaDoCodigo, "data" + File.separator + "usuarios.dat").delete();
            new File(pastaDoCodigo, "data" + File.separator + "comunidades.dat").delete();

            for (String script : par) {
                rodar(pastaDoCodigo, script);
            }
        }
    }

    /**
     * Inicia o sub-processo Java que invoca a biblioteca externa EasyAccept
     * com os classpaths corretos para testar a Facade.
     *
     * @param pastaDoCodigo A pasta raiz do projeto.
     * @param script O caminho do script de teste em texto a ser interpretado.
     * @throws Exception Se houver problemas na submissão do processo ao sistema operacional.
     */
    private static void rodar(File pastaDoCodigo, String script) throws Exception {
        String cpAtual = System.getProperty("java.class.path");
        String easyAcceptJar = new File(pastaDoCodigo, "lib" + File.separator + "easyaccept.jar").getAbsolutePath();
        String cpCompleto = cpAtual + File.pathSeparator + easyAcceptJar;

        String scriptAbsoluto = new File(pastaDoCodigo, script).getAbsolutePath();

        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("-Dfile.encoding=ISO-8859-1");
        cmd.add("-cp");
        cmd.add(cpCompleto);
        cmd.add("easyaccept.EasyAccept");
        cmd.add(FACADE);
        cmd.add(scriptAbsoluto);

        Process p = new ProcessBuilder(cmd)
                .directory(pastaDoCodigo)
                .redirectErrorStream(true)
                .start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        }
        p.waitFor();
    }
}