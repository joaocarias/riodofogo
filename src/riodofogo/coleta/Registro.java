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
public class Registro {
    private int id_registro;
    private int id_servidor;
    private String dt_entrada;
    private String dt_saida;
    private String st_registro;
    private String st_ponto_aberto;
    private String obs;
    private String horas_justificadas;
    private String registro_justificado;
    private String justificativa_aprovada;
    
    public Registro(int id_registro, int id_servidor, String dt_entrada, 
            String dt_saida, String st_registro, String st_ponto_aberto, 
            String obs, String horas_justificadas, 
            String registro_justificado, String justificativa_aprovada) {
        this.id_registro = id_registro;
        this.id_servidor = id_servidor;
        this.dt_entrada = dt_entrada;
        this.dt_saida = dt_saida;
        this.st_registro = st_registro;
        this.st_ponto_aberto = st_ponto_aberto;
        this.obs = obs;
        this.horas_justificadas = horas_justificadas;
        this.registro_justificado = registro_justificado;
        this.justificativa_aprovada = justificativa_aprovada;
    }

    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public int getId_servidor() {
        return id_servidor;
    }

    public void setId_servidor(int id_servidor) {
        this.id_servidor = id_servidor;
    }

    public String getDt_entrada() {
        return dt_entrada;
    }

    public void setDt_entrada(String dt_entrada) {
        this.dt_entrada = dt_entrada;
    }

    public String getDt_saida() {
        return dt_saida;
    }

    public void setDt_saida(String dt_saida) {
        this.dt_saida = dt_saida;
    }

    public String getSt_registro() {
        return st_registro;
    }

    public void setSt_registro(String st_registro) {
        this.st_registro = st_registro;
    }

    public String getSt_ponto_aberto() {
        return st_ponto_aberto;
    }

    public void setSt_ponto_aberto(String st_ponto_aberto) {
        this.st_ponto_aberto = st_ponto_aberto;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getHoras_justificadas() {
        return horas_justificadas;
    }

    public void setHoras_justificadas(String horas_justificadas) {
        this.horas_justificadas = horas_justificadas;
    }

    public String getRegistro_justificado() {
        return registro_justificado;
    }

    public void setRegistro_justificado(String registro_justificado) {
        this.registro_justificado = registro_justificado;
    }

    public String getJustificativa_aprovada() {
        return justificativa_aprovada;
    }

    public void setJustificativa_aprovada(String justificativa_aprovada) {
        this.justificativa_aprovada = justificativa_aprovada;
    }    
}
