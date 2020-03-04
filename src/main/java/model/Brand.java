package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
/*
    // TODO renamed Brand to distillery?
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brand implements Nameable {

    private Long id;
    private String name;

    public Brand(@NonNull  String name) {
        this.name = name;
    }

    public Brand() {
    }
}
