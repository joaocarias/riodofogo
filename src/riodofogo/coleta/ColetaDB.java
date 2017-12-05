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
import riodofogo.Auxiliar;

/**
 *
 * @author joao.franca
 */
public class ColetaDB implements ColetaDBInterface {

    @Override
    public int getIdServidorPorPis(String pis) {
        try{
            ConexaoInterface ci = new ConexaoMySQL();
            Connection conn = ci.criarConexao();
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

    
    
    
    /**
     * Método busca verificar se para o determindado servidor existe um registro
     * aberto, ou seja, se o mesmo ainda não bateu o ponto de saída.
     * @return Retorna 0 em caso de não encontrado ou retorna id_registro da 
     * Tabela registro no caso de encontrado em aberto; Ou ainda pode retornar
     * -1 em caso de erro;
     */
    private Registro getRegistroAbertoMySQL(int idRegistro){
        
        try{                        
            Registro retorno = null;
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();        
            
            Statement stm = conn.createStatement();
            
            String sqlQuery = "SELECT * FROM `registro` "
                    + "WHERE `id_registro` = '"+idRegistro+"' "
                    + "AND `dt_saida` = '0000-00-00 00:00:00' "
                    + "AND `st_registro` = '1' ";                        
            ResultSet rs = stm.executeQuery(sqlQuery);
            
            while(rs.next()){
                retorno = new Registro(
                        Integer.parseInt(rs.getString(1)), 
                        Integer.parseInt(rs.getString(2)), 
                        rs.getString(3), 
                        rs.getString(4),
                        rs.getString(5), 
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)
                );                        
            } 

            rs.close();
            stm.close();
            conn.close();
          
            return retorno;
        }catch(SQLException | NumberFormatException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    @Override
    public boolean gravarColeta(int idServidor, int idRelogio, Coleta coleta) {
        if(verificarColetaInserida(idServidor, idRelogio, coleta)){
            System.out.println("Ingnorar coleta, já que a mesma já se encontra inserida");
        }else{
            
            int idRegistroAberto = this.getIdRegistroAberto(idServidor);
            
            if(idRegistroAberto > 0){
                Registro registro = this.getRegistroAbertoMySQL(idRegistroAberto);
                int testeDatasIguais = Auxiliar.compareData(coleta.getData(), registro.getDt_entrada());
                if(testeDatasIguais > 0){
                    System.out.println("Inserir como saida");
                }else{
                    System.out.println("Inserir saida automaticamente");
                    System.out.println("Inserir como entrada");
                }                
            }else{
                if(testarServidorPlantonistaNoturno(idServidor)){
                    System.out.println("Servidor Plantonista");
                }else{
                    System.out.println("2 Inserir como nova entrada");
                }
            }            
        }
        return true;
    }
    
    /**
     * Método testar se o servidor é plantomista
     * @param idServidor - Id do Servidor 
     * @return - Retorna <i>TRUE</i> em caso do servidor seja plantonista. Retorna <i>FALSE</i>
     * 
     */
    private boolean testarServidorPlantonistaNoturno(int idServidor){
        try{
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();
            
            Statement stm = conn.createStatement();
            
            String query = "SELECT * FROM `plantonista` WHERE `id_servidor` = '"+idServidor+"' AND `noturno` = '1' ";
            
            ResultSet rs = stm.executeQuery(query);
            
            if(rs.first()){
                
                rs.close();
                stm.close();
                conn.close();
                
                return true;
            }else{
                
                rs.close();
                stm.close();
                conn.close();
                
                return false;
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }        
    }
    
    /**
     * Método busca se existe algum registro/ponto em aberto e identifica qual 
     * registro está em aberto.
     * @param idServidor - int - Id de identificação do servidor
     * @return - Retorna um intereiro contendo o id do registro em aberto, desde que exista, 
     * caso não encontrado, então retorna o inteiro 0 (zero). Retorna -1 em caso de erro.
     */
    private int getIdRegistroAberto(int idServidor){
        try{
            int idRegistroAberto = 0;
            
            ConexaoInterface ci = new ConexaoMySQL();
            Connection conn = ci.criarConexao();
            Statement stm = conn.createStatement();
            
            String query = "SELECT `id_registro` FROM `registro` "
                    + "WHERE `id_servidor` = '"+idServidor+"' AND `st_registro` = '1' ";
            
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()){
                idRegistroAberto = Integer.parseInt(rs.getString("id_registro"));
            }
            
            rs.close();
            stm.close();
            conn.close();
            
            return idRegistroAberto;
        }catch(SQLException | NumberFormatException e){
            System.err.println(e.toString());
            return -1;
        }        
    }
    
    /**
     * Médodo busca a existe de uma coleta específica no banco de dados.
     * @param $idServidor - ID do Servidor
     * @param idRelogio - ID de identificação do Relógio
     * @param coleta - Objeto Coleta, contendo as informações básicas da coleta.
     * @return boolean - Retorna TRUE se encontrado, e FALSE se não for encontrado.
     */    
    private boolean verificarColetaInserida(int $idServidor, int idRelogio, Coleta coleta){
        try{
            ConexaoInterface ci = new ConexaoMySQL();
            Connection conn = ci.criarConexao();
            Statement stm = conn.createStatement();
            
            String sql = " SELECT id_registro FROM registro "
                    + "WHERE id_servidor = '"+$idServidor+"' "
                        + "AND (nsr_entrada = '"+coleta.getNsr()+"' OR nsr_saida = '"+coleta.getNsr()+"') "
                        + "AND (idrelogio_entrada = '"+idRelogio+"' OR idrelogio_saida = '"+idRelogio+"') ";
            
            ResultSet rs = stm.executeQuery(sql);
                                   
            int cont = 0;
            
            while(rs.next()){
                cont++;
            }
            
            System.out.println("Verificando existe de Coleta de PIS: "+coleta.getPis()+" e NSR: "+coleta.getNsr());            
            if(cont > 0){
                return true;
            }else{
                return false;
            }                       
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
    
    
}
