package model.vo.telefonia;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

	private Integer id;
	private String nome;
	private String cpf;
	private boolean ativo;
	private List<Telefone> telefones;
	private Endereco endereco;
	
	public Cliente() {
		this.telefones = new ArrayList<>();
	}

	public Cliente(Integer id, String nome, String cpf, boolean ativo, List<Telefone> telefones, Endereco endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.ativo = ativo;
		this.telefones = telefones;
		this.endereco = endereco;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return this.getNome();
	}
//	public String toString() {
//		return "\nID: " + this.getId() + "\nNome: " + this.getNome() + "\nCPF: " + this.getCpf() + "\nAtivo: " + this.isAtivo()
//				+ "\nTelefones: " + this.getTelefones() + "\nEndereco: " + this.getEndereco();
//	}

}
