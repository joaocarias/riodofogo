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
import riodofogo.pis.Pis;
import riodofogo.pis.TmpListaPis;

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
            
            if(idServidor == 0){
                TmpListaPis.adicionaPis(new Pis(pis));
            }
            
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
                int testeDatasIguais = Auxiliar.compareData(Auxiliar.dataComMascara(coleta.getData()), registro.getDt_entrada());
                if(testeDatasIguais > 0){
                    if(inserirComoSaida(idRegistroAberto, Auxiliar.dataComMascara(coleta.getData())+" "+Auxiliar.horaComMascara(coleta.getHora()) , idServidor, coleta.getNsr(), idRelogio)){                        
                        System.out.println("SAÍDA: "+coleta.getData() +" "+coleta.getHora());                        
                    }else{
                        System.err.println("Erro ao inserir saida no ponto: "+coleta.getData()+" "+Auxiliar.horaComMascara(coleta.getHora())+" PIS: "+coleta.getPis());                        
                    }
                }else{
                    fecharSaidaAutomatico(idRegistroAberto, registro.getDt_entrada(), idServidor);
                    System.out.println("Ponto Fechado Automaticamente: "+registro.getDt_entrada());                        
                    inserirComoEntrada(idServidor, Auxiliar.dataComMascara(coleta.getData())+" "+Auxiliar.horaComMascara(coleta.getHora()), coleta.getNsr(), idRelogio);                      
                    System.out.println("ENTRADA: "+coleta.getData());
                }                
            }else{
                if(testarServidorPlantonistaNoturno(idServidor)){
                    int idRegistroFechadoAutomatico = testarRegistroFechadoAutomaticoPorEmDiaAnterior(Auxiliar.dataComMascara(coleta.getData()), idServidor);
                    if(idRegistroFechadoAutomatico > 0){
                        if(inserirComoSaida(idRegistroFechadoAutomatico, Auxiliar.dataComMascara(coleta.getData())+" "+Auxiliar.horaComMascara(coleta.getHora()), idServidor, coleta.getNsr(), idRelogio)){
                            System.out.println("Inserido Saída Ponto: "+Auxiliar.dataComMascara(coleta.getData())+" "+idRegistroFechadoAutomatico);                                              
                        }else{
                            System.err.println("Erro ao inserir saida no ponto: "+coleta.getData());                            
                        }
                    }else{
                        if(inserirComoEntrada(idServidor, Auxiliar.dataComMascara(coleta.getData())+" "+Auxiliar.horaComMascara(coleta.getHora()), coleta.getNsr(), idRelogio)){
                            System.out.println("ENTRADA: "+coleta.getData());                            
                        }else{
                            System.err.println("Erro ao inserir ponto: "+coleta.getData());
                        }  
                    }
                }else{
                    if(inserirComoEntrada(idServidor, Auxiliar.dataComMascara(coleta.getData())+" "+Auxiliar.horaComMascara(coleta.getHora()), coleta.getNsr(), idRelogio)){
                        System.out.println("ENTRADA: "+coleta.getData());                            
                    }else{
                        System.err.println("Erro ao inserir ponto: "+coleta.getData());
                    }  
                }                
            }            
        }
        return true;
    }
    
    private int testarRegistroFechadoAutomaticoPorEmDiaAnterior(String dataColeta, int idServidor) {
        try{
            int id = 0;
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();
            Statement stm = conn.createStatement();
            
            String query = " SELECT id_registro "
                    + " FROM `registro` "
                    + " WHERE DAYOFYEAR(dt_entrada) = (DAYOFYEAR('"+dataColeta+"')-1) "
                    + " AND YEAR(dt_entrada) = YEAR(NOW()) "
                    + " AND st_ponto_aberto = '1' "
                    + " AND id_servidor = '"+idServidor+"' ";
            
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()){
                id = Integer.parseInt(rs.getString(1));
            }
            
            rs.close();
            stm.close();
            conn.close();            
            
            return id;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return -1;
        }        
    }
    
     /**
     * Método insere um novo registro do Ponto na tabela do MySQL
     * @param idServidor - String - ID de Identificação do Servidor
     * @param dataEntrada - String data do registro
     * @return 
     */
    private boolean inserirComoEntrada(int idServidor, String dataEntrada, String nsr, int idRelogio){
        try{            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();
            
            Statement stm = conn.createStatement();
            
            String updateSaida = "INSERT INTO `registro`(`id_servidor`, `dt_entrada`, `data_ultima_atualizacao`, `atualizado_por`, `nsr_entrada`, `idrelogio_entrada`) VALUES ('"+idServidor+"', '"+dataEntrada+"', NOW(), 'JAVINHA_SMS', '"+nsr+"', '"+idRelogio+"')";
            
            boolean b = stm.execute(updateSaida);
            
            mudaStatus(idServidor, 1);         
                      
            stm.close();
            conn.close();
            
            return true;
        }catch(Exception e){
            System.err.println(e.toString());
            return false;
        }
    }
    
    /**
     * Este método realiza a atualização de ponto/registro como saida 
     * @param idRegistro - ID de registro 
     * @param dataSaida - Data de Saída 
     * @return - Returna <i>TRUE</i> quando a operação não apresenta erro durantes
     * a execução; e <i>FALSE</i> quando ocorre algum problema durante a execução.
     */
    private boolean fecharSaidaAutomatico(int idRegistro, String dataSaida, int idServidor){
        try{
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();
            
            Statement stm = conn.createStatement();
            
            String updateSaida = " UPDATE `registro` "
                    + " SET `dt_saida`= '"+dataSaida+"' "
                    + " ,  st_registro = 0, st_ponto_aberto = 1, obs = 'Ponto fechado automaticamente. Horário não contabilizado.'"
                    + " , `data_ultima_atualizacao`= NOW(), `atualizado_por`= 'JAVINHA_SMS' "
                    + " WHERE `id_registro` = '"+idRegistro+"' AND `st_registro` = '1'";
            
            boolean b = stm.execute(updateSaida);
            
            mudaStatus(idServidor, 0);
            
            return true;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * Este método realiza a atualização de ponto/registro como saida 
     * @param idRegistro - ID de registro 
     * @param dataSaida - Data de Saída 
     * @return - Returna <i>TRUE</i> quando a operação não apresenta erro durantes
     * a execução; e <i>FALSE</i> quando ocorre algum problema durante a execução.
     */
    private boolean inserirComoSaida(int idRegistro, String dataSaida, int idServidor, String nsr, int idRelogio){
        try{            
            
            String obs = ", obs = NULL";
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();
            
            Statement stm = conn.createStatement();
            Statement stm1 = conn.createStatement();
            
            String justificado = "SELECT obs FROM `registro`"
                    + " WHERE id_registro = '"+idRegistro+"' and registro_justificado = '1' "
                    + "ORDER BY `dt_entrada` DESC";
            
            ResultSet rs = stm1.executeQuery(justificado);
            
            while(rs.next()){
                obs = "";
            }           
            
            String updateSaida = "UPDATE `registro` "
                    + " SET `dt_saida`= '"+dataSaida+"', st_registro = '0', st_ponto_aberto = 0 "
                    + " "+obs+", `data_ultima_atualizacao`= NOW(), `atualizado_por`= 'JAVINHA_SMS' "
                    + " , `nsr_saida` = '"+nsr+"', `idrelogio_saida` = '"+idRelogio+"' "
                    + " WHERE `id_registro` = '"+idRegistro+"' ";
            System.out.println(updateSaida);
            
            boolean b = stm.execute(updateSaida);
            
            mudaStatus(idServidor, 0);
            
            stm.close();
            conn.close();
            
            return true;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
    
     /**
     * Método muda o Status de Presente ou ausente para este servidor 
     * @param idServidor - ID de identificação do Servidor
     * @param novoStatus - Int Novo Status para o servidor
     * @return - Retorna <i>TRUE</i> caso as operações de atualização ocorra bem, 
     * ou retorna <i>FALSE</i> em caso a execuação de atualização ocorra erro.
     */
    private boolean mudaStatus(int idServidor, int novoStatus){
        try{
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.criarConexao();
            
            Statement stm = conn.createStatement();
                        
            String query = "UPDATE servidor SET status_servidor_presente = '"+novoStatus+"' WHERE id_servidor ='"+idServidor+"'";
            
            boolean b = stm.execute(query);
           
            stm.close();
            conn.close();
            
            return true;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
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
