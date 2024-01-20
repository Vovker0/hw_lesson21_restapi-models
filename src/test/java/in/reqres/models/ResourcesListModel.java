package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourcesListModel {
    Integer total;
    @JsonProperty("total_pages")
    Integer totalPages;
    ColorData[] data;
    SupportData support;
}
