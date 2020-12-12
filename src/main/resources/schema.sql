DROP TABLE IF EXISTS EMP;

CREATE TABLE EMP
(
    emp_id      IDENTITY        PRIMARY KEY,
    emp_name    VARCHAR(30)     NOT NULL
);

DROP TABLE IF EXISTS EMP_WORK;

CREATE TABLE EMP_WORK
(
    emp_id          NUMBER          NOT NULL,
    apply_date      DATE             NOT NULL,
    work_type       VARCHAR(100)     NOT NULL,
    work_shift      VARCHAR(100)    NOT NULL,
    PRIMARY KEY (emp_id, apply_date)
);

CREATE VIEW EMP_WORK_V AS
SELECT  *
FROM    emp_work
WHERE   (emp_id, apply_date) IN
          (SELECT emp_id, MAX(apply_date)
          FROM emp_work
          WHERE sysdate >= apply_date
          GROUP BY emp_id)
;

CREATE VIEW EMP_V AS
SELECT  e.emp_id, e.emp_name, w.work_type, w.work_shift
FROM    EMP e LEFT JOIN EMP_WORK_V w
ON      e.emp_id = w.emp_id
;