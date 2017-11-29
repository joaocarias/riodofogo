
package riodofogo.relogio;

import Conexao.FachadaBanco;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author joao.franca
 */
public class RelogioDB implements RelogioDBInterface{
    public List<Relogio> getList(){
        try{
            
            List<Relogio> relogios = new LinkedList<Relogio>();
            
            FachadaBanco fb = new FachadaBanco();
            Connection conn = fb.criarConecaoMySQL();            
            Statement stm = conn.createStatement();
                     
            String query = "select id_relogio, descricao from relogios order by descricao";
            System.out.println("Buscando Lista de Relógios...");
            
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()){
                Relogio r = new Relogio(
                        Integer.parseInt(rs.getString("id_relogio"))
                        , rs.getString("descricao")
                );
                
                relogios.add(r);
            }

            rs.close();
            stm.close();
            conn.close();
            
            System.out.println("Quantidade de Relógios Encontrados: "+relogios.size());
            return relogios;
        }catch(SQLException | NumberFormatException e){
            System.err.println(e.getMessage());
            return null;
        }        
    }
    
    
}
