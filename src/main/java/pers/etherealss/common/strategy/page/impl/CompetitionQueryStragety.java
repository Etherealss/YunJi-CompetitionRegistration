package pers.etherealss.common.strategy.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import pers.etherealss.common.enums.PublishState;
import pers.etherealss.common.strategy.page.PageQueryStrategy;

/**
 * @author wtk
 * @description 获取页面数据，按时间排序，最新在最前
 * @date 2021-10-09
 */
public class CompetitionQueryStragety<T> implements PageQueryStrategy<T> {

    @Override
    public void definePageData(QueryWrapper<T> queryWrapper) {
        // 列名降序，最新在最前
        queryWrapper.orderByDesc("update_time", "create_time");
        queryWrapper.eq("state", PublishState.PUBLISHED);
    }
}
