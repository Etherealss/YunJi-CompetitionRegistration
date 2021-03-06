package pers.etherealss.pojo.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author 寒洲
 * @description 分页类
 * @date 2020/10/22
 */
@Data
public class PageBo<T> {
	/** 第一条记录的数据库序号 */
	private int start;

	/** 每一页显示的记录数 */
	@JSONField(ordinal = 0)
	private int rows;

	/** 总记录数 */
	@JSONField(ordinal = 0)
	private int totalCount;

	/** 总页码 */
	@JSONField(ordinal = 0)
	private int totalPage;

	/** 当前页码 */
	@JSONField(ordinal = 0)
	private int currentPage;

	/** 每页的数据 */
	@JSONField(ordinal = 1)
	private List<T> pageData;

	/**
	 * @param currentPage 当前页
	 * @param rows 每页显示的行数，对应offset
	 */
	public PageBo(int currentPage, int rows) {
		this.rows = rows;
		this.currentPage = currentPage;
	}

	public PageBo() {
	}
}
