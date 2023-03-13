package executavel;

import java.util.ArrayList;
import java.util.List;

import model.dao.telefonia.EnderecoDAO;
import model.dao.telefonia.TelefoneDAO;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;
import model.vo.telefonia.Telefone;

public class ExecutavelTelefonia {

	public static void main(String[] args) {

//		Endereco endereco1 = new Endereco(null, "88000123", "Mauro Ramos", "50", "Centro", "Florianópolis", "SC");
		Telefone telefone1 = new Telefone(null, null, "48", "998577999", false, true);
		Telefone telefone2 = new Telefone(null, 2, "48", "998577999", false, true);
		
		
//		EnderecoDAO salvadorDeEnderecos = new EnderecoDAO();
//		salvadorDeEnderecos.inserir(endereco1);
//		
//		if (endereco1.getId() != null) {
//			System.out.println("Novo endereço cadastrado!");
//		} else {
//			System.out.println("Erro ao cadastrar endereço!");
//		}
		
		TelefoneDAO salvadorDeTelefones = new TelefoneDAO();
		salvadorDeTelefones.inserir(telefone2);
		
		if (telefone2.getId() != null) {
			System.out.println("Novo telefone cadastrado!");
		} else {
			System.out.println("Erro ao cadastrar telefone!");
		}

//		List<Telefone> telefonesDoSocrates = new ArrayList<Telefone>();
//		Telefone telefone1 = new Telefone(null, null, "48", "32328888", true, false);
//		telefonesDoSocrates.add(telefone1);
//		telefonesDoSocrates.add(new Telefone(null, null, "48", "98881234", true, true));
//
//		Cliente pele = new Cliente(null, "Edson Arantes", "11122233344", true, null, endereco1);
//		Cliente socrates = new Cliente(null, "Sócrates Brasileiro", "33322233344", true, telefonesDoSocrates, endereco1);
//
//		List<Cliente> clientes = new ArrayList<Cliente>();
//		clientes.add(pele);
//		clientes.add(socrates);
//
//		System.out.println("--------------- Clientes ---------------");
//		for (Cliente c : clientes) {
//			System.out.println(c.toString());
//		}
	}

}
