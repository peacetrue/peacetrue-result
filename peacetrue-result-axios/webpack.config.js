const path = require('path');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const {args2options} = require('peacetrue-js/src/node');
let options = args2options(process.argv, '-pt:');
//生成一个源文件、一个压缩文件和一个source map 文件

/*
prefix: -pt:
plugins|p=html,clean            //指定使用的插件
mode|m=production,development   //指定环境
*/
function formatAlias(options, alias) {
    Object.keys(alias).forEach(key => {
        if (key in options) {
            options[alias[key]] = options[key];
        }
    });
}

formatAlias(options, {p: 'plugins', m: 'mode'});
// console.info(options)
let config = {
    // mode: 'development',
    mode: 'production',
    // mode: `${options.mode || 'development'}`,
    entry: './src/index.js',
    devtool: 'source-map',
    plugins: [],
    devServer: {
        contentBase: './'
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        // filename: `peacetrue.result.axios${options.mode === 'production' ? '.min' : ''}.js`,
        filename: `peacetrue.result.axios.js`,
        library: ['Peace', 'Result', 'Axios'],
        libraryExport: '',
        libraryTarget: 'umd',
        globalObject: 'this',
    },
    externals: {
        'axios': 'axios',
        'qs': {
            root: 'Qs',
            commonjs: 'qs',
            commonjs2: 'qs',
            amd: 'qs'
        },
    }
};

if (options.plugins) {
    if (options.plugins.indexOf('html') > -1) {
        config.plugins.push(
            new HtmlWebpackPlugin({
                title: 'index',
                inject: 'head',
                template: 'test/index.ejs',
                filename: 'index.html'
            }),
        );
    }
    if (options.plugins.indexOf('clean') > -1) {
        config.plugins.push(new CleanWebpackPlugin());
    }

}
module.exports = config;