package model.dao.vacinas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.dao.Banco;
import model.vo.vacinas.Pessoa;
import model.vo.vacinas.TipoFaseVacina;
import model.vo.vacinas.Vacina;

public class VacinaDAO {

	public Vacina inserir(Vacina novaVacina) {
		// Conectar ao banco
				Connection conexao = Banco.getConnection();
				String sql = " INSERT INTO VACINA (NOME, PAIS_ORIGEM, IDTIPOFASEVACINA, DATA_INICIO_DA_PESQUISA, IDPESSOA) " + " VALUES (?,?,?,?,?) ";
				PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
				// Executar o INSERT
				try {
					query.setString(1, novaVacina.getNome());
					query.setString(2, novaVacina.getPaisOrigem());
					query.setInt(3, novaVacina.getFaseVacina().getValor());
					query.setObject(4, novaVacina.getDataInicioDaPesquisa());
					query.setInt(5, novaVacina.getPesquisador().getId());
					query.execute();
					// Preencher o ID gerado no banco no objeto
					ResultSet resultado = query.getGeneratedKeys();
					if (resultado.next()) {
						novaVacina.setId(resultado.getInt(1));
					}
				} catch (SQLException erro) {
					System.out.println("Erro ao cadastrar vacina" + "\nCausa: " + erro.getMessage());
				} finally {
					// Fechar a conexão
					Banco.closePreparedStatement(query);
					Banco.closeConnection(conexao);
				}
		return novaVacina;
	}
	
	// UPDATE vacina
		public boolean atualizar(Vacina vacinaEditada) {
			boolean atualizou = false;
			Connection conexao = Banco.getConnection();
			String sql = " UPDATE VACINA " + " SET NOME = ?, PAIS_ORIGEM = ?, IDTIPOFASEVACINA = ?, DATA_INICIO_DA_PESQUISA = ?, IDPESSOA = ? "
					+ "	WHERE IDVACINA = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setString(1, vacinaEditada.getNome());
				query.setString(2, vacinaEditada.getPaisOrigem());
				query.setInt(3, vacinaEditada.getFaseVacina().getValor());
				query.setObject(4, vacinaEditada.getDataInicioDaPesquisa());
				query.setInt(5, vacinaEditada.getPesquisador().getId());
				query.setInt(6, vacinaEditada.getId());

				int qtdeLinhasAtualizadas = query.executeUpdate();
				atualizou = qtdeLinhasAtualizadas > 0;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar a vacina." + "\nCausa: " + erro.getMessage());
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
			String sql = " DELETE FROM VACINA " + "	WHERE IDVACINA = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);

			try {
				query.setInt(1, id);
				int qtdeLinhasAtualizadas = query.executeUpdate();
				excluiu = qtdeLinhasAtualizadas > 0;
			} catch (SQLException erro) {
				System.out.println("Erro ao excluir vacina." + "\nCausa: " + erro.getMessage());
			} finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
			return excluiu;
		}

		// Executar Consulta por ID
		public Vacina consultarPorId(int id) {
			Vacina vacinaConsultada = null;
			Connection conexao = Banco.getConnection();
			String sql = " SELECT * FROM VACINA " + "	WHERE IDVACINA = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setInt(1, id);
				ResultSet resultado = query.executeQuery();
				if (resultado.next()) {
					vacinaConsultada = converterResultSetParaEntidade(resultado);
				}
			} catch (SQLException erro) {
				System.out.println("Erro ao buscar vacina com id: " + id + "\nCausa: " + erro.getMessage());
			} finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
			return vacinaConsultada;
		}

		// Executar Consultar Todos
		public List<Vacina> consultarTodos() {
			Connection conexao = Banco.getConnection();
			List<Vacina> vacinas = new ArrayList<Vacina>();
			String sql = " SELECT * FROM VACINA ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				ResultSet resultado = query.executeQuery();
				while (resultado.next()) {
					Vacina enderecoConsultado = converterResultSetParaEntidade(resultado);
					vacinas.add(enderecoConsultado);
				}
			} catch (SQLException erro) {
				System.out.println("Erro ao buscar todas as vacinas." + "\nCausa: " + erro.getMessage());
			} finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
			return vacinas;
		}

		// Método para deixar o código clean e evitar repetição
		private Vacina converterResultSetParaEntidade(ResultSet resultado) throws SQLException {
			Vacina vacinaConsultada = new Vacina();
			vacinaConsultada.setId(resultado.getInt("idvacina"));
			vacinaConsultada.setNome(resultado.getString("nome"));
			vacinaConsultada.setPaisOrigem(resultado.getString("pais_origem"));
			vacinaConsultada.setFaseVacina(TipoFaseVacina.getTipoFaseVacinaPorValor(resultado.getInt("idtipofasevacina")));
			vacinaConsultada.setDataInicioDaPesquisa(LocalDateTime.parse(resultado.getString("data_inicio_da_pesquisa"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			// Buscar pessoa
			int idPessoa = resultado.getInt("idpessoa");
			PessoaDAO pessoaDAO = new PessoaDAO();
			Pessoa pessoa = pessoaDAO.consultarPorId(idPessoa);
			vacinaConsultada.setPesquisador(pessoa);
			return vacinaConsultada;
		}
}
