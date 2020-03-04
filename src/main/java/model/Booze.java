package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Data
/*
 * Need to add Year, Botanical (ingredients), Notes (taste), Geography/Location (Japanese, Highland, Isley)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booze implements Nameable {

    private Long id;

    private Brand brand;

    private String name;

    private String description;

    public Booze(@NonNull Brand brand, @NonNull String name, @NonNull String description) {
        this.brand = brand;
        this.name = name;
        this.description = description;
    }

    public Booze(@NonNull String name) {
        this.name = name;
    }

    public Booze() {
    }
}
