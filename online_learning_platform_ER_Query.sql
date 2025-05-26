CREATE SCHEMA IF NOT EXISTS online_learning_platform_ER;
USE online_learning_platform_ER;

-- Instructor Table
CREATE TABLE IF NOT EXISTS Instructor (
  instruct_id INT PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  email VARCHAR(45),
  contact VARCHAR(10) NOT NULL,
  date_joined TIMESTAMP NOT NULL,
  bio TEXT
);

-- Course Table
CREATE TABLE IF NOT EXISTS Course (
  cour_id INT PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  description TEXT,
  category ENUM('Programming', 'Aptitude', 'Commmunication', 'General') DEFAULT 'General',
  status ENUM('Active', 'Inactive') DEFAULT 'Inactive',
  price DECIMAL(10,2) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  instruct_id INT,
  FOREIGN KEY (instruct_id) REFERENCES Instructor(instruct_id)
);

-- Student Table
CREATE TABLE IF NOT EXISTS Student (
  stud_id INT PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  email VARCHAR(45) NOT NULL,
  contact VARCHAR(10) NOT NULL
);

-- Enrollment Table
CREATE TABLE IF NOT EXISTS Enrollment (
  enroll_id INT PRIMARY KEY,
  enrolled_date DATE NOT NULL,
  status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
  cour_id INT,
  stud_id INT,
  instruct_id INT,
  FOREIGN KEY (cour_id) REFERENCES Course(cour_id),
  FOREIGN KEY (stud_id) REFERENCES Student(stud_id),
  FOREIGN KEY (instruct_id) REFERENCES Instructor(instruct_id)
);

-- Assignment Table
CREATE TABLE IF NOT EXISTS Assignment (
  assign_id INT PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  description TEXT NOT NULL,
  due_date DATE NOT NULL,
  created_at DATE,
  grade ENUM('good', 'satisfactory', 'bad', 'pending') DEFAULT 'pending',
  cour_id INT,
  instruct_id INT,
  FOREIGN KEY (instruct_id) REFERENCES Instructor(instruct_id),
  FOREIGN KEY (cour_id) REFERENCES Course(cour_id)
);
