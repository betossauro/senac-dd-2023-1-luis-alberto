package controller.telefonia;

import model.bo.telefonia.ClienteBO;
import model.exception.*;
import model.vo.telefonia.Cliente;

import java.util.List;

public class ClienteController {

	private ClienteBO bo = new ClienteBO();

	public Cliente inserir(Cliente novoCliente)
			throws CpfJaUtilizadoException, EnderecoInvalidoException, CampoInvalidoException {
		this.validarCamposObrigatorios(novoCliente);
		return bo.inserir(novoCliente);
	}

	public boolean atualizar(Cliente clienteAlterado)
			throws EnderecoInvalidoException, CpfAlteradoException, CampoInvalidoException {
		this.validarCamposObrigatorios(clienteAlterado);
		return bo.atualizar(clienteAlterado);
	}

	private void validarCamposObrigatorios(Cliente c) throws CampoInvalidoException {
		String mensagemValidacao = "";
		if (c.getNome() == null || c.getNome().trim().length() < 2) {
			mensagemValidacao += "Nome inválido! \n";
		}

		mensagemValidacao += validarCpf(c);

		if (c.getEndereco() == null) {
			mensagemValidacao += "Informe um endereço! \n";
		}

		if (!mensagemValidacao.isEmpty()) {
			throw new CampoInvalidoException(mensagemValidacao);
		}
	}

	private String validarCpf(Cliente c) throws CampoInvalidoException {
		String validacao = "";

		if (c.getCpf() == null) {
			validacao += "Informe um CPF \n";
		} else {
			String cpfSemMascara = c.getCpf().replace(".", "");
			cpfSemMascara = c.getCpf().replace("-", "");
			c.setCpf(cpfSemMascara);
			if (c.getCpf().length() != 11) {
				validacao += "CPF deve possuir 11 dígitos\n";
			}
		}
		return validacao;
	}

	public boolean excluir(int id) throws ClienteComTelefoneException {
		return bo.excluir(id);
	}

	public Cliente consultarPorId(int id) {
		return bo.consultarPorId(id);
	}

	public List<Cliente> consultarTodos() {
		return bo.consultarTodos();
	}
}
