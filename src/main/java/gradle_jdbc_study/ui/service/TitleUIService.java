package gradle_jdbc_study.ui.service;

import java.sql.SQLException;
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

public class TitleUIService {
	private TitleDao titleDao;
	public TitleUIService() throws SQLException {
		titleDao = TitleDaoImpl.getInstance();	
	}
	
	public List<Title> showTitles() throws SQLException{
		return titleDao.selectTitleByAll();
	}
	public void insertTitle(Title title) throws SQLException {
		titleDao.insertTitle(title);	
	}
	public void updateTitle(Title title) throws SQLException {
		titleDao.updateTitle(title);
	}
	public void deleteTitle(Title title) throws SQLException {
		titleDao.deleteTitle(title);
	}
}
