package skbaek.shorteningurl.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApiResponse<T> {

    String result = "OK";
    String status;
    T data;

}
