package model.dao.telefonia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.Banco;
import model.vo.telefonia.Telefone;

public class TelefoneDAO {

	/**
	 * Insere um novo telefone no banco
	 * 
	 * @param novoTelefone o telefone a ser persistido
	 * @return o telefone inserido com a chave primária gerada
	 */
	public Telefone inserir(Telefone novoTelefone) {
		// Conectar ao banco
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO TELEFONE (DDD, NUMERO, ATIVO, MOVEL, ID_CLIENTE) " + " VALUES (?,?,?,?,?) ";
		PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
		// Executar o INSERT
		try {
			query.setString(1, novoTelefone.getDdd());
			query.setString(2, novoTelefone.getNumero());
			query.setBoolean(3, novoTelefone.isAtivo());
			query.setBoolean(4, novoTelefone.isMovel());
			query.setInt(5, novoTelefone.getIdCliente() != null ? novoTelefone.getIdCliente() : 0);
			query.execute();
			// Preencher o ID gerado no banco no objeto
			ResultSet resultado = query.getGeneratedKeys();
			if (resultado.next()) {
				novoTelefone.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao inserir telefone" + "\nCausa: " + erro.getMessage());
		} finally {
			// Fechar a conexão
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoTelefone;
	}

	// UPDATE telefone
	public boolean atualizar(Telefone telefoneEditado) {
		boolean atualizou = false;
		Connection conexao = Banco.getConnection();
		String sql = " UPDATE TELEFONE " + " SET DDD = ?, NUMERO = ?, ATIVO = ?, MOVEL = ?, ID_CLIENTE = ? "
				+ "	WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setString(1, telefoneEditado.getDdd());
			query.setString(2, telefoneEditado.getNumero());
			query.setBoolean(3, telefoneEditado.isAtivo());
			query.setBoolean(4, telefoneEditado.isMovel());
			query.setInt(5, telefoneEditado.getIdCliente());
			query.setInt(6, telefoneEditado.getId());

			int qtdeLinhasAtualizadas = query.executeUpdate();
			atualizou = qtdeLinhasAtualizadas > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar telefone" + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return atualizou;
	}

	// Executar Consulta por ID
	public Telefone consultarPorId(int id) {
		Telefone telefoneConsultado = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM TELEFONE " + "	WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setInt(1, id);
			ResultSet resultado = query.executeQuery();
			if (resultado.next()) {
				telefoneConsultado = new Telefone();
				telefoneConsultado.setId(resultado.getInt("id"));
				telefoneConsultado.setDdd(resultado.getString("ddd"));
				telefoneConsultado.setNumero(resultado.getString("numero"));
				telefoneConsultado.setAtivo(resultado.getBoolean("ativo"));
				telefoneConsultado.setMovel(resultado.getBoolean("movel"));
				telefoneConsultado.setIdCliente(resultado.getInt("id_cliente"));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar telefone com id: " + id + "\nCausa: " + erro.getMessage());
		}
		return telefoneConsultado;
	}

	// Executar DELETE
	public boolean excluir(int id) {
		boolean excluiu = false;
		Connection conexao = Banco.getConnection();
		String sql = " DELETE FROM TELEFONE " + "	WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setInt(1, id);
			int qtdeLinhasAtualizadas = query.executeUpdate();
			excluiu = qtdeLinhasAtualizadas > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir telefone" + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return excluiu;
	}
}
