package mapper.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseData<T> {
    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回描述
     */
    private String message;

    private T data;

    public ResponseData() {
    }

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
