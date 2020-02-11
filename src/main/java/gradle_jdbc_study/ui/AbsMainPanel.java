package gradle_jdbc_study.ui;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.ui.list.AbsTblPanel;

@SuppressWarnings("serial")
public abstract class AbsMainPanel<T> extends JPanel {
	private JPanel pItem;
	private JPanel pBtn;
	private AbsTblPanel<T> pTbl;
	protected JButton btnAdd;
	protected JButton btnCancel;
	protected List<T> itemList;
	protected JPopupMenu popMenu;
	public AbsMainPanel() {
		initialize();
	}
	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		pItem = getItemPanel();
		add(pItem);
		
		pBtn = new JPanel();
		add(pBtn);
		
		btnAdd = new JButton("추가");
		pBtn.add(btnAdd);
		
		btnCancel = new JButton("취소");
		pBtn.add(btnCancel);
		
		pTbl = getTblPanel();
		add(pTbl);
	}
	protected abstract JPanel getItemPanel();
	protected abstract AbsTblPanel<T> getTblPanel();
	protected abstract void initItemList();
	protected abstract void addBtnListener();
	protected abstract void initPopMenu();
}
