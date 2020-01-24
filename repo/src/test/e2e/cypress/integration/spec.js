it('should contain welcome message', () => {
  cy.visit('/')

  cy.get('body')
    .should('contain', 'It worked!')
})

  it('should fail', () => {
  cy.visit('/')

  cy.get('body')
    .should('contain', 'It failed!')
})
