package model.dao.telefonia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.Banco;
import model.vo.telefonia.Endereco;

public class EnderecoDAO {

	/**
	 * Insere um novo endereço no banco
	 * 
	 * @param novoEndereco o endereço a ser persistido
	 * @return o endereço inserido com a chave primária gerada
	 */

	public Endereco inserir(Endereco novoEndereco) {
		// Conectar ao banco
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO ENDERECO (CEP, RUA, NUMERO, BAIRRO, CIDADE, ESTADO) " + " VALUES (?,?,?,?,?,?) ";
		PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);

		// Executar o INSERT
		try {
			query.setString(1, novoEndereco.getCep());
			query.setString(2, novoEndereco.getRua());
			query.setString(3, novoEndereco.getNumero());
			query.setString(4, novoEndereco.getBairro());
			query.setString(5, novoEndereco.getCidade());
			query.setString(6, novoEndereco.getEstado());
			query.execute();

			// Preencher o ID gerado no banco no objeto
			ResultSet resultado = query.getGeneratedKeys();
			if (resultado.next()) {
				novoEndereco.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao inserir endereço" + "\nCausa: " + erro.getMessage());
		} finally {
			// Fechar a conexão
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoEndereco;
	}

	// UPDATE endereco
	public boolean atualizar(Endereco enderecoEditado) {
		boolean atualizou = false;
		Connection conexao = Banco.getConnection();
		String sql = " UPDATE ENDERECO " + " SET CEP = ?, RUA = ?, NUMERO = ?, BAIRRO = ?, CIDADE = ?, ESTADO = ? "
				+ "	WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);

		try {
			query.setString(1, enderecoEditado.getCep());
			query.setString(2, enderecoEditado.getRua());
			query.setString(3, enderecoEditado.getNumero());
			query.setString(4, enderecoEditado.getBairro());
			query.setString(5, enderecoEditado.getCidade());
			query.setString(6, enderecoEditado.getEstado());
			query.setInt(7, enderecoEditado.getId());

			int qtdeLinhasAtualizadas = query.executeUpdate();
			atualizou = qtdeLinhasAtualizadas > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar endereço" + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return atualizou;
	}

	// Executar Consulta por ID
	public Endereco consultarPorId(int id) {
		Endereco enderecoConsultado = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM ENDERECO " + "	WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);

		try {
			query.setInt(1, id);
			ResultSet resultado = query.executeQuery();
			if (resultado.next()) {
				enderecoConsultado = new Endereco();
				enderecoConsultado.setId(resultado.getInt("id"));
				enderecoConsultado.setCep(resultado.getString("cep"));
				enderecoConsultado.setRua(resultado.getString("rua"));
				enderecoConsultado.setNumero(resultado.getString("numero"));
				enderecoConsultado.setBairro(resultado.getString("bairro"));
				enderecoConsultado.setCidade(resultado.getString("cidade"));
				enderecoConsultado.setEstado(resultado.getString("estado"));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar endereço com id: " + id + "\nCausa: " + erro.getMessage());
		}

		return enderecoConsultado;
	}

	// Executar DELETE
	public boolean excluir(int id) {
		boolean excluiu = false;
		Connection conexao = Banco.getConnection();
		String sql = " DELETE FROM ENDERECO " + "	WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);

		try {
			query.setInt(1, id);

			int qtdeLinhasAtualizadas = query.executeUpdate();
			excluiu = qtdeLinhasAtualizadas > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir endereço" + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return excluiu;
	}
}
