/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo.coleta;

import java.util.List;
import java.io.BufferedReader;
import java.util.LinkedList;
import riodofogo.Auxiliar;

/**
 *
 * @author joao.franca
 */
public class Leitura {
    public List<Coleta> lerArquivo(String caminho){
        try{                
            List<Coleta> coletas = new LinkedList<Coleta>();
            BufferedReader in = Auxiliar.leituraArquivo(caminho);

            for (String line = in.readLine(); line != null; line = in.readLine()) {                    
                if(line.length() >= 34){
                    Coleta coleta = new Coleta(
                            line.substring(0, 10)
                            , line.substring(10, 18)
                            , line.substring(18, 22)
                            , line.substring(23, 34)
                    );

                    coletas.add(coleta);
                }                                                            
            }           
            return coletas;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }            
    }
}
