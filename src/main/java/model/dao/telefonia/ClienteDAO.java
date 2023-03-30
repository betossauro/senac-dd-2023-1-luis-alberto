package model.dao.telefonia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.Banco;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;

public class ClienteDAO {

	/**
	 * Insere um novo cliente no banco
	 * 
//	 * @param novoCliente o cliente a ser persistido
	 * @return o cliente inserido com a chave primária gerada
	 */

	// Consultar por ID
	public Cliente consultarPorId(int id) {
		Cliente clienteBuscado = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE " + " WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setInt(1, id);
			ResultSet resultado = query.executeQuery();
			if (resultado.next()) {
				clienteBuscado = new Cliente();
				clienteBuscado.setId(resultado.getInt("id"));
				clienteBuscado.setNome(resultado.getString("nome"));
				clienteBuscado.setCpf(resultado.getString("cpf"));
				clienteBuscado.setAtivo(resultado.getBoolean("ativo"));
				// Buscar endereco do cliente
				int idEnderecoCliente = resultado.getInt("id_endereco");
				EnderecoDAO enderecoDAO = new EnderecoDAO();
				Endereco endereco = enderecoDAO.consultarPorId(idEnderecoCliente);
				clienteBuscado.setEndereco(endereco);
				// Buscar lista de telefones
				TelefoneDAO telefoneDAO = new TelefoneDAO();
				clienteBuscado.setTelefones(telefoneDAO.consultarPorIdCliente(clienteBuscado.getId()));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar cliente com id" + id + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return clienteBuscado;
	}
}
