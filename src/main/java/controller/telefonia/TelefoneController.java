package controller.telefonia;

import java.util.List;

import model.bo.telefonia.TelefoneBO;
import model.exception.CampoInvalidoException;
import model.vo.telefonia.Telefone;

public class TelefoneController {

	private TelefoneBO bo = new TelefoneBO();

	public Telefone inserir(Telefone novoTelefone) throws CampoInvalidoException {
		validarCamposObrigatorios(novoTelefone);
		return bo.inserir(novoTelefone);
	}

	public boolean atualizar(Telefone telefoneAlterado) throws CampoInvalidoException {
		validarCamposObrigatorios(telefoneAlterado);
		return bo.atualizar(telefoneAlterado);
	}
	
	private void validarCamposObrigatorios(Telefone telefone) throws CampoInvalidoException {
		String mensagemValidacao = "";

		mensagemValidacao += validarString(telefone.getDdd(), "ddd");
		mensagemValidacao += validarString(telefone.getNumero(), "número");

		if (!mensagemValidacao.isEmpty()) {
			throw new CampoInvalidoException(mensagemValidacao);
		}
	}
	
	private String validarString(String texto, String nomeCampo) {
		boolean valido = (texto != null) && !texto.trim().isEmpty();

		if (valido) {
			return "";
		} else {
			return "- " + nomeCampo + "\n";
		}
	}

	public boolean excluir(int id) {
		return bo.excluir(id);
	}

	public Telefone consultarPorId(int id) {
		return bo.consultarPorId(id);
	}

	public List<Telefone> consultarTodos() {
		return bo.consultarTodos();
	}
}
