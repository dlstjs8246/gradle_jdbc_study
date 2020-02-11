package gradle_jdbc_study.dao;

import java.util.List;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByNo(Employee emp);
	List<Employee> selectEmployeeByAll();
	int insertEmployee(Employee emp);
	int updateEmployee(Employee emp);
	int deleteEmployee(Employee emp);
	Employee loginEmployee(Employee emp);
	List<Employee> selectGroupByNo(Department dept);
}
