const { Button } = require("bootstrap");

describe('My Home Page', () => {
    it('Does not do much!', () => {
      expect(true).to.equal(true)
    })

    it('Login', () => {
        cy.get('#username').type('bob@burgers.com')
        cy.get('#password').type('password123{enter}')
    });

  });

  beforeEach(function () {
    cy.visit('/login')
  });



  

                                                                                                                                                                                                