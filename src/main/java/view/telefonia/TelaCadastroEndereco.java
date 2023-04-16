package view.telefonia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.telefonia.EnderecoController;
import model.exception.CampoInvalidoException;
import model.vo.telefonia.Endereco;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class TelaCadastroEndereco {

	private JFrame frmCadastroDeEndereco;
	private JTextField txtCep;
	private JTextField txtRua;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JTextField txtNumero;
	private JLabel lblRua;
	private JLabel lblBairro;
	private JLabel lblCep;
	private JLabel lblCidade;
	private JLabel lblNumero;
	private JLabel lblEstado;
	private JButton btnCadastrar;
	private JComboBox cbEstado;

	// TODO chamar API ou backend futuramente
	private String[] estados = { "Paraná", "Rio Grande do Sul", "Santa Catarina" };
	private MaskFormatter mascaraCep;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroEndereco window = new TelaCadastroEndereco();
					window.frmCadastroDeEndereco.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCadastroEndereco() throws ParseException{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws ParseException{
		frmCadastroDeEndereco = new JFrame();
		frmCadastroDeEndereco.setTitle("Cadastro de Endereço");
		frmCadastroDeEndereco.setBounds(100, 100, 375, 250);
		frmCadastroDeEndereco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroDeEndereco.getContentPane().setLayout(null);

		lblCep = new JLabel("CEP:");
		lblCep.setBounds(15, 15, 60, 14);
		frmCadastroDeEndereco.getContentPane().add(lblCep);

		mascaraCep = new MaskFormatter("#####-###");
		mascaraCep.setValueContainsLiteralCharacters(false);

		txtCep = new JFormattedTextField(mascaraCep);
		txtCep.setBounds(60, 10, 275, 20);
		frmCadastroDeEndereco.getContentPane().add(txtCep);
		txtCep.setColumns(10);

		lblRua = new JLabel("Rua:");
		lblRua.setBounds(15, 40, 60, 14);
		frmCadastroDeEndereco.getContentPane().add(lblRua);

		lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(15, 65, 60, 14);
		frmCadastroDeEndereco.getContentPane().add(lblBairro);

		lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(15, 90, 60, 14);
		frmCadastroDeEndereco.getContentPane().add(lblCidade);

		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(15, 115, 60, 14);
		frmCadastroDeEndereco.getContentPane().add(lblNumero);

		lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(15, 140, 60, 14);
		frmCadastroDeEndereco.getContentPane().add(lblEstado);

		txtRua = new JTextField();
		txtRua.setBounds(60, 35, 275, 20);
		frmCadastroDeEndereco.getContentPane().add(txtRua);
		txtRua.setColumns(10);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(60, 60, 275, 20);
		frmCadastroDeEndereco.getContentPane().add(txtBairro);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(60, 85, 275, 20);
		frmCadastroDeEndereco.getContentPane().add(txtCidade);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(60, 110, 275, 20);
		frmCadastroDeEndereco.getContentPane().add(txtNumero);

		cbEstado = new JComboBox(estados);
		cbEstado.setToolTipText("Selecione");
		cbEstado.setSelectedIndex(-1);
		cbEstado.setBounds(60, 135, 275, 22);
		frmCadastroDeEndereco.getContentPane().add(cbEstado);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Endereco endereco = new Endereco();
				endereco.setCep(txtCep.getText());
				endereco.setRua(txtRua.getText());
				endereco.setNumero(txtNumero.getText());
				endereco.setBairro(txtBairro.getText());
				endereco.setCidade(txtCidade.getText());
				endereco.setEstado((String) cbEstado.getSelectedItem());

				EnderecoController controller = new EnderecoController();
				try {
					controller.inserir(endereco);
				} catch (CampoInvalidoException e) {
					JOptionPane.showMessageDialog(null, "Preencha os seguintes campos: \n" + e.getMessage(), "Atenção",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnCadastrar.setBounds(130, 170, 100, 23);
		frmCadastroDeEndereco.getContentPane().add(btnCadastrar);
	}
}
