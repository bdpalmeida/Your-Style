package br.com.YourStyle_Forms;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Controle_Usuario extends JDialog {

	private JPanel contentPane;
	private JLabel lblNomeUsuario;
	private JTextField txtNomeUsuario;
	private JLabel lblUsurio;
	private JTextField txtUsuario;
	private JLabel lblNovaSenha_1;
	private JPasswordField pwdNovaSenha;
	private JButton btnFechar;
	private JButton btnAlterar;
	
	private String usuario;
	
	private ConexaoBD connBD = new ConexaoBD();
	private JLabel lblConfirmarNovaSenha;
	private JPasswordField pwdConfirmarSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controle_Usuario frame = new Controle_Usuario("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param usuario 
	 * @return 
	 */
	public Controle_Usuario(final String nome_usuario) {
		setType(Type.UTILITY);		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Controle de Usu\u00E1rio");
		setBounds(100, 100, 450, 360);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Controle_Usuario.class.getResource("/br/com/YourStyle_Imagens/Flaticon_31618.png")));
		
		lblNomeUsuario = new JLabel("Nome do usu\u00E1rio");		
		
		txtNomeUsuario = new JTextField(nome_usuario);
		txtNomeUsuario.setColumns(10);
		
		usuario = connBD.ConsultaTabelaUsuarioUser(nome_usuario);
		
		lblUsurio = new JLabel("Usu\u00E1rio");
		
		txtUsuario = new JTextField(usuario);
		txtUsuario.setColumns(10);
		
		lblNovaSenha_1 = new JLabel("Nova Senha");
		
		pwdNovaSenha = new JPasswordField();
		
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				dispose();
			}
		});
		btnFechar.setMnemonic(KeyEvent.VK_F);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				if(String.valueOf(pwdNovaSenha.getPassword()).equals(String.valueOf(pwdConfirmarSenha.getPassword())))
					connBD.AlterarTabelaUsuario(nome_usuario, txtNomeUsuario.getText(), txtUsuario.getText(), pwdNovaSenha.getPassword());
				
				dispose();
			}
		});
		btnAlterar.setMnemonic(KeyEvent.VK_ENTER);
		
		lblConfirmarNovaSenha = new JLabel("Confirmar senha");
		
		pwdConfirmarSenha = new JPasswordField();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(153)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(143))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(309, Short.MAX_VALUE)
					.addComponent(btnAlterar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnFechar)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNomeUsuario)
						.addComponent(lblUsurio)
						.addComponent(lblNovaSenha_1)
						.addComponent(lblConfirmarNovaSenha))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(pwdNovaSenha, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
						.addComponent(txtUsuario, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
						.addComponent(txtNomeUsuario, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
						.addComponent(pwdConfirmarSenha, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNomeUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNomeUsuario))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsurio)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNovaSenha_1)
						.addComponent(pwdNovaSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConfirmarNovaSenha)
						.addComponent(pwdConfirmarSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFechar)
						.addComponent(btnAlterar))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);	
	}
}
