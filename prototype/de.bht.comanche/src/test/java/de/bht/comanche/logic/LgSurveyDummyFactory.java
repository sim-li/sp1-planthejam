package de.bht.comanche.logic;

import java.util.Date;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgSurveyType;
import de.bht.comanche.logic.LgTimeUnit;

public class LgSurveyDummyFactory {
	@SuppressWarnings("deprecation")
	public  LgSurvey getSurvey0(){
		return new LgSurvey().setName("Bier trinken")
											.setDescription("puenktlich")
											.setFrequencyDist(1)
											.setDeadline(new Date(114, 7, 17,  7, 15, 0))
											.setFrequencyTimeUnit(LgTimeUnit.DAY)
											.setType(LgSurveyType.ONE_TIME);
	}
	
	@SuppressWarnings("deprecation")
	public  LgSurvey getSurvey1(){
		return new LgSurvey().setName("Fotografieren")
											.setDescription("bringen alle bitte jede ein Regenschirm mit!!!")
											.setFrequencyDist(1)
											.setDeadline(new Date(114, 8, 17,  7, 15, 0))
											.setFrequencyTimeUnit(LgTimeUnit.DAY)
											.setType(LgSurveyType.ONE_TIME);
	}
}
