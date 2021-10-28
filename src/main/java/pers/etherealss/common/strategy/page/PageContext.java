package pers.etherealss.common.strategy.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import pers.etherealss.common.exception.ErrorParamException;
import pers.etherealss.common.strategy.page.impl.DefaultQueryStragety;
import pers.etherealss.pojo.bo.PageBo;

/**
 * @author wtk
 * @description 策略模式：分页策略上下文
 * @date 2021-10-09
 */
@Slf4j
public class PageContext<T> {

    /**
     * 获取数据的mapper
     */
    private BaseMapper<T> baseMapper;

    /**
     * 获取数据的策略
     */
    private PageQueryStrategy<T> queryStrategy;

    /**
     * 传入用于获取数据的Mapper实现类，使用了默认的实现逻辑，将会直接通过limit查询页面数据，不做排序等其他处理
     * @param mapper 用于获取数据的mapper
     */
    public PageContext(BaseMapper<T> mapper) {
        this(mapper, new DefaultQueryStragety<>());
    }

    /**
     * 传入用于获取数据的Mapper以及查询策略
     * @param mapper 用于获取数据的mapper
     * @param queryStrategy 查询策略，见{@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    public PageContext(BaseMapper<T> mapper, PageQueryStrategy<T> queryStrategy) {
        this.baseMapper = mapper;
        this.queryStrategy = queryStrategy;
    }

    /**
     * 获取分页
     * @param curPage
     * @param offset
     * @param keywords
     * @return
     */
    public PageBo<T> bulidPage(int curPage, int offset, String... keywords) {
        // 计算查询索引，其实页面是第1页，但数据库第一条记录的索引是0，所以减1
        int start = (curPage - 1) * offset;

        /*
        获取记录总数，用于计算页码
         */
        int totalCount = baseMapper.selectCount(null);

        // 计算总页码数
        // 如果 总记录数可以整除以每页显示的记录数，那么总页数就是它们的商
        // 否则 说明有几条数据要另开一页显示，总页数+1
        int totalPage = totalCount / offset;
        totalPage = totalCount % offset == 0 ? totalPage : totalPage + 1;
        if (curPage > totalPage) {
            log.warn("当前页码超出最大页码，无匹配项目：当前页数：{}, 总页数：{}", curPage, totalPage);
            throw new ErrorParamException("当前页码超出最大页码，无匹配项目");
        }

        // 包装页面信息
        PageBo<T> page = new PageBo<>(curPage, offset);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);

        /*
        获取查询逻辑QueryWrapper，并查询页面数据
         */
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 添加分页语句
        queryWrapper.last(" limit " + start + ", " + offset);
        queryStrategy.definePageData(queryWrapper, keywords);
        // 查询并保存在page对象中
        page.setPageData(baseMapper.selectList(queryWrapper));

        return page;
    }
}
