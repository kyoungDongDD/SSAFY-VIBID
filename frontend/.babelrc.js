module.exports = {
  presets: ['@babel/preset-env'],
  plugins: [
    ['@babel/plugin-transform-runtime', process.env.NODE_ENV === 'production' ? 'transform-remove-console' : undefined]
  ]
}