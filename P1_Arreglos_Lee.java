/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.p1_arreglos_lee;
import java.util.*;

/**
 *
 * @author keonj
 */
public class P1_Arreglos_Lee {

    public static void main(String[] args) {
        Persona[] personas = ingresarPersonas();

        Scanner scanner = new Scanner(System.in);

        System.out.println("--- MENÚ DE ORDENAMIENTO ---");
        System.out.println("1. Burbuja");
        System.out.println("2. Selección");
        System.out.println("3. Inserción");
        System.out.println("4. Shell");
        System.out.println("5. MergeSort");
        System.out.println("6. QuickSort");
        System.out.print("Seleccione el método de ordenamiento: ");
        int opcion = scanner.nextInt();

        Persona[] copia = Arrays.copyOf(personas, personas.length);
        long inicio = System.nanoTime();

        switch (opcion) {
            case 1:
                burbuja(copia);
                break;
            case 2:
                seleccion(copia);
                break;
            case 3:
                insercion(copia);
                break;
            case 4:
                shell(copia);
                break;
            case 5:
                mergeSort(copia, 0, copia.length - 1);
                break;
            case 6:
                quickSort(copia, 0, copia.length - 1);
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }

        long fin = System.nanoTime();
        System.out.println("Tiempo: " + (fin - inicio) / 1_000_000.0 + " ms");

        mostrarPersonasDescendente(copia);

        System.out.println("--- MENÚ DE BÚSQUEDA POR ID ---");
        System.out.println("1. Búsqueda Lineal");
        System.out.println("2. Búsqueda Binaria");
        System.out.print("Seleccione el tipo de búsqueda: ");
        int tipoBusqueda = scanner.nextInt();

        System.out.print("Ingrese el ID a buscar: ");
        int idBuscar = scanner.nextInt();

        Persona resultado = null;

        if (tipoBusqueda == 1) {
            resultado = busquedaLinealID(copia, idBuscar);
        } else if (tipoBusqueda == 2) {
            Arrays.sort(copia, Comparator.comparingInt(Persona::getId));
            resultado = busquedaBinariaID(copia, idBuscar);
        }

        if (resultado != null) {
            System.out.println("Persona encontrada: " + resultado);
        } else {
            System.out.println("No se encontró una persona con ese ID.");
        }

        ArchivoCSV.guardarPersonasCSV(personas, "personas.csv");
        System.out.println("Personas guardadas en 'personas.csv'.");
    }

    public static Persona[] ingresarPersonas() {
        Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    int cantidad;
    while (true) {
        System.out.print("Ingrese la cantidad de personas: ");
        String entrada = scanner.nextLine();
        try {
            cantidad = Integer.parseInt(entrada);
            if (cantidad > 0) break;
            else System.out.println("Debe ser mayor a 0.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    Persona[] personas = new Persona[cantidad];

    // Listas de nombres para generar aleatoriamente
    String[] nombres = {"Messi","Odette","Mishell","Gabriela","Keonjae","Joji","Carlos","Pedro","Micaela","Julian","Roberto","Alexa","Ana"};
    Set<Integer> idsGenerados = new HashSet<>();

    for (int i = 0; i < cantidad; i++) {
        String nombre = nombres[random.nextInt(nombres.length)];
        int edad = random.nextInt(101); // edad entre 0 y 100

        int id;
        do {
            id = random.nextInt(10000); // ID entre 0 y 9999
        } while (idsGenerados.contains(id));
        idsGenerados.add(id);

        personas[i] = new Persona(nombre, edad, id);
        System.out.println("Generada: " + personas[i]); // para verificar en consola
    }
    return personas;
    }

    public static void mostrarPersonasDescendente(Persona[] arr) {
        System.out.println("Personas ordenadas de mayor a menor edad:");
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.println(arr[i].getNombre() + " - Edad: " + arr[i].getEdad() + " y su Id es: " + arr[i].getId());
        }
    }

    public static void burbuja(Persona[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].getEdad() > arr[j + 1].getEdad()) {
                    Persona temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void seleccion(Persona[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].getEdad() < arr[min].getEdad()) {
                    min = j;
                }
            }
            Persona temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    public static void insercion(Persona[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Persona key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].getEdad() > key.getEdad()) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void shell(Persona[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Persona temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap].getEdad() > temp.getEdad(); j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    public static void mergeSort(Persona[] arr, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int medio = (izquierda + derecha) / 2;
            mergeSort(arr, izquierda, medio);
            mergeSort(arr, medio + 1, derecha);
            merge(arr, izquierda, medio, derecha);
        }
    }

    private static void merge(Persona[] arr, int izquierda, int medio, int derecha) {
        int n1 = medio - izquierda + 1;
        int n2 = derecha - medio;

        Persona[] L = new Persona[n1];
        Persona[] R = new Persona[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[izquierda + i];
        for (int j = 0; j < n2; j++) R[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = izquierda;
        while (i < n1 && j < n2) {
            if (L[i].getEdad() <= R[j].getEdad()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static void quickSort(Persona[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(Persona[] arr, int low, int high) {
        Persona pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].getEdad() < pivot.getEdad()) {
                i++;
                Persona temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Persona temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public static Persona busquedaLinealID(Persona[] personas, int id) {
        for (Persona p : personas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static Persona busquedaBinariaID(Persona[] personas, int id) {
        int izquierda = 0, derecha = personas.length - 1;
        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            if (personas[medio].getId() == id) {
                return personas[medio];
            } else if (personas[medio].getId() < id) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return null;
    }
}
    

