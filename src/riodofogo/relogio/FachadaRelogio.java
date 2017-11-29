/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo.relogio;

import java.util.List;

/**
 *
 * @author joao.franca
 */
public class FachadaRelogio {
    public List<Relogio> getList(){
        RelogioDBInterface relogioDB = new RelogioDB();
        List<Relogio> listaRelogios = relogioDB.getList();
        
        return listaRelogios;
    }
}
