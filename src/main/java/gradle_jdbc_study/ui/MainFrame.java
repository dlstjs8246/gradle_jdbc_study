package gradle_jdbc_study.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gradle_jdbc_study.ui.content.DepartmentPanel;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private LoginFrame LoginFrame;
	private JButton btnLogout;
	private JPanel pLogin;
	private JPanel pBtns;
	private JButton btnDept;
	private JButton btnEmployee;
	private JButton btnTitle;

	public MainFrame() {
		initialize();
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pLogin = new JPanel();
		contentPane.add(pLogin);
		pLogin.setLayout(new BoxLayout(pLogin, BoxLayout.X_AXIS));
		
		JLabel lblLoginName = new JLabel("New label");
		pLogin.add(lblLoginName);
		lblLoginName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginName.setText(LoginFrame.loginEmp.getEmpName());
		
		btnLogout = new JButton("로그아웃");
		pLogin.add(btnLogout);
		btnLogout.addActionListener(this);
		
		pBtns = new JPanel();
		contentPane.add(pBtns);
		pBtns.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnDept = new JButton("부서 정보");
		btnDept.addActionListener(this);
		pBtns.add(btnDept);
		
		btnEmployee = new JButton("사원 정보");
		btnEmployee.addActionListener(this);
		pBtns.add(btnEmployee);
		
		btnTitle = new JButton("직책 정보");
		btnTitle.addActionListener(this);
		pBtns.add(btnTitle);
	}
	
	public void setLoginFrame(LoginFrame loginFrame) {
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEmployee) {
			btnEmployeeActionPerformed(e);
		}
		if (e.getSource() == btnDept) {
			btnDeptActionPerformed(e);
		}
		if (e.getSource() == btnTitle) {
			btnTitleActionPerformed(e);
		}
		if (e.getSource() == btnLogout) {
			btnLogoutActionPerformed(e);
		}
	}
	protected void btnLogoutActionPerformed(ActionEvent e) {
		LoginFrame.loginEmp = null;
		dispose();
		if(LoginFrame==null) {
			LoginFrame = new LoginFrame();
			LoginFrame.setVisible(true);
			LoginFrame.clearTf();
		}
	}
	protected void btnTitleActionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		frame.add(new TitleUIPanel());
		frame.setBounds(100, 100, 600, 600);
		frame.setVisible(true);
	}
	protected void btnDeptActionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		frame.add(new DepartmentUIPanel());
		frame.setBounds(100, 100, 600, 600);
		frame.setVisible(true);
	}
	protected void btnEmployeeActionPerformed(ActionEvent e) {
		
	}
}
