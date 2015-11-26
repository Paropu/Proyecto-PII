package centros_universitarios;

public class Asignatura {

	/* ATRIBUTOS */
	private int idAsignatura;
	private String nombre;
	private String siglas;
	private int curso;
	//prerrequisitos
	//coordinador
	//grupos A
	//grupos B



	/*METODOS */


	/* GETTERS & SETTERS */
	public int getIdAsignatura() {
		return idAsignatura;
	}
	@Override
	public String toString() {
		return idAsignatura + " " + nombre + " " + siglas + " " + curso;
	}
	public void setIdAsignatura(int idAsignatura) {
		this.idAsignatura = idAsignatura;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}


	/* CONSTRUCTORES */
	public Asignatura(int idAsignatura, String nombre, String siglas, int curso) {
		super();
		this.idAsignatura = idAsignatura;
		this.nombre = nombre;
		this.siglas = siglas;
		this.curso = curso;
	}
}