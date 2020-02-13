package gradle_jdbc_study.ui.list;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingConstants;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;

@SuppressWarnings("serial")
public class EmployeeTblPanel extends AbsTblPanel<Employee> {
	private byte[] pic;
	public EmployeeTblPanel() {
		
	}

	@Override
	protected void setTblWidthAlign() {
		tableSetWidth(100,150,50,150,150,100,100,100);
		tableCellAlign(SwingConstants.CENTER,0,1,2,3,5,6,7);
		tableCellAlign(SwingConstants.RIGHT,4);
	}

	@Override
	protected String[] getColumns() {
		return new String[] {"사원번호","사원명","직책","직속상사","급여","부서","입사일자","사진여부"};
	}

	@Override
	protected Object[] toArray(Employee item) {
		if(item.getPic()!=null) {
			pic = item.getPic();
		}
		return new Object[] {item.getEmpNo(),item.getEmpName(),item.getTitle(),String.format("%s(%d)", item.getManager().getEmpName(),item.getManager().getEmpNo()),String.format("%,d", item.getSalary()),String.format("%s(%d)",item.getDept().getDeptName(),item.getDept().getDeptNo()),String.format("%1$tF %1tT$",item.getHireDate()),item.getPic()!=null?"있음":"없음"};
	}

	@Override
	public void updateRow(Employee item, int updateIdx) {
		model.setValueAt(item.getEmpNo(),updateIdx,0);
		model.setValueAt(item.getEmpName(),updateIdx,1);
		model.setValueAt(item.getTitle(),updateIdx,2);
		model.setValueAt(String.format("%s(%d)", item.getManager().getEmpName(),item.getManager().getEmpNo()),updateIdx, 3);
		model.setValueAt(String.format("%,d", item.getSalary()), updateIdx, 4);
		model.setValueAt(String.format("%s(%d)",item.getDept().getDeptName(),item.getDept().getDeptNo()), updateIdx, 5);
		model.setValueAt(String.format("%1$tF %1tT$",item.getHireDate()), updateIdx, 6);
		model.setValueAt(item.getPic()!=null?"있음":"없음", updateIdx, 7);
	}

	@Override
	public Employee getSelectedItem() {
		int selectedIdx = getSelectedRowIdx();
		int empNo = (int)model.getValueAt(selectedIdx, 0);
		String empName = (String)model.getValueAt(selectedIdx, 1);
		Title title = (Title)model.getValueAt(selectedIdx, 2);
		String mgn = (String)model.getValueAt(selectedIdx, 3);
		String manager_name = mgn.substring(0,mgn.indexOf("("));
		int manager_no = Integer.parseInt(mgn.substring(mgn.indexOf("(")+1, mgn.indexOf(")")));
		Employee manager = new Employee(manager_no);
		manager.setEmpName(manager_name);
		String salaryStr = (String)model.getValueAt(selectedIdx, 4);
		int salary = Integer.parseInt(salaryStr.replaceAll("\\,", ""));
		String deptStr = (String)model.getValueAt(selectedIdx, 5);
		String dept_name = deptStr.substring(0, deptStr.indexOf("("));
		int dept_no = Integer.parseInt(deptStr.substring(deptStr.indexOf("(")+1, deptStr.indexOf(")")));
		Department dept = new Department(dept_no);
		dept.setDeptName(dept_name);
		String dateStr = (String)model.getValueAt(selectedIdx, 6);
		Date hire_date = null;
		try {
			hire_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Employee emp = new Employee(empNo, empName, title, manager, salary, dept, hire_date);
		if(pic!=null) {
			emp.setPic(pic);
		}
		return emp;	
	}

}
