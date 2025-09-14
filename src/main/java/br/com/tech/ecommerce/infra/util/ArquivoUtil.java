package src.main.java.br.com.tech.ecommerce.infra.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    public static <T> void gravarListaEmArquivo(List<T> lista, File arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> lerListaDeArquivo(File arquivo) {
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}