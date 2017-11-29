
package riodofogo.coleta;

/**
 *
 * @author joao.franca
 */
public interface ColetaDBInterface {
    public int getIdServidorPorPis(String pis);
    public boolean gravarColeta(int idServidor, int idRelogio, Coleta coleta);
}
