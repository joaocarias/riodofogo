/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    
    /**
     * Método compare duas datas
     * @param data1 - String - data a ser comparada
     * @param data2 - String - a outras data a ser comparada
     * @return - retona 1 se igual, 0 se diferente e -1 em caso apresente algum problema durante 
     * o teste de comparação
     */    
    public static int compareData(String data1, String data2){
        try{
            
            int[] arrayData1 = new int[3];
            int[] arrayData2 = new int[3];
            
            Calendar calendario = Calendar.getInstance();            
            SimpleDateFormat format_ = new SimpleDateFormat("yyyy-MM-dd");                      
            
            //pegando a primeira data (data1)
            Date data = (Date)format_.parse(data1);                        
            calendario.setTime(data);
            arrayData1[0] = calendario.get(Calendar.DAY_OF_MONTH);
            arrayData1[1] = calendario.get(Calendar.MONTH);
            arrayData1[2] = calendario.get(Calendar.YEAR);
            
            //pegando a segunda data (data2)
            data = (Date) format_.parse(data2);
            calendario.setTime(data);            
            arrayData2[0] = calendario.get(Calendar.DAY_OF_MONTH);
            arrayData2[1] = calendario.get(Calendar.MONTH);
            arrayData2[2] = calendario.get(Calendar.YEAR);   
                       
            //testando as datas
            if(arrayData1[0] == arrayData2[0] && arrayData1[1] == arrayData2[1] && arrayData1[2] == arrayData2[2])
            {                
                return 1;
            }else{             
                return 0;
            }        
        }catch(Exception e){
            System.err.println(e.getMessage());
            return -1;
        }            
    }   
    
    public static String dataComMascara(String data){
        try{                        
            String data_tratada = data.substring(4,8)+"-"+data.substring(2,4)+"-"+data.substring(0,2);
            return data_tratada;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return data;
        }
    }
    
    public static String horaComMascara(String hora){
        try{                        
            String hora_tratada = hora.substring(0,2)+":"+hora.substring(2,4)+":00";
            return hora_tratada;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return hora;
        }
    }
}
