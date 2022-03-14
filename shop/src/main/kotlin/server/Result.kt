package server

import rx.Observable
import io.netty.handler.codec.http.HttpResponseStatus

data class Result(val status: HttpResponseStatus, val message: Observable<String>) {
    companion object {
        fun error(message: String): Result {
            return Result(HttpResponseStatus.BAD_REQUEST, Observable.just(message))
        }
    }
}