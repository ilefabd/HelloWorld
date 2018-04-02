package com.info.model;

public class SurveyAnswerStatistics {
	private String organisation;
	  private Long   cnt;

	  public SurveyAnswerStatistics(String organisation, Long cnt) {
	    this.organisation = organisation;
	    this.cnt  = cnt;
	  }

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "SurveyAnswerStatistics [organisation=" + organisation + ", cnt=" + cnt + "]";
	}
	  
	  

}
