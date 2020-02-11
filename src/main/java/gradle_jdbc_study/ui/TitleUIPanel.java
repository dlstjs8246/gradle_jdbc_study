package gradle_jdbc_study.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.ui.content.TitlePanel;
import gradle_jdbc_study.ui.list.AbsTblPanel;
import gradle_jdbc_study.ui.list.TitleTblPanel;
import gradle_jdbc_study.ui.service.TitleUIService;

@SuppressWarnings("serial")
public class TitleUIPanel extends AbsMainPanel<Title> {
	private TitlePanel titlePanel;
	private TitleTblPanel titleTblPanel;
	private TitleUIService service;

	public TitleUIPanel() {

	}

	@Override
	protected JPanel getItemPanel() {
		titlePanel = new TitlePanel();
		return titlePanel;
	}

	@Override
	protected AbsTblPanel<Title> getTblPanel() {
		titleTblPanel = new TitleTblPanel();
		initItemList();
		popMenu = new JPopupMenu();
		initPopMenu();
		addBtnListener();
		titleTblPanel.setPopupMenu(popMenu);
		return titleTblPanel;
	}

	@Override
	protected void initItemList() {
		try {
			service = new TitleUIService();
			itemList = service.showTitles();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		titleTblPanel.loadData(itemList);
	}

	@Override
	protected void addBtnListener() {
		btnAdd.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("수정")) {
					try {
						titleTblPanel.updateRow(titlePanel.getItem(),titleTblPanel.getTable().getSelectedRow());
						try {
							service.updateTitle(titlePanel.getItem());
						} catch (SQLException e1) {
							if(e1.getErrorCode()==1062) {
								JOptionPane.showMessageDialog(null, "부서 번호가 중복");
							}
							e1.printStackTrace();
						}
						titlePanel.tfClear();
						titleTblPanel.clearSection();
						btnAdd.setText("추가");
					}
					catch(RuntimeException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					
				}
				if(e.getActionCommand().equals("추가")) {
					itemList.add(titlePanel.getItem());
					titleTblPanel.loadData(itemList);
					try {
						service.insertTitle(titlePanel.getItem());
					} catch (SQLException e1) {
						if(e1.getErrorCode()==1062) {
							JOptionPane.showMessageDialog(null, "부서 번호가 중복");
						}
						e1.printStackTrace();
					}
					titlePanel.tfClear();
					titleTblPanel.clearSection();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				titlePanel.tfClear();
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
					int selIdx = titleTblPanel.getSelectedRowIdx();
					try {
						service.deleteTitle(itemList.get(selIdx));
						titleTblPanel.removeRow();
						JOptionPane.showMessageDialog(null, "삭제되었습니다");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "문제가 있음" + " " + e1.getErrorCode());
						e1.printStackTrace();
					}
					
				}
				else if (e.getActionCommand().equals("수정")) {
					titlePanel.setItem(titleTblPanel.getSelectedItem());
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
