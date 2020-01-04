package tarea5;

import java.util.Scanner;

/**
 *
 * @author Roach1987
 */
public class CuentaBancaria {

// ******************** Declaración de Atributos y Constantes. ***********************
    // Titular de la cuenta.
    private String titular;
    // Cuenta corriente del titular, es de tipo String, para poder trabajar mejor con este atributo.
    private String cuentaCorriente;
    // Saldo con el que cuenta el titular.
    private double saldoActual;
    // Numero maximo de caracteres permitidos para el titular de la cuenta.
    public static final int MAXIMO_CARACTERES = 50;
    // Numero total caracteres Cuenta corriente
    public static final int TOTAL_CUENTA = 20;
    // Mensaje Validaciones correctas
    public static final String VALIDACION_OK = "OK";
    // Operación ingreso en cuenta
    public static final String INGRESO = "INGRESO";
    // Operación retiro en cuenta
    public static final String RETIRO = "RETIRO";
    // Operación consulta en cuenta
    public static final String CONSULTA = "CONSULTA";
    // Operacion cuenta corriente numero cuenta completo
    public static final String NUMERO_CUENTA_COMPLETO = "Completo";
    // Operacion cuenta corriente entidad
    public static final String NUMERO_ENTIDAD = "Entidad";
    // Operacion cuenta corriente oficina
    public static final String NUMERO_OFICINA = "Oficina";
    // Operacion cuenta corriente numero de cuenta (10 digitos final)
    public static final String NUMERO_CUENTA = "Cuenta";
    // Operacion cuenta corriente digitos de control
    public static final String DIGITOS_CONTROL = "Digitos";
    // Simbolo separador cuenta.
    public static final String SEPARADOR_CUENTA = "-";
// ***********************************************************************************
    
// ************* Declaración de variables Globales de la aplicación. *****************
    // Declaramos un booleano con el que controlaremos el mensaje de siguiente operación
    private boolean siguienteOperacion;
    
    // Declaramos variable que controlara la ejecución del menu, si el 
    // usuario elije la opcion 10 el programa terminara la ejecución.
    private boolean terminaEjecucion;
// ***********************************************************************************

// ********************************* Constructores. **********************************    
    // Constructor por defecto
    public CuentaBancaria() {
    }

    // Constructor con parametro titular y cuentaCorriente en el cual definimos el 
    // saldo actual, que por defecto sera 0.
    // Tambien incializamos los booleanos que se encargaran de detener la aplicación y mostrar mensaje
    // de siguiente ejecución, para poder controlarlos globalmente en la aplicación.
    public CuentaBancaria(String titular, String cuentaCorriente) {
        this.titular = titular;
        this.cuentaCorriente = cuentaCorriente;
        this.saldoActual = 0.0;
        this.terminaEjecucion = false;
        this.siguienteOperacion = true;
    }
// ***********************************************************************************    

    /**
     * Método principal que se expondra al usuario para que realice la comprobacion de su Cuenta corriente.
     * Tanto el titular como la cuenta corriente se pediran al usuario por consola.
     * @param cuentaBancaria
     * @param escanerEntrada
     */
    public void comprobarCuenta(CuentaBancaria cuentaBancaria, Scanner escanerEntrada) {
            
        // Pintamos el menu de opciones.
        pintarOpcionesMenu(cuentaBancaria.isSiguienteOperacion());
                    
        do{
            // Capturamos la opción enviada por el usuario
            String opcionMenu = escanerEntrada.nextLine();
            
            // Implementamos el menu.
            menuImplementado(cuentaBancaria, opcionMenu, escanerEntrada);
            
            if(!cuentaBancaria.isTerminaEjecucion() && cuentaBancaria.isSiguienteOperacion()){
                System.out.println("");
                System.out.println("*****************************************************************");
                System.out.println("*******   ¡Indique la siguiente operación a realizar!   *********");
                System.out.println("*****************************************************************");
                System.out.println("");
            }
            
            // Comprobamos si el booleano de siguiente operación esta a false, esto ocurrira
            // si el usuario elige una opcion no contemplada en el menu de la aplicación,
            // no pintara en esa vuelta de bucle, pero si que debera pintar en las siguietes,
            // por lo que setearemos este a true.
            if(!cuentaBancaria.isSiguienteOperacion())
                cuentaBancaria.setSiguienteOperacion(true);
        }while(!cuentaBancaria.isTerminaEjecucion());
// ***********************************************************************************
    }
    
// ********************* Metodos control cuenta corriente y titular ******************
    /**
     * Método que comprueba que la longitud del nombre del titular no sobrepase
     * los 50 carateres.
     * @param titularProvisional
     * @return (String) Mensaje de la operación.
     */
    public static String compruebaTitular(String titularProvisional) {
        String mensajeTitular = "";
        // se comprueba que el numero de caracteres sea menor o igual
        // que los caracteres permitidos.
        if (titularProvisional.length() <= MAXIMO_CARACTERES) {
            mensajeTitular = "OK";
        } else {
            mensajeTitular = "El titular no puede superar los " + MAXIMO_CARACTERES + " caracteres.";
            mensajeTitular += " vuelva a intentarlo.";
        }
        return mensajeTitular;
    }

    /**
     * Método que valida el numero de la cuenta que se recibe por parametro.
     * Devolvera true si el numero de cuenta introducido es valido, para ello comprobara
     * cada uno de los componentes de la cuenta, incluido las digitos de control.
     * @param cuentaProvisional
     * @return (String) Mensaje de la operación.
     */
    public static String validarCuentaCorrienteCliente(String cuentaProvisional) {
        String mensajeCuenta = "";

        // Declaramos un booleano que comprobara que cada componente de la cuenta
        // corriente sea correcto.
        boolean validaComponente = true;
       
        // Declaramos una cadena con la Entidad y la oficina, para validar los 
        // digitos de control
        String controlEntidadSucursal;

        // variable para en la que guardaremos el primer digito de control
        int digitoEntidadSucursal;

        // Declaramos una cadena con el numero de cuenta, para validar los 
        // digitos de control
        String controlNumeroCuenta;

        // variable para en la que guardaremos el segundo digito de control
        int digitoNumeroCuenta;

        // Validamos que el numero de cuenta tenga los caracteres que tiene que tener.
        if (cuentaProvisional.length() != TOTAL_CUENTA) {
            mensajeCuenta = "El numero de cuenta debe tener 20 digitos!";
        } else {
            // Declaramos una variable auxiliar que se sobreescribira con 
            // el trozo de la cuenta corriente correspondiente a los 4 componentes
            // de la Cuenta Corriente
            String cadenaAuxiliar = "";

            // Recorreremos el bucle 4 veces cada vuelta corresponde a un trozo de 
            // la cuenta corriente (Entidad, Sucursal, Digito de Control y Numero Cuenta)
            for (int i = 0; i < 4; i++) {
                if (validaComponente) {
                    switch (i) {
                        case 0: // Entidad
                            cadenaAuxiliar = cuentaProvisional.substring(0, 4);
                            if (!validaNumeros(cadenaAuxiliar)) {
                                mensajeCuenta = "Los digitos de la entidad deben de ser numericos.";
                                validaComponente = false;
                            }
                            break;
                        case 1: // Sucursal
                            cadenaAuxiliar = cuentaProvisional.substring(4, 8);
                            if (!validaNumeros(cadenaAuxiliar)) {
                                mensajeCuenta = "Los digitos de la sucursal deben de ser numericos.";
                                validaComponente = false;
                            }
                            break;
                        case 2: // Digito de control
                            cadenaAuxiliar = cuentaProvisional.substring(8, 10);
                            if (!validaNumeros(cadenaAuxiliar)) {
                                mensajeCuenta = "Los digitos del control deben de ser numericos.";
                                validaComponente = false;
                            }
// *********************************** Primer Digito de Control **********************************************
                            // Concatenamos dos ceros al principio, para que sumen 10 digitos
                            // necesario para obtener el primer digito de control
                            controlEntidadSucursal = "00".concat(cuentaProvisional.substring(0, 8));
                            digitoEntidadSucursal = obtenerDigitoControl(controlEntidadSucursal);
// *********************************** Segundo Digito de Control *********************************************
                            // obtenemos el numero de cuenta
                            controlNumeroCuenta = cuentaProvisional.substring(10, 20);
                            digitoNumeroCuenta = obtenerDigitoControl(controlNumeroCuenta);
// *********************************** Digito de Control resultado *******************************************
                            // obtenemos el digito de control completo, que es el resultado
                            // de concatenar el primer digito con el segundo.
                            String digitoControlResultado = String.valueOf(digitoEntidadSucursal).concat(String.valueOf(digitoNumeroCuenta));
// ***********************************************************************************************************                                
                            // Comprobamos que el digito de control resultante sea valido
                            if (!digitoControlResultado.equals(cadenaAuxiliar)) {
                                mensajeCuenta = "Los digitos del control no son validos";
                                validaComponente = false;
                            }
                            break;
                        case 3: // Numero de cuenta
                            cadenaAuxiliar = cuentaProvisional.substring(10, 20);
                            if (!validaNumeros(cadenaAuxiliar)) {
                                mensajeCuenta = "Los digitos del numero de cuenta deben de ser numericos.";
                                validaComponente = false;
                            }
                            break;
                    }    
                } else {
                    // Si algun componente de la cuenta no es valido, se rompe el bucle y  se
                    // vuelve a solicitar el numero de cuenta al usuario.
                    break;
                }
            }
            mensajeCuenta = (validaComponente) ? VALIDACION_OK : mensajeCuenta;
        }
        return mensajeCuenta;
    }

    /**
     * Método que comprueba la cuenta llegada por parametro y devuelve el digito de control
     * para un correcto uso del metodo se debe enviar cada parte del numero de cuenta
     * en la primera invocación enviar los numeros de entidad y sucursal concatenados con 2 ceros 
     * al principio, y luego el numero de cuenta, para asi obtener los dos digitos de control a comprobar.
     * @param cuenta
     * @return (int) digito de control
     */
    public static int obtenerDigitoControl(String cuenta) {
        int resultado = 0;
        int[] factores = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        int longitudCuenta = cuenta.length();
        int digito;

        // Recorremos el numero de cuenta.
        for (int i = 0; i < longitudCuenta; i++) {
            // Obtenemos el caracter actual de la cuenta.
            String caracterActual = String.valueOf(cuenta.charAt(i));
            // Parseamos el valor del caracter actual a entero. 
            digito = Integer.parseInt(caracterActual);
            // Guardamos el caracter parseado.
            resultado += digito * factores[i];
        }
        resultado = 11 - (resultado % 11);
        
        // Comprobamos que el modulo de la operacion del digito, y luego se le restara
        // el propio valor del digito, y se comprobara que sea
        // 11 en cuyo caso se asignara el valor 0, 10 en este caso se asignara el valor 1
        // u otro resultado en cuyo caso se pondra su valor (0 al 9).
        if(resultado == 11){
            resultado = 0;
        }else if(resultado == 10){
            resultado = 1;
        }

        return resultado;
    }
// ***********************************************************************************

// ****************************** Metodos Getters y Setters **************************
// ToDo Solo ofreceremos la opción de obtener los datos del titular y la cuenta corriente
// pero no asi la opción de poder sobreescribir los datos de estos.
// caso contrario para con
    public String getTitular() {
        return titular;
    }

    public String getCuentaCorriente() {
        return cuentaCorriente;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    } 

    public boolean isSiguienteOperacion() {
        return siguienteOperacion;
    }

    public void setSiguienteOperacion(boolean siguienteOperacion) {
        this.siguienteOperacion = siguienteOperacion;
    }

    public boolean isTerminaEjecucion() {
        return terminaEjecucion;
    }

    public void setTerminaEjecucion(boolean terminaEjecucion) {
        this.terminaEjecucion = terminaEjecucion;
    }
// ***********************************************************************************

// ********************************* Metodos Utilitarios *****************************
   /**
     * Método utilitario, en el que validaremos que el String llegado sean todo
     * numeros.
     * @param cadena
     * @return (boolean) true si todos los caracteres de la cadena llegada son
     * numeros.
     */
    public static boolean validaNumeros(String cadena) {
        boolean resultado = true;
        int longitud = cadena.length();

        // Recorremos la cadena llegada
        for (int i = 0; i < longitud; i++) {
            // Comprobamos que el caracter es un numero.
            if (!Character.isDigit(cadena.charAt(i))) {
                resultado = false;
                break;
            }
        }
        return resultado;
    }
    
    /**
     * Método utilitario que comprueba si una cantidad es cero(0) o inicia en negativo (-)
     * @param cantidad
     * @return (boolean) true si la cantidad es cero o negativa.
     */
    private boolean compruebaCantidad(String cantidad){
        return (cantidad.equals("0") || cantidad.equals("0.0") || cantidad.startsWith("-"));
    }
    /**
     * Método utilitario que elimina los espacios en blanco y guiones (-) 
     * dentro de una cadena de caracteres, ya que se puede dar ambas casuisticas 
     * a la hora de enviar una los numeros de una cuenta corriente.
     * @param cadena
     * @return (String) cadena sin espacios.
     */
    public static String eliminarEspaciosGuiones(String cadena){
        int longitud = cadena.length();
        StringBuilder constructorCadenas = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            if (cadena.charAt(i) != ' ' && cadena.charAt(i) != '-')
            constructorCadenas.append(cadena.charAt(i));
        }
        return constructorCadenas.toString();
    }
    
   /**
     * Método que pinta en consola las opciones del menu de la aplicación.
     */
    private void pintarOpcionesMenu(boolean siguienteOperacion){

        // Comprobamos si viene informado el titular, si no viene informado significa que se ha elegido
        // una opción no valida en el menu de la aplicación.
        String mensajeBienvenida = (siguienteOperacion) 
                ? "************              Bienvenido             ****************"
                : "************           Opciones validas          ****************";
        
        System.out.println("*****************************************************************");
        System.out.println(mensajeBienvenida);
        System.out.println("*****************************************************************");
        System.out.println("*****     Dispone de las siguientes opciones.       *************");
        System.out.println("*****************************************************************");
        System.out.println("***** (1) Ver el número de cuenta completo.         *************");
        System.out.println("***** (2) Ver el titular de la cuenta.              *************");
        System.out.println("***** (3) Ver el código de la entidad.              *************");
        System.out.println("***** (4) Ver el código de la oficina.              *************");
        System.out.println("***** (5) Ver el número de la cuenta.               *************");
        System.out.println("***** (6) Ver los dígitos de control de la cuenta.  *************");
        System.out.println("***** (7) Realizar un ingreso.                      *************");
        System.out.println("***** (8) Retirar efectivo.                         *************");
        System.out.println("***** (9) Consultar saldo.                          *************");
        System.out.println("**** (10) Salir de la aplicación.                   *************");
        System.out.println("*****************************************************************");
        System.out.println("**** ¡Seleccione unicamente el numero de la opcion deseada! *****");
        System.out.println("**********    ¿Que operación desea realizar?     ****************");
        System.out.println("*****************************************************************");
    }
    
    /**
     * Método que ejecuta la opcion que el usuario elija.
     * se validara que las opciones (del 1 al 10) si no es ninguna de estas
     * enviara al usuario un mensaje de error y volvera a solicitar la opcion.
     * @param cuentaBancaria
     * @param opcion
     * @param escanerEntrada
     * @param terminaAplicacion 
     */
    private void menuImplementado(CuentaBancaria cuentaBancaria, String opcion, Scanner escanerEntrada){
        // Variable en la que se guardara la cantidad introducida por el usuario.
        String cantidadSaldoUsuario;
        
        String mensajeOperacionCuenta;
        System.out.println("******************************************************");
        switch(opcion){
            case "1": // Ver el número de cuenta completo.
                mensajeOperacionCuenta = operacionesCuentaCorriente(cuentaBancaria.getCuentaCorriente(), NUMERO_CUENTA_COMPLETO);
                System.out.println("********** Su numero de cuenta completo CCC **********");
                System.out.println("**********     ".concat(mensajeOperacionCuenta).concat("      **********"));
                System.out.println("******************************************************");
                break;
            case "2": // Ver el titular de la cuenta.
                System.out.println("***** El titular de la cuenta es " + cuentaBancaria.getTitular() + " *****");
                System.out.println("******************************************************");
                break;
            case "3": // Ver el código de la entidad.
                mensajeOperacionCuenta = operacionesCuentaCorriente(cuentaBancaria.getCuentaCorriente(), NUMERO_ENTIDAD);
                System.out.println("***************** Su numero entidad ******************");
                System.out.println("*********************** ".concat(mensajeOperacionCuenta).concat(" *************************"));
                System.out.println("******************************************************");
                break;
            case "4": // Ver el código de la oficina.
                mensajeOperacionCuenta = operacionesCuentaCorriente(cuentaBancaria.getCuentaCorriente(), NUMERO_OFICINA);
                System.out.println("***************** Su numero oficina ******************");
                System.out.println("*********************** ".concat(mensajeOperacionCuenta).concat(" *************************"));
                System.out.println("******************************************************");
                break;
            case "5": // Ver el número de la cuenta.
                mensajeOperacionCuenta = operacionesCuentaCorriente(cuentaBancaria.getCuentaCorriente(), NUMERO_CUENTA);
                System.out.println("**************** Su numero de cuenta *****************");
                System.out.println("******************** ".concat(mensajeOperacionCuenta).concat(" **********************"));
                System.out.println("******************************************************");
                break;
            case "6": // Ver los dígitos de control de la cuenta.
                mensajeOperacionCuenta = operacionesCuentaCorriente(cuentaBancaria.getCuentaCorriente(), DIGITOS_CONTROL);
                System.out.println("******** Los digitos de control de su cuenta *********");
                System.out.println("************************* ".concat(mensajeOperacionCuenta).concat(" *************************"));
                System.out.println("******************************************************");
                break;
            case "7": // Realizar un ingreso.
                System.out.println("***** Inserte cantidad a ingresar en su cuenta *****");
                cantidadSaldoUsuario = escanerEntrada.nextLine();
                operacionesSaldo(cuentaBancaria, cantidadSaldoUsuario, INGRESO);
                break;
            case "8": // Retirar efectivo.
                // Si la cuenta actualmente esta en saldo 0 no se podra realizar ningun retiro.
                if(cuentaBancaria.getSaldoActual() == 0){
                    System.out.println("**** El saldo actual es 0.0, no puede realizar ningun retiro. ***");
                }else{
                    System.out.println("***** Inserte cantidad a retirar de su cuenta *****");
                    cantidadSaldoUsuario = escanerEntrada.nextLine();
                    operacionesSaldo(cuentaBancaria, cantidadSaldoUsuario, RETIRO);
                }
                break;
            case "9": // Consultar saldo.
                operacionesSaldo(cuentaBancaria, "", CONSULTA);
                break;
            case "10": // Termina aplicación
                cuentaBancaria.setTerminaEjecucion(true);
                System.out.println("*********** Adios "+ cuentaBancaria.getTitular() + ", hasta pronto **********");
                System.out.println("******************************************************");
                break;
            default : // Cualquier otra casuistica no contemplada.
                cuentaBancaria.setSiguienteOperacion(false);
                System.out.println("** Esta opción (" + opcion + ") no esta contemplada.**");
                System.out.println("******************************************************");
                pintarOpcionesMenu(cuentaBancaria.isSiguienteOperacion());
                break;
        }
    }
    
    /**
     * Método que realizara las operaciones sobre el saldo, estas operaciones seran
     * ingreso (+), retiro(-) y consulta(=)
     * @param cuentaBancaria
     * @param cantidadParametro
     * @param operacionParametro 
     */
    public void operacionesSaldo(CuentaBancaria cuentaBancaria, String cantidadParametro, String operacionParametro){
        System.out.println("******************************************************");
        // Convertimos la operación llegada a mayusculas
        String operacion = operacionParametro.toUpperCase();
        
        // Realizamos la comprobación de que la operacion llegada sea una de las operaciones validas.
        // y el parametro no este vacio.
        if(operacion.equals(CONSULTA) || operacion.equals(INGRESO) || operacion.equals(RETIRO) || !operacion.isEmpty()){
            // Obtenemos el saldo actual de la cuenta.
            double saldoActualCuenta = cuentaBancaria.getSaldoActual();

            //Declaramos la variable en la que guardaremos la cantidad parseada de String a double
            double cantidadNumerica = 0.0;

            try{
                // Parsearemos la cantidad a tipo double
                if(!operacion.equals(CONSULTA)){
                    cantidadNumerica = Double.parseDouble(cantidadParametro);
                }

                switch(operacion){
                    case INGRESO: // Sumamos la cantidad llegada por parametro al saldo disponible.
                        String mensaje;
                        // Controlamos que la cantidad introducida no sea 0 ni un numero negativo.
                        if(compruebaCantidad(cantidadParametro)){
                            mensaje = "********* La cantidad introducida ha sido " + cantidadParametro + " no ha surgido ningun efecto ********";
                        }else{
                            mensaje = "********* " + cantidadParametro + " ingresados correctamente. ***********";
                            cuentaBancaria.setSaldoActual(saldoActualCuenta + cantidadNumerica);
                        }

                        System.out.println(mensaje);
                        break;
                    case RETIRO: // Sumamos la cantidad llegada por parametro al saldo disponible.
                        // Comprobamos que la cantidad a retirar no sea cero o un numero negativo.
                        if(compruebaCantidad(cantidadParametro)){
                            System.out.println("***** Ha introducido " + cantidadParametro + ", la cantidad no puede ser 0 ni negativa. ***********");
                        }else{
                            // Comprobamos que el saldo sea mayor que la cantidad a retirar, y que el saldo
                            // sea mayor que cero.
                            if(saldoActualCuenta < cantidadNumerica){
                                System.out.println("El saldo actual es de " + saldoActualCuenta + ", y la cantidad a retirar es " + "\n" + cantidadParametro
                                             + ", no se puede realizar la operación solicitada.");
                            }else{
                                cuentaBancaria.setSaldoActual(saldoActualCuenta - cantidadNumerica);
                                System.out.println("********* " + cantidadParametro + " retirados correctamente. ***********");
                            }
                        }
                        break;
                    case CONSULTA: // Mostamos al usuario el saldo que tiene disponible.
                        System.out.println("El saldo actual de su cuenta es " + saldoActualCuenta);
                        break;
                }
            }catch(NumberFormatException ex){
                // Si la cantidad introducida por el usuario no es un numero, lanzara una excepción.
                System.out.println("La cantidad introducida " + cantidadParametro + " debe ser un numero.");
            }
        }else{
            System.out.println("********** La operación (" + operacion + ") no esta disponible, las operaciones disponibles son: **********" + "\n");
            System.out.println("Ingreso, Retiro y Consulta");
        }
        System.out.println("******************************************************");
    }

    /**
     * Método que realizara las operaciones sobre el numero de cuenta bancaria.
     * las operaciones disponibles son:
     * COMPLETO: para devolver el numero de cuenta completo con formaro 000-0000-00-000000000
     * ENTIDAD: devuelve el numero de entidad.
     * OFICINA: devuelve el numero de oficina.
     * CUENTA: devuelve los 10 digitos del numero de cuenta
     * DIGITOS: devulve los 2 digitos de control de la cuenta.
     * 
     * @param cuentaCorriente
     * @param operacion 
     * @return (String) resultado de la operación solicitada.
     */
    public static String operacionesCuentaCorriente(String cuentaCorriente, String operacion){
        String resultado = "";
        // Entidad
        String entidad = cuentaCorriente.substring(0, 4);
        
        // Oficina
        String oficina = cuentaCorriente.substring(4, 8);
        
        // Digitos de control
        String digitosControl = cuentaCorriente.substring(8, 10);
        
        // Numero de cuenta
        String numeroCuenta = cuentaCorriente.substring(10, 20);
                
        switch(operacion){
            case NUMERO_CUENTA_COMPLETO :
                resultado = (entidad).concat(SEPARADOR_CUENTA).concat(oficina).concat(SEPARADOR_CUENTA)
                    .concat(digitosControl).concat(SEPARADOR_CUENTA).concat(numeroCuenta);
                break;
            case NUMERO_ENTIDAD :
                resultado = entidad;
                break;
            case NUMERO_OFICINA :
                resultado = oficina;
                break;
            case NUMERO_CUENTA :
                resultado = numeroCuenta;
                break;
            case DIGITOS_CONTROL :
                resultado = digitosControl;
                break;
        }        
        return resultado;
    }
// ***********************************************************************************
}
