
package riodofogo.pis;

/**
 *
 * @author joao.franca
 */
public class Pis {
    private String pis;

    public Pis(String pis) {
        super();
        this.pis = pis;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }       
    
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Pis)){
            return false;
        }
        final Pis outro = (Pis) obj;
        return this.getPis().equals(outro.getPis());
    }
}
