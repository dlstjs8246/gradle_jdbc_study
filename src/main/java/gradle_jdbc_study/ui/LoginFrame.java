package gradle_jdbc_study.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.ui.service.LoginService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tfEmpNo;
	private JPasswordField pfPassWd;
	private JButton btnLogin;
	private JButton btnCancel;
	private LoginService service;
	private MainFrame main;
	static Employee loginEmp;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		intialize();	
	}

	private void intialize() {
		service = new LoginService();
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 368, 174);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pContent = new JPanel();
		pContent.setBorder(new TitledBorder(null, "\uB85C\uADF8\uC778", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pContent);
		pContent.setLayout(new GridLayout(2, 0, 10, 10));
		
		JLabel lblEmpNo = new JLabel("사원번호");
		lblEmpNo.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblEmpNo);
		
		tfEmpNo = new JTextField();
		pContent.add(tfEmpNo);
		tfEmpNo.setColumns(10);
		
		JLabel lblPasswd = new JLabel("비밀번호");
		lblPasswd.setHorizontalAlignment(SwingConstants.RIGHT);
		pContent.add(lblPasswd);
		
		pfPassWd = new JPasswordField();
		pContent.add(pfPassWd);
		
		JPanel pBtn = new JPanel();
		contentPane.add(pBtn, BorderLayout.SOUTH);
		
		btnLogin = new JButton("로그인");
		btnLogin.addActionListener(this);
		pBtn.add(btnLogin);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtn.add(btnCancel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			btnCancelActionPerformed(e);
		}
		if (e.getSource() == btnLogin) {
			btnLoginActionPerformed(e);
		}
	}
	protected void btnLoginActionPerformed(ActionEvent e) {
		int empNo = Integer.parseInt(tfEmpNo.getText().trim());
		String passwd = new String(pfPassWd.getPassword());
		loginEmp = service.login(new Employee(empNo,passwd));
		if(loginEmp == null) {
			JOptionPane.showMessageDialog(null, "사원 번호 추가 및 비밀번호가 맞지 않음");
			return;
		}
		JOptionPane.showMessageDialog(null, loginEmp.getEmpName() + "님 반갑습니다");
		if(main==null) {
			main = new MainFrame();
			main.setLoginFrame(this);
		}
		dispose();
		main.setVisible(true);
	}
	protected void btnCancelActionPerformed(ActionEvent e) {
		clearTf();
	}

	public void clearTf() {
		tfEmpNo.setText("");
		pfPassWd.setText("");
	}
}
