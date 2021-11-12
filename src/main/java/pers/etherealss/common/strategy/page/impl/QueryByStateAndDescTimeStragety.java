package pers.etherealss.common.strategy.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import pers.etherealss.common.strategy.page.PageQueryStrategy;

/**
 * @author wtk
 * @description 获取页面数据，按时间排序，最新在最前
 * @date 2021-10-09
 */
public class QueryByStateAndDescTimeStragety<T> implements PageQueryStrategy<T> {

    @Override
    public void definePageData(QueryWrapper<T> queryWrapper) {
    }
}
