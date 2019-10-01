const assert = require('assert');
const axios = require('axios').create();
require('../src/server/spring_boot').inject(axios);
const interceptor = require('../src/prompt').inject(axios);

//此测试需要先运行：NormalResultApplication
global.alert = (wrapper) => {console.info(wrapper)};

describe('#prompt.js', () => {

    describe('#onFulfilled', () => {
        it('10/10 应该正常返回 1', () => {
            interceptor.successPrompt = function (wrapper) {
                assert.strictEqual(wrapper.data, 1);
            };
            interceptor.failurePrompt = function (wrapper) {
                assert.fail();
            }
        });
        return axios.get('http://localhost:8081/calculate/divide?divisor=10&dividend=10');
    });

    describe('#onRejected', () => {

        it('不传参数应该返回 400', () => {
            interceptor.successPrompt = function (wrapper) {
                assert.fail();
            };
            interceptor.failurePrompt = function (wrapper) {
                assert.strictEqual(400, wrapper.data.code);
            };
            return axios.get('http://localhost:8081/calculate/divide')
                .catch(() => {});//避免未catch异常导致失败
        });

        it('传递非正确格式的参数应该返回 400', () => {
            interceptor.successPrompt = function (wrapper) {
                assert.fail();
            };
            interceptor.failurePrompt = function (wrapper) {
                assert.strictEqual(400, wrapper.data.code);
            };
            return axios.get('http://localhost:8081/calculate/divide?divisor=10.1&dividend=10.1')
                .catch(() => {});//避免未catch异常导致失败;
        });

        it('服务端异常应该返回 500', () => {
            interceptor.successPrompt = function (wrapper) {
                assert.fail();
            };
            interceptor.failurePrompt = function (wrapper) {
                assert.strictEqual(500, wrapper.data.code);
            };
            return axios.get('http://localhost:8081/calculate/divide?divisor=10&dividend=0')
                .catch(() => {});//避免未catch异常导致失败
        });
    });

});
