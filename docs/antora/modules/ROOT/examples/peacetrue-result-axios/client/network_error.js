/* 处理 网络异常的 返回 */
let Interceptor = require('../interceptor');
let Result = require('../structure');
let interceptor = new Interceptor();

interceptor.onRejected = function (wrapper) {
    if (wrapper.code === 'ECONNREFUSED'//NODE 环境
        || wrapper.message === "Network Error"//浏览器环境
    ) {
        wrapper.data = new Result('network_error', '网络异常');
        return Promise.reject(wrapper);
    }
};

module.exports = interceptor;
