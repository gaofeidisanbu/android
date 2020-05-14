package com.example.lib.intercepter

interface Intercepter {

    fun intercepter(chain: Chain): Response
}

interface Chain {

    fun proceed(): Response

    fun getRequest(): Request

}

interface Request {

}

interface Response {

}


class IntercepterChain(val intercepter: List<Intercepter>, val index: Int, val chain: Chain) : Chain {


    override fun proceed(): Response {
        val currIntercepter = intercepter[index]
        val nextChain = IntercepterChain(intercepter, index + 1, chain)
        val response = currIntercepter.intercepter(chain)
        return response
    }

    override fun getRequest(): Request {
        return chain.getRequest()
    }


}



