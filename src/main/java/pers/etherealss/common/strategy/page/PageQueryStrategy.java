package pers.etherealss.common.strategy.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author wtk
 * @description 分页数据查询策略
 * @date 2021-10-09
 */
public interface PageQueryStrategy<T> {

    /**
     * 定义查询逻辑
     *
     * @param queryWrapper
     * @return
     */
    void definePageData(QueryWrapper<T> queryWrapper);
}
