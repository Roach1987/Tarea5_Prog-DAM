package tarea5;

import java.util.Scanner;


/**
 * 
 * @author Roach1987
 */
public class AplicacionCuentaBancaria {
    
    
    /**
     * Método principal, encargado de arrancar la aplicación, 
     * pedira los datos de titular y cuenta bancaria al usuario.
     * @param args 
     */
    public static void main (String[] args){
               // Declaramos las variables para obtenes los
        // datos del titular y numero de cuenta
        String titularValido = "";
        String cuentaValida = "";
        
        // Objeto con el que pediremos y recibiremos la entrada de parametros por el usuario.
        Scanner escanerEntrada = new Scanner(System.in);
        
        System.out.println("***********************************************************");
        System.out.println("** Hola, por favor introduzca los datos correspondientes a su cuenta bancaria. **");
// ********************************* Validaciones ************************************
        // Condición del bucle do while del titular
        boolean condicionTitular = false;
        
        System.out.println("***** Titular de la cuenta. *****");
        
        // Pedimos los datos al usuario, este metodo se encargara de validar y 
        // de pedir de nuevo la información si la introducida no es correcta
        do {
            // Obtenemos el titular de la cuenta y guardamos la 
            // información en una variable provisional.
            String titularProvisional = escanerEntrada.nextLine();
            
            String mensajeTitular = CuentaBancaria.compruebaTitular(titularProvisional);
            
            if(mensajeTitular.equals("OK")){
                condicionTitular = true;
                titularValido = titularProvisional;
            }else{
                System.out.println(mensajeTitular);
            }
        } while (!condicionTitular);

        System.out.println("***** Cuenta Corriente completa. *****");
        
        // Condición del bucle do while Cuenta Corriente.
        boolean condicionCuenta = false;
        
        // Obtenemos la Cuenta Corriente de Cliente.
        do {
            // Obtenemos la cuenta corriente y guardamos la 
            // información en una variable provisional.
            String cuentaProvisional = escanerEntrada.nextLine();
            
            // Eliminamos espacios en blanco dentro de la cuenta si los tuviera.
            String cuentaLimpia = CuentaBancaria.eliminarEspaciosGuiones(cuentaProvisional);
            
            // Validamos la cuenta que ha introducido el usuario.
            String mensajeCuenta = CuentaBancaria.validarCuentaCorrienteCliente(cuentaLimpia);
            
            if(mensajeCuenta.equals("OK")){
                condicionCuenta = true;
                cuentaValida = cuentaLimpia;
            }else{
                System.out.println(mensajeCuenta);
            }
        } while (!condicionCuenta);
// ***********************************************************************************
// ********************************* Menu aplicación *********************************
        // Creamos el objeto CuentaBancaria con la informacion obtenida.
        CuentaBancaria cuentaBancaria = new CuentaBancaria(titularValido, cuentaValida);
        
        // Realizamos la llamada al metodo que pedira los datos por consola.
        cuentaBancaria.comprobarCuenta(cuentaBancaria, escanerEntrada);
    }
}
