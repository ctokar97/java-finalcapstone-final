module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/essential',
    'eslint:recommended',
  ],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'import/no-named-as-default-member': 'off',
    'no-undef': 'off'
  },
  parserOptions: {
    parser: '@babel/eslint-parser'
  },
  settings: {
    'import/resolver': {
      alias: {
        map: [['@', './src']],
        extensions: [".vue", ".js", ".cy.js"]
      }
    }
  }
}
