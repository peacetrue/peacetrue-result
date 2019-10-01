let Qs = require('qs');

/** 序列化参数，符合 spring mvc 数据绑定要求 */
module.exports = {
    /**处理url上的参数*/
    paramsSerializer(params) {
        return Qs.stringify(params, {arrayFormat: 'repeat', serializeDate: (d) => d.getTime(), allowDots: true, skipNulls: true,});
    },
    /**处理请求体中的参数*/
    transformRequest(data, headers) {
        return Qs.stringify(data, {arrayFormat: 'repeat', serializeDate: (d) => d.getTime(), allowDots: true, skipNulls: true,});
    },
    /**注入到axios中*/
    inject(axios) {
        axios.defaults.paramsSerializer = this.paramsSerializer;
        axios.defaults.transformRequest.unshift(this.transformRequest);
    }
};