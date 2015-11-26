package centros_universitarios;

import java.util.GregorianCalendar;

public class Profesor extends Persona {

	/* ATRIBUTOS */
	private String categoria;
	private String departamento;
	private int horasDocenciaAsignables; //Integer??? -----
	//docencia impartida
	//asignaturas coordinadas


	/* METODOS */
	@Override
	public String toString() {
		return super.toString() + " " + categoria + " " + departamento + " " + horasDocenciaAsignables;
	}


	/* GETTERS & SETTERS */
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public int getHorasDocenciaAsignables() {
		return horasDocenciaAsignables;
	}
	public void setHorasDocenciaAsignables(int horasDocenciaAsignables) {
		this.horasDocenciaAsignables = horasDocenciaAsignables;
	}

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	/* CONSTRUCTORES */
	public Profesor(String dNi, String nOmbre, String aPellidos, GregorianCalendar fEchaNacimiento, String cAtegoría, String dEpartamento, int hOrasDocenciaAsignables) { //Constructor.
		super(dNi, nOmbre, aPellidos, fEchaNacimiento); //Llamada al metodo constructor de la clase padre.
		this.setCategoria(cAtegoría);
		this.departamento = dEpartamento;
		this.horasDocenciaAsignables = hOrasDocenciaAsignables;
	}


}