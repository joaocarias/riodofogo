
package riodofogo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author joao.carias
 */
public class Propriedades {
    
    private String arquivoPropriedade = "ConexaoPropriedades.properties";
    
    private String getArquivoPropriedade(){
        return this.arquivoPropriedade;
    }        
    
    /**
     * Busca o valor de uma determinada variável.
     * @param variavel - Nome da variável que se deseja ler;
     * @return Retorna uma String com o valor da variável, em caso de erro, retorna NULL.
     */    
    public String lerPropriedade(String variavel){
        Properties propriedades = new Properties();
        
        try{
             propriedades.load(new FileInputStream(this.getArquivoPropriedade()));
             return propriedades.getProperty(variavel);
        }catch(IOException e){         
            return null;
        }
    }
    
    public boolean gravarPropriedade(Map<String, String> map){
        try{
            Properties p = new Properties();
            String nomeProperties = "ConexaoPropriedades.properties";    
            
            p.load(new FileInputStream(nomeProperties));
            
            for(String key : map.keySet()){
                p.setProperty(key, map.get(key));
            }   
            
            FileOutputStream fos = new FileOutputStream(nomeProperties);
            p.store(fos, "FILE JDBC PROPERTIES:");
            fos.close();
               
            return true;
        }catch(Exception e){            
            return false;
        }
    }    
    
    public String getPorta(){
        return lerPropriedade("porta_mysql");        
    }
    
    public String getServidor(){
        return lerPropriedade("servidor_mysql");
    }
     
    public String getNomeBanco(){
        return lerPropriedade("banco_mysql");            
    }
     
    public String getUsuario(){
       return lerPropriedade("usuario_mysql");            
    }
          
    public String getSenha() {
        return lerPropriedade("senha_mysql");            
    }
}
