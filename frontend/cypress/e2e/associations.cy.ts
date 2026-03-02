describe('Associations CRUD', () => {
    beforeEach(() => {
      cy.visit('/product-materials');
    });
  
    it('should display Associations page', () => {
      cy.contains('h1', 'Associations').should('be.visible');
      cy.contains('New Association').should('be.visible');
    });
  
    it('should open form when clicking New Association', () => {
      cy.contains('New Association').click();
      cy.get('select').first().should('be.visible');
      cy.get('input[placeholder="Quantity Required"]').should('be.visible');
    });
  });