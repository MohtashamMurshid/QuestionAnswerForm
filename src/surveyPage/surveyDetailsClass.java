package surveyPage;

public class surveyDetailsClass {

    private String surveyCreatorId;

    private String surveyTitle;

    private String surveyNumber;

    private String surveyDescription;

    public surveyDetailsClass() {
        this.surveyCreatorId = null;
        this.surveyTitle = null;
        this.surveyNumber = null;
        this.surveyDescription = null;
    }

    public String getSurveyCreatorId() {
        return surveyCreatorId;
    }

    public void setSurveyCreatorId(String surveyCreatorId) {
        this.surveyCreatorId = surveyCreatorId;
    }
    
    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyNumber() {
        return ("surv" + surveyNumber);
    }
    public void setSurveyNumber(String surveyNumber) {
        this.surveyNumber = surveyNumber;
    }
    
    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }
}
