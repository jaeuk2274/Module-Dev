DROP TABLE IF EXISTS EMP;

CREATE TABLE EMP
(
    emp_id      IDENTITY        PRIMARY KEY,
    emp_name    VARCHAR(30)     NOT NULL
);

DROP TABLE IF EXISTS EMP_WORK;

CREATE TABLE EMP_WORK
(
    emp_id      IDENTITY        PRIMARY KEY,
    apply_date   DATE            NOT NULL,
    work_type    VARCHAR(100)     NOT NULL,
    work_shift    VARCHAR(100)     NULL
);

