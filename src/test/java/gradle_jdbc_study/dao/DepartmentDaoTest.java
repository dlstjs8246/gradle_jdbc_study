package gradle_jdbc_study.dao;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gradle_jdbc_study.dao.impl.DepartmentDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.util.LogUtil;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private static DepartmentDao dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = DepartmentDaoImpl.getInstance();
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
	public void test01SelectByNo() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(1);
		dept = dao.selectByNo(dept);
		Assert.assertNotNull(dept);
		LogUtil.prnLog(dept);
	}

	@Test
	public void test02SelectByAll() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Department> lists = dao.selectByAll();
		Assert.assertNotEquals(0, lists.size());
		for(Department d : lists) LogUtil.prnLog(d);
	}

	@Test
	public void test03InsertDepartment() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5,"마케팅",10);
		int res = dao.insertDepartment(dept);
		Assert.assertEquals(1, res);
		LogUtil.prnLog(dept);
	}

	@Test
	public void test04UpdateDepartment() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5,"마케팅",30);
		int res = dao.updateDepartment(dept);
		Assert.assertEquals(1, res);
		LogUtil.prnLog(dept);
	}

	@Test
	public void test05DeleteDepartment() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5);
		int res = dao.deleteDepartment(dept);
		Assert.assertEquals(1, res);
		LogUtil.prnLog(dept);
	}

}
