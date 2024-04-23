package ipvc.gymbuddy.api.core

import com.google.gson.Gson
import retrofit2.Response

class ResponseParser {

    companion object {
        fun <T> Success(response: Response<T>): Success<*> {
            val parsed = response.body() as Success<*>
            parsed.status = ResponseStatus.Success
            return parsed
        }

        fun <T> Error(response: Response<T>): Error {
            val parsed = Gson().fromJson(response.errorBody()!!.string(), Error::class.java)
            parsed.status = ResponseStatus.Error
            return parsed
        }

        inline fun <reified T : Any> payload (obj: Any): T {
            if (obj is Success<*>) {
                if (obj.data is T) {
                    return obj.data
                }
            }
            throw Exception("Unsafe object cast from ${obj::class.simpleName} to Success<${T::class.simpleName}>")
        }
    }
}