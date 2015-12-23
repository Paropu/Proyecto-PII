package centros_universitarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Gestion {
	public static void main (String args[]){

		Funcionalidades funcionalidad = new Funcionalidades(); //Composicion de Funcionalidades.
		ComprobacionErrores comprobacion = new ComprobacionErrores(); //Composicion de ComprobacionErrores.

		
		TreeMap <String, Profesor> profesores = new TreeMap <String, Profesor> ();
		TreeMap <String, Alumno> alumnos = new TreeMap<String, Alumno> ();
		TreeMap <Integer, Asignatura> asignaturas = new TreeMap<Integer, Asignatura>();
		//cargarFicheroPersonas(); //Se carga el fichero personas.txt. Hay que hacerlo por partes para poder cargar todos los datos sin problemas. No se puede cargar toda la info de golpe, ya que se necesita relacionar las clases.
		profesores=cargarProfesores(); //Carga toda la informacioon de los profesores excepto la docencia impartida, que requiere que existan los grupos relacionados.
		alumnos=cargarAlumnos(); //Carga toda la informacion de los alumnos excepto las asignaturas aprobadas y la docencia recibida.
		asignaturas=cargarAsignaturas(); //Carga toda la info de las asignaturas en dos fases. En la primera fase carga los datos básico y en la segunda, los prerrequisitos.
		//cargarAsignaturasSuperadas.
		//cargarDocenciaRecibida.
		//cargarDocenciaImpartida.
		}

	public static TreeMap<String, Profesor> cargarProfesores(){
		
		TreeMap<String, Profesor> profesores = new TreeMap<String, Profesor>();
		FileInputStream flujo_entrada = null;
		try {
			flujo_entrada = new FileInputStream("personas.txt"); // Se crea un flujo de datos al fichero.
		} 
		catch (FileNotFoundException NoExisteFichero) { // Si el fichero no existe, salta excepcion y se muestra mensaje por pantalla.
			System.out.println("Fichero \"personas.txt\" inexistente");
			System.exit(-1); // Mostrar error en el fichero Avisos.txt ----- ???
		}
		Scanner entrada = new Scanner(flujo_entrada); // Se crea un objeto para escanear la linea del fichero
		String linea = null; // Variable que contendra la informacion escaneada del fichero
		while (entrada.hasNextLine()) {
			linea=entrada.nextLine();
			if(linea.contains("profesor")){ //Se recogen los datos del profesor.
				String dni= entrada.nextLine();
				String nombre = entrada.nextLine();
				String apellidos = entrada.nextLine();
				linea = entrada.nextLine();
				String[] fecha = linea.split("/");
				GregorianCalendar fechaNacimiento = new GregorianCalendar (Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
				String categoria= entrada.nextLine();
				String departamento= entrada.nextLine();
				Integer horasDocenciaAsignables = Integer.parseInt(entrada.nextLine());
				linea=entrada.nextLine();//OMISION de carga de docencia impartida por el profesor.
				TreeMap<Integer, Grupo> docenciaImpartida = new TreeMap<Integer, Grupo> (); //VACIO
				TreeMap<Integer, Asignatura> asignaturasCoordinadas = new TreeMap<Integer, Asignatura> (); //VACIO. En principio vacio, luego se completa al cargar las asignaturas.
				Profesor profesor = new Profesor(dni, nombre, apellidos, fechaNacimiento,categoria, departamento, horasDocenciaAsignables, docenciaImpartida, asignaturasCoordinadas);
				profesores.put(dni, profesor);	
			}
			else { //Se salta el bloque del alumno.
				int i;
				for(i=0; i<7 ; i++) linea=entrada.nextLine();
			}
			
			if(entrada.hasNextLine())
				linea=entrada.nextLine(); //Se recoge el "*" de separacion.
		}
		entrada.close();
		
		return profesores;
	}
	
	
	
	public static TreeMap <String, Alumno> cargarAlumnos(){
		
		TreeMap<String, Alumno> alumnos = new TreeMap<String, Alumno>();
		FileInputStream flujo_entrada = null;
		try {
			flujo_entrada = new FileInputStream("personas.txt"); // Se crea un flujo de datos al fichero.
		} 
		catch (FileNotFoundException NoExisteFichero) { // Si el fichero no existe, salta excepcion y se muestra mensaje por pantalla.
			System.out.println("Fichero \"personas.txt\" inexistente");
			System.exit(-1); // Mostrar error en el fichero Avisos.txt ----- ???
		}
		Scanner entrada = new Scanner(flujo_entrada); // Se crea un objeto para escanear la linea del fichero
		String linea = null; // Variable que contendra la informacion escaneada del fichero
		while (entrada.hasNextLine()) {
			linea=entrada.nextLine();
			if(linea.contains("alumno")){ //Se recogenlos datos del alumno.
				String dni= entrada.nextLine();
				String nombre = entrada.nextLine();
				String apellidos = entrada.nextLine();
				linea = entrada.nextLine();
				String[] fecha = linea.split("/");
				GregorianCalendar fechaNacimiento = new GregorianCalendar (Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
				linea = entrada.nextLine();
				fecha = linea.split("/");
				GregorianCalendar fechaIngreso = new GregorianCalendar (Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
				linea=entrada.nextLine();//OMISION de asignaturas superadas del alumno.
				TreeMap<Integer, NotaFinal> asignaturasAprobadas = new TreeMap<Integer, NotaFinal>(); 
				linea=entrada.nextLine();//OMISION de la docencia recibida del alumno.
				TreeMap<Integer, Grupo> docenciaRecibida = new TreeMap<Integer, Grupo>();
				Alumno alumno = new Alumno (dni, nombre,  apellidos, fechaNacimiento,  fechaIngreso, docenciaRecibida,  asignaturasAprobadas);
				alumnos.put(dni, alumno);
			}
			else { //Se salta el bloque del profesor.
				int i;
				for(i=0; i<8 ; i++) linea=entrada.nextLine();
			}
			if(entrada.hasNextLine())
				linea=entrada.nextLine(); //Se recoge el "*" de separacion.
		}
		entrada.close();
		
		return alumnos;
	}
	
	
	
	public static TreeMap<Integer, Asignatura> cargarAsignaturas(){
		//PRIMERA FASE.
		TreeMap<Integer, Asignatura> asignaturas = new TreeMap<Integer, Asignatura>(); //TreeMap que contendrá las asignaturas.
		
		FileInputStream flujo_entrada = null;
		try {
			flujo_entrada = new FileInputStream("asignaturas.txt"); // Se crea un flujo de datos al fichero.
		} 
		catch (FileNotFoundException NoExisteFichero) { // Si el fichero no existe, salta excepcion y se muestra mensaje por pantalla.
			System.out.println("Fichero \"asignaturas.txt\" inexistente");
			System.exit(-1); // Mostrar error en el fichero Avisos.txt ----- ???
		}
		Scanner entrada = new Scanner(flujo_entrada); // Se crea un objeto para escanear la linea del fichero
		String linea = null; // Variable que contendra la informacion escaneada del fichero
		while (entrada.hasNextLine()) {
			Integer idAsignatura = Integer.parseInt(entrada.nextLine());
			String nombre = entrada.nextLine();
			String siglas = entrada.nextLine();
			Integer curso = Integer.parseInt(entrada.nextLine());
			linea=entrada.nextLine();//OMISION de carga del coordinador de la asignatura.
			Profesor coordinador;
			String[] arrayPrerrequisitos=entrada.nextLine().split(", ");
			TreeMap<Integer, Asignatura> prerrequisitos = new TreeMap<Integer, Asignatura>();
			
			Asignatura asignatura= new Asignatura(idAsignatura,  nombre,  siglas,  curso, new Profesor(), prerrequisitos,  new TreeMap<Integer, Grupo>(), new TreeMap<Integer, Grupo>(), arrayPrerrequisitos);//AÑADIR posteriormente los grupos A y B.
			
			TreeMap<Integer, Grupo> gruposA = new TreeMap<Integer, Grupo>(); //CARGAR gruposA
			linea=entrada.nextLine(); //Formato: ID_grupo dia horaini horafin
			String[] arrayGruposA = linea.split("; ");
			int i;
			for(i=0; i<arrayGruposA.length; i++){
				String[] grupo = arrayGruposA[i].split(" ");
				Integer idGrupo=Integer.parseInt(grupo[0]);
				String dia= grupo[1];
				Integer horaInicio = Integer.parseInt(grupo[2]);
				Integer horaFin = Integer.parseInt(grupo[3]);
				Grupo grupoA = new Grupo("A",  idGrupo,  dia,  horaInicio,  horaFin, asignatura);//AÑADIR asignatura posteriormente.
				gruposA.put(idGrupo, grupoA);//Se añade el grupo al Treemap de grupos A de la asignatura.
			}
			
			TreeMap<Integer, Grupo> gruposB = new TreeMap<Integer, Grupo>(); //CARGAR gruposB.
			linea=entrada.nextLine(); //Formato: ID_grupo dia horaini horafin
			String[] arrayGruposB = linea.split("; ");
			for(i=0; i<arrayGruposB.length; i++){
				String[] grupo = arrayGruposB[i].split(" ");
				Integer idGrupo=Integer.parseInt(grupo[0]);
				String dia= grupo[1];
				Integer horaInicio = Integer.parseInt(grupo[2]);
				Integer horaFin = Integer.parseInt(grupo[3]);
				Grupo grupoA = new Grupo("B",  idGrupo,  dia,  horaInicio,  horaFin, asignatura);//AÑADIR asignatura posteriormente.
				gruposB.put(idGrupo, grupoA);//Se añade el grupo al Treemap de grupos B de la asignatura.
			}
			asignatura.setGruposA(gruposA);
			asignatura.setGruposB(gruposB);
			
			asignaturas.put(asignatura.getIdAsignatura(), asignatura); //Se añade la asignatura al TreeMap de asignaturas.
			
			if(entrada.hasNextLine())
				linea=entrada.nextLine(); //Se recoge el "*" de separacion.
		}
		entrada.close();
	
		//SEGUNDA FASE - ACTUALIZACION DE PRERREQUISITOS
		Set setAsignaturas = asignaturas.keySet(); //CREACION de un Set con las claves de las asignaturas en el TreeMap asignaturas.
		Iterator it = setAsignaturas.iterator(); //Se linkea un Iterator al Set para navegarlo.
		while(it.hasNext()){ //Se navega sobre cada elemento del set para extraer la key.
			Asignatura asignatura = asignaturas.get(it.next()); //Se recoge la asignatura del TreeMap mediante la key	
			TreeMap<Integer, Asignatura> nuevosPrerrequisitos = new TreeMap<Integer, Asignatura>(); //Nuevo TreeMap donde se guardaran los nuevos prerrequisitos, para posteriormente añadirlos a la asignatura mediante un setPrerrequisitos().
			int i;
			for(i=0; i<asignatura.getArrayPrerrequisitos().length; i++){ //Bucle en el que se accede a la info de las asignaturas prerrequisito y se añaden estas al TreeMap de nuevosPrerrequisitos para posteriormente hacer un set().
				nuevosPrerrequisitos.put(Integer.parseInt(asignatura.getArrayPrerrequisitos()[i]), asignaturas.get(Integer.parseInt(asignatura.getArrayPrerrequisitos()[i])));
			}
			asignatura.setPrerrequisitos(nuevosPrerrequisitos);
		}
		//========================================================================================================================================================================================================================================
		
		return asignaturas;
	}
}
