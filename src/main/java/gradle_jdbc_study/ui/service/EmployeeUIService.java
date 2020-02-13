package gradle_jdbc_study.ui.service;

import java.util.List;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.dao.impl.DepartmentDaoImpl;
import gradle_jdbc_study.dao.impl.EmployeeDaoImpl;
import gradle_jdbc_study.dao.impl.TitleDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;

public class EmployeeUIService {
	private EmployeeDao empDao;
	private DepartmentDao deptDao;
	private TitleDao titleDao;
	public EmployeeUIService() {
		empDao = EmployeeDaoImpl.getInstance();
		deptDao = DepartmentDaoImpl.getInstance();
		titleDao = TitleDaoImpl.getInstance();
	}
	public List<Department> showDeptList() {
		return deptDao.selectByAll();
	}
	public List<Employee> showManagerList(Department dept) {
		return empDao.selectGroupByNo(dept);
	}
	public List<Title> showTitle() {
		return titleDao.selectTitleByAll();
	}
	public List<Employee> showEmployees() {
		return empDao.selectEmployeeByAll();
	}
	public int updateEmployee(Employee emp) {
		return empDao.updateEmployee(emp);
	}
	public int insertEmployee(Employee emp) {
		return empDao.insertEmployee(emp);
	}
	public int deleteEmployee(Employee emp) {
		return empDao.deleteEmployee(emp);	
	}
}
