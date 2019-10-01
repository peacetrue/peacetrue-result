/**拦截器的结构*/
class Interceptor {
    /**正常状态下的处理函数*/
    onFulfilled(wrapper) {}

    /**异常状态下的处理函数*/
    onRejected(wrapper) {}
}

/** 注入到 axios 的拦截器中 */
Interceptor.prototype.interceptorId = new Map();
Interceptor.prototype.inject = function (axios, eject = false) {
    if (eject === true) {
        if (this.interceptorId.has(axios)) {
            axios.interceptors.response.eject(this.interceptorId.get(axios));
            this.interceptorId.delete(axios);
        }
    } else {
        if (!this.interceptorId.has(axios)) {
            const interceptorId = axios.interceptors.response.use(
                Interceptor.prototype.onFulfilled === this.onFulfilled ? null : (wrapper) => this.onFulfilled(wrapper),
                Interceptor.prototype.onRejected === this.onRejected ? null : (wrapper) => this.onRejected(wrapper)
            );
            this.interceptorId.set(axios, interceptorId);
        }
    }
    return this;
};

module.exports = Interceptor;