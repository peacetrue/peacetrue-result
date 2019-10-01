/* 处理 请求超时的 返回 */
let Interceptor = require('../interceptor');
let Result = require('../structure');
let interceptor = new Interceptor();

interceptor.onRejected = function (wrapper) {
    if (wrapper.message && wrapper.message.startsWith("timeout of ")) {
        let timeout = wrapper.config.timeout / 1000;
        wrapper.data = new Result('timeout', `请求时长已超过最大限制\'${timeout}\'秒`, {timeout: timeout});
        return Promise.reject(wrapper);
    }
};

module.exports = interceptor;