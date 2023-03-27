package model.vo.vacinas;

public enum TipoFaseVacina {

	INICIAL(1), TESTES(2), APLICACAO_EM_MASSA(3);

	private int valor;

	private TipoFaseVacina(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static TipoFaseVacina getTipoFaseVacinaPorValor(int valor) {
		TipoFaseVacina tipoVacina = null;
		for (TipoFaseVacina elemento : TipoFaseVacina.values()) {
			if (elemento.getValor() == valor) {
				tipoVacina = elemento;
			}
		}
		return tipoVacina;
	}
}
