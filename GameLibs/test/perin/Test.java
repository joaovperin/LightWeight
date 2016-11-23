/*
 * GameLibs
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package perin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Descrição da classe.
 */
public class Test {

    public static void main(String[] args) {

//        String fileName = "src/br/com/perin/shaders/vertexShader.txt";
        String fileName = "res/test-01.png";
        String content = readFile(fileName);

        System.out.println("BYTES: ");
        System.out.println(content);

    }

    private static String readFile(String fileName) {
        File file = new File(fileName);
        byte[] bytes = null;
        try (InputStream is = new FileInputStream(file)) {
            // Pega o tamanho do arquivo
            long length = file.length();
            // Se superou o tamanho máximo
            if (length > Integer.MAX_VALUE) {
                throw new IOException("File is too large to process " + file.getName());
            }
            // Cria nova cadeia de bytes
            bytes = new byte[(int) length];
            // Lê o arquivo
            int offset = 0;
            int numRead = 0;
            while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return new String(bytes);
    }

}
