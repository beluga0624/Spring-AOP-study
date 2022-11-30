package org.fintech.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample1Mapper {
	//mybatis 관련 어노테이션
	@Insert("insert into tbl_sample1 (col1) values (#{data})")
	public int isertCol1(String data);
}
