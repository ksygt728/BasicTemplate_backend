package com.basic.app.annotation;

import java.lang.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.basic.app.api.ResponseApi;
import com.basic.app.api.ResponseApiFailForSwagger;
import com.basic.app.api.ResponseApiSuccessForSwagger;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses({
                @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class))),
                @ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ResponseApiFailForSwagger.class))),
                @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(schema = @Schema(implementation = ResponseApiFailForSwagger.class)))
})
public @interface SwaggerCommonResponseApi {
}