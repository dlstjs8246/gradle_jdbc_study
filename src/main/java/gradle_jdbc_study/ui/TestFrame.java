package gradle_jdbc_study.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.ui.content.DepartmentPanel;

public class TestFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
//		TitlePanel tp = new TitlePanel();
		DepartmentPanel dp = new DepartmentPanel();
		frame.add(dp);
		
		JButton btn1 = new JButton("확인");
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				Title title = tp.getItem();
				Department dept = dp.getItem();
				JOptionPane.showMessageDialog(null, dept);
			}
		});
		
		JButton btn2 = new JButton("취소");
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dp.tfClear();
			}
		});
		JButton btn3 = new JButton("추가");
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Department dept = new Department(5, "마케팅", 30);
				dp.setItem(dept);
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
