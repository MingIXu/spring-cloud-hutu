package com.hutu.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * service实现父类
 *
 * @author hutu
 * @date 2020/6/22 2:06 下午
 */
public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {
    // 可添加一下公共方法
}
