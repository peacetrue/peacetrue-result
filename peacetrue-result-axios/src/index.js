let axios = require('axios');

let ParamStringify = require('./param_stringify');
ParamStringify.inject(axios);

let interceptors = {};
let standardizedInterceptor = require('./standardized');//响应数据标准化拦截器
standardizedInterceptor.register(require('./client/network_error'));
standardizedInterceptor.register(require('./client/timeout'));
standardizedInterceptor.register(require('./client/url_not_exists'));
standardizedInterceptor.register(require('./server/standard'));
standardizedInterceptor.register(require('./server/spring_boot'));
standardizedInterceptor.register(require('./server/success'));
interceptors.standardized = standardizedInterceptor.inject(axios);
interceptors.prompt = require('./prompt').inject(axios);//默认提示拦截器
interceptors.reset = require('./reset').inject(axios);//重置综合信息拦截器

module.exports = {axios, interceptors};