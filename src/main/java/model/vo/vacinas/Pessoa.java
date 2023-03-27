package model.vo.vacinas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pessoa {

	private Integer id;
	private String nome;
	private LocalDateTime dataNascimento;
	private char sexo;
	private String cpf;
	private TipoPublicoVacina publicoAlvo;
	

	public Pessoa(Integer id, String nome, LocalDateTime dataNascimento, char sexo, String cpf,
			TipoPublicoVacina publicoAlvo) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cpf = cpf;
		this.publicoAlvo = publicoAlvo;
	}
	
	public Pessoa(Integer id, String nome, LocalDateTime dataNascimento, char sexo, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cpf = cpf;
	}

	public Pessoa() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoPublicoVacina getPublicoAlvo() {
		return publicoAlvo;
	}

	public void setPublicoAlvo(TipoPublicoVacina publicoAlvo) {
		this.publicoAlvo = publicoAlvo;
	}

	@Override
	public String toString() {
		return 	"\nID: " + this.getId() 
				+ "\nNome: " + this.getNome() 
				+ "\nData de nascimento: " + this.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+ "\nSexo: " + this.getSexo()
				+ "\nCPF: " + this.getCpf() 
				+ "\nPúblico alvo: " + this.getPublicoAlvo(); 
	}

	
}
