
package riodofogo.relogio;

/**
 *
 * @author joao.franca
 */
public class Relogio {
    private int idRelogio;
    private String nomeRelogio;
    private int idUnidade;
    private String nomeUnidade;

    public Relogio(int idRelogio, String nomeRelogio, int idUnidade, String nomeUnidade) {
        this.idRelogio = idRelogio;
        this.nomeRelogio = nomeRelogio;
        this.idUnidade = idUnidade;
        this.nomeUnidade = nomeUnidade;
    }

    public int getIdRelogio() {
        return idRelogio;
    }

    public void setIdRelogio(int idRelogio) {
        this.idRelogio = idRelogio;
    }

    public String getNomeRelogio() {
        return nomeRelogio;
    }

    public void setNomeRelogio(String nomeRelogio) {
        this.nomeRelogio = nomeRelogio;
    }

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }
}
