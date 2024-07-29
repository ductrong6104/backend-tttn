package org.example.dienluc.errors;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.payload.ResponseData;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import tech.jhipster.config.JHipsterDefaults;


import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles {@link EntityNotFoundException}.
     * Được sử dụng để chỉ ra rằng một thực thể không được tìm thấy trong cơ sở dữ liệu.
     * @param ex the exception
     * @param request the web request
     * @return the {@link ResponseEntity} with status {@code 404 (Not Found)} and a {@link ResponseData} containing the error message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String errorMessage = "Entity not found exception: " + ex.getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("Entity not found exception")
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    /**
     * Handles {@link DataIntegrityViolationException}.
     * Được sử dụng khi có vi phạm về tính toàn vẹn dữ liệu, chẳng hạn như vi phạm ràng buộc khóa chính, khóa ngoại, hoặc các ràng buộc duy nhất.
     * @param ex the exception
     * @param request the web request
     * @return the {@link ResponseEntity} with status {@code 400 (Bad Request)} and a {@link ResponseData} containing the error message
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String errorMessage = "Data integrity violation: " + ex.getRootCause().getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("Data integrity violation")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    /**
     * Handles {@link ConstraintViolationException}.
     * Được sử dụng khi có vi phạm về ràng buộc dữ liệu ở mức Hibernate (ORM), chẳng hạn như các ràng buộc về độ dài, định dạng hoặc các ràng buộc tùy chỉnh khác được định nghĩa trong thực thể.
     * @param ex the exception
     * @param request the web request
     * @return the {@link ResponseEntity} with status {@code 400 (Bad Request)} and a {@link ResponseData} containing the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String errorMessage = "Constraint violation: " + ex.getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("Constraint violation")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    /**
     * Handles {@link MethodArgumentNotValidException}.
     * Được sử dụng khi các tham số của phương thức không hợp lệ, thường xảy ra trong quá trình xác thực dữ liệu đầu vào của các phương thức xử lý yêu cầu REST.
     * @param ex the exception
     * @param request the web request
     * @return the {@link ResponseEntity} with status {@code 400 (Bad Request)} and a {@link ResponseData} containing the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseData responseData = ResponseData.builder()
                .data(errors)
                .message("Not valid argument in method")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    /**
     * Handles {@link AccessDeniedException}.
     * Được sử dụng khi người dùng không có quyền truy cập vào tài nguyên hoặc thực hiện hành động nào đó.
     * @param ex the exception
     * @param request the web request
     * @return the {@link ResponseEntity} with status {@code 403 (Forbidden)} and a {@link ResponseData} containing the error message
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String errorMessage = "Access denied: " + ex.getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("Access denied")
                .status(HttpStatus.FORBIDDEN.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    /**
     * Handles general {@link Exception}.
     * Lớp ngoại lệ tổng quát, được sử dụng để xử lý tất cả các ngoại lệ không được xử lý bởi các trình xử lý ngoại lệ cụ thể hơn.
     * @param ex the exception
     * @param request the web request
     * @return the {@link ResponseEntity} with status {@code 500 (Internal Server Error)} and a {@link ResponseData} containing the error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = "An error occurred: " + ex.getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("An error occurred:")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
