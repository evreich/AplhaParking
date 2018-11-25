const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
 
module.exports = env => {
    const isDevBuild = !(env && env.prod);
    //для исключения предупреждения о невыбранном режиме запуска приожения в asp.net core
    const mode = isDevBuild ? 'development' : 'production';
    const config = {
        mode: mode,
        entry: {
            app: ['./clientApp/app.tsx']
        },
    
        output: {
            filename: `script.js`,
            path: path.resolve(__dirname, 'wwwroot', 'dist'),
            publicPath: '/dist/',
        },
        devtool: 'source-map',
        resolve: {
            modules: [path.resolve(__dirname, 'clientApp'), 'node_modules'],
            extensions: ['.jsx', '.js', ".ts", ".tsx"]
        },
    
        module: {
            rules: [{
                test: /\.(t|j)sx?$/,
                loader: ['ts-loader'],
            },
            {
                test: /\.(sa|sc|c)ss$/,
                //для hmr и обновления css файла после изменения стилей
                use: [isDevBuild ? 'style-loader' : MiniCssExtractPlugin.loader,
                    'css-loader',
                    'sass-loader',
                ]
            },
            ]
        },
    
        plugins: [
            new HtmlWebpackPlugin({
                template: path.resolve(__dirname, 'Views', 'Spa', 'index.cshtml'),
                inject: false
            }),
            new MiniCssExtractPlugin({
                filename: 'bundle.css',
                chunkFilename: 'bundle.css',
            })],
    };
    return [config];

};