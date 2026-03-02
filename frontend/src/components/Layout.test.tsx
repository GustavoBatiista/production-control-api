import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/react'
import { MemoryRouter, Routes, Route } from 'react-router-dom'
import { Layout } from './Layout'

function renderLayout(initialPath = '/') {
  return render(
    <MemoryRouter initialEntries={[initialPath]}>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<div>Home</div>} />
          <Route path="products" element={<div>Products Page</div>} />
        </Route>
      </Routes>
    </MemoryRouter>
  )
}

describe('Layout', () => {
  it('should render navigation links', () => {
    renderLayout()
    expect(screen.getByRole('link', { name: /products/i })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: /raw materials/i })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: /associations/i })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: /producible products/i })).toBeInTheDocument()
  })

  it('should render outlet content', () => {
    renderLayout()
    expect(screen.getByText('Home')).toBeInTheDocument()
  })

  it('should apply active class to current route link', () => {
    render(
      <MemoryRouter initialEntries={['/products']}>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route path="products" element={<div>Products Page</div>} />
          </Route>
        </Routes>
      </MemoryRouter>
    )
    const productsLink = screen.getByRole('link', { name: /products/i })
    expect(productsLink).toHaveClass('active')
  })
})