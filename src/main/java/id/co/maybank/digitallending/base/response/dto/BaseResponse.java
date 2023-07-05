package id.co.maybank.digitallending.base.response.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse {

	public static Response response(HttpStatus httpStatus, String responseMessage, Object object) {

		var header = Header.builder().trxLog("").processingTime(121).httpStatus(httpStatus)
				.responseMessage(responseMessage).build();

		var data = Data.builder().object(object).build();

		var body = Body.builder().data(data).build();

		return Response.builder().body(body).header(header).build();
	}
}
