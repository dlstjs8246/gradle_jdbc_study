select * from title;
select * from department;
select * from employee;
select title_no,title_name from title;
select title_no,title_name from title where title_no = 1;
truncate table title;
select * from department where dept_no = 1;
-- 조민희가 로그인하려고 할 경우
select emp_no,emp_name,title,manager,salary,dept,hire_date from employee where emp_no = 1003 and passwd = password('1234567');
select emp_no,emp_name,title,manager,salary,dept,hire_date,pic from employee where emp_no =4377;
select e.emp_no,e.emp_name,e.title,e2.emp_name,e.manager, e.salary, (select d.dept_name from department d where e.dept = d.dept_no) as 'dept_name',e.dept,e.hire_date,e.pic from employee e join employee e2 on e.manager = e2.emp_no where e.dept = 1;
