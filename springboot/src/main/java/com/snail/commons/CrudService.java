package com.snail.commons;
public interface CrudService<Model, Dto> {
	/**
	 * 保存一条信息
	 * 
	 * @param dto
	 * @return
	 */
	public Model save(Dto dto);

	/**
	 * 删除一跳信息
	 * 
	 * @param id
	 * @return
	 */
	public Model delete(long id);

	/**
	 * 更新一条细信息
	 * 
	 * @param dto
	 * @return
	 */
	public Model update(long id, Dto dto);

	/**
	 * 获取一条信息
	 * 
	 * @param id
	 * @return
	 */
	public Model get(long id);
}