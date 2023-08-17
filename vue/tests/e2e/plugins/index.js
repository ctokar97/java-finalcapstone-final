module.exports = (on, config) => {

    return Object.assign({}, config, {
        integrationFolder: 'tests/e2e/specs'
    })
}