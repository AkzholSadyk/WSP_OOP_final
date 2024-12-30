import java.text.SimpleDateFormat;
import java.util.Date;

public class ResearchPaper {
    private String title;
    private String authors;
    private String journal;
    private int pages;
    private Date datePublished;
    private String doi;
    private int citations;

    public ResearchPaper(String title, String authors, String journal, int pages, Date datePublished, String doi, int citations) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.datePublished = new Date();
        this.doi = doi;
        this.citations = citations;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getCitations() {
        return citations;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public int getPages() {
        return pages;
    }

    public String getCitation(String format) {
        if ("Plain Text".equalsIgnoreCase(format)) {
            return String.format("%s, %s. \"%s.\" %s, %s, DOI: %s",
                    authors, datePublished.toString(), title, journal, pages, doi);
        } else if ("Bibtex".equalsIgnoreCase(format)) {
            return String.format("@article{%s,\n  author={%s},\n  title={%s},\n  journal={%s},\n  year={%s},\n  pages={%d},\n  doi={%s}\n}",
                    title.replaceAll(" ", "_"), authors, title, journal, new SimpleDateFormat("yyyy").format(datePublished), pages, doi);
        } else {
            return "Invalid format";
        }
    }

    public String toString() {
        return String.format("Title: %s, Authors: %s, Journal: %s, Date: %s, Pages: %d, DOI: %s, Citations: %d",
                title, authors, journal, new SimpleDateFormat("dd/MM/yyyy").format(datePublished), pages, doi, citations);
    }
}
