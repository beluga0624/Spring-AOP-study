package org.fintech.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample2Mapper {
	//mybatis 관련 어노테이션
	@Insert("insert into tbl_sample2 (col2) values (#{data})")
	public int isertCol2(String data);
}
