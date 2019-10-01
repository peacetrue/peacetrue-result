/*
* 重置综合数据
* 扩展配置：
* disableReset：是否禁用重置功能，默认启用重置功能
*/
let Interceptor = require('./interceptor');
let interceptor = new Interceptor();
interceptor.onRejected = function (wrapper) {
    //正常状态，使用负载数据作为参数
    return Promise.resolve(wrapper.config.disableReset ? wrapper : wrapper.data.data);
};
interceptor.onRejected = function (wrapper) {
    //异常状态，使用标准响应数据作为参数
    return Promise.reject(wrapper.config.disableReset ? wrapper : wrapper.data);
};

module.exports = interceptor;
