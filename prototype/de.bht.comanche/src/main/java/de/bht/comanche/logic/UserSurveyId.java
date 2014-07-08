package webTest.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserSurveyId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private LgUser user;
	
	@ManyToOne
	private LgSurvey survey;

	public LgUser getUser() {
		return user;
	}

	public void setUser(LgUser user) {
		this.user = user;
	}

	public LgSurvey getSurvey() {
		return survey;
	}

	public void setSurvey(LgSurvey survey) {
		this.survey = survey;
	}	
}
