/*
* 处理标准响应数据组件的返回。
* 标准响应数据组件会返回标准的响应数据且始终使用200状态码
*/
let Interceptor = require('../interceptor');
let interceptor = new Interceptor();

/** 是否标准响应数据 */
function isStandard(data) {
    return data
        && data.code
        && data.message;
}

interceptor.onFulfilled = function (wrapper) {
    let data = wrapper.data;
    if (!isStandard(data)) return;
    if (data.code === 'success') {
        //如果操作成功
        return Promise.resolve(wrapper);
    }
    //如果操作失败
    return Promise.reject(wrapper);
};

module.exports = interceptor;