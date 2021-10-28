package pers.etherealss.common.strategy.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import pers.etherealss.common.strategy.page.PageQueryStrategy;

/**
 * @author wtk
 * @description 获取页面数据
 * @date 2021-10-09
 */
public class DefaultQueryStragety<T> implements PageQueryStrategy<T> {

    @Override
    public void definePageData(QueryWrapper<T> queryWrapper, String... keywords) {
        // 空实现
    }
}
