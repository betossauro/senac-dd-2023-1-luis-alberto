package model.dao.telefonia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.dao.Banco;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;


public class ClienteDAO {

    /**
     * Insere um novo cliente no banco
     *
     * @param novoCliente o cliente a ser persistido
     * @return o cliente inserido com a chave primária gerada
     */
    public Cliente inserir(Cliente novoCliente) {
        // Conectar ao banco
        Connection conexao = Banco.getConnection();
        String sql = " INSERT INTO CLIENTE (NOME, CPF, ATIVO, ID_ENDERECO) " + " VALUES (?,?,?,?) ";
        PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
        // Executar o INSERT
        try {
            query.setString(1, novoCliente.getNome());
            query.setString(2, novoCliente.getCpf());
            query.setBoolean(3, novoCliente.isAtivo());
            query.setInt(4, novoCliente.getEndereco().getId());
            query.execute();
            // Preencher o ID gerado no banco no objeto
            ResultSet resultado = query.getGeneratedKeys();
            if (resultado.next()) {
                novoCliente.setId(resultado.getInt(1));
            }
            if (novoCliente.getTelefones() != null && !novoCliente.getTelefones().isEmpty()){
                TelefoneDAO telefoneDAO = new TelefoneDAO();
                telefoneDAO.ativarTelefones(novoCliente.getId(), novoCliente.getTelefones());
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao inserir cliente" + "\nCausa: " + erro.getMessage());
        } finally {
            // Fechar a conexão
            Banco.closePreparedStatement(query);
            Banco.closeConnection(conexao);
        }
        return novoCliente;
    }

    public boolean atualizar(Cliente clienteAlterado) {
        Connection conexao = Banco.getConnection();
        String sql = " UPDATE CLIENTE SET NOME = ?, CPF = ?, ID_ENDERECO = ?, ATIVO = ? "
                + " WHERE ID = ?";
        PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
        int registrosAlterados = 0;
        try {
            query.setString(1, clienteAlterado.getNome());
            query.setString(2, clienteAlterado.getCpf());
            query.setInt(3, clienteAlterado.getEndereco().getId());
            query.setBoolean(4, clienteAlterado.isAtivo());
            query.setInt(5, clienteAlterado.getId());
            registrosAlterados = query.executeUpdate();

            TelefoneDAO telefoneDAO = new TelefoneDAO();
            telefoneDAO.ativarTelefones(clienteAlterado.getId(), clienteAlterado.getTelefones());
        } catch (SQLException e) {
            System.out.println("Erro ao inserir novo cliente.");
            System.out.println("Erro: " + e.getMessage());
        }

        return registrosAlterados > 0;
    }

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

    public List<Cliente> consultarTodos() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Connection conexao = Banco.getConnection();
        String sql = " SELECT * FROM CLIENTE ";

        PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
        try {
            ResultSet resultado = query.executeQuery();
            while (resultado.next()) {
                Cliente clienteBuscado = montarClienteComResultadoDoBanco(resultado);
                clientes.add(clienteBuscado);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar todos os clientes. \n Causa:" + e.getMessage());
        } finally {
            Banco.closePreparedStatement(query);
            Banco.closeConnection(conexao);
        }
        return clientes;
    }

    public boolean cpfJaUtilizado(String cpfBuscado) {
        boolean cpfJaUtilizado = false;
        Connection conexao = Banco.getConnection();
        String sql = " SELECT COUNT(*) FROM CLIENTE " + " WHERE CPF = ? ";
        PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
        try {
            query.setString(1, cpfBuscado);
            ResultSet resultado = query.executeQuery();
            if (resultado.next()) {
                cpfJaUtilizado = resultado.getInt(1) > 0;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao verificar uso do CPF: " + cpfBuscado + "\nCausa: " + erro.getMessage());
        } finally {
            Banco.closePreparedStatement(query);
            Banco.closeConnection(conexao);
        }
        return cpfJaUtilizado;
    }

    public boolean excluir(int id) {
        Connection conexao = Banco.getConnection();
        String sql = " DELETE FROM CLIENTE WHERE ID = " + id;
        Statement query = Banco.getStatement(conexao);
        int quantidadeLinhasAfetadas = 0;
        try {
            quantidadeLinhasAfetadas = query.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente.");
            System.out.println("Erro: " + e.getMessage());
        }
        boolean excluiu = quantidadeLinhasAfetadas > 0;

        if (excluiu) {
            TelefoneDAO telefoneDAO = new TelefoneDAO();
            telefoneDAO.desativarTelefones(id);
        }
        return excluiu;
    }


    private Cliente montarClienteComResultadoDoBanco(ResultSet resultado) throws SQLException {
        Cliente clienteBuscado = new Cliente();
        clienteBuscado.setId(resultado.getInt("id"));
        clienteBuscado.setNome(resultado.getString("nome"));
        clienteBuscado.setCpf(resultado.getString("cpf"));
        clienteBuscado.setAtivo(resultado.getBoolean("ativo"));
        int idEnderecoDoCliente = resultado.getInt("id_endereco");
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        Endereco endereco = enderecoDAO.consultarPorId(idEnderecoDoCliente);
        clienteBuscado.setEndereco(endereco);
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        clienteBuscado.setTelefones(telefoneDAO.consultarPorIdCliente(clienteBuscado.getId()));
        return clienteBuscado;
    }

    public int contarClientesQueResidemNoEndereco(Integer idEndereco) {
        int totalClientesDoEnderecoBuscado = 0;
        Connection conexao = Banco.getConnection();
        String sql = " SELECT COUNT(ID) FROM CLIENTE "
                + " WHERE ID_ENDERECO = ? ";
        PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
        try {
            query.setInt(1, idEndereco);
            ResultSet resultado = query.executeQuery();
            if(resultado.next()) {
                totalClientesDoEnderecoBuscado = resultado.getInt(1);
            }
        }catch (Exception e) {
            System.out.println("Erro contar os clientes que residem em um endereço. \n Causa:" + e.getMessage());
        }finally {
            Banco.closePreparedStatement(query);
            Banco.closeConnection(conexao);
        }
        return totalClientesDoEnderecoBuscado;
    }
}
