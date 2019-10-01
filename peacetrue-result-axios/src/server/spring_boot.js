/* 处理 spring-boot 的返回 */
let Interceptor = require('../interceptor');
let Result = require('../structure');
let interceptor = new Interceptor();

/** 是否 spring-boot basic error controller 返回的数据格式*/
function isSpringBoot(data) {
    return data
        && data.timestamp
        && data.status
        && data.error
        && data.message
        && data.path;
}

interceptor.onRejected = function (wrapper) {
    if (!wrapper.response) return;
    let data = wrapper.response.data;
    if (isSpringBoot(data)) {
        //响应状态码作为标准响应数据的编码
        wrapper.data = new Result(data.status, data.message);
        return Promise.reject(wrapper);
    }
};

module.exports = interceptor;