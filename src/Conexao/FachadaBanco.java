
package Conexao;

import java.sql.Connection;
import riodofogo.Propriedades;

/**
 *
 * @author joao.franca
 */
public class FachadaBanco {
    public Connection criarConecaoMySQL(){
        try{
            ConexaoInterface c = new ConexaoMySQL();
            Connection conn = c.criarConexao();
            return conn;
        }catch(Exception e){
            System.err.println(e.toString());
            return null;
        }
    }
    
    public boolean testarConexao(Connection c){
        try{
            if(c != null){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.err.println(e.toString());
            return false;
        }
    }
    
    public String getPorta(){
        Propriedades p = new Propriedades();
        return p.getPorta();
    }
    
    public String getServidor(){
        Propriedades p = new Propriedades();
        return p.getServidor();
    }
    
    public String getNomeBanco(){
        Propriedades p = new Propriedades();
        return p.getNomeBanco();
    }        
     
    public String getUsuario(){
        Propriedades p = new Propriedades();
        return p.getUsuario();
    }

    public String getSenha() {
        Propriedades p = new Propriedades();
        return p.getSenha();
    }    
}
