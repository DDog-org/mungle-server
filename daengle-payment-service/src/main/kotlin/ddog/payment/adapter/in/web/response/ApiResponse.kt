package ddog.payment.adapter.`in`.web.response

import org.springframework.http.HttpStatus

data class ApiResponse<T> (
    val status: Int = 200,
    val message: String = "",
    val data: T? = null
) {

    companion object {
        fun <T> with(httpStatus: HttpStatus, message: String, data: T?): ApiResponse<T> {
            return ApiResponse(status = httpStatus.value(), message = message, data = data)
        }
    }
}