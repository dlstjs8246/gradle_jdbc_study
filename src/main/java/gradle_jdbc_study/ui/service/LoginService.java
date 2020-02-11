package gradle_jdbc_study.ui.service;

import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dao.impl.EmployeeDaoImpl;
import gradle_jdbc_study.dto.Employee;

public class LoginService {
	private EmployeeDao dao = EmployeeDaoImpl.getInstance();
	
	public Employee login(Employee emp) {
		return dao.loginEmployee(emp);
	}
}
