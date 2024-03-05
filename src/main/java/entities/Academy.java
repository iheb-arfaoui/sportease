package entities;

import java.util.List;
import javax.persistence.OneToMany;

/**
 *
 * @author ramib
 */
public class Academy {
    private int id;
    private String name;
    private String category;
    private String imageName;
    private String created_by;

    private String localisation;


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Academy(int id, String name, String category, String imageName, String created_by, String localisation, List<Coach> coaches) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageName = imageName;
        this.created_by = created_by;
        this.localisation = localisation;
        this.coaches = coaches;
    }

    public Academy(int id, String name, String category, String created_by, String localisation) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.created_by = created_by;
        this.localisation = localisation;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_by() {
        return created_by;
    }

    private List<Coach> coaches;

    public Academy() {}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    @Override
    public String toString() {
        return "Academy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", imageName='" + imageName + '\'' +
                ", created_by='" + created_by + '\'' +
                ", localisation='" + localisation + '\'' +
                ", coaches=" + coaches +
                '}';
    }

    @OneToMany(mappedBy = "academy")
    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }
}


