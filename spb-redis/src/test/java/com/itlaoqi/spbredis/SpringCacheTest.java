package com.itlaoqi.spbredis;

import com.itlaoqi.spbredis.entity.Emp;
import com.itlaoqi.spbredis.service.EmpService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class SpringCacheTest {
    @Resource
    private EmpService empService;

    @Test
    public void testFindById(){
        Emp emp = empService.findById(1001);
        emp = empService.findById(1001);
        emp = empService.findById(1001);
        emp = empService.findById(1001);
        emp = empService.findById(1001);
        emp = empService.findById(1001);
        System.out.println(emp.getName());
        emp = empService.findById(1000);
        emp = empService.findById(1000);
        emp = empService.findById(1000);
        System.out.println(emp.getName());
    }

    @Test
    public void testEmpRank() {
        List<Emp> list = empService.getEmpRank();
        list = empService.getEmpRank();
        for(Emp emp:list){
            System.out.println(emp);
        }
    }
    @Test
    public void testCreate(){
        empService.create(new Emp(1002 , "emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
        empService.create(new Emp(1002 , "emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
        empService.create(new Emp(1002 , "emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
        Emp emp = empService.findById(1002);
        System.out.println(emp);
    }

    @Test
    public void testUpdate(){
        empService.update(new Emp(1002 , "u-emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
        empService.update(new Emp(1002 , "u-emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
        empService.update(new Emp(1002 , "u-emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
        Emp emp = empService.findById(1002);
        System.out.println(emp);
    }
    @Test
    public void testDelete(){
        empService.delete(1002);
        //Emp emp = empService.findById(1002);
        //System.out.println(emp);
    }
}
