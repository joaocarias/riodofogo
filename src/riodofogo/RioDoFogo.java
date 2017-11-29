package riodofogo;

import riodofogo.telas.Principal;

/**
 *
 * @author joao.franca
 */
public class RioDoFogo {
    public static void main(String[] args) {
        
        System.out.println("Iniciando Sistema de Coleta por Arquivo...");
        
        Principal p = new Principal();
        p.setVisible(true);;
        p.setLocationRelativeTo(null);
        p.setTitle("Sistema de Ponto Eletrôncio Rio do Fogo");
        
        
        
    }    
}
