package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourcesListModel {
    Integer total, total_pages;
    ColorData[] data;
    SupportData support;
}
