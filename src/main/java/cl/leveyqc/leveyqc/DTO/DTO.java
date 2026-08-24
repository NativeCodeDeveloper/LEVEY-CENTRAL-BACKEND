package cl.leveyqc.leveyqc.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DTO {
    private boolean success;
    private String message;
    private Object data;

    public DTO() {
    }

    public DTO(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
