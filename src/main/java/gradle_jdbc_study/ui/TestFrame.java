package gradle_jdbc_study.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.ui.content.DepartmentPanel;
import gradle_jdbc_study.ui.content.EmployeePanel;
import gradle_jdbc_study.ui.service.EmployeeUIService;

public class TestFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
//		TitlePanel tp = new TitlePanel();
//		DepartmentPanel dp = new DepartmentPanel();
		EmployeePanel ep = new EmployeePanel();
		EmployeeUIService service = new EmployeeUIService();
		List<Department> list = service.showDeptList();
		ep.setCmbDeptList(list);
		ep.getCmbDept().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					ep.setCmbMgnList(service.showManagerList((Department)e.getItem()));
				}
			}
		});
		List<Title> titleList = service.showTitle();
		ep.setCmbTitleList(titleList);
		frame.add(ep);
		frame.setBounds(100, 100, 450, 360);
		frame.setVisible(true);
		
		JButton btn1 = new JButton("확인");
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				Title title = tp.getItem();
				Employee emp = ep.getItem();
				JOptionPane.showMessageDialog(null, emp);
			}
		});
		
		JButton btn2 = new JButton("취소");
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ep.tfClear();
			}
		});
		JButton btn3 = new JButton("추가");
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JPanel btns = new JPanel();
		btns.add(btn1);
		btns.add(btn2);
		btns.add(btn3);
		frame.add(btns, BorderLayout.SOUTH);
		frame.setBounds(100, 100, 450, 300);
		frame.setVisible(true);
	}

}
