package webTest.entity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Lg_Invite")
@AssociationOverrides({
		@AssociationOverride(name = "usi.user", joinColumns = @JoinColumn(name = "user_id")),
		@AssociationOverride(name = "usi.survey", joinColumns = @JoinColumn(name = "survey_id")) })
public class LgInvite implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserSurveyId usi = new UserSurveyId();

	private boolean isHost;
	private boolean isIgnored;

	
	public UserSurveyId getUsi() {
		return usi;
	}

	public void setUsi(UserSurveyId usi) {
		this.usi = usi;
	}

	@Transient
	public LgUser getUser(){
		return getUsi().getUser();
	}
	
	public void setUser(LgUser user){
		getUsi().setUser(user);
	}
	
	@Transient
	public LgSurvey getSurvey(){
		return getUsi().getSurvey();
	}
	
	public void setSurvey(LgSurvey survey){
		getUsi().setSurvey(survey);
	}
	
	public boolean isHost() {
		return isHost;
	}

	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

	public boolean isIgnored() {
		return isIgnored;
	}

	public void setIgnored(boolean isIgnored) {
		this.isIgnored = isIgnored;
	}
}
