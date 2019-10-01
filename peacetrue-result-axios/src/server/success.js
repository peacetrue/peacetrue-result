/* 处理 一般性正常的 返回 */
let Interceptor = require('../interceptor');
let Result = require('../structure');
let interceptor = new Interceptor();

interceptor.onFulfilled = function (wrapper) {
    //此处无条件判断，必须位于子拦截器的末尾
    wrapper.data = new Result('success', '操作成功!', wrapper.data);
    return Promise.resolve(wrapper);
};

module.exports = interceptor;