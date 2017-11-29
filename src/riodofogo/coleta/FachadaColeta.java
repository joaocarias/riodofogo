
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
    
    
}
