package Ejercicio3;
import Metodos.ConexionDataBaseEjercicio3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal {

    public static final int OPCION_ANADE_PRODUCTO = 1;
    public static final int OPCION_MUESTRA_PRODUCTOS = 2;
    public static final int OPCION_ANADE_CLIENTE = 3;
    public static final int OPCION_MUESTRA_CLIENTES = 4;
    public static final int OPCION_COMPRA = 5;
    public static final int OPCION_MUESTRA_CARROS = 6;
    public static final int OPCION_MUESTRA_CARROS_PRECIO = 7;
    public static final int OPCION_SALIR = 8;
    public static final int IMPORTE_SUPERIOR_AL_INTRODUCIDO = 9;
    public static final int ANYADIR_COMPRA = 10;

    public static void mostrarMenu() {
        System.out.println("==================================================");
        System.out.println(" Chomon ONLINE ");
        System.out.println("==================================================");
        System.out.println("1.-  Añade producto");
        System.out.println("2.-  Muestra productos");
        System.out.println("3.-  Añade cliente");
        System.out.println("4.-  Muestra clientes");
        System.out.println("5.-  Realiza compra");
        System.out.println("6.-  Muestra carros");
        System.out.println("7.-  Muestra carros precio");
        System.out.println("8.-  Salir");
        System.out.println("9.-  mostrar productos con un importe superior al importe que introduzcas");
        System.out.println("10.-  anyadir compra");
        System.out.println("==================================================");
        System.out.print("Introduce una opción: ");
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Scanner sc;
        int opcion;
        ListaProductos miListaProductos = new ListaProductos();
        ListaPersonas miListaClientes = new ListaPersonas();
        ListaCarros miListaCarros = new ListaCarros();
        ConexionDataBaseEjercicio3 conexionDataBaseEjercicio3 = new ConexionDataBaseEjercicio3();

        Producto productoAux;
        Persona clienteAux;
        Carro carroAux;

        do {
            mostrarMenu();
            sc = new Scanner(System.in);
            opcion = sc.nextInt();
            switch (opcion) {
                case OPCION_ANADE_PRODUCTO: {
                    System.out.println("Alta de producto: ");
                    productoAux = new Producto();
                    productoAux.alta();
                    miListaProductos.anadirProducto(productoAux);
                    break;
                }
                case OPCION_MUESTRA_PRODUCTOS: {
                    System.out.println("Listado de productos: ");
                    miListaProductos.mostrarProductos();
                    break;
                }
                case OPCION_ANADE_CLIENTE : {
                    System.out.println("Alta de cliente: ");
                    clienteAux = new Persona();
                    clienteAux.alta();
                    miListaClientes.anadirPersona(clienteAux);
                    break;
                }
                case OPCION_MUESTRA_CLIENTES : {
                    System.out.println("Listado de personas: ");
                    miListaClientes.mostrarPersonas();
                    break;
                }
                case OPCION_COMPRA : {
                    System.out.println("Selecciona un cliente de la lista: ");
                    miListaClientes.mostrarPersonas();
                    int numero = sc.nextInt();
                    if (numero >= 0 && numero <= miListaClientes.numeroPersonas()) {
                        carroAux = new Carro();
                        carroAux.setCliente(miListaClientes.personaPosicionI(numero));
                        carroAux.compra(miListaProductos);
                        miListaCarros.anadirCarro(carroAux);
                    } else {
                        System.out.println("Introduce un número de producto válido");
                    }
                    break;
                }
                case OPCION_MUESTRA_CARROS : {
                    System.out.println("Carros dados de alta");
                    miListaCarros.mostrarCarros();
                    break;
                }
                case OPCION_MUESTRA_CARROS_PRECIO : {
                    System.out.println("Carros con información específica: ");
                    miListaCarros.mostrarCarrosPrecio();
                    break;
                }
                case OPCION_SALIR : {
                    System.out.println("Fin del programa. Recuerda pulsar ALT+F4 para cerrar la ventana");
                    break;
                }
                case IMPORTE_SUPERIOR_AL_INTRODUCIDO : {
                    System.out.print("introduce un importe para los productos y te mostrare los que sean" +
                            "superiores a ese importe: ");
                    double importeSeleccionado = sc.nextInt();

                    try (Connection connection = conexionDataBaseEjercicio3.connect()) {
                        String consultaSql = "select * from productos where precio > ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(consultaSql);
                        preparedStatement.setDouble(1, importeSeleccionado);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            String codigo = resultSet.getString(1);
                            String nombre = resultSet.getString(2);
                            String descripcion = resultSet.getString(3);
                            String marca = resultSet.getString(4);
                            double precio = resultSet.getDouble(5);
                            System.out.println(productoAux = new Producto(codigo, nombre, descripcion, marca, precio));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case ANYADIR_COMPRA : {
                    ListaProductos listaProductos2 = new ListaProductos();
                    System.out.println(listaProductos2);
                    Carro carro = new Carro();

                    carro.compra(listaProductos2);

                    System.out.println(listaProductos2.getLista());
                    break;
                }
                default : {
                    System.out.println("Introduce una opción válida");
                    break;
                }
            }
        } while (opcion != OPCION_SALIR);
    }
}