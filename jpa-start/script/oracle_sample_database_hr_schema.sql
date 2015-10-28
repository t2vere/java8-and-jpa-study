CREATE TABLE OE.CATEGORIES
  (
    CATEGORY_NAME        VARCHAR2 (50 BYTE) ,
    CATEGORY_DESCRIPTION VARCHAR2 (1000 BYTE) ,
    CATEGORY_ID          NUMBER (2) NOT NULL
  ) ;
ALTER TABLE OE.CATEGORIES ADD CONSTRAINT CATEGORIES_PK1 PRIMARY KEY ( CATEGORY_ID ) ;
CREATE TABLE HR.COUNTRIES2
  (
    COUNTRY_ID   CHAR (2 BYTE) NOT NULL ,
    COUNTRY_NAME VARCHAR2 (40 BYTE) ,
    REGION_ID    NUMBER
  ) ;
COMMENT ON COLUMN HR.COUNTRIES2.COUNTRY_ID
IS
  'Primary key of countries table.' ;
  COMMENT ON COLUMN HR.COUNTRIES2.COUNTRY_NAME
IS
  'Country name' ;
  COMMENT ON COLUMN HR.COUNTRIES2.REGION_ID
IS
  'Region ID for the country. Foreign key to region_id column in the departments table.' ;
  ALTER TABLE HR.COUNTRIES2 ADD CONSTRAINT COUNTRY_C_ID_PK PRIMARY KEY ( COUNTRY_ID ) ;
  CREATE TABLE OE.CUSTOMERS2
    (
      CUSTOMER_ID     NUMBER (6) NOT NULL ,
      CUST_FIRST_NAME VARCHAR2 (20 BYTE) NOT NULL ,
      CUST_LAST_NAME  VARCHAR2 (20 BYTE) NOT NULL ,
      CUST_ADDRESS CUST_ADDRESS_TYP ,
      PHONE_NUMBERS PHONE_LIST_TYP ,
      NLS_LANGUAGE      VARCHAR2 (3 BYTE) ,
      NLS_TERRITORY     VARCHAR2 (30 BYTE) ,
      CREDIT_LIMIT      NUMBER (9,2) ,
      CUST_EMAIL        VARCHAR2 (30 BYTE) ,
      ACCOUNT_MGR_ID    NUMBER (6) ,
      CUST_GEO_LOCATION NUMBER (14,4) ,
      DATE_OF_BIRTH     DATE ,
      MARITAL_STATUS    VARCHAR2 (20 BYTE) ,
      GENDER            VARCHAR2 (1 BYTE) ,
      INCOME_LEVEL      VARCHAR2 (20 BYTE)
    ) ;
  ALTER TABLE OE.CUSTOMERS2 ADD CONSTRAINT CUSTOMER_ID_MIN CHECK ( customer_id             > 0) ;
  ALTER TABLE OE.CUSTOMERS2 ADD CONSTRAINT CUSTOMER_CREDIT_LIMIT_MAX CHECK ( credit_limit <= 5000) ;
  CREATE INDEX OE.CUST_ACCOUNT_MANAGER_IX ON OE.CUSTOMERS2
    (
      ACCOUNT_MGR_ID ASC
    ) ;
  CREATE INDEX OE.CUST_LNAME_IX ON OE.CUSTOMERS2
    ( CUST_LAST_NAME ASC
    ) ;
  CREATE INDEX OE.CUST_EMAIL_IX ON OE.CUSTOMERS2
    ( CUST_EMAIL ASC
    ) ;
  CREATE INDEX OE.CUST_UPPER_NAME_IX ON OE.CUSTOMERS2
    (
      UPPER("CUST_LAST_NAME"),
      UPPER("CUST_FIRST_NAME")
    ) ;
  ALTER TABLE OE.CUSTOMERS2 ADD CONSTRAINT CUSTOMERS_PK PRIMARY KEY ( CUSTOMER_ID ) ;
  CREATE TABLE HR.DEPARTMENTS
    (
      DEPARTMENT_ID   NUMBER (4) NOT NULL ,
      DEPARTMENT_NAME VARCHAR2 (30 BYTE) NOT NULL ,
      MANAGER_ID      NUMBER (6) ,
      LOCATION_ID     NUMBER (4)
    ) ;
  COMMENT ON COLUMN HR.DEPARTMENTS.DEPARTMENT_ID
IS
  'Primary key column of departments table.' ;
  COMMENT ON COLUMN HR.DEPARTMENTS.DEPARTMENT_NAME
IS
  'A not null column that shows name of a department. Administration, 
Marketing, Purchasing, Human Resources, Shipping, IT, Executive, Public 
Relations, Sales, Finance, and Accounting. ' ;
  COMMENT ON COLUMN HR.DEPARTMENTS.MANAGER_ID
IS
  'Manager_id of a department. Foreign key to employee_id column of employees table. The manager_id column of the employee table references this column.' ;
  COMMENT ON COLUMN HR.DEPARTMENTS.LOCATION_ID
IS
  'Location id where a department is located. Foreign key to location_id column of locations table.' ;
  CREATE INDEX HR.DEPT_LOCATION_IX ON HR.DEPARTMENTS
    ( LOCATION_ID ASC
    ) ;
  ALTER TABLE HR.DEPARTMENTS ADD CONSTRAINT DEPT_ID_PK PRIMARY KEY ( DEPARTMENT_ID ) ;
  CREATE TABLE HR.EMPLOYEES
    (
      EMPLOYEE_ID    NUMBER (6) NOT NULL ,
      FIRST_NAME     VARCHAR2 (20 BYTE) ,
      LAST_NAME      VARCHAR2 (25 BYTE) NOT NULL ,
      EMAIL          VARCHAR2 (25 BYTE) NOT NULL ,
      PHONE_NUMBER   VARCHAR2 (20 BYTE) ,
      HIRE_DATE      DATE NOT NULL ,
      JOB_ID         VARCHAR2 (10 BYTE) NOT NULL ,
      SALARY         NUMBER (8,2) ,
      COMMISSION_PCT NUMBER (2,2) ,
      MANAGER_ID     NUMBER (6) ,
      DEPARTMENT_ID  NUMBER (4)
    ) ;
  ALTER TABLE HR.EMPLOYEES ADD CONSTRAINT EMP_SALARY_MIN CHECK ( salary > 0) ;
  COMMENT ON COLUMN HR.EMPLOYEES.EMPLOYEE_ID
IS
  'Primary key of employees table.' ;
  COMMENT ON COLUMN HR.EMPLOYEES.FIRST_NAME
IS
  'First name of the employee. A not null column.' ;
  COMMENT ON COLUMN HR.EMPLOYEES.LAST_NAME
IS
  'Last name of the employee. A not null column.' ;
  COMMENT ON COLUMN HR.EMPLOYEES.EMAIL
IS
  'Email id of the employee' ;
  COMMENT ON COLUMN HR.EMPLOYEES.PHONE_NUMBER
IS
  'Phone number of the employee; includes country code and area code' ;
  COMMENT ON COLUMN HR.EMPLOYEES.HIRE_DATE
IS
  'Date when the employee started on this job. A not null column.' ;
  COMMENT ON COLUMN HR.EMPLOYEES.JOB_ID
IS
  'Current job of the employee; foreign key to job_id column of the 
jobs table. A not null column.' ;
  COMMENT ON COLUMN HR.EMPLOYEES.SALARY
IS
  'Monthly salary of the employee. Must be greater 
than zero (enforced by constraint emp_salary_min)' ;
  COMMENT ON COLUMN HR.EMPLOYEES.COMMISSION_PCT
IS
  'Commission percentage of the employee; Only employees in sales 
department elgible for commission percentage' ;
  COMMENT ON COLUMN HR.EMPLOYEES.MANAGER_ID
IS
  'Manager id of the employee; has same domain as manager_id in 
departments table. Foreign key to employee_id column of employees table.
(useful for reflexive joins and CONNECT BY query)' ;
  COMMENT ON COLUMN HR.EMPLOYEES.DEPARTMENT_ID
IS
  'Department id where employee works; foreign key to department_id 
column of the departments table' ;
  CREATE INDEX HR.EMP_DEPARTMENT_IX ON HR.EMPLOYEES
    ( DEPARTMENT_ID ASC
    ) ;
  CREATE INDEX HR.EMP_JOB_IX ON HR.EMPLOYEES
    ( JOB_ID ASC
    ) ;
  CREATE INDEX HR.EMP_MANAGER_IX ON HR.EMPLOYEES
    ( MANAGER_ID ASC
    ) ;
  CREATE INDEX HR.EMP_NAME_IX ON HR.EMPLOYEES
    (
      LAST_NAME ASC ,
      FIRST_NAME ASC
    ) ;
  ALTER TABLE HR.EMPLOYEES ADD CONSTRAINT EMP_EMP_ID_PK PRIMARY KEY ( EMPLOYEE_ID ) ;
  ALTER TABLE HR.EMPLOYEES ADD CONSTRAINT EMP_EMAIL_UK UNIQUE ( EMAIL ) ;
  CREATE TABLE OE.INVENTORIES
    (
      PRODUCT_ID       NUMBER (6) NOT NULL ,
      WAREHOUSE_ID     NUMBER (3) NOT NULL ,
      QUANTITY_ON_HAND NUMBER (8) NOT NULL
    ) ;
  CREATE INDEX OE.INV_PRODUCT_IX ON OE.INVENTORIES
    ( PRODUCT_ID ASC
    ) ;
  CREATE INDEX OE.INV_WAREHOUSE_IX ON OE.INVENTORIES
    ( WAREHOUSE_ID ASC
    ) ;
  ALTER TABLE OE.INVENTORIES ADD CONSTRAINT INVENTORY_PK PRIMARY KEY ( PRODUCT_ID, WAREHOUSE_ID ) ;
  CREATE TABLE HR.JOBS
    (
      JOB_ID     VARCHAR2 (10 BYTE) NOT NULL ,
      JOB_TITLE  VARCHAR2 (35 BYTE) NOT NULL ,
      MIN_SALARY NUMBER (6) ,
      MAX_SALARY NUMBER (6)
    ) ;
  COMMENT ON COLUMN HR.JOBS.JOB_ID
IS
  'Primary key of jobs table.' ;
  COMMENT ON COLUMN HR.JOBS.JOB_TITLE
IS
  'A not null column that shows job title, e.g. AD_VP, FI_ACCOUNTANT' ;
  COMMENT ON COLUMN HR.JOBS.MIN_SALARY
IS
  'Minimum salary for a job title.' ;
  COMMENT ON COLUMN HR.JOBS.MAX_SALARY
IS
  'Maximum salary for a job title' ;
  ALTER TABLE HR.JOBS ADD CONSTRAINT JOB_ID_PK PRIMARY KEY ( JOB_ID ) ;
  CREATE TABLE HR.JOB_HISTORY
    (
      EMPLOYEE_ID   NUMBER (6) NOT NULL ,
      START_DATE    DATE NOT NULL ,
      END_DATE      DATE NOT NULL ,
      JOB_ID        VARCHAR2 (10 BYTE) NOT NULL ,
      DEPARTMENT_ID NUMBER (4)
    ) ;
  ALTER TABLE HR.JOB_HISTORY ADD CONSTRAINT JHIST_DATE_INTERVAL CHECK (end_date > start_date) ;
  COMMENT ON COLUMN HR.JOB_HISTORY.EMPLOYEE_ID
IS
  'A not null column in the complex primary key employee_id+start_date.
Foreign key to employee_id column of the employee table' ;
  COMMENT ON COLUMN HR.JOB_HISTORY.START_DATE
IS
  'A not null column in the complex primary key employee_id+start_date. 
Must be less than the end_date of the job_history table. (enforced by 
constraint jhist_date_interval)' ;
  COMMENT ON COLUMN HR.JOB_HISTORY.END_DATE
IS
  'Last day of the employee in this job role. A not null column. Must be 
greater than the start_date of the job_history table. 
(enforced by constraint jhist_date_interval)' ;
  COMMENT ON COLUMN HR.JOB_HISTORY.JOB_ID
IS
  'Job role in which the employee worked in the past; foreign key to 
job_id column in the jobs table. A not null column.' ;
  COMMENT ON COLUMN HR.JOB_HISTORY.DEPARTMENT_ID
IS
  'Department id in which the employee worked in the past; foreign key to deparment_id column in the departments table' ;
  CREATE INDEX HR.JHIST_JOB_IX ON HR.JOB_HISTORY
    ( JOB_ID ASC
    ) ;
  CREATE INDEX HR.JHIST_EMPLOYEE_IX ON HR.JOB_HISTORY
    ( EMPLOYEE_ID ASC
    ) ;
  CREATE INDEX HR.JHIST_DEPARTMENT_IX ON HR.JOB_HISTORY
    ( DEPARTMENT_ID ASC
    ) ;
  ALTER TABLE HR.JOB_HISTORY ADD CONSTRAINT JHIST_EMP_ID_ST_DATE_PK PRIMARY KEY ( EMPLOYEE_ID, START_DATE ) ;
  CREATE TABLE HR.LOCATIONS
    (
      LOCATION_ID    NUMBER (4) NOT NULL ,
      STREET_ADDRESS VARCHAR2 (40 BYTE) ,
      POSTAL_CODE    VARCHAR2 (12 BYTE) ,
      CITY           VARCHAR2 (30 BYTE) NOT NULL ,
      STATE_PROVINCE VARCHAR2 (25 BYTE) ,
      COUNTRY_ID     CHAR (2 BYTE)
    ) ;
  COMMENT ON COLUMN HR.LOCATIONS.LOCATION_ID
IS
  'Primary key of locations table' ;
  COMMENT ON COLUMN HR.LOCATIONS.STREET_ADDRESS
IS
  'Street address of an office, warehouse, or production site of a company.
Contains building number and street name' ;
  COMMENT ON COLUMN HR.LOCATIONS.POSTAL_CODE
IS
  'Postal code of the location of an office, warehouse, or production site 
of a company. ' ;
  COMMENT ON COLUMN HR.LOCATIONS.CITY
IS
  'A not null column that shows city where an office, warehouse, or 
production site of a company is located. ' ;
  COMMENT ON COLUMN HR.LOCATIONS.STATE_PROVINCE
IS
  'State or Province where an office, warehouse, or production site of a 
company is located.' ;
  COMMENT ON COLUMN HR.LOCATIONS.COUNTRY_ID
IS
  'Country where an office, warehouse, or production site of a company is
located. Foreign key to country_id column of the countries table.' ;
  CREATE INDEX HR.LOC_CITY_IX ON HR.LOCATIONS
    ( CITY ASC
    ) ;
  CREATE INDEX HR.LOC_STATE_PROVINCE_IX ON HR.LOCATIONS
    ( STATE_PROVINCE ASC
    ) ;
  CREATE INDEX HR.LOC_COUNTRY_IX ON HR.LOCATIONS
    ( COUNTRY_ID ASC
    ) ;
  ALTER TABLE HR.LOCATIONS ADD CONSTRAINT LOC_ID_PK PRIMARY KEY ( LOCATION_ID ) ;
  CREATE TABLE OE.ORDERS
    (
      ORDER_ID     NUMBER (12) NOT NULL ,
      ORDER_DATE   TIMESTAMP WITH LOCAL TIME ZONE NOT NULL ,
      ORDER_MODE   VARCHAR2 (8 BYTE) ,
      CUSTOMER_ID  NUMBER (6) NOT NULL ,
      ORDER_STATUS NUMBER (2) ,
      ORDER_TOTAL  NUMBER (8,2) ,
      SALES_REP_ID NUMBER (6) ,
      PROMOTION_ID NUMBER (6)
    ) ;
  ALTER TABLE OE.ORDERS ADD CONSTRAINT ORDER_MODE_LOV CHECK ( order_mode IN ('direct','online')) ;
  ALTER TABLE OE.ORDERS ADD CONSTRAINT ORDER_TOTAL_MIN CHECK ( order_total >= 0) ;
  CREATE INDEX OE.ORD_SALES_REP_IX ON OE.ORDERS
    ( SALES_REP_ID ASC
    ) ;
  CREATE INDEX OE.ORD_CUSTOMER_IX ON OE.ORDERS
    ( CUSTOMER_ID ASC
    ) ;
  CREATE INDEX OE.ORD_ORDER_DATE_IX ON OE.ORDERS
    ( ORDER_DATE ASC
    ) ;
  ALTER TABLE OE.ORDERS ADD CONSTRAINT ORDER_PK PRIMARY KEY ( ORDER_ID ) ;
  CREATE TABLE OE.ORDER_ITEMS
    (
      ORDER_ID     NUMBER (12) NOT NULL ,
      LINE_ITEM_ID NUMBER (3) NOT NULL ,
      PRODUCT_ID   NUMBER (6) NOT NULL ,
      UNIT_PRICE   NUMBER (8,2) ,
      QUANTITY     NUMBER (8)
    ) ;
  CREATE INDEX OE.ITEM_ORDER_IX ON OE.ORDER_ITEMS
    ( ORDER_ID ASC
    ) ;
  CREATE INDEX OE.ITEM_PRODUCT_IX ON OE.ORDER_ITEMS
    ( PRODUCT_ID ASC
    ) ;
CREATE UNIQUE INDEX OE.ORDER_ITEMS_UK ON OE.ORDER_ITEMS
  (
    ORDER_ID ASC , PRODUCT_ID ASC
  )
  ;
  ALTER TABLE OE.ORDER_ITEMS ADD CONSTRAINT ORDER_ITEMS_PK PRIMARY KEY ( ORDER_ID, LINE_ITEM_ID ) ;
  CREATE TABLE OE.PRODUCT_INFORMATION
    (
      PRODUCT_ID          NUMBER (6) NOT NULL ,
      PRODUCT_NAME        VARCHAR2 (50 BYTE) ,
      PRODUCT_DESCRIPTION VARCHAR2 (2000 BYTE) ,
      CATEGORY_ID         NUMBER (2) ,
      WEIGHT_CLASS        NUMBER (1) ,
      WARRANTY_PERIOD INTERVAL YEAR TO MONTH ,
      SUPPLIER_ID    NUMBER (6) ,
      PRODUCT_STATUS VARCHAR2 (20 BYTE) ,
      LIST_PRICE     NUMBER (8,2) ,
      MIN_PRICE      NUMBER (8,2) ,
      CATALOG_URL    VARCHAR2 (50 BYTE)
    ) ;
  ALTER TABLE OE.PRODUCT_INFORMATION ADD CONSTRAINT PRODUCT_STATUS_LOV CHECK ( product_status IN ('orderable' ,'planned' ,'under development' ,'obsolete')) ;
  CREATE INDEX OE.PROD_SUPPLIER_IX ON OE.PRODUCT_INFORMATION
    (
      SUPPLIER_ID ASC
    ) ;
  ALTER TABLE OE.PRODUCT_INFORMATION ADD CONSTRAINT PRODUCT_INFORMATION_PK PRIMARY KEY ( PRODUCT_ID ) ;
  CREATE TABLE HR.REGIONS
    (
      REGION_ID   NUMBER NOT NULL ,
      REGION_NAME VARCHAR2 (25 BYTE)
    ) ;
  COMMENT ON COLUMN HR.REGIONS.REGION_ID
IS
  'Primary key of regions table.' ;
  COMMENT ON COLUMN HR.REGIONS.REGION_NAME
IS
  'Names of regions. Locations are in the countries of these regions.' ;
  ALTER TABLE HR.REGIONS ADD CONSTRAINT REG_ID_PK PRIMARY KEY ( REGION_ID ) ;
  CREATE TABLE OE.WAREHOUSES
    (
      WAREHOUSE_ID NUMBER (3) NOT NULL ,
      WAREHOUSE_SPEC XMLTYPE ,
      WAREHOUSE_NAME  VARCHAR2 (35 BYTE) ,
      LOCATION_ID     NUMBER (4) ,
      WH_GEO_LOCATION NUMBER (14,4)
    )
    XMLTYPE COLUMN WAREHOUSE_SPEC STORE AS CLOB
    (
      STORAGE ( PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 BUFFER_POOL DEFAULT ) RETENTION ENABLE STORAGE IN ROW NOCACHE
    ) ;
  CREATE INDEX OE.WHS_LOCATION_IX ON OE.WAREHOUSES
    ( LOCATION_ID ASC
    ) ;
  ALTER TABLE OE.WAREHOUSES ADD CONSTRAINT WAREHOUSES_PK PRIMARY KEY ( WAREHOUSE_ID ) ;
  ALTER TABLE HR.COUNTRIES2 ADD CONSTRAINT COUNTR_REG_FK FOREIGN KEY ( REGION_ID ) REFERENCES HR.REGIONS ( REGION_ID ) ;
  ALTER TABLE OE.CUSTOMERS2 ADD CONSTRAINT CUSTOMERS_ACCOUNT_MANAGER_FK FOREIGN KEY ( ACCOUNT_MGR_ID ) REFERENCES HR.EMPLOYEES ( EMPLOYEE_ID ) ON
  DELETE SET NULL ;
  ALTER TABLE HR.DEPARTMENTS ADD CONSTRAINT DEPT_LOC_FK FOREIGN KEY ( LOCATION_ID ) REFERENCES HR.LOCATIONS ( LOCATION_ID ) ;
  ALTER TABLE HR.DEPARTMENTS ADD CONSTRAINT DEPT_MGR_FK FOREIGN KEY ( MANAGER_ID ) REFERENCES HR.EMPLOYEES ( EMPLOYEE_ID ) ;
  ALTER TABLE HR.EMPLOYEES ADD CONSTRAINT EMP_DEPT_FK FOREIGN KEY ( DEPARTMENT_ID ) REFERENCES HR.DEPARTMENTS ( DEPARTMENT_ID ) ;
  ALTER TABLE HR.EMPLOYEES ADD CONSTRAINT EMP_JOB_FK FOREIGN KEY ( JOB_ID ) REFERENCES HR.JOBS ( JOB_ID ) ;
  ALTER TABLE HR.EMPLOYEES ADD CONSTRAINT EMP_MANAGER_FK FOREIGN KEY ( MANAGER_ID ) REFERENCES HR.EMPLOYEES ( EMPLOYEE_ID ) ;
  ALTER TABLE OE.INVENTORIES ADD CONSTRAINT INVENTORIES_PRODUCT_ID_FK FOREIGN KEY ( PRODUCT_ID ) REFERENCES OE.PRODUCT_INFORMATION ( PRODUCT_ID ) ;
  ALTER TABLE OE.INVENTORIES ADD CONSTRAINT INVENTORIES_WAREHOUSES_FK FOREIGN KEY ( WAREHOUSE_ID ) REFERENCES OE.WAREHOUSES ( WAREHOUSE_ID ) ;
  ALTER TABLE HR.JOB_HISTORY ADD CONSTRAINT JHIST_DEPT_FK FOREIGN KEY ( DEPARTMENT_ID ) REFERENCES HR.DEPARTMENTS ( DEPARTMENT_ID ) ;
  ALTER TABLE HR.JOB_HISTORY ADD CONSTRAINT JHIST_EMP_FK FOREIGN KEY ( EMPLOYEE_ID ) REFERENCES HR.EMPLOYEES ( EMPLOYEE_ID ) ;
  ALTER TABLE HR.JOB_HISTORY ADD CONSTRAINT JHIST_JOB_FK FOREIGN KEY ( JOB_ID ) REFERENCES HR.JOBS ( JOB_ID ) ;
  ALTER TABLE HR.LOCATIONS ADD CONSTRAINT LOC_C_ID_FK FOREIGN KEY ( COUNTRY_ID ) REFERENCES HR.COUNTRIES2 ( COUNTRY_ID ) ;
  ALTER TABLE OE.ORDERS ADD CONSTRAINT ORDERS_CUSTOMER_ID_FK FOREIGN KEY ( CUSTOMER_ID ) REFERENCES OE.CUSTOMERS2 ( CUSTOMER_ID ) ON
  DELETE SET NULL ;
  ALTER TABLE OE.ORDERS ADD CONSTRAINT ORDERS_SALES_REP_FK FOREIGN KEY ( SALES_REP_ID ) REFERENCES HR.EMPLOYEES ( EMPLOYEE_ID ) ON
  DELETE SET NULL ;
  ALTER TABLE OE.ORDER_ITEMS ADD CONSTRAINT ORDER_ITEMS_ORDER_ID_FK FOREIGN KEY ( ORDER_ID ) REFERENCES OE.ORDERS ( ORDER_ID ) ON
  DELETE CASCADE ;
  ALTER TABLE OE.ORDER_ITEMS ADD CONSTRAINT ORDER_ITEMS_PRODUCT_ID_FK FOREIGN KEY ( PRODUCT_ID ) REFERENCES OE.PRODUCT_INFORMATION ( PRODUCT_ID ) ;
  ALTER TABLE OE.PRODUCT_INFORMATION ADD CONSTRAINT PRODUCT_INFO_CATEGORY_FK FOREIGN KEY ( CATEGORY_ID ) REFERENCES OE.CATEGORIES ( CATEGORY_ID ) ;
  ALTER TABLE OE.WAREHOUSES ADD CONSTRAINT WAREHOUSES_LOCATION_FK FOREIGN KEY ( LOCATION_ID ) REFERENCES HR.LOCATIONS ( LOCATION_ID ) ON
  DELETE SET NULL ;
