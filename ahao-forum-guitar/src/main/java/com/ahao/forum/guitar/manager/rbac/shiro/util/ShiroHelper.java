package com.ahao.forum.guitar.manager.rbac.shiro.util;

import com.ahao.core.config.Setter;
import com.ahao.core.entity.IDataSet;
import org.apache.shiro.SecurityUtils;

public abstract class ShiroHelper {

    public static long getMyUserId(){
        return getCurrentUser().getLong("id");
    }

    public static int getMyUserWeight(){
        return getCurrentUser().getInt("weight");
    }

    public static boolean isRoot(){
        int maxWeight = Setter.getInt("role.weight.max");
        int weight = ShiroHelper.getMyUserWeight();
        return weight >= maxWeight;
    }


    public static IDataSet getCurrentUser(){
        return (IDataSet) SecurityUtils.getSubject().getPrincipal();
    }
}