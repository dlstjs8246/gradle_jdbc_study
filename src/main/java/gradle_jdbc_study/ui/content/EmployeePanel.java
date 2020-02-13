package gradle_jdbc_study.ui.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.ui.MyDocumentListener;
import gradle_jdbc_study.ui.exception.InvalidCheckExcepation;
import gradle_jdbc_study.util.LogUtil;

@SuppressWarnings("serial")
public class EmployeePanel extends AbsItemPanel<Employee> implements ActionListener {
	private JTextField tfNo;
	private JTextField tfName;
	private JComboBox<Department> cmbDept;
	private JComboBox<Employee> cmbMgn;
	private JComboBox<Title> cmbTitle;
	private JPasswordField pfPasswd;
	private JPasswordField pfConfirmPasswd;
	private JLabel lblPasswdEqual;
	private Dimension picDimension = new Dimension(150, 300);
	private JLabel lblImg;
	private JButton btnAddImg;
	private JSpinner sSalary;
	private JDateChooser tfHireDate;
	private boolean isPic;
	private String picPath;

	public EmployeePanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel pWest = new JPanel();
		add(pWest, BorderLayout.WEST);
		pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));

		JPanel pImg = new JPanel();
		pWest.add(pImg);

		lblImg = new JLabel("");
		lblImg.setSize(picDimension);
		lblImg.setPreferredSize(picDimension);
		lblImg.setIcon(new ImageIcon(
				new ImageIcon("D:\\workspace_gradle\\gradle_jdbc_study\\src\\main\\resources\\no-image.png").getImage()
						.getScaledInstance((int) picDimension.getWidth(), (int) picDimension.getHeight(), 1)));
		pImg.add(lblImg);

		JPanel pButton = new JPanel();
		pWest.add(pButton);
		pButton.setLayout(new BoxLayout(pButton, BoxLayout.X_AXIS));

		btnAddImg = new JButton("증명 사진");
		btnAddImg.addActionListener(this);
		pButton.add(btnAddImg);

		JPanel pCenter = new JPanel();
		add(pCenter, BorderLayout.CENTER);
		pCenter.setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblNo = new JLabel("사원번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblNo);

		tfNo = new JTextField();
		pCenter.add(tfNo);
		tfNo.setColumns(10);

		JLabel lblName = new JLabel("사원이름");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblName);

		tfName = new JTextField();
		tfName.setColumns(10);
		pCenter.add(tfName);

		JLabel lblDept = new JLabel("부서");
		lblDept.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblDept);

		cmbDept = new JComboBox<>();
		pCenter.add(cmbDept);

		JLabel lblMgn = new JLabel("직속상사");
		lblMgn.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblMgn);

		cmbMgn = new JComboBox<>();
		pCenter.add(cmbMgn);

		JLabel lblTitle = new JLabel("직책");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblTitle);

		cmbTitle = new JComboBox<Title>();
		pCenter.add(cmbTitle);

		JLabel lblHireDate = new JLabel("입사일");
		lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblHireDate);

		tfHireDate = new JDateChooser(new Date(), "yyyy-MM-dd hh:mm");
		pCenter.add(tfHireDate);

		JLabel lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblSalary);

		sSalary = new JSpinner();
		sSalary.setModel(new SpinnerNumberModel(1500000, 1000000, 5000000, 100000));
		pCenter.add(sSalary);

		lblPasswdEqual = new JLabel("비밀번호 확인");
		lblPasswdEqual.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswdEqual.setFont(new Font("굴림", Font.BOLD, 12));
		lblPasswdEqual.setForeground(Color.RED);

		JLabel lblPasswd = new JLabel("비밀번호");
		lblPasswd.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblPasswd);
		MyDocumentListener passListener = new MyDocumentListener() {
			@Override
			public void msg() {
				String pw1 = new String(pfPasswd.getPassword());
				String pw2 = new String(pfConfirmPasswd.getPassword());
				if (pw1.length() == 0 || pw2.length() == 0) {
					lblPasswdEqual.setText("");
				} else if (pw1.equals(pw2)) {
					lblPasswdEqual.setText("비밀번호 일치");
					lblPasswdEqual.setForeground(Color.GREEN);
				} else {
					lblPasswdEqual.setText("비밀번호 불일치");
					lblPasswdEqual.setForeground(Color.RED);
				}
			}
		};

		pfPasswd = new JPasswordField();
		pfPasswd.getDocument().addDocumentListener(passListener);
		pCenter.add(pfPasswd);

		JLabel lblConFirmPasswd = new JLabel("비밀번호 확인");
		lblConFirmPasswd.setHorizontalAlignment(SwingConstants.RIGHT);
		pCenter.add(lblConFirmPasswd);

		pfConfirmPasswd = new JPasswordField();
		pfConfirmPasswd.getDocument().addDocumentListener(passListener);
		pCenter.add(pfConfirmPasswd);

		JPanel panel = new JPanel();
		pCenter.add(panel);
		pCenter.add(lblPasswdEqual);
	}

	public void setCmbDeptList(List<Department> deptList) {
		DefaultComboBoxModel<Department> model = new DefaultComboBoxModel<Department>(new Vector<>(deptList));
		cmbDept.setModel(model);
		cmbDept.setSelectedIndex(-1);
	}

	public void setCmbMgnList(List<Employee> empList) {
		DefaultComboBoxModel<Employee> model = new DefaultComboBoxModel<Employee>(new Vector<>(empList));
		cmbMgn.setModel(model);
		cmbMgn.setSelectedIndex(-1);
	}

	public void setCmbTitleList(List<Title> titleList) {
		DefaultComboBoxModel<Title> model = new DefaultComboBoxModel<Title>(new Vector<>(titleList));
		cmbTitle.setModel(model);
		cmbTitle.setSelectedIndex(-1);
	}

	@Override
	public Employee getItem() {
		int empNo = Integer.parseInt(tfNo.getText());
		String empName = tfName.getText();
		Title title = (Title)cmbTitle.getSelectedItem();
		Employee manager = (Employee)cmbMgn.getSelectedItem();
		int salary = (int)sSalary.getValue();
		Department dept = (Department)cmbDept.getSelectedItem();
		Date hireDate = tfHireDate.getDate();
		Employee emp = new Employee(empNo, empName, title, manager, salary, dept, hireDate);	
		File imgFile = new File(picPath);
		isPic = imgFile.length()==0?false:true;
		if(isPic) {
			byte[] pic = new byte[(int)imgFile.length()];
			try(FileInputStream fis = new FileInputStream(imgFile)) {
				fis.read(pic);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			emp.setPic(pic);
		}
		validCheck();
		return emp;
	}

	@Override
	public void setItem(Employee item) {
		tfNo.setText(item.getEmpNo()+"");
		tfName.setText(item.getEmpName());
		cmbTitle.setSelectedItem(item.getTitle());
		cmbDept.setSelectedItem(item.getDept());
		cmbMgn.setSelectedItem(item.getManager());
		sSalary.setValue(item.getSalary());
		tfHireDate.setDate(item.getHireDate());
		isPic = item.getPic()==null?false:true;
		String imgPath = "D:\\workspace_gradle\\gradle_jdbc_study\\images\\";
		if(isPic) {
			File file = new File(imgPath + item.getEmpNo() + ".jpg");
			try(FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(item.getPic());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			lblImg.setIcon(new ImageIcon(
					new ImageIcon(imgPath + item.getEmpNo() + ".jpg").getImage()
							.getScaledInstance((int) picDimension.getWidth(), (int) picDimension.getHeight(), 1)));
		}
		else {
			lblImg.setIcon(new ImageIcon(
					new ImageIcon("D:\\workspace_gradle\\gradle_jdbc_study\\src\\main\\resources\\no-image.png").getImage()
							.getScaledInstance((int) picDimension.getWidth(), (int) picDimension.getHeight(), 1)));
		}
	}

	@Override
	public void tfClear() {
		tfNo.setText("");
		tfName.setText("");
		cmbTitle.setSelectedIndex(-1);
		cmbMgn.setSelectedIndex(-1);
		sSalary.setModel(new SpinnerNumberModel(1500000, 1000000, 5000000, 100000));
		cmbDept.setSelectedIndex(-1);
		tfHireDate.setDate(new Date());
		lblImg.setIcon(new ImageIcon(
				new ImageIcon("D:\\workspace_gradle\\gradle_jdbc_study\\src\\main\\resources\\no-image.png").getImage()
						.getScaledInstance((int) picDimension.getWidth(), (int) picDimension.getHeight(), 1)));
		pfPasswd.setText("");
		pfConfirmPasswd.setText("");
	}

	@Override
	public void validCheck() {
	      if (tfNo.getText().contentEquals("") || tfName.getText().contentEquals("") || cmbDept.getSelectedIndex() == -1
	            || cmbMgn.getSelectedIndex() == -1 || cmbTitle.getSelectedIndex() == -1
	            || !lblPasswdEqual.getText().contentEquals("비밀번호 일치")) {
	         throw new InvalidCheckExcepation();
	      }
	   }

	public JComboBox<Department> getCmbDept() {
		return cmbDept;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddImg) {
			btnAddImgActionPerformed(e);
		}
	}

	protected void btnAddImgActionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG or GIF or PNG", "jpg","gif","png");
		chooser.setFileFilter(filter);
		int res = chooser.showOpenDialog(null);
		if(res != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일이 선택되지 않았습니다");
			return;
		}
		picPath = chooser.getSelectedFile().getPath();
		lblImg.setIcon(new ImageIcon(
				new ImageIcon(picPath).getImage()
						.getScaledInstance((int) picDimension.getWidth(), (int) picDimension.getHeight(), 1)));
	}
}
