package view.telefonia;

import controller.telefonia.ClienteController;
import controller.telefonia.EnderecoController;
import model.exception.CampoInvalidoException;
import model.exception.CpfJaUtilizadoException;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;

import java.awt.EventQueue;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroCliente {

	private JFrame frmCadastroDeCliente;
	private JTextField txtNome;
	private JComboBox cbEndereco;
	private JLabel lblCpf;
	private JLabel lblNome;
	private JLabel lblEndereco;
	private JFormattedTextField txtfCpf;
	private JButton btnCadastrar;
	private ArrayList<Endereco> enderecos;
	private MaskFormatter mascaraCpf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroCliente window = new TelaCadastroCliente();
					window.frmCadastroDeCliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public TelaCadastroCliente() throws ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws  ParseException {
		frmCadastroDeCliente = new JFrame();
		frmCadastroDeCliente.setTitle("Cadastro de Cliente");
		frmCadastroDeCliente.setBounds(100, 100, 400, 190);
		frmCadastroDeCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroDeCliente.getContentPane().setLayout(null);
		
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(15, 15, 65, 15);
		frmCadastroDeCliente.getContentPane().add(lblNome);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(15, 40, 65, 15);
		frmCadastroDeCliente.getContentPane().add(lblCpf);
		
		lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(15, 65, 65, 15);
		frmCadastroDeCliente.getContentPane().add(lblEndereco);
		
		txtNome = new JTextField();
		txtNome.setBounds(90, 10, 270, 20);
		frmCadastroDeCliente.getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setValueContainsLiteralCharacters(false);

		txtfCpf = new JFormattedTextField(mascaraCpf);
		txtfCpf.setBounds(90, 35, 270, 20);
		frmCadastroDeCliente.getContentPane().add(txtfCpf);
		
		EnderecoController controller = new EnderecoController();
		enderecos = (ArrayList<Endereco>) controller.consultarTodos();
		
		cbEndereco = new JComboBox(enderecos.toArray());
		cbEndereco.setToolTipText("Selecione");
		cbEndereco.setSelectedIndex(-1);
		cbEndereco.setBounds(90, 60, 270, 20);
		frmCadastroDeCliente.getContentPane().add(cbEndereco);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente novoCliente = new Cliente();
				novoCliente.setNome(txtNome.getText());
				try {
					String cpfSemMascara = (String) mascaraCpf.stringToValue(
							txtfCpf.getText()); // CPF sem mascara
					novoCliente.setCpf(cpfSemMascara); 
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao converter o CPF", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				novoCliente.setEndereco((Endereco) cbEndereco.getSelectedItem());

				ClienteController controller = new ClienteController();
				try {
					controller.inserir(novoCliente);

					JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Cadastro com sucesso", JOptionPane.INFORMATION_MESSAGE);
				} catch (CpfJaUtilizadoException | EnderecoInvalidoException | CampoInvalidoException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCadastrar.setBounds(150, 105, 100, 25);
//		btnCadastrar.setOpaque(true); // Cor do botão
		frmCadastroDeCliente.getContentPane().add(btnCadastrar);
	}
}
