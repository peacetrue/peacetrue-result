/*
* 默认提示拦截器，用于提供一种默认的提示方式。
* 允许在请求时通过配置禁用默认提示。
* 扩展配置：
* disableSuccessPrompt：是否禁用成功提示，默认启用成功提示，简称：dsp
* disableFailurePrompt：是否禁用异常提示，默认启用异常提示，简称：dfp
*/
let Interceptor = require('./interceptor');
let interceptor = new Interceptor();

interceptor.onFulfilled = function (wrapper) {
    //如果没有明确禁用成功提示
    !(wrapper.disableSuccessPrompt === true || wrapper.dsp === true)
    && this.successPrompt(wrapper);
    return Promise.resolve(wrapper);
};

/**默认通过 alert 提示，实际使用中需重写此方法*/
interceptor.successPrompt = function (wrapper) {
    alert(this.successMessage(wrapper));
};

interceptor.successMessage = function (wrapper) {
    return '操作成功';
};

interceptor.onRejected = function (wrapper) {
    //如果没有明确禁用失败提示
    !(wrapper.disableFailurePrompt === true || wrapper.dfp === true)
    && this.failurePrompt(wrapper);
    return Promise.reject(wrapper);
};

/**默认通过 alert 提示，实际使用中需重写此方法*/
interceptor.failurePrompt = function (wrapper) {
    alert(`操作失败!${this.failureMessage(wrapper)}`);
};

interceptor.failureMessage = function (wrapper) {
    let data = wrapper.data;
    let message = data.message;
    if (data.code === 'errors') {
        data.data.forEach(items => {
            message += "<br>" + items.message;
        });
    } else if (data.code === 'failure' && data.data) {
        message += `(${data.data})`;
    }
    return message;
};

module.exports = interceptor;