package model.vo.vacinas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vacina {

	private Integer id;
	private String nome;
	private String paisOrigem;
	private TipoFaseVacina faseVacina;
	private LocalDateTime dataInicioDaPesquisa;
	private Pessoa pesquisador;
	
	public Vacina(Integer id, String nome, String paisOrigem, TipoFaseVacina faseVacina, LocalDateTime dataInicioDaPesquisa,
			Pessoa pesquisador) {
		super();
		this.id = id;
		this.nome = nome;
		this.paisOrigem = paisOrigem;
		this.faseVacina = faseVacina;
		this.dataInicioDaPesquisa = dataInicioDaPesquisa;
		this.pesquisador = pesquisador;
	}

	public Vacina() {
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

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public TipoFaseVacina getFaseVacina() {
		return faseVacina;
	}

	public void setFaseVacina(TipoFaseVacina faseVacina) {
		this.faseVacina = faseVacina;
	}

	public LocalDateTime getDataInicioDaPesquisa() {
		return dataInicioDaPesquisa;
	}

	public void setDataInicioDaPesquisa(LocalDateTime dataInicioDaPesquisa) {
		this.dataInicioDaPesquisa = dataInicioDaPesquisa;
	}

	public Pessoa getPesquisador() {
		return pesquisador;
	}

	public void setPesquisador(Pessoa pesquisador) {
		this.pesquisador = pesquisador;
	}

	@Override
	public String toString() {
		return 	"\nID: " + this.getId()
				+ "\nNome: " + this.getNome()
				+ "\nPaís de origem: " + this.getPaisOrigem() 
				+ "\nFase da vacina: " + this.getFaseVacina() 
				+ "\nData de início da pesquisa: " + this.getDataInicioDaPesquisa().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
				+ "\nPesquisador: " + this.getPesquisador().getNome();
	}
	
	
}
