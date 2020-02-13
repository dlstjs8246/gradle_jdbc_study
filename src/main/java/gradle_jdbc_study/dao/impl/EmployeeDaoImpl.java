package gradle_jdbc_study.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.ds.MySqlDataSource;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.util.LogUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	private boolean isPic;
	private EmployeeDaoImpl() {};
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee selectEmployeeByNo(Employee emp) {
		String sql = "select emp_no,emp_name,title,manager,salary,dept,hire_date,pic from employee where emp_no = ?";
		Employee employee = new Employee();
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					employee = getEmployee(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("emp_no");
		String empName = rs.getString("emp_name");
		Title title = new Title(rs.getInt("title"));
		Employee manager = rs.getInt("manager")==0?null:new Employee(rs.getInt("manager"));	
		int salary = rs.getInt("salary");
		Department dept = new Department(rs.getInt("dept"));
		Date hireDate = rs.getTimestamp("hire_date");
		Employee emp = new Employee(empNo, empName, title, manager, salary, dept, hireDate);
		isPic = emp.getPic()==null?false:true;
		if(isPic) {
			byte[] pic = rs.getBytes("pic")==null?null:rs.getBytes("pic");
			emp.setPic(pic);
		}
		return emp;
	}

	@Override
	public int insertEmployee(Employee emp) {
		String sql = null;
		isPic = emp.getPic()!=null?true:isPic;
		int res = 0;
		if(isPic) {
			sql = "insert into employee(emp_no,emp_name,title,manager,salary,dept,hire_date,pic) values(?,?,?,?,?,?,?,?)";
		}
		else {
			sql = "insert into employee(emp_no,emp_name,title,manager,salary,dept,hire_date) values(?,?,?,?,?,?,?)";
		}
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setInt(3, emp.getTitle().getTitleNo());
			pstmt.setInt(4, emp.getManager().getEmpNo());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDept().getDeptNo());
			pstmt.setTimestamp(7, new Timestamp(emp.getHireDate().getTime()));
			if(isPic) {
				pstmt.setBytes(8, emp.getPic());
			}
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int updateEmployee(Employee emp) {
		int res = 0;
		isPic = emp.getPic()!=null?true:isPic;
		StringBuilder sql = new StringBuilder("update employee set ");
		int[] idxs = new int[emp.getClass().getDeclaredFields().length];
		List<String> field = new ArrayList<>();
		checkSql(emp, sql,field,idxs);
		String ConSql = sql.toString();
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(ConSql)) {
			for(int i=0;i<field.size();i++) {
				switch(field.get(i)) {
				case "emp_no":
					pstmt.setInt(idxs[i], emp.getEmpNo());
					break;
				case "emp_name":
					pstmt.setString(idxs[i],emp.getEmpName());
					break;
				case "title":
					pstmt.setInt(idxs[i], emp.getTitle().getTitleNo());
					break;
				case "manager":
					pstmt.setInt(idxs[i], emp.getManager().getEmpNo());
					break;
				case "salary":
					pstmt.setInt(idxs[i], emp.getSalary());
					break;
				case "dept":
					pstmt.setInt(idxs[i], emp.getDept().getDeptNo());
					break;
				case "hire_date":
					pstmt.setTimestamp(idxs[i], new Timestamp(emp.getHireDate().getTime()));
					break;
				case "pic":
					if(isPic) {
						pstmt.setBytes(idxs[i], emp.getPic());
					}
					break;
				}
			}
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	private void checkSql(Employee emp, StringBuilder sql, List<String> field, int[] idxs) {
		if(emp.getEmpName()!=null) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("emp_name = ?");
				countComma(sql, idxs);
				field.add("emp_name");
			}
			else {
				sql.append(",emp_name = ?");
				countComma(sql, idxs);
				field.add("emp_name");
			}
		}
		if(emp.getTitle()!=null) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("title = ?");
				countComma(sql, idxs);
				field.add("title");
			}
			else {
				sql.append(",title = ?");
				countComma(sql, idxs);
				field.add("title");
			}
		}
		if(emp.getManager()!=null) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("manager = ?");
				countComma(sql, idxs);
				field.add("manager");
			}
			else {
				sql.append(",manager = ?");
				countComma(sql, idxs);
				field.add("manager");
			}
		}
		if(emp.getSalary()!=0) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("salary = ?");
				countComma(sql, idxs);
				field.add("salary");
			}
			else {
				sql.append(",salary = ?");
				countComma(sql, idxs);
				field.add("salary");
			}
		}
		if(emp.getDept()!=null) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("dept = ?");
				countComma(sql, idxs);
				field.add("dept");
			}
			else {
				sql.append(",dept = ?");
				countComma(sql, idxs);
				field.add("dept");
			}
		}
		if(emp.getHireDate()!=null) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("hire_date = ?");
				countComma(sql, idxs);
				field.add("hire_date");
			}
			else {
				sql.append(",hire_date = ?");
				countComma(sql, idxs);
				field.add("hire_date");
			}
		}
		if(emp.getPic()!=null) {
			if(sql.substring(sql.length()-2,sql.length()-1).equals("t")) {
				sql.append("pic = ?");
				countComma(sql, idxs);
				field.add("pic");
			}
			else {
				sql.append(",pic = ?");
				countComma(sql, idxs);
				field.add("pic");
			}
		}
		sql.append(" where emp_no = ?");
		countComma(sql, idxs);
		field.add("emp_no");
	}

	private void countComma(StringBuilder sql, int[] idxs) {
		int count = 0;
		for(int i=0;i<sql.length();i++) {
			if(sql.charAt(i)==',') {
				count++;
			}
		}
		if(sql.indexOf("emp_no")>-1) {
			idxs[++count] = ++count;
		}
		else {
			idxs[count] = ++count;
		}
	}

	@Override
	public int deleteEmployee(Employee emp) {
		int res = 0;
		String sql = "delete from employee where emp_no = ?";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Employee loginEmployee(Employee emp) {
		String sql = "select emp_no,emp_name,passwd from employee where emp_no = ?";
		Employee loginEmp = null;
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					loginEmp = getEmployeePassInfo(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginEmp;
	}

	private Employee getEmployeePassInfo(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("emp_no");
		String passwd = rs.getString("passwd");
		Employee emp = new Employee(empNo, passwd);
		emp.setEmpName(rs.getString("emp_name"));
		return emp;
	}

	@Override
	public List<Employee> selectGroupByNo(Department dept) {
		String sql = "select e.emp_no,e.emp_name,t.title_no,t.title_name,e2.emp_name,e.manager,e.salary, (select d.dept_name from department d where e.dept = d.dept_no) as 'dept_name',e.dept,e.hire_date,e.pic from employee e left join employee e2 on e.manager = e2.emp_no join title t on e.title = t.title_no where e.dept = ?;";
		List<Employee> list = new ArrayList<>();
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, dept.getDeptNo());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getEmployeeGroupByNo(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Employee> selectEmployeeByAll() {
		String sql = "select e.emp_no,e.emp_name,t.title_no,t.title_name,e2.emp_name,e.manager,e.salary, (select d.dept_name from department d where e.dept = d.dept_no) as 'dept_name',e.dept,e.hire_date,e.pic from employee e left join employee e2 on e.manager = e2.emp_no join title t on e.title = t.title_no";
		List<Employee> lists = new ArrayList<>();
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					lists.add(getEmployeeGroupByNo(rs));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}

	private Employee getEmployeeGroupByNo(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("e.emp_no");
		String empName = rs.getString("e.emp_name");
		Title title = new Title(rs.getInt("t.title_no"));
		title.setTitleName(rs.getString("t.title_name"));
		Employee manager = rs.getInt("e.manager")==0?new Employee():new Employee(rs.getInt("e.manager"));
		LogUtil.prnLog(rs.getString("e2.emp_name"));
		manager.setEmpName(rs.getString("e2.emp_name")==null?null:rs.getString("e2.emp_name"));
		int salary = rs.getInt("salary");
		Department dept = new Department(rs.getInt("e.dept"));
		dept.setDeptName(rs.getString("dept_name"));
		Date hireDate = rs.getTimestamp("e.hire_date");
		Employee emp = new Employee(empNo, empName, title, manager, salary, dept, hireDate);
		emp.setPic(rs.getBytes("e.pic")==null?null:rs.getBytes("e.pic"));
		return emp;
	}

}
