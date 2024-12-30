import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public interface Researcher {
    ArrayList<ResearchPaper> getPapers();

    default void printPapers(Comparator<ResearchPaper> comparator) {
        ArrayList<ResearchPaper> papers = getPapers();
        Collections.sort(papers, comparator);
        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }

    default int calculateHIndex() {
        ArrayList<ResearchPaper> papers = getPapers();
        papers.sort((a, b) -> Integer.compare(b.getCitations(), a.getCitations()));
        int hIndex = 0;
        for (int i = 0; i < papers.size(); i++) {
            if (papers.get(i).getCitations() >= i + 1) {
                hIndex++;
            } else {
                break;
            }
        }
        return hIndex;
    }
}
