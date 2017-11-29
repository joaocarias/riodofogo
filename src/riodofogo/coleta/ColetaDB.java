/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo.coleta;

import Conexao.ConexaoInterface;
import Conexao.ConexaoMySQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author joao.franca
 */
public class ColetaDB implements ColetaDBInterface {

    @Override
    public int getIdServidorPorPis(String pis) {
        try{
            ConexaoInterface ci = new ConexaoMySQL();
            Connection conn = ci.criarCriacao();
            Statement stm = conn.createStatement();
            
            int idServidor = 0;
            
            String query = "SELECT id_servidor FROM servidor WHERE pis = '"+pis+"'; ";
            
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()){
                idServidor = Integer.parseInt(rs.getString(1));
            }            
            
            rs.close();
            stm.close();
            conn.close();
            
            return idServidor;
        }catch(SQLException | NumberFormatException e){
            System.err.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean gravarColeta(int idServidor, int idRelogio, Coleta coleta) {
        return true;
    }
    
    
    
}
