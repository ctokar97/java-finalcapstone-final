const { Button } = require("bootstrap");

describe('My Home Page', () => {
    it('Does not do much!', () => {
      expect(true).to.equal(true)
    })

    it('Click button', () => {
      cy.wait(1000);
      cy.get('button').click()
    });


  });

  beforeEach(function () {
    cy.visit('/login')
  });



  

                                                                                                                                                                                                