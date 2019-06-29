const path = require("path");

module.exports = {
    outputDir: 'target/dist',
    assetsDir: 'assets',
    pages: {
        free: {
            // точка входа для страницы
            entry: 'src/pages/free/main.js',
            // исходный шаблон
            template: 'public/pages/free/index.html',
            // в результате будет dist/index.html
            filename: 'pages/free/index.html',
            // когда используется опция title, то <title> в шаблоне
            // должен быть <title><%= htmlWebpackPlugin.options.title %></title>
            title: 'FREE',
            // все фрагменты, добавляемые на этой странице, по умолчанию
            // это извлеченные общий фрагмент и вендорный фрагмент.
            // chunks: ['chunk-vendors', 'chunk-common', 'index']
        },
        user: {
            entry: 'src/pages/user/main.js',
            template: 'public/pages/user/index.html',
            filename: 'pages/user/index.html',
            title: 'USER',
        },
        admin: {
            entry: 'src/pages/admin/main.js',
            template: 'public/pages/admin/index.html',
            filename: 'pages/admin/index.html',
            title: 'ADMIN',
        }
    },
    configureWebpack: {
        resolve: {
            alias: {
                '_free': path.resolve(__dirname, 'src/pages/free')
            }
        },
    }
    // configureWebpack: {
    //     output: {
    //         path: path.resolve(__dirname, './target/dist'),
    //         filename: 'static/[name]/js/[name].[contenthash:8].js',
    //         // filename: (chunkData) => {
    //         //     if (chunkData.chunk.name.includes('free') ||
    //         //         chunkData.chunk.name.includes('admin'))
    //         //         return 'static/[name]/js/[name].[contenthash:8].js';
    //         //     return 'static/js/[name].[contenthash:8].js';
    //         // },
    //         publicPath: '/',
    //         chunkFilename: 'static/js/[name].[contenthash:8].js'
    //     },
    //     module: {
    //         rules: [

    //         ]
    //     }
    // }

}