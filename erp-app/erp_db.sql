
DROP DATABASE IF EXISTS employeedb;
DROP ROLE IF EXISTS employee;


CREATE ROLE employee WITH LOGIN PASSWORD 'admin' NOSUPERUSER NOCREATEDB NOCREATEROLE;
CREATE DATABASE employeedb WITH OWNER = employee;


\connect employeedb;


ALTER DEFAULT PRIVILEGES FOR ROLE employee GRANT ALL ON TABLES TO PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE employee GRANT ALL ON SEQUENCES TO PUBLIC;


CREATE TABLE emp_user (
    emp_id SERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE emp_category (
    category_id SERIAL PRIMARY KEY,
    emp_id INTEGER NOT NULL,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(40) NOT NULL,
    CONSTRAINT cat_emp_fk FOREIGN KEY (emp_id) REFERENCES emp_user(emp_id)
);

CREATE TABLE emp_salary (
    sal_id SERIAL PRIMARY KEY,
    category_id INTEGER NOT NULL,
    emp_id INTEGER NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    note VARCHAR(50) NOT NULL,
    CONSTRAINT sal_cat_fk FOREIGN KEY (category_id) REFERENCES emp_category(category_id),
    CONSTRAINT sal_emp_fk FOREIGN KEY (emp_id) REFERENCES emp_user(emp_id)
);


CREATE SEQUENCE emp_user_seq START 1 INCREMENT 1;
CREATE SEQUENCE emp_category_seq START 1 INCREMENT 1;
CREATE SEQUENCE emp_salary_seq START 1000 INCREMENT 1;
