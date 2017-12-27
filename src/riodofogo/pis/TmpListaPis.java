/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riodofogo.pis;

import java.util.ArrayList;
import java.util.List;
import log.MeuLog;

/**
 *
 * @author joao.franca
 */
public class TmpListaPis {
    private static List<Pis> lista = new ArrayList<>();
    
    public static void adicionaPis(Pis pis){
        if(lista.contains(pis)){
           
        }else{
            lista.add(pis);
            MeuLog.gravaLog("PIS NÃO ENCONTRADO: "+pis.getPis(), MeuLog.INFO);
        }
    }
    
    public static int getTamanho(){
        return lista.size();
    }
}
