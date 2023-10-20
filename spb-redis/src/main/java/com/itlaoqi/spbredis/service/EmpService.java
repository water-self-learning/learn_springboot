package com.itlaoqi.spbredis.service;

import com.itlaoqi.spbredis.dao.EmpDAO;
import com.itlaoqi.spbredis.entity.Emp;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmpService {
    @Resource
    EmpDAO empDao;

    // 对于默认情况下, Redis对象的序列化使用的是JDK序列化.必须要求实体类实现Serili..接口
    // Cacheable会将方法的返回值序列化后存储到redis中,key就是参数执行的字符串
    // Cacheable的用途就是在执行方法前检查对应的key是否存在,存在则直接从redis中取出不执行方法中的代码
    // 没有对应的key则执行方法代码,并将返回值序列化保存到redis中
    // condition代表条件成立的时候才执行缓存的数据 , 反之有一个unless ,代表条件不成立的时候才获取缓存
    @Cacheable(value = "emp" , key = "#empId" ,condition = "#empId != 1000" ,cacheManager = "cacheManager1m")
    public Emp findById(Integer empId) {
        return empDao.findById(empId);
    }

    @Cacheable(value = "emp:rank:salary")
    public List<Emp> getEmpRank() {
        return empDao.selectByParams();
    }

    //@CachePut 更新缓存，没有key则创建
    @CachePut(value="emp" , key="#emp.empno")
    public Emp create(Emp emp) {
        return empDao.insert(emp);
    }

    @CachePut(value="emp" , key="#emp.empno")
    public Emp update(Emp emp) {
        return empDao.update(emp);
    }

    //@CacheEvict 删除缓存
    @CacheEvict(value = "emp",key = "#empno")
    public void delete(Integer empno) {
        empDao.delete(empno);
    }
}
