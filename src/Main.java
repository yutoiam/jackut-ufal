import java.io.File;

/**
 * Ponto de entrada da aplicação Jackut.
 *
 * <p>Esta classe foi automatizada para rodar todos os scripts de teste
 * do EasyAccept sequencialmente, isolando cada execução num processo
 * separado. Isso evita que o comando 'quit' encerre a bateria de testes
 * prematuramente e garante o fluxo correto da persistência de dados.</p>
 *
 * @author Yuri Raphael Mota De Araujo Barbosa
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Lista de todos os testes na ordem correta
        String[] arquivosDeTeste = {
                "tests/us1_1.txt", "tests/us1_2.txt",
                "tests/us2_1.txt", "tests/us2_2.txt",
                "tests/us3_1.txt", "tests/us3_2.txt",
                "tests/us4_1.txt", "tests/us4_2.txt"
        };

        // Captura os caminhos do Java e do projeto dinamicamente
        String separador = File.separator;
        String javaPath = System.getProperty("java.home") + separador + "bin" + separador + "java";
        String classPath = System.getProperty("java.class.path");

        for (String teste : arquivosDeTeste) {
            System.out.println("\n==================================================");
            System.out.println("EXECUTANDO TESTE: " + teste);
            System.out.println("==================================================");

            // Monta o comando de terminal para executar o EasyAccept com o novo pacote
            ProcessBuilder builder = new ProcessBuilder(
                    javaPath,
                    "-Dfile.encoding=ISO-8859-1", // Para conseguir ler as acentuações corretamente
                    "-cp", classPath,
                    "easyaccept.EasyAccept",
                    "br.ufal.ic.p2.jackut.Facade", // Caminho padrão conforme o bootstrap
                    teste
            );

            // Redireciona a saída do EasyAccept para o console do IntelliJ
            builder.inheritIO();

            // Inicia o processo isolado e espera que termine antes de ir para o próximo
            Process processo = builder.start();
            processo.waitFor();
        }

        System.out.println("\nBATERIA DE TESTES CONCLUÍDA COM SUCESSO!");
    }
}