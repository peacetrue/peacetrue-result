const assert = require('assert');
const axios = require('axios').create();
const springBoot = require('../src/server/spring_boot');
springBoot.inject(axios);

//此测试需要运行：NormalResultApplication

describe('#server/spring_boot.js', () => {

    describe('#onFulfilled', () => {
        it('10/10 应该正常返回 1', () => {
            return axios.get('http://localhost:8081/calculate/divide?divisor=10&dividend=10')
                .then(wrapper => {
                    assert.strictEqual(wrapper.data, 1);
                }, wrapper => {
                    assert.fail();
                });
        });
    });

    describe('#onRejected', () => {

        it('不传参数应该返回 400', () => {
            return axios.get('http://localhost:8081/calculate/divide')
                .then(wrapper => {
                    assert.fail();
                }, wrapper => {
                    assert.strictEqual(400, wrapper.data.code);
                });
        });

        it('传递非正确格式的参数应该返回 400', () => {
            return axios.get('http://localhost:8081/calculate/divide?divisor=10.1&dividend=10.1')
                .then(wrapper => {
                    assert.fail();
                }, wrapper => {
                    assert.strictEqual(400, wrapper.data.code);
                });
        });

        it('服务端异常应该返回 500', () => {
            return axios.get('http://localhost:8081/calculate/divide?divisor=10&dividend=0')
                .then(wrapper => {
                    assert.fail();
                }, wrapper => {
                    assert.strictEqual(500, wrapper.data.code);
                });
        });
    });
});
