package gradle_jdbc_study.ui.list;

import javax.swing.SwingConstants;

import gradle_jdbc_study.dto.Title;

@SuppressWarnings("serial")
public class TitleTblPanel extends AbsTblPanel<Title> {

	/**
	 * Create the panel.
	 */
	public TitleTblPanel() {

	}

	@Override
	protected void setTblWidthAlign() {
		tableSetWidth(100,100);
		tableCellAlign(SwingConstants.CENTER,0,1);
	}

	@Override
	protected String[] getColumns() {
		return new String[] {"번호","직책명"};
	}

	@Override
	protected Object[] toArray(Title item) {
		return new Object[] {item.getTitleNo(),item.getTitleName()};
	}

	@Override
	public void updateRow(Title item, int updateIdx) {
		model.setValueAt(item.getTitleNo(),updateIdx, 0);
		model.setValueAt(item.getTitleName(),updateIdx, 1);
		
	}

	@Override
	public Title getSelectedItem() {
		int selectedIndex = getSelectedRowIdx();
		int titleNo = (int)model.getValueAt(selectedIndex, 0);
		String titleName = (String)model.getValueAt(selectedIndex, 1);
		return new Title(titleNo, titleName);
	}

}
