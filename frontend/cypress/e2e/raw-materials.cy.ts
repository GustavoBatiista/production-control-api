describe('Raw Materials CRUD', () => {
    beforeEach(() => {
      cy.visit('/raw-materials');
    });
  
    it('should display Raw Materials page', () => {
      cy.contains('h1', 'Raw Materials').should('be.visible');
      cy.contains('New Raw Material').should('be.visible');
    });
  
    it('should open form when clicking New Raw Material', () => {
      cy.contains('New Raw Material').click();
      cy.get('input[placeholder="Code"]').should('be.visible');
      cy.get('input[placeholder="Name"]').should('be.visible');
      cy.get('input[placeholder="Stock Quantity"]').should('be.visible');
    });
  
    it('should create a raw material', () => {
      cy.contains('New Raw Material').click();
      cy.get('input[placeholder="Code"]').type('CYP-RM001');
      cy.get('input[placeholder="Name"]').type('Cypress Test Raw Material');
      cy.get('input[placeholder="Stock Quantity"]').type('500');
      cy.get('button[type="submit"]').click();
      cy.contains('CYP-RM001').should('be.visible');
    });
  });