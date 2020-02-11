package gradle_jdbc_study.ui.service;

import java.sql.SQLException;
import java.util.List;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dao.impl.DepartmentDaoImpl;
import gradle_jdbc_study.dao.impl.EmployeeDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;

public class DepartmentUIService {
	private DepartmentDao deptDao;
	private EmployeeDao empDao;
	public DepartmentUIService() throws SQLException {
		deptDao = DepartmentDaoImpl.getInstance();
		empDao = EmployeeDaoImpl.getInstance();
		
	}
	
	public List<Department> showDepartments() throws SQLException{
		return deptDao.selectByAll();
	}
	public List<Employee> showEmployees() throws SQLException {
		return empDao.selectEmployeeByAll();
	}
	public List<Employee> showGroupByDnoEmployees(int deptNo) throws SQLException {
		Department dept = new Department(deptNo);
		return empDao.selectGroupByNo(dept);
	}
	public void insertEmployee(Department dept) throws SQLException {
		deptDao.insertDepartment(dept);	
	}
	public void updateEmployee(Department dept) throws SQLException {
		deptDao.updateDepartment(dept);	
	}
	public void deleteEmployee(Department dept) throws SQLException {
		deptDao.deleteDepartment(dept);
	}
}
