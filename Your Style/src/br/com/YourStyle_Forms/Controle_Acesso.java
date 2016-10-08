package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
import javax.swing.border.LineBorder;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Controle_Acesso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel label;
	private JLabel label_1;
	private JLabel lblUsurio;
	private JTextField txtUsuario;
	private JLabel lblSenha;
	private JPasswordField pwdSenha;
	private JButton okButton;
	private JButton cancelButton;
	
	private String nome_usuario;
	
	private ConexaoBD connBD = new ConexaoBD();
	private JLabel lblInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			Controle_Acesso dialog = new Controle_Acesso();
			if( dialog.connBD.TestaConexao() ){
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
			else
				dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Controle_Acesso() {
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Controle de Acesso");
		setBounds(100, 100, 450, 245);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Controle_Acesso.class.getResource("/br/com/YourStyle_Imagens/IconeYourStyle.png")));
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Controle_Acesso.class.getResource("/br/com/YourStyle_Imagens/YourStyle.png")));
		
		lblUsurio = new JLabel("Usu\u00E1rio");
		
		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if( pwdSenha.getPassword().length > 0 )
					lblInfo.setText("");
			}
		});
		txtUsuario.setToolTipText("Digite seu usuario.");
		txtUsuario.setColumns(10);
		
		lblSenha = new JLabel("Senha");
		
		pwdSenha = new JPasswordField();
		pwdSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if( txtUsuario.getText().length() > 0)
					lblInfo.setText("");
			}
		});
		pwdSenha.setToolTipText("Digite sua senha.");
		{
			okButton = new JButton("Entrar");
			okButton.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent arg0) {					
					if( connBD.ConsultaTabelaUsuario(txtUsuario.getText(), pwdSenha.getPassword()) ){
						lblInfo.setText("Acesso Permitido.");
						nome_usuario = connBD.ConsultaTabelaUsuarioNome(txtUsuario.getText(), pwdSenha.getPassword());						
						setVisible(false);
						new Principal(nome_usuario).setVisible(true);						
						dispose();
					}
					else{
						LimparCampos();					
						lblInfo.setText("Acesso Negado: Usuário ou senha incorretos!");						
					}
				}
			});
			okButton.setMnemonic(KeyEvent.VK_ENTER);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Sair");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			cancelButton.setMnemonic(KeyEvent.VK_S);
			cancelButton.setActionCommand("Cancel");
		}
		
		lblInfo = new JLabel("");
		lblInfo.setForeground(Color.RED);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
							.addGap(23)
							.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
							.addGap(152))
						.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblSenha)
										.addComponent(lblUsurio))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(pwdSenha, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
										.addComponent(txtUsuario, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)))
								.addComponent(lblInfo))
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(cancelButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(okButton)
							.addContainerGap())))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1)
					.addGap(26)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsurio)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(pwdSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfo)
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(okButton)
						.addComponent(cancelButton))
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(24)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addGap(14))
		);
		contentPanel.setLayout(gl_contentPanel);
	}

	protected void LimparCampos() {
		txtUsuario.setText("");
		pwdSenha.setText("");
		
		txtUsuario.requestFocus();
	}
}
