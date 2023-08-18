const { Button } = require("bootstrap");

describe('My Home Page', () => {
  it('Click button', () => {
      cy.get('button').click()
      expect(true).to.equal(true)
    });

    it('home hRef works', () => {
      cy.get('.home-router').click()
      expect(true).to.equal(true)
    });

  });

  beforeEach(function () {
    cy.visit('/')
  });



  

                                                                                                                                                                                                