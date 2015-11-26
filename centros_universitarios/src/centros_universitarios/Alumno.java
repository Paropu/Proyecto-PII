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
	public void setFechaIngreso(GregorianCalendar fEchaIngreso) {
		this.fechaIngreso = fEchaIngreso;
	}


	/* CONSTRUCTORES */
	public Alumno (String dNi, String nOmbre, String aPellidos, GregorianCalendar fEchaNacimiento, GregorianCalendar fEchaIngreso){ //Constructor.
		super(dNi, nOmbre, aPellidos, fEchaNacimiento); //Llamada al metodo constructor de la clase padre.
		this.fechaIngreso = fEchaIngreso;
	}



}