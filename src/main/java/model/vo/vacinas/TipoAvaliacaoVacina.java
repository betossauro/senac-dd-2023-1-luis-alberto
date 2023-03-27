package model.vo.vacinas;

public enum TipoAvaliacaoVacina {

	PESSIMA(1), RUIM(2), OK(3), BOA(4), OTIMA(5);

	private int valor;

	private TipoAvaliacaoVacina(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static TipoAvaliacaoVacina getTipoAvaliacaoVacinaPorValor(int valor) {
		TipoAvaliacaoVacina tipoAvaliacaoVacina = null;
		for (TipoAvaliacaoVacina elemento : TipoAvaliacaoVacina.values()) {
			if (elemento.getValor() == valor) {
				tipoAvaliacaoVacina = elemento;
			}
		}
		return tipoAvaliacaoVacina;
	}
}
