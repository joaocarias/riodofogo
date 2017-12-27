package log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author joao.franca
 */
public class MeuLog {
    public static final int INFO = 1;
    public static final int ERRO = 2;
    public static final int FATAL = 3;
    public static final int WARN = 4;
    public static final int DEBUG = 5;
    
    private static final Logger meuLog_ = Logger.getLogger(MeuLog.class.getName());    
    
    /**
     * Método grava um log do sistema 
     * @param msg - Mensagem a ser gravada 
     * @param i - tipo de log pode ser: 1 - Informação; 2 - Erro; 3 - Fatal; 4 - Warn; 5 - Debug;
     */
    public static void gravaLog(String msg, int i){        
        PropertyConfigurator.configure("log.properties");
        switch(i){
            case 1:
                meuLog_.info(msg);
                break;
            case 2:
                meuLog_.error(msg);
                break;
            case 3:
                meuLog_.fatal(msg);
                break;
            case 4:
                meuLog_.warn(msg);
                break;
            case 5:
                meuLog_.debug(msg);
                break;
            default:
                meuLog_.error("[TIPO DE LOG DESCONHECIDO] "+msg);
                break;               
        }
    }
}
