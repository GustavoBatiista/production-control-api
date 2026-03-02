import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter } from 'react-router-dom'
import { ProducibleProductsPage } from './ProducibleProductsPage'
import * as api from '../services/api'

vi.mock('../services/api')

const mockProducibleProducts = [
  { productId: 1, productCode: 'P001', productName: 'Cement', price: 55, maxQuantity: 100 },
]

describe('ProducibleProductsPage - API integration', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('should display producible products from API', async () => {
    vi.mocked(api.getProducibleProducts).mockResolvedValue({ data: mockProducibleProducts })

    render(
      <MemoryRouter>
        <ProducibleProductsPage />
      </MemoryRouter>
    )

    await waitFor(() => expect(api.getProducibleProducts).toHaveBeenCalledTimes(1))

    expect(screen.getByText('P001')).toBeInTheDocument()
    expect(screen.getByText('Cement')).toBeInTheDocument()
    const rows = screen.getAllByRole('row')
    expect(rows.some(row => row.textContent?.includes('55'))).toBe(true)
    expect(rows.some(row => row.textContent?.includes('100'))).toBe(true)
  })

  it('should call API again when clicking Refresh', async () => {
    vi.mocked(api.getProducibleProducts).mockResolvedValue({ data: [] })

    const user = userEvent.setup()
    render(
      <MemoryRouter>
        <ProducibleProductsPage />
      </MemoryRouter>
    )

    await waitFor(() => expect(api.getProducibleProducts).toHaveBeenCalledTimes(1))
    await user.click(screen.getByRole('button', { name: /refresh/i }))
    await waitFor(() => expect(api.getProducibleProducts).toHaveBeenCalledTimes(2))
  })
})