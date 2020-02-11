package gradle_jdbc_study.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gradle_jdbc_study.dao.impl.EmployeeDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.util.LogUtil;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private static EmployeeDao dao; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = EmployeeDaoImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
	}

	@After
	public void tearDown() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
	}

	@Test
	public void test01SelectEmployeeByNo() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = dao.selectEmployeeByNo(new Employee(4377));
		Assert.assertNotNull(emp);
		LogUtil.prnLog(emp);
	}

	@Test
	public void test02SelectEmployeeByAll() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Employee> list = dao.selectEmployeeByAll();
		Assert.assertNotEquals(0, list.size());
		for(Employee e : list) LogUtil.prnLog(e);
	}

	@Test
	public void test03InsertEmployee() throws FileNotFoundException, IOException {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1004,"김태리",new Title(5),new Employee(4377),1500000,new Department(2),new Date());
		String imgPath = System.getProperty("user.dir") + "//images//김태리.jpg";
		File imgFile = new File(imgPath);
		try(FileInputStream fis = new FileInputStream(imgFile)) {
			byte[] pic = new byte[(int)imgFile.length()];
			fis.read(pic);
			emp.setPic(pic);
		}
		int res = dao.insertEmployee(emp);
		Assert.assertEquals(1, res);
		LogUtil.prnLog(emp);
	}

	@Test
	public void test04UpdateEmployee() throws FileNotFoundException, IOException {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1004,"박은빈",new Title(5),new Employee(4377),1500000,new Department(2),new Date());
		String imgPath = System.getProperty("user.dir") + "//images//박은빈.jpg";
		File imgFile = new File(imgPath);
		try(FileInputStream fis = new FileInputStream(imgFile)) {
			byte[] pic = new byte[(int)imgFile.length()];
			fis.read(pic);
			emp.setPic(pic);
		}
		int res = dao.updateEmployee(emp);
		Assert.assertEquals(1, res);
		LogUtil.prnLog(emp);
	}

	@Test
	public void test05DeleteEmployee() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1004);
		int res = dao.deleteEmployee(emp);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test06LoginEmployee() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		fail("Not yet implemented");
	}

}
