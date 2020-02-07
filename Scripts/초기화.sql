-- 그레이들_프로젝트
DROP SCHEMA IF EXISTS gradle_jdbc;

-- 그레이들_프로젝트
CREATE SCHEMA gradle_jdbc;

-- 직책
CREATE TABLE gradle_jdbc.title (
	title_no   INTEGER     NOT NULL COMMENT '직책번호', -- 직책번호
	title_name VARCHAR(20) NOT NULL COMMENT '직책명' -- 직책명
)
COMMENT '직책';

-- 직책
ALTER TABLE gradle_jdbc.title
	ADD CONSTRAINT PK_title -- 직책 기본키
		PRIMARY KEY (
			title_no -- 직책번호
		);

-- 부서
CREATE TABLE gradle_jdbc.Department (
	dept_no   INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
	dept_name VARCHAR(20) NULL     COMMENT '부서명', -- 부서명
	floor     INTEGER     NULL     COMMENT '위치' -- 위치
)
COMMENT '부서';

-- 부서
ALTER TABLE gradle_jdbc.Department
	ADD CONSTRAINT PK_Department -- 부서 기본키
		PRIMARY KEY (
			dept_no -- 부서번호
		);

-- 사원
CREATE TABLE gradle_jdbc.Employee (
	emp_no   INTEGER     NOT NULL COMMENT '사원번호', -- 사원번호
	emp_name VARCHAR(20) NULL     COMMENT '사원명', -- 사원명
	title    INTEGER     NOT NULL COMMENT '직책', -- 직책
	manager  INTEGER     NULL     COMMENT '직속상사', -- 직속상사
	salary   INTEGER     NULL     COMMENT '급여', -- 급여
	dept     INTEGER     NULL     COMMENT '부서', -- 부서
	pic      LONGBLOB    NULL     COMMENT '증명사진', -- 증명사진
	pass     char(41)    NULL     COMMENT '비밀번호' -- 비밀번호
)
COMMENT '사원';

-- 사원
ALTER TABLE gradle_jdbc.Employee
	ADD CONSTRAINT PK_Employee -- 사원 기본키
		PRIMARY KEY (
			emp_no -- 사원번호
		);

-- 사원
ALTER TABLE gradle_jdbc.Employee
	ADD CONSTRAINT FK_Employee_TO_Employee -- 사원 -> 사원
		FOREIGN KEY (
			manager -- 직속상사
		)
		REFERENCES gradle_jdbc.Employee ( -- 사원
			emp_no -- 사원번호
		);

-- 사원
ALTER TABLE gradle_jdbc.Employee
	ADD CONSTRAINT FK_Department_TO_Employee -- 부서 -> 사원
		FOREIGN KEY (
			dept -- 부서
		)
		REFERENCES gradle_jdbc.Department ( -- 부서
			dept_no -- 부서번호
		);
drop user if exists 'user_gradle_jdbc'@'localhost';
grant all privileges on gradle_jdbc.* to 'user_gradle_jdbc'@'localhost' identified by 'rootroot';
flush privileges;