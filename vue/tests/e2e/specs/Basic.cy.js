describe('My Home Page', () => {
    it('Does not do much!', () => {
      expect(true).to.equal(true)
    })

    it('Displays at least one party', () => {
      cy.wait(5000);
      cy.get('.display-container.party').should('have.length.of.at.least', 1);

    });

    it('My Test', () => {

    })
  });

  beforeEach(function () {
    cy.visit('/')
  });



  

                                                                                                                                                                                                