import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/react'
import { TableContainer } from './TableContainer'

describe('TableContainer', () => {
  it('should render children', () => {
    render(
      <TableContainer>
        <span>Table content</span>
      </TableContainer>
    )
    expect(screen.getByText('Table content')).toBeInTheDocument()
  })

  it('should have table-container class', () => {
    const { container } = render(
      <TableContainer>
        <div>Content</div>
      </TableContainer>
    )
    const wrapper = container.querySelector('.table-container')
    expect(wrapper).toBeInTheDocument()
  })
})