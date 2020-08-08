module.exports = {
  devServer: {
    open: false,
    host: 'localhost',
    port: '8082',
    proxy: {
      '/': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: {
          '^/': ''
        }
      }
    }
  }
}
