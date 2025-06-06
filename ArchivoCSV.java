/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_arreglos_lee;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author keonj
 */
public class ArchivoCSV {
    public static void guardarPersonasCSV(Persona[] personas, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Nombre;Edad;ID;");
            for (Persona p : personas) {
                writer.write(p.getNombre() + ";" + p.getEdad() + ";" + p.getId() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo CSV: " + e.getMessage());
        }
    }
}
