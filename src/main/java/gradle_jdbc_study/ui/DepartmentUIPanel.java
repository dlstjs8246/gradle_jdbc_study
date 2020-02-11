package gradle_jdbc_study.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.ui.content.DepartmentPanel;
import gradle_jdbc_study.ui.list.AbsTblPanel;
import gradle_jdbc_study.ui.list.DepartmentTblPanel;
import gradle_jdbc_study.ui.service.DepartmentUIService;

@SuppressWarnings("serial")
public class DepartmentUIPanel extends AbsMainPanel<Department> {
	private DepartmentPanel deptPanel;
	private DepartmentTblPanel deptTblPanel;
	private DepartmentUIService service;
	private List<Employee> list;
	private DlgEmployee dialog;
	public DepartmentUIPanel() {
		dialog = new DlgEmployee();
	}

	@Override
	protected JPanel getItemPanel() {
		deptPanel = new DepartmentPanel();
		return deptPanel;
	}

	@Override
	protected AbsTblPanel<Department> getTblPanel() {
		deptTblPanel = new DepartmentTblPanel();
		initItemList();
		popMenu = new JPopupMenu();
		initPopMenu();
		addBtnListener();
		deptTblPanel.setPopupMenu(popMenu);
		return deptTblPanel;
	}

	@Override
	protected void initItemList() {
		try {
			service = new DepartmentUIService();
			itemList = service.showDepartments();
			list = service.showEmployees();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deptTblPanel.loadData(itemList);
		
	}

	@Override
	protected void addBtnListener() {
		btnAdd.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("수정")) {
					try {
						deptTblPanel.updateRow(deptPanel.getItem(),deptTblPanel.getTable().getSelectedRow());
						try {
							service.updateEmployee(deptPanel.getItem());
						} catch (SQLException e1) {
							if(e1.getErrorCode()==1062) {
								JOptionPane.showMessageDialog(null, "부서 번호가 중복");
							}
							e1.printStackTrace();
						}
						deptPanel.tfClear();
						deptTblPanel.clearSection();
						btnAdd.setText("추가");
					}
					catch(RuntimeException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					
				}
				if(e.getActionCommand().equals("추가")) {
					itemList.add(deptPanel.getItem());
					deptTblPanel.loadData(itemList);
					try {
						service.insertEmployee(deptPanel.getItem());
					} catch (SQLException e1) {
						if(e1.getErrorCode()==1062) {
							JOptionPane.showMessageDialog(null, "부서 번호가 중복");
						}
						e1.printStackTrace();
					}
					deptPanel.tfClear();
					deptTblPanel.clearSection();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				deptPanel.tfClear();
			}
		});
		
	}

	@Override
	protected void initPopMenu() {
		JMenuItem updateItem = new JMenuItem("수정");
		JMenuItem deleteItem = new JMenuItem("삭제");
		JMenuItem selectEmp = new JMenuItem("전체 사원 보기");
		JMenuItem selectGroupByEmp = new JMenuItem("소속 사원 보기");
		ActionListener popListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("삭제")) {
					int selIdx = deptTblPanel.getSelectedRowIdx();
					try {
						service.deleteEmployee(itemList.get(selIdx));
						deptTblPanel.removeRow();
						JOptionPane.showMessageDialog(null, "삭제되었습니다");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "문제가 있음" + " " + e1.getErrorCode());
						e1.printStackTrace();
					}
					
				}
				else if (e.getActionCommand().equals("수정")) {
					deptPanel.setItem(deptTblPanel.getSelectedItem());
					btnAdd.setText("수정");
				}
				else if (e.getActionCommand().equals("소속 사원 보기")) {
					try {
						dialog.setEmpList(service.showGroupByDnoEmployees(deptTblPanel.getSelectedItem().getDeptNo()));
						dialog.setTitle(deptTblPanel.getSelectedItem().getDeptName() + "부서");
					} catch (SQLException e2) {
						System.out.println(e2.getMessage());
					}
					dialog.setModal(true);
					dialog.setVisible(true);
				}
				
				else {
					JOptionPane.showMessageDialog(null, list);
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
	}

}
