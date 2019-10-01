/* 处理响应状态码为404的返回 */
let Interceptor = require('../interceptor');
let Result = require('../structure');
let interceptor = new Interceptor();
interceptor.onRejected = function (wrapper) {
    if (!wrapper.response) return;
    //根据响应状态码判断
    if (wrapper.response.status === 404) {
        wrapper.data = new Result(
            "url_not_exists",
            `请求地址'${wrapper.config.url}'不存在`,
            {url: wrapper.config.url}
        );
        return Promise.reject(wrapper);
    }
};

module.exports = interceptor;