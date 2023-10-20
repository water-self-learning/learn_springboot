package com.itlaoqi.spbredis.entity;

import java.io.Serializable;
import java.util.Date;

public class Emp implements Serializable{
    private Integer empno;
    private String name;
    private Date birthday;
    private Float salary;
    private String department;
    public Emp(){ //必须要有默认构造函数

    }
    public Emp(Integer empno, String name, Date birthday, Float salary, String department) {
        this.empno = empno;
        this.name = name;
        this.birthday = birthday;
        this.salary = salary;
        this.department = department;
    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
