package gradle_jdbc_study.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.ui.content.EmployeePanel;
import gradle_jdbc_study.ui.exception.InvalidCheckExcepation;
import gradle_jdbc_study.ui.list.AbsTblPanel;
import gradle_jdbc_study.ui.list.EmployeeTblPanel;
import gradle_jdbc_study.ui.service.EmployeeUIService;

@SuppressWarnings("serial")
public class EmployeeUIPanel extends AbsMainPanel<Employee> {
	private EmployeePanel empPanel;
	private EmployeeTblPanel empTblPanel;
	private EmployeeUIService service;
	public EmployeeUIPanel() {

	}
	public EmployeePanel getEmpPanel() {
		return empPanel;
	}
	@Override
	protected JPanel getItemPanel() {
		empPanel = new EmployeePanel();
		return empPanel;
	}

	@Override
	protected AbsTblPanel<Employee> getTblPanel() {
		empTblPanel = new EmployeeTblPanel();
		initItemList();
		popMenu = new JPopupMenu();
		initPopMenu();
		addBtnListener();
		empTblPanel.setPopupMenu(popMenu);
		return empTblPanel;
	}

	@Override
	protected void initItemList() {
		service = new EmployeeUIService();
		itemList = service.showEmployees();
		empTblPanel.loadData(itemList);
	}
	

	@Override
	protected void addBtnListener() {
		btnAdd.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("수정")) {
					try {
						empTblPanel.updateRow(empPanel.getItem(),empTblPanel.getTable().getSelectedRow());
						service.updateEmployee(empPanel.getItem());
						empPanel.tfClear();
						empTblPanel.clearSection();
						btnAdd.setText("추가");
					}
					catch(RuntimeException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					
				}
				if(e.getActionCommand().equals("추가")) {
					try {
						itemList.add(empPanel.getItem());
						empTblPanel.loadData(itemList);
						service.insertEmployee(empPanel.getItem());
					} catch(InvalidCheckExcepation e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					empPanel.tfClear();
					empTblPanel.clearSection();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				empPanel.tfClear();
			}
		});
		
	}

	@Override
	protected void initPopMenu() {
		JMenuItem updateItem = new JMenuItem("수정");
		JMenuItem deleteItem = new JMenuItem("삭제");
		ActionListener popListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("삭제")) {
					int selIdx = empTblPanel.getSelectedRowIdx();
					service.deleteEmployee(itemList.get(selIdx));
					empTblPanel.removeRow();
					JOptionPane.showMessageDialog(null, "삭제되었습니다");
					
				}
				else if (e.getActionCommand().equals("수정")) {
					Employee emp = empTblPanel.getSelectedItem();
					if(emp.getPic()!=null) {
						JOptionPane.showMessageDialog(null, emp.getPic().length);
					}
					else {
						JOptionPane.showMessageDialog(null,"사진이 존재하지 않습니다");
					}
					empPanel.setItem(emp);
					btnAdd.setText("수정");
				}
			}
		};
		deleteItem.addActionListener(popListener);
		updateItem.addActionListener(popListener);
		popMenu.add(updateItem);
		popMenu.add(deleteItem);
	}

}
