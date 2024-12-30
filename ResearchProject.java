import java.util.ArrayList;

public class ResearchProject {
    private String topic;
    private ArrayList<ResearchPaper> publishedPapers;
    private ArrayList<Researcher> participants;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Object person) throws Exception {
        if (person instanceof Researcher) {
            participants.add((Researcher) person);
        } else {
            throw new Exception("Only researchers can join a research project.");
        }
    }

    public void publishPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
    }

    public ArrayList<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }
}
