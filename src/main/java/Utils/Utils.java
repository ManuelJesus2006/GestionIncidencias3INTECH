package Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    public static final String RUTA_PROP = "application.properties";
    public static String leerUsuarioBaseDatos(){
        Properties prop = new Properties();
        try{
            prop.load(new FileReader(RUTA_PROP));
            return prop.getProperty("usuarioBaseDatos", "root");
        }catch (IOException e){
            return "";
        }
    }

    public static String leerContraBaseDatos(){
        Properties prop = new Properties();
        try{
            prop.load(new FileReader(RUTA_PROP));
            return prop.getProperty("contraBaseDatos", "root");
        }catch (IOException e){
            return "";
        }
    }
}
