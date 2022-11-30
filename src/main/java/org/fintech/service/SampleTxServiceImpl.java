package org.fintech.service;

import org.fintech.mapper.Sample1Mapper;
import org.fintech.mapper.Sample2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService{
	//컴파일시 인터페이스를 자동 주입
	@Setter(onMethod_ = {@Autowired})
	private Sample1Mapper mapper1;
	
	@Setter(onMethod_ = {@Autowired})
	private Sample2Mapper mapper2;
	
	//한개의 Task에 있는 두개의 테이블에 데이터를 insert하는 두개의 작업이 모두 성공하면 
	//commit 하고 하나라도 실패하면 자동 Rollback 처리
	@Transactional
	@Override
	public void addData(String value) {
		log.info("mapper1.................");
		mapper1.isertCol1(value);
		
		log.info("mapper2.................");
		mapper2.isertCol2(value);
		
		log.info("end.....................");	
	}

}
