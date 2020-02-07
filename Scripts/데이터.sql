desc title;
desc department;
desc employee;

insert into title values
(1,'사장'),
(2,'부장'),
(3,'과장'),
(4,'대리'),
(5,'사원');


insert into department values
(1,'영업',8),
(2,'기획',10),
(3,'개발',9),
(4,'총무',7);

insert into employee(emp_no,emp_name,title,manager,salary,dept,pass) values
(4377,'이성래',1,null,5000000,2,password('1234567')),
(3426,'박영권',3,4377,3000000,1,password('1234567')),
(1003,'조민희',3,4377,3000000,2,password('1234567')),
(3011,'이수민',2,4377,4000000,3,password('1234567')),
(1365,'김상원',5,3426,1500000,1,password('1234567')),
(2106,'김창섭',4,1003,2500000,2,password('1234567')),
(3427,'최종철',5,3011,1500000,3,password('1234567'));