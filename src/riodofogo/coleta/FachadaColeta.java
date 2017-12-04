
package riodofogo.coleta;

/**
 *
 * @author joao.franca
 */
public class FachadaColeta {
    public int getIdServidorPorPis(String pis){
        ColetaDBInterface c = new ColetaDB();
        return c.getIdServidorPorPis(pis);        
    }
    
    public boolean gravarColeta(int idServidor, int idRelogio, Coleta coleta){
        ColetaDBInterface c = new ColetaDB();
        return c.gravarColeta(idServidor, idRelogio, coleta);
    }
    
    
}
