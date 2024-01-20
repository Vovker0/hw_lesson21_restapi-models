package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ColorData {
    Integer id, year;
    String name, color;
    @JsonProperty("pantone_value")
    String pantoneValue;
}