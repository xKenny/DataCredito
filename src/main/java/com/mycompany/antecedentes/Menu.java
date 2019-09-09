/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.antecedentes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Clase que contiene la logica del programa
 * @author Duvan Poveda
 */
public class Menu {
    Scanner scanner = new Scanner(System.in);
    List<Persona> personas = new ArrayList();
    
    /**
    *Metodo que recibe la opcion de la accion que desea realizar el usuario
    */
    public void menuPrincipal(){
        cargarDatos();
        System.out.println("----Registro de antecedentes----");
        System.out.println("1. Registrar una nueva persona.");
        System.out.println("2. Editar datos de una persona.");
        System.out.println("3. Valide un registro");
        System.out.println("4. Adicione un nuevo antecedente.");
        System.out.println("5. Elimine un antecedente.");
        System.out.println("6. Visualizacion de antecedentes.");
        System.out.println("7. Eliminar persona.");
        System.out.println("Digite su opcion");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1:
                registrarPersona();
                break;
            case 2:
                editarDatos();
                break;
            case 3:
                validarRegistro();
                break;
            case 4:
                agregarAntecedente();
                break;
            case 5:
                eliminarAntecedente();
                break;
            case 6:
                verAntecedentes();
                break;
            case 7:
                eliminarPersona();
                break;
        }
    }
    
    /**
    *Metodo que se encarga de crear los datos de una persona nueva
    */
    private void registrarPersona() {
        
        System.out.println("----Registrando Persona----");
        Persona persona = new Persona();
        System.out.println("Ingrese el nombre:");
        persona.setNombre(scanner.nextLine());
        scanner.nextLine();
        System.out.println("Ingrese la cedula:");
        persona.setCedula(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Ingrese la edad:");
        persona.setEdad(scanner.nextShort());
        scanner.nextLine();
        System.out.println("Ingrese el genero (M/F):");
        persona.setGenero(scanner.next().charAt(0));
        scanner.nextLine();
        System.out.println("Ingresando datos del antecedente");
         persona.setAntecedentes(añadirAntecedente());
        
        System.out.println("Se ha guardado correctamente");
        try {
            guardarArchivo(persona);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        menuPrincipal();
    }
 
    /**
    *Metodo que se encarga de editar los datos de una persona ya creada
    */
    private void editarDatos() {
        System.out.println("Ingrese la cedula de la persona que desea editar");
        int cedula = scanner.nextInt();
        scanner.nextLine();
        short error = 1;
        for(Persona aux: personas){
            if(cedula == aux.getCedula()){
                error = 0;
                System.out.println("Nombre: ");
                aux.setNombre(scanner.nextLine());
                scanner.nextLine();
                System.out.println("Edad: ");
                aux.setEdad(scanner.nextShort());
                scanner.nextLine();
            }
        }
        if(error == 1){
            System.out.println("No se encontro el registro");
        }
        File miFile = new File("Personas");
            ObjectOutputStream oos;
        try {    
            oos = new ObjectOutputStream(new FileOutputStream(miFile));
            oos.writeObject(personas);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
        cargarDatos();
        menuPrincipal();
        
        
    }
 
    /**
    *Metodo que se encarga de buscar y mostrar los datos de una persona
    */
    private void validarRegistro() {
        System.out.println("Ingrese la cedula de la persona que desea buscar");
        int cedula = scanner.nextInt();
        short error = 1;
        for(Persona aux: personas){
            if(cedula == aux.getCedula()){
                System.out.println("Se ha encontrado el registro");
                System.out.println("Nombre = " + aux.getNombre());
                System.out.println("Edad = " + aux.getEdad());
                System.out.println("Genero = " + aux.getGenero());
                for(Antecedentes antecedente: aux.getAntecedentes()){
                    System.out.println("Datos del antecedente");
                    System.out.println("Fecha del antecedente: " + antecedente.getFecha());
                    System.out.println("Descripcion del antecedente: " + antecedente.getDescripcion());
                    System.out.println("Tipo de antecedente: " + antecedente.getTipo());
                }
                error = 0;
            }
        }
        if(error == 1){
            System.out.println("No se ha encontrado el registro"); 
        }
        menuPrincipal();
    }
 
    /**
    *Metodo que se encarga de agregar antecedentes a una persona ya existente en el sistema
    */
     private void agregarAntecedente() {
         System.out.println("Ingrese la cedula de la persona a la cual desea agregar un antecedente:");
         int cedula = scanner.nextInt();
         String respuesta = "si";
         List<Antecedentes> antecedentes = new ArrayList();
         short error = 1;
         for(Persona aux: personas){
            if(cedula == aux.getCedula()){
                System.out.println("Se encontro el registro");
                antecedentes = aux.getAntecedentes();
                error = 0;
                do{
                   Antecedentes antecedente = new Antecedentes();
                   System.out.println("Ingrese la fecha del antecedente");
                   antecedente.setFecha(scanner.next());
                   scanner.nextLine();
                   System.out.println("Ingrese una breve descripcion del antecedente");
                   antecedente.setDescripcion(scanner.nextLine());
                   scanner.nextLine();
                   System.out.println("Ingrese el tipo de accidente (positivo/negativo)");
                   antecedente.setTipo(scanner.nextLine());
                   scanner.nextLine();
                   antecedentes.add(antecedente);
                   System.out.println("Desea ingresar otro antecedente? (si/no)");
                   respuesta = scanner.next();
                }while(respuesta.equals("si")||respuesta.equals("SI"));
                aux.setAntecedentes(antecedentes);
             }
         }
         if(error == 1){
             System.out.println("no se encontro el registro");
         }else{
            
         }
         File miFile = new File("Personas");
            ObjectOutputStream oos;
        try {    
            oos = new ObjectOutputStream(new FileOutputStream(miFile));
            oos.writeObject(personas);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
        cargarDatos();
        menuPrincipal();
    }
    
    /**
    *Metodo que se encarga de añadir antecedentes a una persona que se esta registrando por primera vez
    * @return List<Antecedentes> devuelve la lista de los antecedentes registrados
    */
    private List<Antecedentes> añadirAntecedente() {
        String respuesta = "si";
        List<Antecedentes> antecedentes = new ArrayList();
        do{
            Antecedentes antecedente = new Antecedentes();
            System.out.println("Ingrese la fecha del antecedente");
            antecedente.setFecha(scanner.next());
            scanner.nextLine();
            System.out.println("Ingrese una breve descripcion del antecedente");
            antecedente.setDescripcion(scanner.nextLine());
            scanner.nextLine();
            System.out.println("Ingrese el tipo de accidente (positivo/negativo)");
            antecedente.setTipo(scanner.nextLine());
            scanner.nextLine();
            antecedentes.add(antecedente);
            System.out.println("Desea ingresar otro antecedente? (si/no)");
            respuesta = scanner.next();
            
        }while(respuesta.equals("si")||respuesta.equals("SI"));
        return antecedentes;
    }
    
    /**
    *Metodo que se encarga de eliminar antecedentes negativos de una persona
    */
    private void eliminarAntecedente() {
        System.out.println("Ingrese la cedula de la persona a la cual desea eliminar un antecedente:");
        int cedula = scanner.nextInt();
        List<Antecedentes> antecedentes = new ArrayList();
        short error = 1;  
        for(Persona aux: personas){
            if(cedula == aux.getCedula()){
                error = 0;
                System.out.println("Se encontro el registro");
                antecedentes = aux.getAntecedentes();
                for(int i=0; i<antecedentes.size();i++){
                    if(antecedentes.get(i).getTipo().equals("negativo")){
                        System.out.println("Antecedente numero "+(i+1));
                        System.out.println("Fecha del antecedente: " + antecedentes.get(i).getFecha());
                        System.out.println("Descripcion del antecedente: " + antecedentes.get(i).getDescripcion());
                        System.out.println("Tipo de antecedente: " + antecedentes.get(i).getTipo());
                    }
                }
                System.out.println("Ingrese el numero del antecedente que desea eliminar");
                int indexElim = scanner.nextInt();
                if(antecedentes.get(indexElim-1).getTipo().equals("positivo")){
                    System.out.println("el antecedente no se puede eliminar por que es positivo");
                }else{
                    antecedentes.remove(indexElim-1);
                    System.out.println("Se ha eliminado el registro con exito");
                }
                aux.setAntecedentes(antecedentes);
            }
         }
        if(error == 1){
            System.out.println("No se encontro el registro");
        }
          File miFile = new File("Personas");
            ObjectOutputStream oos;
        try {    
            oos = new ObjectOutputStream(new FileOutputStream(miFile));
            oos.writeObject(personas);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
        cargarDatos();
        menuPrincipal();
    }
    
    /**
    *Metodo que se encarga de mostrar todos los antecedentes de una persona
    */
    private void verAntecedentes() {
        System.out.println("Ingrese la cedula de la persona de la cual desea ver los antecedentes:");
        int cedula = scanner.nextInt();
        List<Antecedentes> antecedentes = new ArrayList();
        for(Persona aux: personas){
            if(cedula == aux.getCedula()){
                System.out.println("Se encontro el registro");
                antecedentes = aux.getAntecedentes();
                for(int i=0; i<antecedentes.size();i++){
                    System.out.println("Antecedente numero "+(i+1));
                    System.out.println("Fecha del antecedente: " + antecedentes.get(i).getFecha());
                    System.out.println("Descripcion del antecedente: " + antecedentes.get(i).getDescripcion());
                    System.out.println("Tipo de antecedente: " + antecedentes.get(i).getTipo());
                }
            }
        }
        menuPrincipal();
    }
    
    /**
    *Metodo que se encarga de guardar una nueva persona en el archivo
    */
    private void guardarArchivo(Persona persona) throws FileNotFoundException {
        
            File miFile = new File("Personas");
            ObjectOutputStream oos;
            ObjectInputStream ois;
        
        try {
            ois = new ObjectInputStream(new FileInputStream(miFile));
            personas = (List)ois.readObject();  
            personas.add(persona);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {    
            oos = new ObjectOutputStream(new FileOutputStream(miFile));
            oos.writeObject(personas);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    /**
    *Metodo que carga los datos del archivo a la lista
    */
    private void cargarDatos() {
        ObjectInputStream ois;
        File miFile = new File("Personas");
        try {
            ois = new ObjectInputStream(new FileInputStream(miFile));
            personas = (List)ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    *Metodo que se encarga de eliminar una persona
    */
    private void eliminarPersona() {
        System.out.println("Ingrese la cedula de la persona que desea eliminar:");
        int cedula = scanner.nextInt();
        short error = 1;
        int index = 0;
        for(Persona aux: personas){
            if(cedula == aux.getCedula()){
                System.out.println("Registro eliminado con exito");
                error = 0;
                break;
            }
            index = index+1;
        }
        personas.remove(index);
        if(error == 1){
            System.out.println("No se ha encontrado el registro"); 
        }
        File miFile = new File("Personas");
            ObjectOutputStream oos;
        try {    
            oos = new ObjectOutputStream(new FileOutputStream(miFile));
            oos.writeObject(personas);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
        cargarDatos();
        menuPrincipal();
    }
}
