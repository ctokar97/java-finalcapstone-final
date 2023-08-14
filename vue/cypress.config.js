const { defineConfig } = require('cypress')

module.exports = defineConfig({
  defaultCommandTimeout: 1500,
  screenshotOnRunFailure: false,
  video: false,
  e2e: {
    // We've imported your old cypress plugins here.
    // You may want to clean this up later by importing these.
    setupNodeEvents(on, config) {
      return require('./tests/e2e/plugins/index.js')(on, config)
    },
    supportFile: false,
    specPattern: 'tests/e2e/specs/**/*.cy.{js,jsx,ts,tsx}',
  },
})
