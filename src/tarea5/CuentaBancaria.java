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
    public static final String INGRESO = "+";
    // Operación retiro en cuenta
    public static final String RETIRO = "-";
    // Operación consulta en cuenta
    public static final String CONSULTA = "=";
    // Operacion cuenta corriente numero cuenta completo
    private final String NUMERO_CUENTA_COMPLETO = "Completo";
    // Operacion cuenta corriente entidad
    private final String NUMERO_ENTIDAD = "Entidad";
    // Operacion cuenta corriente oficina
    private final String NUMERO_OFICINA = "Oficina";
    // Operacion cuenta corriente numero de cuenta (10 digitos final)
    private final String NUMERO_CUENTA = "Cuenta";
    // Operacion cuenta corriente digitos de control
    private final String DIGITOS_CONTROL = "Digitos";
// ***********************************************************************************

// ********************************* Constructores. **********************************    
    // Constructor por defecto
    public CuentaBancaria() {
    }

    // Constructor con parametro titular y cuentaCorriente en el cual definimos el 
    // saldo actual, que por defecto sera 0
    public CuentaBancaria(String titular, String cuentaCorriente) {
        this.titular = titular;
        this.cuentaCorriente = cuentaCorriente;
        this.saldoActual = 0.0;
    }
// ***********************************************************************************    

    /**
     * Método principal que se expondra al usuario para que realice la comprobacion de su Cuenta corriente.
     * Tanto el titular como la cuenta corriente se pediran al usuario por consola.
     */
    public void comprobarCuenta() {
        // Declaramos las variables para obtenes los
        // datos del titular y numero de cuenta
        String TitularValido = "";
        String cuentaValida = "";
        
        // Objeto con el que pediremos y recibiremos la entrada de parametros por el usuario.
        Scanner escanerEntrada = new Scanner(System.in);
        
        System.out.println("***********************************************************");
        System.out.println("** Hola, por favor introduzca los datos correspondientes a su cuenta bancaria.");
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
            
            String mensajeTitular = compruebaTitular(titularProvisional);
            
            if(mensajeTitular.equals(VALIDACION_OK)){
                condicionTitular = true;
                TitularValido = titularProvisional;
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
            String cuentaLimpia = eliminarEspaciosGuiones(cuentaProvisional);
        
            // Validamos la cuenta que ha introducido el usuario.
            String mensajeCuenta = validarCuentaCorrienteCliente(cuentaLimpia);
            
            if(mensajeCuenta.equals(VALIDACION_OK)){
                condicionCuenta = true;
                cuentaValida = cuentaLimpia;
            }else{
                System.out.println(mensajeCuenta);
            }
        } while (!condicionCuenta);
// ***********************************************************************************
// ********************************* Menu aplicación *********************************
    // Creamos el objeto CuentaBancaria con la informacion obtenida.
        CuentaBancaria cuentaBancaria = new CuentaBancaria(TitularValido, cuentaValida);
    
    // Pintamos el menu de opciones.
        pintarOpcionesMenu(TitularValido);
            
    // Declaramos variable que controlara la ejecución del menu, si el 
    // usuario elije la opcion 10 el programa terminara la ejecución.
        boolean terminaEjecucion = false;
        
        do{
            // Capturamos la opción enviada por el usuario
            String opcionMenu = escanerEntrada.nextLine();
            
            // Implementamos el menu.
            menuImplementado(cuentaBancaria, opcionMenu, escanerEntrada, terminaEjecucion);
        }while(!terminaEjecucion);
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
// ***********************************************************************************

// ********************************* Metodos Utilitarios *****************************
   /**
     * Método utilitario, en el que validaremos que el String llegado sean todo
     * numeros.
     * @param cadena
     * @return (boolean) true si todos los caracteres de la cadena llegada son
     * numeros.
     */
    private static boolean validaNumeros(String cadena) {
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
     * Método utilitario que elimina los espacios en blanco y guiones (-) 
     * dentro de una cadena de caracteres, ya que se puede dar ambas casuisticas 
     * a la hora de enviar una los numeros de una cuenta corriente.
     * @param cadena
     * @return (String) cadena sin espacios.
     */
    private static String eliminarEspaciosGuiones(String cadena){
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
    private void pintarOpcionesMenu(String titular){

        // Comprobamos si viene informado el titular, si no viene informado significa que se ha elegido
        // una opción no valida en el menu de la aplicación.
        String mensajeBienvenida = (null != titular && !titular.isEmpty()) 
                ? "*****       Bienvenido      " + titular + "      ****************"
                : "************           Opciones validas          ****************";
        
        System.out.println("*****************************************************************");
        System.out.println(mensajeBienvenida);
        System.out.println("*****************************************************************");
        System.out.println("***** Dispone de las siguientes opciones:           *************");
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
    }
    
    /**
     * Método que ejecuta la opcion que el usuario elija.
     * se validara que las opciones (del 1 al 10) si no es ninguna de estas
     * enviara al usuario un mensaje de error y volvera a solicitar la opcion.
     * @param opcion 
     */
    private void menuImplementado(CuentaBancaria cuentaBancaria, String opcion, Scanner escanerEntrada,
            boolean terminaAplicacion){
        // Variable en la que se guardara la cantidad introducida por el usuario.
        String cantidadSaldoUsuario;       
        
        switch(opcion){
            case "1": // Ver el número de cuenta completo.
                operacionesCuentaCorriente(cuentaBancaria, NUMERO_CUENTA_COMPLETO);
                break;
            case "2": // Ver el titular de la cuenta.
                System.out.println("***** El titular de la cuenta es " + cuentaBancaria.getTitular() + " *****");
                break;
            case "3": // Ver el código de la entidad.
                operacionesCuentaCorriente(cuentaBancaria, NUMERO_ENTIDAD);
                break;
            case "4": // Ver el código de la oficina.
                operacionesCuentaCorriente(cuentaBancaria, NUMERO_OFICINA);
                break;
            case "5": // Ver el número de la cuenta.
                operacionesCuentaCorriente(cuentaBancaria, NUMERO_CUENTA);
                break;
            case "6": // Ver los dígitos de control de la cuenta.
                operacionesCuentaCorriente(cuentaBancaria, DIGITOS_CONTROL);
                break;
            case "7": // Realizar un ingreso.
                System.out.println("***** Inserte cantidad a ingresar en su cuenta *****");
                cantidadSaldoUsuario = escanerEntrada.nextLine();
                operacionesSaldo(cuentaBancaria, cantidadSaldoUsuario, opcion);
                break;
            case "8": // Retirar efectivo.
                System.out.println("***** Inserte cantidad a retirar de su cuenta *****");
                cantidadSaldoUsuario = escanerEntrada.nextLine();
                operacionesSaldo(cuentaBancaria, cantidadSaldoUsuario, opcion);
                break;
            case "9": // Consultar saldo.
                operacionesSaldo(cuentaBancaria, "", opcion);
                break;
            case "10": // Termina aplicación
                terminaAplicacion = true;
                break;
            default : // Cualquier otra casuistica no contemplada.
                System.out.println("Esta opción " + opcion + " no esta contemplada.");
                pintarOpcionesMenu("");
                break;
        }
    }
    
    /**
     * Método que realizara las operaciones sobre el saldo, estas operaciones seran
     * ingreso (+), retiro(-) y consulta(=)
     * @param cuentaBancaria
     * @param cantidad
     * @param operacion 
     */
    private void operacionesSaldo(CuentaBancaria cuentaBancaria, String cantidadParametro, String operacion){
        // Obtenemos el saldo actual de la cuenta.
        double saldoActualCuenta = cuentaBancaria.getSaldoActual();
        try{
            // Parsearemos la cantidad a tipo double
            double cantidadNumerica = Double.parseDouble(cantidadParametro);
            switch(operacion){
                case INGRESO: // Sumamos la cantidad llegada por parametro al saldo disponible.
                    cuentaBancaria.setSaldoActual(saldoActualCuenta + cantidadNumerica);
                    break;
                case RETIRO: // Sumamos la cantidad llegada por parametro al saldo disponible.
                    // Comprobamos que el saldo sea mayor que la cantidad a retirar, y que el saldo
                    // sea mayor que cero.
                    if(saldoActualCuenta == 0.0 || saldoActualCuenta < cantidadNumerica){
                        String mensaje = (saldoActualCuenta == 0.0) 
                                ? "El saldo actual es 0?, no puede realizar ningun retiro de dinero."
                                : "El saldo actual es de " + saldoActual + "?, y la cantidad a retirar es mayor " + "\n" 
                                    + cantidadParametro + "?, no se puede realizar la operación solicitada.";
                        System.out.println(mensaje);
                    }else{
                        cuentaBancaria.setSaldoActual(saldoActualCuenta - cantidadNumerica);
                    }
                    break;
                case CONSULTA: // Mostamos al usuario el saldo que tiene disponible.
                    System.out.println("El saldo actual de su cuenta es " + saldoActualCuenta + "?.");
                    break;
            }
        }catch(NumberFormatException ex){
            // Si la cantidad introducida por el usuario no es un numero, lanzara una excepción.
            System.out.println("La cantidad introducida " + cantidadParametro + " debe ser un numero.");
        }
    }
    
    /**
     * Método que realizara las operaciones sobre el numero de cuenta bancaria.
     * @param cuentaBancaria
     * @param operacion 
     */
    private void operacionesCuentaCorriente(CuentaBancaria cuentaBancaria, String operacion){
    
        switch(operacion){
            case NUMERO_CUENTA_COMPLETO :
                
                break;
            case NUMERO_ENTIDAD :
                
                break;
            case NUMERO_OFICINA :
                
                break;
            case NUMERO_CUENTA :
                
                break;
            case DIGITOS_CONTROL :
                
                break;
        }
    }
// ***********************************************************************************
}
