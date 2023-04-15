package model.bo.telefonia;

import model.dao.telefonia.ClienteDAO;
import model.dao.telefonia.TelefoneDAO;
import model.exception.ClienteComTelefoneException;
import model.exception.CpfAlteradoException;
import model.exception.CpfJaUtilizadoException;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Telefone;

import java.util.List;

public class ClienteBO {

    private ClienteDAO dao = new ClienteDAO();

    public Cliente inserir(Cliente novoCliente) throws CpfJaUtilizadoException, EnderecoInvalidoException {
        if (dao.cpfJaUtilizado(novoCliente.getCpf())) {
            throw new CpfJaUtilizadoException("CPF informado já foi cadastrado");
        }

        if (novoCliente.getEndereco() == null || novoCliente.getEndereco().getId() == null) {
            throw new EnderecoInvalidoException("Endereço não informado");
        }

        return dao.inserir(novoCliente);
    }

    public boolean atualizar(Cliente clienteAlterado) throws EnderecoInvalidoException, CpfAlteradoException {
        Cliente clienteOriginal = dao.consultarPorId(clienteAlterado.getId());
        //Caso cliente possua telefone(s): lançar ClienteComTelefoneException
        if(clienteAlterado.getCpf() != clienteOriginal.getCpf()) {
            throw new CpfAlteradoException("CPF não pode ser alterado");
        }
        //Caso contrário: chamar dao.excluir(id)
        validarEndereco(clienteAlterado);
        return dao.atualizar(clienteAlterado);
    }

    public boolean excluir(int id) throws ClienteComTelefoneException {
        TelefoneDAO telefoneDao = new TelefoneDAO();
        List<Telefone> telefonesDoCliente = telefoneDao.consultarPorIdCliente(id);
        if (telefonesDoCliente!= null && !telefonesDoCliente.isEmpty()) {
            throw new ClienteComTelefoneException("Cliente não pode ser excluído pois possui telefone associado");
        }
        return dao.excluir(id);

        // OU
//        Cliente clienteBuscado = dao.consultarPorId(id);
//        if (!clienteBuscado.getTelefones().isEmpty()) {
//            throw new ClienteComTelefoneException("Cliente não pode ser excluído pois possui telefone associado");
//        }
//        return dao.excluir(id);
    }

    public Cliente consultarPorId(int id) {
        return dao.consultarPorId(id);
    }

    public List<Cliente> consultarTodos() {
        return dao.consultarTodos();
    }

    private void validarEndereco(Cliente cliente) throws EnderecoInvalidoException {
        if(cliente.getEndereco() == null || cliente.getEndereco().getId() == null) {
            throw new EnderecoInvalidoException("Endereço não informado");
        }
    }
}
