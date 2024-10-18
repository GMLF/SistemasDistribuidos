/**
 * Lab0: Leitura de Base de Dados N�o-Distribuida
 * 
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 20/02/2023
 * 
 * Referencias: 
 * https://docs.oracle.com/javase/tutorial/essential/io
 * 
 */
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

public class Principal_v0 {

    public final static Path path = Paths.get("src\\fortune-br.txt");
    private int NUM_FORTUNES = 0;

    public class FileReader {

        public int countFortunes() throws FileNotFoundException {

            int lineCount = 0;

            InputStream is = new BufferedInputStream(new FileInputStream(
                    path.toString()));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    is))) {

                String line = "";
                while ((line = br.readLine()) != null) {

                    if (line.equals("%"))
                        lineCount++;

                } // fim while

                System.out.println(lineCount);
            } catch (IOException e) {
                System.out.println("SHOW: Excecao na leitura do arquivo.");
            }
            return lineCount;
        }

        public void parser(HashMap<Integer, String> hm)
                throws FileNotFoundException {

            InputStream is = new BufferedInputStream(new FileInputStream(
                    path.toString()));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    is))) {

                int lineCount = 0;

                String line = "";
                while ((line = br.readLine()) != null) {

                    if (line.equals("%"))
                        lineCount++;

                    StringBuffer fortune = new StringBuffer();
                    while (!(line == null) && !line.equals("%")) {
                        fortune.append(line + "\n");
                        line = br.readLine();
                    }

                    hm.put(lineCount, fortune.toString());
                    System.out.println(fortune.toString());

                    System.out.println(lineCount);
                } // fim while

            } catch (IOException e) {
                System.out.println("SHOW: Excecao na leitura do arquivo.");
            }
        }

        // Método para ler uma fortuna aleatória do HashMap
        public void read(HashMap<Integer, String> hm) throws FileNotFoundException {

            if (hm.isEmpty()) {
                System.out.println("Nenhuma fortuna disponível.");
                return;
            }

            // Seleciona uma fortuna aleatória
            Random random = new SecureRandom();
            int randomIndex = random.nextInt(hm.size()) + 1; // Índices no HashMap começam em 1
            String randomFortune = hm.get(randomIndex);

            System.out.println("Fortuna aleatória:\n" + randomFortune);
        }

        // Método para adicionar uma nova fortuna no arquivo
        public void write(HashMap<Integer, String> hm) throws FileNotFoundException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), true))) {
                // Nova fortuna a ser adicionada
                String novaFortuna = "O sucesso é ir de fracasso em fracasso sem perder entusiasmo.\nWinston Churchill";
                
                writer.write(novaFortuna);
                writer.newLine();
                writer.write("%");  // Adiciona o separador
                writer.newLine();

                System.out.println("Nova fortuna adicionada com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        }
    }

    public void iniciar() {

        FileReader fr = new FileReader();
        try {
            NUM_FORTUNES = fr.countFortunes();
            HashMap<Integer, String> hm = new HashMap<>();
            fr.parser(hm);
            fr.read(hm);  // Leitura de uma fortuna aleatória
            fr.write(hm); // Escreve uma nova fortuna no arquivo
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Principal_v0().iniciar();
    }

}
