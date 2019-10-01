/*
 * 标准响应数据拦截器用于将综合数据上携带的非标准的响应数据标准化。
 * 标准化拦截器使用一组 子拦截器 处理综合数据，
 * 遇到第一个能够处理的 拦截器 后停止，
 * 如果没有找到能够处理的拦截器抛出异常。
 * 子拦截器必须将标准响应数据设置在综合数据的data属性上
 */
let Interceptor = require('./interceptor');
let interceptor = new Interceptor();
interceptor.interceptors = [];
/**注册子拦截器*/
interceptor.register = function (interceptor) {
    this.interceptors.push(interceptor);
    return this;
};
interceptor.onFulfilled = function (wrapper) {
    for (let interceptor of this.interceptors) {
        if (!interceptor.onFulfilled) continue;
        let promise = interceptor.onFulfilled(wrapper);
        if (promise instanceof Promise) return promise;
    }
    throw new Error(`没有可处理的 interceptor.onFulfilled`)
};

interceptor.onRejected = function (wrapper) {
    for (let interceptor of this.interceptors) {
        if (!interceptor.onRejected) continue;
        let promise = interceptor.onRejected(wrapper);
        if (promise instanceof Promise) return promise;
    }
    throw new Error(`没有可处理的 interceptor.onRejected`)
};

module.exports = interceptor;