package com.snail.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 公用转换器
 * 
 * @author snail
 *
 * @param <M>
 * @param <DTO>
 */
public abstract class AbstractConverter<M, DTO extends Dto> {

	/**
	 * 将实体模型转换为DTO
	 * 
	 * @param model
	 * @return
	 */
	public abstract DTO toDto(M model);

	/**
	 * 将实体模型列表转换为DTO列表
	 * 
	 * @param models
	 * @return
	 */
	public List<DTO> toDto(List<M> models) {
		List<DTO> result;
		if (CollectionUtils.isNotEmpty(models)) {
			result = new ArrayList<DTO>();
			for (M m : models) {
				result.add(toDto(m));
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	public List<DTO> toDto(Iterator<M> models) {
		List<DTO> result = new ArrayList<DTO>();
		while (models.hasNext()) {
			M m = models.next();
			result.add(toDto(m));
		}
		return result;
	}
	/**
	 * 将DTO中的数据复制到实体模型<br />
	 * 
	 * 所有的Converter必须实现该方法<br />
	 * 注意在复制数据的时候，如果存在关联关系，复制不应该在converter进行<br />
	 * converter不应该实现任何关联Respository的操做，它仅仅只作为一个复制数据的工具出现的
	 * 
	 * @param Model
	 * @param dto
	 */
	public abstract void copyProperties(M model, DTO dto);

}

