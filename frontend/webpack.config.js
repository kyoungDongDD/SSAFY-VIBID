const path = require("path");
const HtmlPlugin = require("html-webpack-plugin");
const CopyPlugin = require('copy-webpack-plugin')
const { VueLoaderPlugin } = require("vue-loader");

module.exports = {
  resolve: {
    extensions: [".js", ".vue", ".css", "scss"],
    alias: {
      "@": path.resolve(__dirname, "src"),
      assets: path.resolve(__dirname, "src/assets"),
    },
  },
  entry: "./src/main.js",
  output: {
    path: path.resolve(__dirname, "dist"),
    filename: "main.js",
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        use: "vue-loader",
      },
      {
        test: /\.s?css$/,
        use: [
          // 순서 중요!
          "vue-style-loader",
          "style-loader",
          "css-loader",
          "postcss-loader",
          {
            loader: "sass-loader",
            options: {
              additionalData: '@import "@/scss/main";',
            },
          },
        ],
      },
      {
        test: /\.js$/,
        exclude: /node_modules/, // 제외할 경로
        use: ["babel-loader"],
      },
      {
        test: /\.(png|jpe?g|gif|webp|jfif)$/,
        use: [{
          loader: "file-loader",
          options: {
            esModule: false,
          }
        }
        ],
      },
    ]
  },
    // 번들링 후 결과물의 처리 방식 등 다양한 플러그인들을 설정
    plugins: [
      new HtmlPlugin({
        template: './index.html',
      }),
      new CopyPlugin({
        patterns: [
          { from: 'static' }
        ]
      }),
      new VueLoaderPlugin()
    ],
    // 개발 서버 옵션
    devServer: {
      host: 'localhost',
      port: 8090,
      hot: true,
    }
}
