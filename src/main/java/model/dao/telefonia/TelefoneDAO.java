package model.dao.telefonia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
				telefoneConsultado = converterResultSetParaEntidade(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar telefone com id: " + id + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return telefoneConsultado;
	}
	
	// Executar Consultar Todos
	public List<Telefone> consultarTodos() {
		Connection conexao = Banco.getConnection();
		List<Telefone> telefones = new ArrayList<Telefone>();
		String sql = " SELECT * FROM TELEFONE ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			ResultSet resultado = query.executeQuery();
			while (resultado.next()) {
				Telefone telefoneConsultado = converterResultSetParaEntidade(resultado);
				telefones.add(telefoneConsultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar todos os endereços." + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return telefones;
	}

	private Telefone converterResultSetParaEntidade(ResultSet resultado) throws SQLException {
		Telefone telefoneConsultado = new Telefone();
		telefoneConsultado.setId(resultado.getInt("id"));
		telefoneConsultado.setDdd(resultado.getString("ddd"));
		telefoneConsultado.setNumero(resultado.getString("numero"));
		telefoneConsultado.setAtivo(resultado.getBoolean("ativo"));
		telefoneConsultado.setMovel(resultado.getBoolean("movel"));
		telefoneConsultado.setIdCliente(resultado.getInt("id_cliente"));
		return telefoneConsultado;
	}

	// Método para consultar todos os telefones para determinado ID
	public List<Telefone> consultarPorIdCliente(Integer id) {
		List<Telefone> telefones = new ArrayList<Telefone>();
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM TELEFONE " + "	WHERE ID_CLIENTE = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setInt(1, id);
			ResultSet resultado = query.executeQuery();
			while (resultado.next()) {
				Telefone telefoneConsultado = converterResultSetParaEntidade(resultado);
				telefones.add(telefoneConsultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar todos os telefones do cliente informado. " + "\nCausa: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return telefones;
	}

	// Método para ativar telefone de um cliente
	public void ativarTelefones(Integer idDono, List<Telefone> telefones) {
		for (Telefone telefoneDoCliente : telefones) {
			telefoneDoCliente.setIdCliente(idDono);
			telefoneDoCliente.setAtivo(true);
			if (telefoneDoCliente.getId() > 0) {
				// UPDATE no Telefone
				this.atualizar(telefoneDoCliente);
			} else {
				// INSERT no Telefone
				this.inserir(telefoneDoCliente);
			}
		}
	}

	// Método para desativar telefone de um cliente
	public void desativarTelefones(int id) {
		Connection conn = Banco.getConnection();
		String sql = " UPDATE TELEFONIA "
				+ " SET ID_CLIENTE=NULL, ativo=0 "
				+ " WHERE ID_CLIENTE=? ";

		PreparedStatement stmt = Banco.getPreparedStatement(conn, sql);

		try {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao desativar telefone.");
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
