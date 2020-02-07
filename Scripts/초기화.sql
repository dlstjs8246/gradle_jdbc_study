-- �׷��̵�_������Ʈ
DROP SCHEMA IF EXISTS gradle_jdbc;

-- �׷��̵�_������Ʈ
CREATE SCHEMA gradle_jdbc;

-- ��å
CREATE TABLE gradle_jdbc.title (
	title_no   INTEGER     NOT NULL COMMENT '��å��ȣ', -- ��å��ȣ
	title_name VARCHAR(20) NOT NULL COMMENT '��å��' -- ��å��
)
COMMENT '��å';

-- ��å
ALTER TABLE gradle_jdbc.title
	ADD CONSTRAINT PK_title -- ��å �⺻Ű
		PRIMARY KEY (
			title_no -- ��å��ȣ
		);

-- �μ�
CREATE TABLE gradle_jdbc.Department (
	dept_no   INTEGER     NOT NULL COMMENT '�μ���ȣ', -- �μ���ȣ
	dept_name VARCHAR(20) NULL     COMMENT '�μ���', -- �μ���
	floor     INTEGER     NULL     COMMENT '��ġ' -- ��ġ
)
COMMENT '�μ�';

-- �μ�
ALTER TABLE gradle_jdbc.Department
	ADD CONSTRAINT PK_Department -- �μ� �⺻Ű
		PRIMARY KEY (
			dept_no -- �μ���ȣ
		);

-- ���
CREATE TABLE gradle_jdbc.Employee (
	emp_no   INTEGER     NOT NULL COMMENT '�����ȣ', -- �����ȣ
	emp_name VARCHAR(20) NULL     COMMENT '�����', -- �����
	title    INTEGER     NOT NULL COMMENT '��å', -- ��å
	manager  INTEGER     NULL     COMMENT '���ӻ��', -- ���ӻ��
	salary   INTEGER     NULL     COMMENT '�޿�', -- �޿�
	dept     INTEGER     NULL     COMMENT '�μ�', -- �μ�
	pic      LONGBLOB    NULL     COMMENT '�������', -- �������
	pass     char(41)    NULL     COMMENT '��й�ȣ' -- ��й�ȣ
)
COMMENT '���';

-- ���
ALTER TABLE gradle_jdbc.Employee
	ADD CONSTRAINT PK_Employee -- ��� �⺻Ű
		PRIMARY KEY (
			emp_no -- �����ȣ
		);

-- ���
ALTER TABLE gradle_jdbc.Employee
	ADD CONSTRAINT FK_Employee_TO_Employee -- ��� -> ���
		FOREIGN KEY (
			manager -- ���ӻ��
		)
		REFERENCES gradle_jdbc.Employee ( -- ���
			emp_no -- �����ȣ
		);

-- ���
ALTER TABLE gradle_jdbc.Employee
	ADD CONSTRAINT FK_Department_TO_Employee -- �μ� -> ���
		FOREIGN KEY (
			dept -- �μ�
		)
		REFERENCES gradle_jdbc.Department ( -- �μ�
			dept_no -- �μ���ȣ
		);
drop user if exists 'user_gradle_jdbc'@'localhost';
grant all privileges on gradle_jdbc.* to 'user_gradle_jdbc'@'localhost' identified by 'rootroot';
flush privileges;