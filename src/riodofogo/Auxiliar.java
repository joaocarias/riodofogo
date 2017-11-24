/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author joao.franca
 */
public class Auxiliar {
    /**
     * 
     * @param caminho - Caminho/Localização do arquivo 
     * @return BufferedReader 
     * @throws FileNotFoundException 
     */
    public static BufferedReader leituraArquivo(String caminho) throws FileNotFoundException{
        FileReader arquivo = new FileReader(caminho);
        BufferedReader lerArquivo = new BufferedReader(arquivo);     
        return lerArquivo;
    }
}
