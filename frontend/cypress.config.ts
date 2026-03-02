import { defineConfig } from "cypress";

export default defineConfig({
  allowCypressEnv: false,

  e2e: {
    baseUrl: "http://localhost:5173",
    specPattern: [
      "cypress/e2e/products.cy.ts",
      "cypress/e2e/raw-materials.cy.ts",
      "cypress/e2e/associations.cy.ts"
    ],
    setupNodeEvents(on, config) { },
  },
});