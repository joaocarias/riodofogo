
package Conexao;

import java.sql.Connection;

/**
 *
 * @author joao.franca
 */
public interface ConexaoInterface {
    public boolean testarConexao();
    public Connection criarCriacao();
    public boolean disconectar();
    public int getPorta();
    public void setPorta(int porta);
    public boolean getStatus();
    public void setStatus(boolean status);
    public String getServidor();
    public void setServidor(String servidor);
    public String getNomeBanco();
    public void setNomeBanco(String nomeBanco);
    public String getUrl();
    public void setUrl(String url);
    public String getUsuario();
    public void setUsuario(String usuario);
    public String getSenha();
    public void setSenha(String senha);  
}
