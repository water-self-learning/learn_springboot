package com.itlaoqi.spbredis.dao;

import com.itlaoqi.spbredis.entity.Emp;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class EmpDAO {
    public Emp findById(Integer empId){
        System.out.println("执行了findById方法:EmpId:" + empId);
        return new Emp(empId , "itlaoqi"  , new Date()  , 1000f ,"RESEARCH");
    }

    public List<Emp> selectByParams(){
        System.out.println("已执行selectByPrams方法");
        List list = new ArrayList();
        for(int i = 0 ; i < 10 ; i++) {
            list.add(new Emp(i , "emp" +  i , new Date() , 5000 + i * 100f , "RESEARCH"));
        }
        return list;
    }

    public Emp insert(Emp emp){
        System.out.println("正在创建" + emp.getEmpno() + "员工信息");
        return emp;
    }

    public Emp update(Emp emp){
        System.out.println("正在更新" + emp.getEmpno() + "员工信息");
        return emp;
    }

    public void delete(Integer empno){
        System.out.println("删除" + empno + "员工信息");
    }
}
