const assert = require('assert');
describe('#Promise.js', () => {
    describe('#then', () => {
        it('undefined onfulfilled to resolve with unchanged data ', () => {
            return Promise.resolve(1)
                .then()
                .then(t => assert.strictEqual(1, t))
        });
        it('onfulfilled return undefined to resolve with undefined data', () => {
            return Promise.resolve(1)
                .then(t => {})
                .then(t => assert.strictEqual(undefined, t), t => assert.fail(t))
        });
        it('onfulfilled return 2 to resolve with 2 data', () => {
            return Promise.resolve(1)
                .then(t => {return 2;})
                .then(t => assert.strictEqual(2, t), t => assert.fail(t))
        });
    });

});
