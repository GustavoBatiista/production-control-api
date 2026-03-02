import { describe, it, expect } from 'vitest'
import { getErrorMessage } from './errorMessage'

describe('getErrorMessage', () => {
  it('should return message from axios error response', () => {
    const err = Object.assign(new Error(), {
      response: { data: { message: 'Product code already exists' } },
    })
    expect(getErrorMessage(err)).toBe('Product code already exists')
  })

  it('should return error from axios error response when message is missing', () => {
    const err = Object.assign(new Error(), {
      response: { data: { error: 'Bad Request' } },
    })
    expect(getErrorMessage(err)).toBe('Bad Request')
  })

  it('should return error message for plain Error', () => {
    expect(getErrorMessage(new Error('Network error'))).toBe('Network error')
  })

  it('should return generic message for unknown error', () => {
    expect(getErrorMessage({})).toBe('An unexpected error occurred.')
  })
})