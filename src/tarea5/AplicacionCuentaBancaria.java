package tarea5;

/**
 * 
 * @author Roach1987
 */
public class AplicacionCuentaBancaria {
    
    
    /**
     * Método principal, encargado de arrancar la aplicación.
     * @param args 
     */
    public static void main (String[] args){
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        
        // Realizamos la llamada al metodo que pedira los datos por consola.
        cuentaBancaria.comprobarCuenta();
    }
}
