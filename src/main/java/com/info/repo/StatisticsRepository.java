package com.info.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.info.model.Response;
import com.info.model.Statistic;
import com.info.model.SurveyAnswerStatistics;

public interface StatisticsRepository  extends CrudRepository<Statistic, Long>{
	
	
}
