import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import { RawMaterialsPage } from './RawMaterialsPage'
import * as api from '../services/api'

vi.mock('../services/api')

const mockRawMaterials = [
  { id: 1, code: 'RM001', name: 'Raw 1', stockQuantity: 100 },
  { id: 2, code: 'RM002', name: 'Raw 2', stockQuantity: 200 },
]

describe('RawMaterialsPage - API integration', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('should display raw materials from API in table', async () => {
    vi.mocked(api.getRawMaterials).mockResolvedValue({ data: mockRawMaterials })

    render(
      <MemoryRouter>
        <RawMaterialsPage />
      </MemoryRouter>
    )

    await waitFor(() => expect(api.getRawMaterials).toHaveBeenCalledTimes(1))

    expect(screen.getByText('RM001')).toBeInTheDocument()
    expect(screen.getByText('Raw 1')).toBeInTheDocument()
    expect(screen.getByText('RM002')).toBeInTheDocument()
  })

  it('should display error when API fails', async () => {
    vi.mocked(api.getRawMaterials).mockRejectedValue(
      Object.assign(new Error(), { response: { data: { message: 'Server error' } } })
    )

    render(
      <MemoryRouter>
        <RawMaterialsPage />
      </MemoryRouter>
    )

    await waitFor(() => expect(screen.getByText('Server error')).toBeInTheDocument())
  })
})