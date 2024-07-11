import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect'; // for better assertions
import ChangePassword from './changePassword';
import { mockAxios } from './mockAxios';

// Mock the axios module
jest.mock('axios');

//const mockAxios = axios as jest.Mocked<typeof axios>;

describe('ChangePassword Component', () => {
    const userId = 'AT-101';

    beforeEach(() => {
        render(<ChangePassword userid={userId} />);
    });

    test('renders the component with inputs and button', () => {
        expect(screen.getByPlaceholderText('Current password')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('New password')).toBeInTheDocument();
        expect(screen.getByText('Update')).toBeInTheDocument();
    });

    test('displays error message when fields are empty', async () => {
        fireEvent.click(screen.getByText('Update'));
        await waitFor(() => expect(screen.getByText('All fields are mandatory')).toBeInTheDocument());
    });

    test('submits the form with correct input', async () => {
        // Set up the mock response
        mockAxios.put.mockResolvedValue({ data: { message: 'Password changed' } });

        fireEvent.change(screen.getByPlaceholderText('Current password'), { target: { name: 'current', value: 'currentPassword' } });
        fireEvent.change(screen.getByPlaceholderText('New password'), { target: { name: 'new', value: 'newPassword' } });

        fireEvent.click(screen.getByText('Update'));

        await waitFor(() => {
            expect(mockAxios.put).toHaveBeenCalledWith(`http://localhost:8080/api/changePassword/${userId}/currentPassword/newPassword`);
            expect(screen.getByText('Password changed successfully')).toBeInTheDocument();
        });
    });

    test('handles wrong password response', async () => {
        // Set up the mock response
        mockAxios.put.mockResolvedValue({ data: { message: 'Wrong password' } });

        fireEvent.change(screen.getByPlaceholderText('Current password'), { target: { name: 'current', value: 'wrongPassword' } });
        fireEvent.change(screen.getByPlaceholderText('New password'), { target: { name: 'new', value: 'newPassword' } });

        fireEvent.click(screen.getByText('Update'));

        await waitFor(() => {
            expect(mockAxios.put).toHaveBeenCalledWith(`http://localhost:8080/api/changePassword/${userId}/wrongPassword/newPassword`);
            expect(screen.getByText('Current password not matching')).toBeInTheDocument();
        });
    });
});
