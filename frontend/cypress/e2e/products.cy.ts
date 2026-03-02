describe('Producible Products', () => {
  beforeEach(() => {
    cy.visit('/producible-products');
  });

  it('should display Producible Products page', () => {
    cy.contains('h1', 'Producible Products').should('be.visible');
    cy.contains('Refresh').should('be.visible');
  });

  it('should display table headers', () => {
    cy.contains('th', 'Code').should('be.visible');
    cy.contains('th', 'Name').should('be.visible');
    cy.contains('th', 'Price').should('be.visible');
    cy.contains('th', 'Max Quantity').should('be.visible');
  });
});