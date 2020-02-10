package gradle_jdbc_study.dao;

import java.util.List;

import gradle_jdbc_study.dto.Department;

public interface DepartmentDao {
	Department selectByNo(Department dept);
	List<Department> selectByAll();
	int insertDepartment(Department dept);
	int updateDepartment(Department dept);
	int deleteDepartment(Department dept);
}
