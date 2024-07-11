import React from 'react'
import Header from './header'
import { render, screen } from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect';



test('renders header with text and image', () => {
    render(<Header />)

    expect(screen.getByText('Leave Application Portal')).toBeInTheDocument()

    const logo = screen.getByAltText('Logo')
    expect(logo).toBeInTheDocument()
    expect(logo).toHaveAttribute('src', 'https://images.g2crowd.com/uploads/product/image/social_landscape/social_landscape_153a7716444063dc2d7e94cdffeabf8f/avanseus.png')
    expect(logo).toHaveClass('img')
    expect(logo).toHaveAttribute('align', 'left')
})

