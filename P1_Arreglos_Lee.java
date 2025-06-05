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
        int cantidad = 1000;
        Persona[] personas = ingresarPersonas();

        Persona[] copiaBurbuja = Arrays.copyOf(personas, personas.length);
        long inicioBurbuja = System.nanoTime();
        burbuja(copiaBurbuja);
        long finBurbuja = System.nanoTime();
        System.out.println("Burbuja: " + (finBurbuja - inicioBurbuja) / 1_000_000.0 + " ms");

        Persona[] copiaSeleccion = Arrays.copyOf(personas, personas.length);
        long inicioSeleccion = System.nanoTime();
        seleccion(copiaSeleccion);
        long finSeleccion = System.nanoTime();
        System.out.println("Selección: " + (finSeleccion - inicioSeleccion) / 1_000_000.0 + " ms");

        Persona[] copiaInsercion = Arrays.copyOf(personas, personas.length);
        long inicioInsercion = System.nanoTime();
        insercion(copiaInsercion);
        long finInsercion = System.nanoTime();
        System.out.println("Inserción: " + (finInsercion - inicioInsercion) / 1_000_000.0 + " ms");

        Persona[] copiaShell = Arrays.copyOf(personas, personas.length);
        long inicioShell = System.nanoTime();
        shell(copiaShell);
        long finShell = System.nanoTime();
        System.out.println("Shell: " + (finShell - inicioShell) / 1_000_000.0 + " ms");

        Persona[] copiaMerge = Arrays.copyOf(personas, personas.length);
        long inicioMerge = System.nanoTime();
        mergeSort(copiaMerge, 0, copiaMerge.length - 1);
        long finMerge = System.nanoTime();
        System.out.println("MergeSort: " + (finMerge - inicioMerge) / 1_000_000.0 + " ms");

        Persona[] copiaQuick = Arrays.copyOf(personas, personas.length);
        long inicioQuick = System.nanoTime();
        quickSort(copiaQuick, 0, copiaQuick.length - 1);
        long finQuick = System.nanoTime();
        System.out.println("QuickSort: " + (finQuick - inicioQuick) / 1_000_000.0 + " ms");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la edad para buscar mediante busqueda lineal (Entre 0-10): ");
        int edadBuscar = scanner.nextInt();
        scanner.nextLine(); 

        Persona[] encontrados = busquedaLineal(personas, edadBuscar);
        System.out.println("Resultados encontrados mediante bsuqueda lineal:");
        for (Persona p : encontrados) {
            System.out.println(p);
        }

        Arrays.sort(personas, Comparator.comparingInt(Persona::getEdad));
        System.out.print("Ingrese la edad para buscar mediante binaria (Entre 0-10): ");
        int edadBin = scanner.nextInt();

        Persona resultadoBin = busquedaBinaria(personas, edadBin);
        if (resultadoBin != null) {
            System.out.println("Resultado encontrado por busqueda Binaria: " + resultadoBin);
        } else {
            System.out.println("No se encontró una persona con esa edad.");
        }
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

        for (int i = 0; i < cantidad; i++) {
            String nombre;
            while (true) {
                System.out.print("Ingrese el nombre de la persona #" + (i + 1) + ": ");
                nombre = scanner.nextLine();
                if (nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) break;
                else System.out.println("Nombre inválido. Solo letras.");
            }
            int edad = random.nextInt(11); 
            personas[i] = new Persona(nombre, edad);
        }
        return personas;
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

    public static Persona[] busquedaLineal(Persona[] personas, int edad) {
        List<Persona> resultado = new ArrayList<>();
        for (Persona p : personas) {
            if (p.getEdad() == edad) {
                resultado.add(p);
            }
        }
        return resultado.toArray(Persona[]::new);
    }

    public static Persona busquedaBinaria(Persona[] personas, int edad) {
        int izquierda = 0, derecha = personas.length - 1;
        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            if (personas[medio].getEdad() == edad) {
                return personas[medio];
            } else if (personas[medio].getEdad() < edad) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return null;
    }
}
    

