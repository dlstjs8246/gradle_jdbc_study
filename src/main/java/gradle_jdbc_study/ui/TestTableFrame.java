package gradle_jdbc_study.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import gradle_jdbc_study.dao.impl.DepartmentDaoImpl;
import gradle_jdbc_study.dao.impl.TitleDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.ui.list.DepartmentTblPanel;
import gradle_jdbc_study.ui.list.TitleTblPanel;

public class TestTableFrame {

	private static DepartmentTblPanel tp;
	private static TitleTblPanel ttp;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
//		tp = new DepartmentTblPanel();
		ttp = new TitleTblPanel();
		frame.add(ttp);
		List<Title> list = TitleDaoImpl.getInstance().selectTitleByAll();
		ttp.loadData(list);
		frame.setBounds(100, 100, 450, 300);
		ttp.setPopupMenu(createPopupMenu());
		frame.setVisible(true);
	}

	private static JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();
		JMenuItem updateItem = new JMenuItem("수정");
		JMenuItem deleteItem = new JMenuItem("삭제");
		JMenuItem selectEmp = new JMenuItem("전체 사원 보기");
		JMenuItem selectGroupByEmp = new JMenuItem("소속 사원 보기");
		ActionListener popListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("삭제")) {
					ttp.removeRow();
				}
				else if (e.getActionCommand().equals("수정")) {
					Title updateTitle = new Title(6,"인턴");
					ttp.updateRow(updateTitle, ttp.getSelectedRowIdx());
				}
				else if (e.getActionCommand().equals("소속 사원 보기")) {
					
				}
				else {
					
				}
			}
		};
		deleteItem.addActionListener(popListener);
		updateItem.addActionListener(popListener);
		selectEmp.addActionListener(popListener);
		selectGroupByEmp.addActionListener(popListener);
		popMenu.add(updateItem);
		popMenu.add(deleteItem);
		popMenu.add(selectEmp);
		popMenu.add(selectGroupByEmp);
		return popMenu;
	}

}
