/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo.coleta;

/**
 *
 * @author joao.franca
 */
public class Coleta {
    private String nsr;
    private String data;
    private String hora;
    private String pis;

    public Coleta(String nsr, String data, String hora, String pis) {
        this.nsr = nsr;
        this.data = data;
        this.hora = hora;
        this.pis = pis;
    }

    public String getNsr() {
        return nsr;
    }

    public void setNsr(String nsr) {
        this.nsr = nsr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }
}
