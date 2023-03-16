package model.vo.telefonia;

public class Telefone {

	private Integer id;
	private Integer idCliente;
	private String ddd;
	private String numero;
	private boolean ativo;
	private boolean movel;

	public Telefone(Integer id, Integer idCliente, String ddd, String numero, boolean ativo, boolean movel) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.ddd = ddd;
		this.numero = numero;
		this.ativo = ativo;
		this.movel = movel;
	}

	public Telefone() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isMovel() {
		return movel;
	}

	public void setMovel(boolean movel) {
		this.movel = movel;
	}

	@Override
	public String toString() {
		return "\nID: " + this.getId() + "\nID Cliente: " + this.getIdCliente() + "\nDDD: " + this.getDdd() + "\nNúmero: "
				+ this.getNumero() + "\nAtivo: " + this.isAtivo() + "\nMóvel: " + this.isMovel();
	}

}
