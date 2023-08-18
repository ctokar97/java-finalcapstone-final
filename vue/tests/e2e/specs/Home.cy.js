const { Button } = require("bootstrap");

describe('My Home Page', () => {
    
    it('Login', () => {
      cy.get('#username').type('ruslan')
      cy.get('#password').type('ruslan{enter}')
      expect(true).to.equal(true)
  });

    it('', () => {
     
    });


  });

  beforeEach(function () {
    cy.visit('/home')
  });

