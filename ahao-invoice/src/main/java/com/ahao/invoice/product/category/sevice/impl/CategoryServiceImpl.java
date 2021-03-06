package com.ahao.invoice.product.category.sevice.impl;

import com.ahao.invoice.base.service.impl.PageServiceImpl;
import com.ahao.invoice.product.category.dao.CategoryDAO;
import com.ahao.invoice.product.category.entity.CategoryDO;
import com.ahao.invoice.product.category.sevice.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * Created by Ahaochan on 2017/8/21.
 */
@Service
public class CategoryServiceImpl extends PageServiceImpl<CategoryDO> implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    protected Mapper<CategoryDO> dao() {
        return categoryDAO;
    }

    @Override
    protected Class<CategoryDO> clazz() {
        return CategoryDO.class;
    }

    @Override
    protected Collection<CategoryDO> getByPage(int start, int pageSize, String sort, String order) {
        return categoryDAO.selectPage(start, pageSize, sort, order);
    }

    @Override
    public List<CategoryDO> selectByName(String name) {

        Example example = new Example(CategoryDO.class);
        example.selectProperties("id", "name", "description")
                .createCriteria().andLike("name", name+"%");
        return categoryDAO.selectByExample(example);
    }
}
