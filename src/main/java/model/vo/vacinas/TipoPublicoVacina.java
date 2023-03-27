package model.vo.vacinas;

public enum TipoPublicoVacina {

	PESQUISADORES(1), VOLUNTARIOS(2), PUBLICO_GERAL(3);

	private int valor;

	private TipoPublicoVacina(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static TipoPublicoVacina getTipoPublicoVacinaPorValor(int valor) {
		TipoPublicoVacina tipoPublicoVacina = null;
		for (TipoPublicoVacina elemento : TipoPublicoVacina.values()) {
			if (elemento.getValor() == valor) {
				tipoPublicoVacina = elemento;
			}
		}
		return tipoPublicoVacina;
	}
}
