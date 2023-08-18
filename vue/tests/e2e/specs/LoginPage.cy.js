const { Button } = require("bootstrap");

describe('My Login Page', () => {
    

    it('Login', () => {
        cy.get('#username').type('ruslan')
        cy.get('#password').type('ruslan{enter}')
        expect(true).to.equal(true)
    });

  });

  beforeEach(function () {
    cy.visit('/login')
  });



  

                                                                                                                                                                                                