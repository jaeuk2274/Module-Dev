DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee
(
    emp_id      IDENTITY        PRIMARY KEY,
    emp_name    VARCHAR(30)     NOT NULL,
    work_shift  VARCHAR(100)    NOT NULL
);