import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter } from 'react-router-dom'
import { ProductsPage } from './ProductsPage'
import * as api from '../services/api'

vi.mock('../services/api')

const mockProducts = [
  { id: 1, code: 'P001', name: 'Product 1', price: 10 },
  { id: 2, code: 'P002', name: 'Product 2', price: 20 },
]

describe('ProductsPage', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    vi.mocked(api.getProducts).mockResolvedValue({ data: [] })
  })

  it('should display Products title', async () => {
    render(
      <MemoryRouter>
        <ProductsPage />
      </MemoryRouter>
    )
    await waitFor(() => {
      expect(screen.getByRole('heading', { name: /products/i })).toBeInTheDocument()
    })
  })

  it('should display New Product button', async () => {
    render(
      <MemoryRouter>
        <ProductsPage />
      </MemoryRouter>
    )
    await waitFor(() => {
      expect(screen.getByRole('button', { name: /new product/i })).toBeInTheDocument()
    })
  })

  it('should open form when clicking New Product', async () => {
    const user = userEvent.setup()
    render(
      <MemoryRouter>
        <ProductsPage />
      </MemoryRouter>
    )
    await waitFor(() => {
      expect(screen.getByRole('button', { name: /new product/i })).toBeInTheDocument()
    })
    await user.click(screen.getByRole('button', { name: /new product/i }))
    expect(screen.getByPlaceholderText('Code')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Name')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Price')).toBeInTheDocument()
  })

  it('should display products from API in table', async () => {
    vi.mocked(api.getProducts).mockResolvedValue({ data: mockProducts })

    render(
      <MemoryRouter>
        <ProductsPage />
      </MemoryRouter>
    )

    await waitFor(() => {
      expect(api.getProducts).toHaveBeenCalledTimes(1)
    })

    expect(screen.getByText('P001')).toBeInTheDocument()
    expect(screen.getByText('Product 1')).toBeInTheDocument()
    expect(screen.getByText('P002')).toBeInTheDocument()
    expect(screen.getByText('Product 2')).toBeInTheDocument()
  })

  it('should display error message when API fails', async () => {
    vi.mocked(api.getProducts).mockRejectedValue(
      Object.assign(new Error(), { response: { data: { message: 'Network error' } } })
    )

    render(
      <MemoryRouter>
        <ProductsPage />
      </MemoryRouter>
    )

    await waitFor(() => {
      expect(screen.getByText('Network error')).toBeInTheDocument()
    })
  })

  it('should call createProduct and refresh when submitting form', async () => {
    vi.mocked(api.getProducts)
      .mockResolvedValueOnce({ data: [] })
      .mockResolvedValueOnce({ data: [{ id: 1, code: 'NEW', name: 'New Product', price: 99 }] })
    vi.mocked(api.createProduct).mockResolvedValue({ data: { id: 1, code: 'NEW', name: 'New Product', price: 99 } } as never)

    const user = userEvent.setup()
    render(
      <MemoryRouter>
        <ProductsPage />
      </MemoryRouter>
    )

    await waitFor(() => expect(screen.getByRole('button', { name: /new product/i })).toBeInTheDocument())
    await user.click(screen.getByRole('button', { name: /new product/i }))

    await waitFor(() => expect(screen.getByPlaceholderText('Code')).toBeInTheDocument())

    const codeInput = screen.getByPlaceholderText('Code')
    const nameInput = screen.getByPlaceholderText('Name')
    const priceInput = screen.getByPlaceholderText('Price')

    await user.clear(codeInput)
    await user.type(codeInput, 'NEW')
    await user.clear(nameInput)
    await user.type(nameInput, 'New Product')
    await user.clear(priceInput)
    await user.type(priceInput, '99')

    await user.click(screen.getByRole('button', { name: /create/i }))

    await waitFor(() => {
      expect(api.createProduct).toHaveBeenCalledWith({ code: 'NEW', name: 'New Product', price: 99 })
    })
    await waitFor(() => {
      expect(screen.getByText('NEW')).toBeInTheDocument()
    })
  })
})