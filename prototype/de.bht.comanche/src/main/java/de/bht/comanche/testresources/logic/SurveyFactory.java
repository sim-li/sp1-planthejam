package de.bht.comanche.testresources.logic;

import java.util.Date;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.SurveyType;
import de.bht.comanche.logic.TimeUnit;

public class SurveyFactory {
	@SuppressWarnings("deprecation")
	public  LgSurvey getSurvey0(){
		return new LgSurvey().setName("Bier trinken")
											.setDescription("puenktlich")
											.setFrequencyDist(1)
											.setDeadline(new Date(114, 7, 17,  7, 15, 0))
											.setFrequencyTimeUnit(TimeUnit.DAY)
											.setType(SurveyType.ONE_TIME);
	}
	
	@SuppressWarnings("deprecation")
	public  LgSurvey getSurvey1(){
		return new LgSurvey().setName("Fotografieren")
											.setDescription("bringen alle bitte jede ein Regenschirm mit!!!")
											.setFrequencyDist(1)
											.setDeadline(new Date(114, 8, 17,  7, 15, 0))
											.setFrequencyTimeUnit(TimeUnit.DAY)
											.setType(SurveyType.ONE_TIME);
	}
}
