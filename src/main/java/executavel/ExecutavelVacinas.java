package executavel;

import java.time.LocalDateTime;
import java.util.Iterator;

import model.dao.vacinas.PessoaDAO;
import model.dao.vacinas.VacinaDAO;
import model.vo.vacinas.Pessoa;
import model.vo.vacinas.TipoFaseVacina;
import model.vo.vacinas.TipoPublicoVacina;
import model.vo.vacinas.Vacina;

public class ExecutavelVacinas {

	public static void main(String[] args) {
		
		// Cadastro de Pessoa
		Pessoa pessoa1 = new Pessoa(null, "Luis Alberto", LocalDateTime.of(1999, 5, 19, 0, 0), 'M', "10810699966", TipoPublicoVacina.PUBLICO_GERAL);
		Pessoa pessoa2 = new Pessoa(null, "Carlos", LocalDateTime.of(1985, 7, 14, 0, 0), 'M', "11122233344", TipoPublicoVacina.PESQUISADORES);
		Pessoa pessoa3 = new Pessoa(null, "Isabella", LocalDateTime.of(2003, 10, 4, 0, 0), 'F', "22233344455", TipoPublicoVacina.VOLUNTARIOS);
		PessoaDAO cadastradorDePessoas = new PessoaDAO();
//		cadastradorDePessoas.inserir(pessoa3);
//		
//		if (pessoa3.getId() != null) {
//			System.out.println("Nova pessoa cadastrada!");
//		} else {
//			System.out.println("Erro ao cadastrar nova pessoa!");
//		}
		
		// Atualizar Pessoa
//		Pessoa pessoaExistente = cadastradorDePessoas.consultarPorId(4);
//		pessoaExistente.setCpf("22244466688");
//		boolean atualizou = cadastradorDePessoas.atualizar(pessoaExistente);
//		
//		if (atualizou) {
//			System.out.println("Pessoa foi atualizada!");
//		} else {
//			System.out.println("Erro ao atualizar pessoa!");
//		}
		
		// Excluir Pessoa
//		if (cadastradorDePessoas.excluir(2)) {
//			System.out.println("Pessoa foi excluida!");
//		} else {
//			System.out.println("Erro ao excluir pessoa!");
//		}
		
		// Consultar Todas Pessoas
//		for (Pessoa pessoa : cadastradorDePessoas.consultarTodos()) {
//			System.out.println(pessoa.getNome());
//		}
		
		// Consultar pessoa por ID
//		System.out.println(cadastradorDePessoas.consultarPorId(3).getNome());
		
		
// ----------------------------------------------------------------------------------------------------------------------------
		
		
		// Cadastro de Vacina
		Vacina vacina1 = new Vacina(null, "Vacina da Gripe", "Brasil", TipoFaseVacina.INICIAL, LocalDateTime.now(), cadastradorDePessoas.consultarPorId(2));
		Vacina vacina2 = new Vacina(null, "Vacina Covid", "Estados Unidos", TipoFaseVacina.INICIAL, LocalDateTime.now(), cadastradorDePessoas.consultarPorId(1));
		Vacina vacina3 = new Vacina(null, "Vacina da Dengue", "Brasil", TipoFaseVacina.TESTES, LocalDateTime.now(), cadastradorDePessoas.consultarPorId(3));
		VacinaDAO cadastradorDeVacinas = new VacinaDAO();
//		cadastradorDeVacinas.inserir(vacina3);
//		
//		if (vacina3.getId() != null) {
//			System.out.println("Nova vacina cadastrada!");
//		} else {
//			System.out.println("Erro ao cadastrar nova vacina!");
//		}
		
		// Atualizar Vacina
//		Vacina vacinaExistente = cadastradorDeVacinas.consultarPorId(1);
//		vacinaExistente.setPesquisador(cadastradorDePessoas.consultarPorId(4));
//		vacinaExistente.setPaisOrigem("Brasil");
//		boolean atualizou = cadastradorDeVacinas.atualizar(vacinaExistente);
//		
//		if (atualizou) {
//			System.out.println("Vacina foi atualizada!");
//		} else {
//			System.out.println("Erro ao atualizar vacina!");
//		}
		
		// Excluir Vacina
//		if (cadastradorDeVacinas.excluir(2)) {
//			System.out.println("Vacina foi excluida!");
//		} else {
//			System.out.println("Erro ao excluir vacina!");
//		}
		
		// Consultar Todas Vacinas
//		for (Vacina vacina : cadastradorDeVacinas.consultarTodos()) {
//			System.out.println(vacina.getNome() + ", Pesquisador responsável: " + vacina.getPesquisador().getNome());
//		}
		
		// Consultar Vacina por ID
//		System.out.println(cadastradorDeVacinas.consultarPorId(1).getNome());

	}

}
