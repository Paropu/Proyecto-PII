package centros_universitarios;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Alumno extends Persona {

	/* ATRIBUTOS */
	private GregorianCalendar fechaIngreso;
	//docencia recibida
	//asignaturas aprobadas

	/* METODOS */
	@Override
	public String toString(){ //Metodo toString sobreescrito reciclando el metodo de la clase padre.
		return super.toString() + " " + fechaIngreso.get(Calendar.DATE) + "/" + fechaIngreso.get(Calendar.MONTH)+ "/" + fechaIngreso.get(Calendar.YEAR);
	}


	/* GETTERS & SETTERS */
	public GregorianCalendar getFechaIngreso(){
		return this.fechaIngreso;
	}
	public void setFechaIngreso(GregorianCalendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	/* CONSTRUCTORES */
	public Alumno (String dni, String nombre, String apellidos, GregorianCalendar fechaNacimiento, GregorianCalendar fechaIngreso){ //Constructor.
		super(dni, nombre, apellidos, fechaNacimiento); //Llamada al metodo constructor de la clase padre.
		this.fechaIngreso = fechaIngreso;
	}



}