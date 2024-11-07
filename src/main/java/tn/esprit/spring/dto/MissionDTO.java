package tn.esprit.spring.dto;

public class MissionDTO {
    private String name;
    private String description;
    private int id;
    
    public MissionDTO() {
        super();
    }
    
    
    public MissionDTO(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }
    
    public MissionDTO(String name) {
        super();
        this.name = name;
    }
    
    public MissionDTO(int id) {
        super();
        this.id = id;
    }
    
    public MissionDTO(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }
    
    public MissionDTO(String name, String description, int id) {
        super();
        this.name = name;
        this.description = description;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
}
