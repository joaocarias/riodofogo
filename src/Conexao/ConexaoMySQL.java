
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import riodofogo.Propriedades;

/**
 *
 * @author joao.franca
 */
public class ConexaoMySQL implements ConexaoInterface {
    private boolean status;  
    private String servidor;
    private String nomeBanco;
    private String url;
    private String usuario;
    private String senha;  
    private String driverName; 
    private int porta;
    private Connection conn;
    
    public ConexaoMySQL(){
        Propriedades p = new Propriedades();
        
        this.servidor = p.lerPropriedade("servidor_mysql");    //caminho do servidor do BD
        this.nomeBanco = p.lerPropriedade("banco_mysql");        //nome do seu banco de dados
        //é necessário usar ?zeroDateTimeBehavior=convertToNull para evitar o erro "java.sql.SQLException: Value '0000-00-00 00:00:00' can not be represented as java.sql.Date"
        this.url = "jdbc:mysql://" + this.servidor + "/" + this.nomeBanco+"?zeroDateTimeBehavior=convertToNull";
        this.usuario = p.lerPropriedade("usuario_mysql");        //nome de um usuário de seu BD      
        this.senha = p.lerPropriedade("senha_mysql");      //sua senha de acesso    
        this.driverName = "com.mysql.jdbc.Driver";          
        this.porta = Integer.parseInt(p.lerPropriedade("porta_mysql"));
    }
    
    @Override
    public boolean testarConexao() {
        try{
            if(this.conn != null){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public Connection criarCriacao() {
        this.conn = null;
        
        try {
           Class.forName(this.getDriverName()).newInstance();
           this.conn = DriverManager.getConnection(this.getUrl(), this.getUsuario(), this.getSenha());          
            
            //Testa sua conexão//  
            if (this.conn != null){
                this.setStatus(true);
                return conn;
            } else {
                this.setStatus(false);
                return null;
            }            
        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("O driver expecificado nao foi encontrado.");            
            System.err.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            System.err.println(e.getMessage());
            return null;        
        }catch( Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean disconectar() {
        try{
            this.conn.close();
            return true;
        }catch(Exception e){ 
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public int getPorta() {
        return this.porta;
    }

    @Override
    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    @Override
    public boolean getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String getServidor() {
        return this.servidor;
    }

    @Override
    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    @Override
    public String getNomeBanco() {
        return this.nomeBanco;
    }

    @Override
    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsuario() {
        return this.usuario;
    }

    @Override
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getSenha() {
        return this.senha;
    }

    @Override
    public void setSenha(String senha) {
        this.senha = senha;
    }    
}
